# WeChatPay
包含获取用户openid和同意支付下单获取prepay id的功能。module that contains fetching openid in WeChat, and unified order

# 使用（usage）
使用Eclipse开发，gradle打包，打出来的jar包将不包含依赖的dom4j，所以项目中需要含有dom4j的jar包。

    Kuikuis-MBP:WeChatPay kuikui$ gradle jar
    :compileJava
    :processResources
    :classes
    :jar
    
    BUILD SUCCESSFUL
    
    Total time: 4.833 secs
    
    This build could be faster, please consider using the Gradle Daemon: https://docs.gradle.org/2.12/userguide/gradle_daemon.html
    Kuikuis-MBP:WeChatPay kuikui$ ll build/libs/
    total 56
    drwxr-xr-x   4 kuikui  staff    136 Apr  2 13:39 .
    drwxr-xr-x  10 kuikui  staff    340 Apr  1 16:59 ..
    -rw-r--r--@  1 kuikui  staff   6148 Apr  1 21:27 .DS_Store
    -rw-r--r--   1 kuikui  staff  18785 Apr  2 13:39 WeChatPay-1.0.jar
    
    

作者在Play framework下使用，将wechat.properties放在conf目录下即可
