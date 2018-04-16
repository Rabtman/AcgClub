# AcgClub
宅社AcgClub，一款纯粹的ACG聚合类App

出于爱好与学习的目的做出了这款MD风格的应用，旨意通过涵盖Android端的一些热门技术框架来打造一个面向市场级别的产品

通过本项目，你可以了解到以下技术：

* Material Design
* MVP
* 组件化
* Kotlin
* RxJava2 
* Retrofit
* Dagger2
* Realm
* Glide
* Arouter
* Jsoup
* Gradle配置
* 混淆、多渠道包

## 预览

[应用下载体验](![](https://www.coolapk.com/apk/171021))
(Android 4.4+)
![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/qr-code.png)

![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/1.png)![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/2.png)![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/3.png)![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/4.png)![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/5.png)



## 项目相关

### 项目结构

```
AcgClub    
    - app                              宿主app
    - common                           基础库
    - common-res                       公用资源
    - component-acgcomic               漫画组件
      - src/main
        - runalone                     组件独立运行时生效
    - component-acgnews                资讯组件
    - component-acgschedule            番剧组件
    - router                           路由配置及相关服务实现
    - third-party-libs                 三方库存放
    - base_component.gradle            组件依赖配置
    - base_component_compiler.gradle   java注解处理配置
    - base_component_kapt.gradle       kotlin注解处理配置
    - config.gradle                    项目信息配置
```

### 新增组件

- 组件名固定前缀为“component-”

- 组件内build.gradle需进行如下配置：
```groovy
 //必备
 apply from:"../base_component.gradle"
 //使用java
 apply from:"../base_component_compiler.gradle"
 //或kotlin
 apply from:"../base_component_kapt.gradle"
 //如果用到数据库
 apply plugin: 'realm-android'
```

- 组件内res文件将以组件真名为前缀进行约束（例如：component-acgnews，一个布局文件名则需要以此打头：acgnews_layout.xml）

- 组件独立运行时还需要注意提供相关的application，入口activity，AndroidManifest.xml等

### 项目配置

config.gradle中进行项目项目的属性配置，例如：包名、版本号、编译版本...

其中：

```
//在该属性中填写需要合并到主程序运行的组件,如果没有填写的组件将独立运行
merge = [
            "acgnews",
            "acgschedule"
            //"acgcomic"
    ]
```

merge属性修改完毕后，需要重新构建项目

