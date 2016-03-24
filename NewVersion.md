### XinkVpnk 3.0 released ###
3.0版本仍然在尝试突破Android [keystore服务的uid限制](http://code.google.com/p/xinkvpn/wiki/VpnKeystoreIssue_zh_CN)。<br>这次使用的新方案是替换系统的keystore进程，操作步骤请<a href='http://code.google.com/p/xinkvpn/wiki/HackKeyStore_zh_CN'>浏览这里</a>。<br>
<br>
The new version was still finding a way to <a href='http://code.google.com/p/xinkvpn/wiki/VpnKeystoreIssueEn'>make L2TP secret and IPSec PSK available</a>.<br>This time, I will replace system's keystore process (<a href='http://code.google.com/p/xinkvpn/wiki/HackKeyStore_en'>Instructions</a>).<br>
<br>
<h4>Release notes</h4>
<ol><li>放弃使用system签名，可以适用于更多的ROM。实际上，3.0版本是在1.1版本的代码基础上进行开发的（启用了<a href='http://code.google.com/p/xinkvpn/source/browse/?name=hack-keystore'>'hack-keystore'分支</a>）<br>
</li><li>对于使用L2TP密钥和IPSec预共享密钥的用户来说，需要root，因为需要<a href='http://code.google.com/p/xinkvpn/wiki/HackKeyStore_zh_CN'>替换系统的keystore进程</a>
对于正在使用2.0版本的机油来说很不幸，又要重装了。。。不幸中的万幸是，参考上一个版本的<a href='http://code.google.com/p/xinkvpn/w/edit/MigrateTo2_0_zh_CN'>升级说明</a>进行重装就可以了。</li></ol>

<ol><li>Stop signing as 'system', make xinkvpn work on more vendor's devices. In fact, 3.0 is developed base on source code of version 1.1, in a branch named <a href='http://code.google.com/p/xinkvpn/source/browse/?name=hack-keystore'>'hack-keystore'</a>.<br>
</li><li>For users using L2TP secret and IPSec PSK, root is required, to <a href='http://code.google.com/p/xinkvpn/wiki/HackKeyStore_en'>replace the system's keystore process</a>
If you're using xinkvpn 2.0, reinstall is required.