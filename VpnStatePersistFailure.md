# 问题 Issue #
刷CM 7.0.3后发现VPN无法连接，logcat打印以下信息：<br>
After upgrade my phone to CM 7.0.3, the app failed to make VPN connections. Error found in logs:<br>
<br>
<pre><code>E/VpnService( 2501): onError()<br>
E/VpnService( 2501): java.io.FileNotFoundException: /data/data/misc/vpn/.states (No such file or directory)<br>
E/VpnService( 2501):    at org.apache.harmony.luni.platform.OSFileSystem.open(Native Method)<br>
E/VpnService( 2501):    at dalvik.system.BlockGuard$WrappedFileSystem.open(BlockGuard.java:232)<br>
E/VpnService( 2501):    at java.io.FileOutputStream.&lt;init&gt;(FileOutputStream.java:94)<br>
E/VpnService( 2501):    at java.io.FileOutputStream.&lt;init&gt;(FileOutputStream.java:165)<br>
E/VpnService( 2501):    at java.io.FileOutputStream.&lt;init&gt;(FileOutputStream.java:144)<br>
E/VpnService( 2501):    at com.android.server.vpn.VpnServiceBinder.saveStates(VpnServiceBinder.java:96)<br>
E/VpnService( 2501):    at com.android.server.vpn.VpnService.saveSelf(VpnService.java:258)<br>
E/VpnService( 2501):    at com.android.server.vpn.VpnService.onConnected(VpnService.java:251)<br>
E/VpnService( 2501):    at com.android.server.vpn.VpnService.waitUntilConnectedOrTimedout(VpnService.java:205)<br>
E/VpnService( 2501):    at com.android.server.vpn.VpnService.onConnect(VpnService.java:148)<br>
E/VpnService( 2501):    at com.android.server.vpn.VpnServiceBinder$2.run(VpnServiceBinder.java:118)<br>
E/VpnService( 2501):    at java.lang.Thread.run(Thread.java:1019)<br>
I/VpnService( 2501): disconnecting VPN...<br>
</code></pre>
问题的原因是data下缺少misc/vpn目录，导致系统保存VPN状态失败。<br>
Root cause is, VPN state persistence failed due to the lack of misc/vpn directory.<br>
<br>
<h1>解决办法 Solutions</h1>
<ol><li>先使用系统的VPN配置工具连接一下，系统将生成misc目录，再使用XinkVpn就可以了<br>Connect to any VPN using system's VPN setting first, the system will make the missing directory for us, then you can use XinkVpn normally.<br>
</li><li>或者，手工创建misc/vpn目录（需要root）<br>Or, create the missing directory yourself.<br>
<pre><code>adb remount<br>
adb shell mkdir -p /data/data/misc/vpn<br>
</code></pre>