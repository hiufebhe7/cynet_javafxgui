# cynet 
一款基于 **mastodon** 上传 下载 分享 功能的网盘app。  
<article class="logo">
	<img src="https://github.com/hiufebhe7/cynet_javafxgui/blob/master/image/logo.svg" alt="logo" width="128" height="128" align="bottom" />
</article>

## 最新版本 0.0.5
由于国内上传速度十分不稳定，目前移除jar+jre下载。  
预编译文件为out/artifacts/cynet_javafxgui_jar/*  
需要下载当前平台的jre解压到同目录下  
比如  

cynet_javafxgui.jar  
run_linux_mac.bash  
run_windows.bat  
jre1.8.0_241(jre运行时，当前开发版本为8.241，如果下载的是其他版本需要修改 run 脚本里面的jre目录路径)  

## 使用注意
上传的文件不要使用过长的文件命名，不然会发生错误。最好使用简单的英文命名。不然有时候会发生文件读写错误。原因是在缓存读写编码时被编码的文件名会过长超出当前系统最长文件名限制。

### 开发依赖 
* javafx  
* kotlin  
* tornadofx  

### release进度
| Windows<br>![img](https://img.shields.io/badge/build-success-green.svg?logo=windows) | Linux<br>![img](https://img.shields.io/badge/build-success-green.svg?logo=linux)  | Mac Os<br>![img](https://img.shields.io/badge/build-success-green.svg?logo=apple)  |
| --- | --- | --- | 


## 目标是一个分布式的免费,更通用广泛的开源网盘  
比起主流商业网盘cynet有哪些更好的地方  
1. 无需繁琐的认证信息，基于mastodon账号，申请简单使用广泛无门槛。  
2. 分布式的存储。cynet是通过mastodon媒体协议，使用隐写的方式把文件分割存储在mastodon实例上。  
3. 更安全，更隐私，更灵活的分享方式。
 
### 最后关，于本项目。  
cynet会持续更新，作者目前半全职开发。继续完善它的功能。  
**最美好的记忆，给最爱的人(●’◡’●)**