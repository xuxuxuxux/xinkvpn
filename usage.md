# XinkVpn的使用方法 #
XinkVpn是一个提供Android平台快速连接/关闭VPN能力的应用，替代Android的原生应用。
Android自带的VPN设置不支持记忆密码，每次连接都需要输入，网络情况不好需要多次连接的时候绝对可以让人抓狂，因此自己开发了一个，在此共享给有此需要的人。

### 特性 ###
  * 支持Android 2.1 - 2.3.3 (2.3.3经过HTC真机测试，2.1/2.2使用模拟器测试)
  * 支持VPN类型：PPTP，L2TP，L2TP/IPSec PSK，暂不支持基于证书的L2TP/IPSec以及OpenVPN
  * 支持桌面小工具一键开关VPN

### 使用 ###
  * 安装
    * 请到[此处](http://code.google.com/p/xinkvpn/downloads/list)下载安装

  * 配置VPN
    * 配置的方法与Android的原生应用是一样的
    * **注意：由于Android没有开放VPN配置的接口，原来通过Android的原生应用配置的VPN就不能在XinkVpn中使用了**
> http://az8cgg.blu.livefilestore.com/y1pYW_sHZKmHUhCBi1Pkg33bEn_EBRGFZYEWKVEVsqFWiaTWKNNBc18p6ZqSAEMSozbyfaFRjgusQvhblDPrcHzWsfugNUSkLUh/addvpn-type.png?psid=1
> http://az8cgg.blu.livefilestore.com/y1pqp84KET4HXHte_yv7KUZAj-X-xi4j6Tuz99aB_0UozuV9LSosHrVPnw8xVQqO2ctNgEeife66RFIIxI27eLkDR89hk1njAsA/addvpn-pptp.png?psid=1

  * 连接/断开VPN
    * 使用屏幕右侧的切换按钮，即可方便的连接/断开VPN
> http://az8cgg.blu.livefilestore.com/y1p7LCtkVEEm7HYB8_A74oYwSstu_BjEF1xZohEoKNLULK-YAm30odlYnA58lAiVuQaY3OTD_YrmYCUzvBFnb8Yz71i2Cjs5Whq/vpn_list_conn.png?psid=1
  * 使用桌面小工具
    * 首先要指定默认的VPN，仍然在上述界面，通过屏幕左侧的单选按钮指定
    * 将XinkVpn小工具加入桌面，即可实现一键连接/断开VPN
    * **注意：要使用桌面小工具，请将XinkVpn安装到手机内存，Android不支持安装在SD卡的应用使用小工具**
> http://az8cgg.blu.livefilestore.com/y1p7X_TZI3MBFiQR7wHDVSoMSsuVF2-s7-w5QWf67U1vPCWJNxwwMSSAOI4vjf2ifarKE6a7puq0Dyk_cvUVX3z4Hj3PkPZETUh/vpn_widget.png?psid=1

