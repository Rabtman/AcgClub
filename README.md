# ![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/cover.jpg)

![](https://img.shields.io/badge/version-v0.4.0-brightgreen.svg)   ![](https://img.shields.io/badge/license-MIT-blue.svg)

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
* 热更新
* 混淆、多渠道包



~~**宅社壁纸API正式上线！**~~

~~[效果体验]~~

（API已停止提供服务）

## 近期羊毛

* 支付宝扫一扫红包

  ![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/zhifubao.jpg)

* GitHub用户在 2019-02-04 那一周账号有 15 个以上 followers且保留有当时的 SSH / PGP 私钥](https://zhuanlan.zhihu.com/p/108215705?utm_source=wechat_session&utm_medium=social&utm_oi=32062987829248)

* [度小满（原百度金融）羊毛，点击链接可领取3.6%加息券，建议配合购买1个月的银行理财保本且收益最佳，高达7.9%](https://8.baidu.com/activity/newcomer-reborn201909/newfriend.html?shareCode=836af90c5ad69016aab0cfbdd95e5b87&channel=2)

  ![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/duxiaoman.png)

## 预览

[应用下载体验](https://www.coolapk.com/apk/171021)

![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/qr-code.png)

![](https://img.shields.io/badge/Android-4.4%20or%20above-brightgreen.svg)



![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/preview.gif)   ![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/1.png)   ![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/2.png)   ![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/3.png)   ![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/4.png)   ![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/5.png)



## 项目相关

### 项目环境

![](https://img.shields.io/badge/Android%20Studio-3.1.2-blue.svg)   ![](https://img.shields.io/badge/gradle-4.4-brightgreen.svg)    ![](https://img.shields.io/badge/kotlin-1.2.21-orange.svg)   ![](https://img.shields.io/badge/compileVersion-26-ff69b4.svg)

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
    - component-acgpicture             看图组件
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
//在该属性中填写需要合并到主程序运行的组件,没有填写的组件将独立运行
merge = [
            "acgnews",
            "acgschedule"
            //"acgcomic"
    ]
```

merge属性修改完毕后，需要重新构建项目

### 其他

* 项目中提示缺失DaggerXXX时，通过完成编译将有Dagger2自动生成
* 在本地的local.properties按自己所需进行一些三方库的key、签名的配置，不需要的可以自行去掉

```
#阿里云用户反馈
fbAppKey=""
fbAppSecret=""
#友盟
umengAppKey=""
#bugly
buglyAppId=""

#签名信息
storeFile=
storePassword=
keyAlias=
keyPassword=

#友盟分享key
SINA_WEIBO_KEY=""
SINA_WEIBO_SECRET=""
QQ_ZONE_ID=""
QQ_ZONE_KEY=""
WEIXIN_ID=""
WEIXIN_KEY=""
```
* 为了确保bugly热更新能生效，请每次打出正式包的时候，确认app目录下tinker-support.gradle文件中的tinkerId的唯一性

## 鸣谢

* [`RxJava`](https://github.com/ReactiveX/RxJava)
* [`RxAndroid`](https://github.com/ReactiveX/RxAndroid)
* [`Dagger2`](https://github.com/google/dagger)
* [`RxPermissions`](https://github.com/tbruyelle/RxPermissions)
* [`RxCache`](https://github.com/VictorAlbertos/RxCache)
* [`Retrofit`](https://github.com/square/retrofit)
* [`Okhttp`](https://github.com/square/okhttp)
* [`Gson`](https://github.com/google/gson)
* [`Butterknife`](https://github.com/JakeWharton/butterknife)
* [`Glide`](https://github.com/bumptech/glide)
* [`LeakCanary`](https://github.com/square/leakcanary)
* [`Realm`](https://github.com/realm/realm-java)
* [`MVPArms`](https://github.com/JessYanCoding/MVPArms)
* [`Jsoup`](https://github.com/jhy/jsoup)
* [`Jsoup-Annotations`](https://github.com/fcannizzaro/jsoup-annotations)
* [`Fragmentation`](https://github.com/YoKeyword/Fragmentation)
* [`BlockCanary`](https://github.com/markzhai/AndroidPerformanceMonitor)
* [`ARouter`](https://github.com/alibaba/ARouter)
* [`DialogUtil`](https://github.com/hss01248/DialogUtil)
* [`DToast`](https://github.com/Dovar66/DToast)
* [`BaseRecyclerViewAdapterHelper`](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* [`HtmlTextView`](https://github.com/PrivacyApps/html-textview)
* [`MZBannerView`](https://github.com/pinguo-zhouwei/MZBannerView)
* [`StatusBarUtil`](https://github.com/laobie/StatusBarUtil)
* [`LoadSir`](https://github.com/KingJA/LoadSir)
* [`Logger`](https://github.com/orhanobut/logger)
* [`AndroidUtilCode`](https://github.com/Blankj/AndroidUtilCode)
* [`VasDolly`](https://github.com/Tencent/VasDolly)



## 捐赠

如果认为项目有趣或对你有帮助，欢迎捐赠支持。捐赠金额将用于支付域名和服务器费用，方便为大家提供更好的服务！

![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/pay_alipay.jpg)![](https://github.com/Rabtman/AcgClub/raw/master/screenshots/pay_wxpay.jpg)


## 联系

项目需要完善的地方还有很多，如有BUG或者更好的建议欢迎提出

* [issue](https://github.com/Rabtman/AcgClub/issues)
* mail：[acgclub@rabtman.com](mailto:acgclub@rabtman.com) 或 [zhangjm05@gmail.com](mailto:zhangjm05@gmail.co)
* blog：[https://rabtman.com/](https://rabtman.com/)
