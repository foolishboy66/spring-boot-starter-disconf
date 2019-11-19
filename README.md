# spring-boot-starter-disconf
spring-boot-starter for disconf

## 特点

- 修复了spring5以上disconf方法找不到报错的问题
- 引入starter的依赖即可使用disocnf，支持springboot2，快速构建应用

## 快速开始

1. 引入maven依赖，如有需要，可自行发布到私服引用

   ```java
   <dependency>
       <groupId>com.github.foolishboy</groupId>
       <artifactId>spring-boot-starter-disconf</artifactId>
       <version>0.0.1-SNAPSHOT</version>
   </dependency>
   ```

2. 在springboot的配置文件中加入以下配置

   - [x] application.properties

     ```properties
     # 使用cglib的aop
     spring.aop.proxy-target-class=true
     # 扫描包的基础路径
     spring.disconf.scan-package=com.github.foolishboy
     # 是否使用远程配置文件, true(默认)会从远程获取配置 false则直接获取本地配置
     spring.disconf.enable-remote-conf=true
     # 配置服务器的 HOST,用逗号分隔 127.0.0.1:8004,127.0.0.1:8004
     spring.disconf.conf_server_host=127.0.0.1:8081
     # 版本, 请采用 X_X_X_X 格式
     spring.disconf.version=1_0_0_0
     # APP 请采用 产品线_服务名 格式
     spring.disconf.app=disconf_demo
     # 环境
     spring.disconf.env=rd
     # 忽略哪些分布式配置，用逗号分隔
     spring.disconf.ignore=
     # 获取远程配置 重试次数，默认是3次
     spring.disconf.conf_server_url_retry_times=2
     # 获取远程配置 重试时休眠时间，默认是5
     spring.disconf.conf_server_url_retry_sleep_seconds=1
     # 用户指定的下载文件夹, 远程文件下载后会放在这里
     spring.disconf.user_define_download_dir=./disconf/download
     # 下载的文件会被迁移到classpath根路径下，强烈建议将此选项置为 true(默认是true)
     spring.disconf.enable_local_download_dir_in_class_path=true
     # 当disconf上文件修改时，会自动加载的文件，多个用英文逗号隔开
     #spring.disconf.reloadable-files=remote.properties
     spring.disconf.reloadable-files=remote.properties
     # 当disconf上文件修改时，不会自动加载的文件，多个用英文逗号隔开
     #spring.disconf.un-reloadable-files=redis.properties
     spring.disconf.un-reloadable-files=redis.properties
     ```
     
   - [x] application.yml

     ```yaml
     spring:
       # 使用cglib的aop
       aop:
         proxy-target-class: true
       disconf:
         # APP 请采用 产品线_服务名 格式
         app: disconf_demo
         # 配置服务器的 HOST,用逗号分隔 127.0.0.1:8004,127.0.0.1:8004
         conf_server_host: 127.0.0.1:8081
         # 获取远程配置 重试时休眠时间，默认是5
         conf_server_url_retry_sleep_seconds: 1
         # 获取远程配置 重试次数，默认是3次
         conf_server_url_retry_times: 2
         # 是否使用远程配置文件, true(默认)会从远程获取配置 false则直接获取本地配置
         enable-remote-conf: true
         # 下载的文件会被迁移到classpath根路径下，强烈建议将此选项置为 true(默认是true)
         enable_local_download_dir_in_class_path: true
         # 环境
         env: rd
         # 忽略哪些分布式配置，用逗号分隔
         ignore:
         # 当disconf上文件修改时，会自动加载的文件，多个用英文逗号隔开
         reloadable-files: remote.properties
         # 扫描包的基础路径
         scan-package: com.github.foolishboy
         # 当disconf上文件修改时，不会自动加载的文件，多个用英文逗号隔开
         un-reloadable-files: redis.properties
         # 用户指定的下载文件夹, 远程文件下载后会放在这里
         user_define_download_dir: ./disconf/download
         # 版本, 请采用 X_X_X_X 格式
         version: '1_0_0_0'
     ```
     
     

3. demo可以参考pringboot-disconf-test工程，git地址如下：

   [https://github.com/foolishboy66/springboot-disconf-test.git](https://github.com/foolishboy66/springboot-disconf-test.git)

4. 代码参考了如下同学代码（**请尊重他们的劳动成果**）

   [https://github.com/xjzrc/disconf-spring-boot-starter.git](https://github.com/xjzrc/disconf-spring-boot-starter.git)



