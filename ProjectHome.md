## 简介 Introduction ##
如果你曾经使用过Android原生的VPN设置应用，一定曾为之抓狂...每次连接都要重新输入密码，要知道密码通常都是不怎么好输入滴...<br>
而通过XinkVpn桌面小工具，将可以实现一键连接/关闭VPN，是时候把Android原生应用扔一边去了。<br>
<br>
XinkVpn is an Android app with which you can switch VPN connectivity quickly, provides an alternative for the native VPN setting app. <br>
Android native VPN settings won't remember passwords, you'll have to enter the password(long and hard to input:() each time you connect.<br>
XinVpn shall help you making it much more easy.<br>
<br>
<h3>特性</h3>
<ul><li>支持Android 2.x (2.3.3经过HTC真机测试，2.1/2.2使用模拟器测试)<br>
</li><li>支持VPN类型：PPTP，L2TP，L2TP/IPSec PSK，暂不支持基于证书的L2TP/IPSec以及OpenVPN<br>
</li><li>支持桌面小工具一键开关VPN<br>
详情请参考<a href='usage.md'>使用说明</a>。</li></ul>

<h3>Features</h3>
<ul><li>Supports for Android 2.x (2.3.3 tested with HTC device, 2.1/2.2 tested with simulators)<br>
</li><li>Supported VPN Types: PPTP, L2TP, L2TP/IPSec PSK<br>
</li><li>Supports App Widgets<br>
Please find <a href='usage.md'>details here</a>.<br>
<br>
</li><li><b>XinkVpn并不提供VPN服务</b>，有此需要的同学还请到别处寻觅。。。<br>
</li><li><b>XinkVpn does not offer VPN services</b>, it's the wrong place if you're finding one...<br>
<br></li></ul>

<hr />

<h3>FAQ</h3>
<ul><li>首次使用XinkVpn（或刷机后），很可能会碰到这个问题，请参考：VpnStatePersistFailure。<br>
If it's your first time using XinkVpn (or after flushing a new ROM), you probably encounter this problem, please try this solution: VpnStatePersistFailure.</li></ul>

<ul><li>StreamCrypto.java not found<br>
The raw key for AES encryption is in this file, so it should not be check in SCM, in the next version, I'll move out the key, and commit the source file.</li></ul>

You can download a copy here: <a href='http://xinkvpn.googlecode.com/issues/attachment?aid=110001000&name=StreamCrypto.java&token=MrUKbaaDo0XRj8e-8eKhqQ5HMXg%3A1332583907098'>StreamCrypto.java</a>, don't forget to change the RAWKEY to yours (any 16 bytes).