# cynet

一款基于 **[Mastodon](https://github.com/tootsuite/mastodon)**  **上传，下载，分享** 功能的网盘app。  

<article class="logo">
    <img src="https://github.com/hiufebhe7/cynet_javafxgui/blob/master/image/logo3.svg" alt="logo" width="128" height="128" align="bottom" />
</article>

| Windows<br>![img](https://img.shields.io/badge/build-success-green.svg?logo=windows) | Linux<br>![img](https://img.shields.io/badge/build-success-green.svg?logo=linux) | Mac Os<br>![img](https://img.shields.io/badge/build-success-green.svg?logo=apple) |
| ------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- |

### 开发依赖

* javafx  

* kotlin  

* tornadofx 

* jdk8

## 最新版本 0.1.0

1. 下载[jre8_241运行时](https://github.com/hiufebhe7/cynet_javafxgui/releases/tag/jre)  
2. 下载/out/artifacts/cynet_javafxgui_jar/*  目录下的jar文件  
3. 解压前平台的jre到目录下  

**app目录各文件解释**  

| 文件名                                | 描述                  |
| ---------------------------------- | ------------------- |
| /cache/*                            | 下载任务缓存              |
| /cache/u.*.json                            | 上传任务缓存              |
| /cache/d.*.json                            | 下载任务缓存              |
| /cache/config.json                 | app配置信息             |
| cynet_javafxgui.jar                | 主程序文件，版本更新直接下载替换掉即可 |
| run_windows.bat<br>run_linux_mac.bash | 跨平台运行脚本             |
| /jre1.8.0_241/*                    | jre运行时              |

## 使用注意

1. 上传的文件不要使用过长的文件命名，不然会发生错误。最好使用简单的英文命名。不然有时候会发生文件读写错误。原因是在缓存读写编码时被编码的文件名会过长超出当前系统最长文件名限制。
2. 任务未暂停前不要destroy任务
3. 在上传了大概100mb或者更少的数据之后会发生上传阻塞，这是由于mastodon的上传反制机制导致的。
可以登录当前实例账号上传一张测试图片得到解阻塞时间，通常是10-15分钟后。（目前关于这一点我存在考虑是否要突破反制机制，会对小实例造成很大的负担存疑考虑）
3. 上传有阻塞反制，下载没有

## 开发初衷

目标是一个依托mastodon社区的免费，通用的开源网盘app。  

比起主流商业网盘cynet有哪些更好的地方  

1. 无需繁琐的认证信息。基于mastodon账号，申请简单使用广泛无门槛。  
2. 更隐私的存储方式。cynet是通过mastodon媒体协议，使用隐写的方式把文件分割存储在mastodon实例上。  
3. 更灵活的分享方式。生成一个类似磁力链接的 cynet=xxxx 地址，直接分享地址即可下载。

## 最后关，于本项目。

cynet会持续更新，作者目前半全职开发。继续完善它的功能。  
**此app给最爱的cy(●’◡’●)**