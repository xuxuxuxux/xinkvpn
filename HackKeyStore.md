Android系统通过权限机制对系统服务进行保护，提高系统的安全性，但同时也给xinkvpn这样的第三方应用设置了障碍（[浏览详情](http://code.google.com/p/xinkvpn/wiki/VpnKeystoreIssue_zh_CN)）。
<br>为了绕过这个障碍，我们需要替换掉系统的keystore进程（ <b>需要root</b> ）。<br>
<br>
<h3>替换keystore服务</h3>
<ol><li>下载<a href='http://code.google.com/p/xinkvpn/downloads/detail?name=keystore&can=2&q='>keystore程序</a>
<blockquote>此程序是基于android2.3的源码进行修改和编译得到的，绝无后门，源码已经上传到xinkvpn的代码库（hack-keystore分支，keystore.c），请放心使用<br>
</blockquote></li><li>并上传至手机（如果直接下载到手机，请略过这一步）：<br>
<pre><code>adb push keystore /sdcard/tmp<br>
</code></pre>
</li><li>挂载/system为可写<br>
<ul><li>在pc上通过adb挂载：<br>
<pre><code>adb remount<br>
</code></pre>
</li><li>或在shell中执行命令：<br>
<pre><code>adb shell<br>
su<br>
mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system <br>
</code></pre>
</li></ul></li><li>备份并替换keystore<br>
<pre><code>cd /system/bin<br>
mv keystore keystore_orig<br>
cp /sdcard/tmp/keystore .<br>
</code></pre>
</li><li>重启手机</li></ol>

<h3>检测替换是否成功</h3>
<ol><li>打开xinkvpn应用<br>
</li><li>选择“破解”菜单，进行检测<br>
<blockquote>如果成功，会提示“keystore服务替换成功”，反之则会出现以下提示：<br>
<img src='http://xinkvpn.googlecode.com/files/snap20110529_173920-1.jpg' /></blockquote></li></ol>

<h2>FAQ</h2>
<ul><li>我的手机没有root怎么办？<br>
<blockquote>如果您只需要使用PPTP VPN，则没有关系，仍然可以正常使用xinkvpn，勾选上图中的“不再提示”可以避免这烦人的对话框再次出现。<br>
只有*使用L2TP密钥和IPSec预共享密钥*的机油才需要替换keystore（密钥正是存储在keystore中）<br>
</blockquote></li><li>下载的keystore程序适用于哪些ROM?<br>
<blockquote>作者财力和精力有限，只有一部机器，因此测试的范围有限:( 请大家谅解<br>本人的机器是HTC DHD，在原装的HTC Sense（2.2）以及CM7（2.3.3）上做过测试，keystore工作正常<br>PS. 欢迎拥有不同机器不同ROM的机油贡献问题，最好直接贡献补丁:)