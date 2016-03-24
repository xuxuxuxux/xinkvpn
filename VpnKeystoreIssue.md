## 关于L2TP VPN连接问题的详细说明 ##
问题的现象是这样，当L2TP VPN启用了密钥，或使用IPSec预共享密钥时，无法连接成功，但使用系统的VPN配置则没有问题。

仔细分析了日志以后，发现了以下信息：<br>
<pre><code>I/keystore( 1202): uid: 1016 action: g -&gt; 7 state: 1 -&gt; 1 retry: 4<br>
E/racoon (10550): couldn't find the pskey for 192.168.1.2.<br>
E/racoon (10550): failed to process packet.<br>
E/racoon (10550): phase1 negotiation failed.<br>
</code></pre>
而使用系统的VPN配置连接成功时，则是这样：<br>
<pre><code>I/keystore( 1202): uid: 1016 action: g -&gt; 1 state: 1 -&gt; 1 retry: 4<br>
I/racoon (10885): ISAKMP-SA established 192.168.1.3[500]-192.168.1.2[500] spi:82b084163a442d0a:810ac642bff108fd<br>
I/racoon (10885): initiate new phase 2 negotiation: 192.168.1.3[500]&lt;=&gt;192.168.1.2[500]<br>
</code></pre>
由此可知问题根源是，keystore中无法找到指定密钥导致了SA阶段协商失败。<br>
<h3>Android的keystore服务</h3>
但是我很快发现，即使增加了储存密钥的处理（通过系统KeyStore API），系统仍然会打印出上述错误信息。<br>
好吧，只能去看android系统的相关代码了，在浏览了keystore的实现以后，我找到了答案：<br>
<pre><code>static int8_t insert()<br>
{<br>
    char name[NAME_MAX];<br>
    int n = sprintf(name, "%u_", uid);<br>
    encode_key(&amp;name[n], params[0].value, params[0].length);<br>
    ...<br>
}<br>
...<br>
static int8_t get()<br>
{<br>
    char name[NAME_MAX];<br>
    int n = sprintf(name, "%u_", uid);<br>
    encode_key(&amp;name[n], params[0].value, params[0].length);<br>
    ...<br>
}<br>
</code></pre>
在keystore的insert和get函数中可以看到，密钥的索引是包含uid的，即发出此次请求的进程的用户标示。<br>
Android系统的VPN服务在SA协商阶段会到keystore获取密钥（get），以下这段代码显示，keystore会将VPN进程映射为System的uid，也就是说，VPN服务只能够识别索引中携带System uid的密钥。<br>
<pre><code>static struct user {<br>
    uid_t uid;<br>
    uid_t euid;<br>
    uint32_t perms;<br>
} users[] = {<br>
    {AID_SYSTEM,   ~0,         ~GET},<br>
    {AID_VPN,      AID_SYSTEM, GET},<br>
   ...<br>
    {~0,           ~0,         TEST | GET | INSERT | DELETE | EXIST | SAW},<br>
};<br>
</code></pre>
<h3>解决办法</h3>
要解决这个问题，有两种方案：<br>
<ul><li>将应用签名为System，这样XinkVpn存储至keystore的密钥就能够被VPN服务识别了<br>
</li></ul><blockquote>XinkVpn2.0就是采取了这个方案，但是副作用也相当明显，相信大家也看到我的相关说明了:(<br>
</blockquote><ul><li>另一方案就是把系统的keystore进程给替换掉，把system uid的限制给去掉<br>
</li></ul><blockquote>当然这样做需要root权限，我正在尝试这个方案，我想到时这个版本就叫3.0了，应用签名又要改回去了，呵呵</blockquote>

<blockquote>这个东西啊，还是要Android系统或者厂商来做，通过第三方应用就只能兜着圈走了。Android应该向iPhone学习，iPhone的VPN是提供了快捷开关的。