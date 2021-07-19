object App {
    val organization = "com.rabtman"
    val applicationId = "${organization}.acgclub"
    val compileSdkVersion = 28
    val buildToolsVersion = "28.0.0"
    val minSdkVersion = 21
    val targetSdkVersion = 28
    val versionCode = 7
    val versionName = "1.0.0"
    val resConfigs = "zh"

    //合并到主程序运行的组件,没有填写的组件则可以独立运行
    val components = arrayOf<String>(
            "acgnews",
            "acgschedule",
            "acgcomic",
            "acgmusic",
            "acgpicture"
    )
}


object DepVer {
    val kotlin_version = "1.4.32"
    val realm_version = "5.0.1"
    val retrofit = "2.9.0"
    val okhttp = "4.9.1"
    val glide = "4.12.0"
    val dagger2 = "2.37"
    val butterknife = "10.2.1"
    val blockcanary = "1.5.0"
    val leakcanary = "1.5.4"
}

object Libs {
    //base
    val material = "com.google.android.material:material:1.0.0"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

    //ui
    val refreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
    val dialog = "com.github.hss01248:DialogUtil:master-SNAPSHOT"
    val toast = "com.github.Dovar66:DToast:1.1.3"
    val base_rcv_helper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"
    val html_textview = "org.sufficientlysecure:html-textview:3.3"
    val bottom_bar = "com.roughike:bottom-bar:2.3.1"
    val banner = "com.youth.banner:banner:2.0.12"
    val materialsearchview = "com.miguelcatalan:materialsearchview:1.4.0"
    val statusbarutil = "com.jaeger.statusbarutil:library:1.4.0"
    val loadsir = "com.kingja.loadsir:loadsir:1.3.6"

    //other
    val multidex = "androidx.multidex:multidex:2.0.0"
    val logger = "com.orhanobut:logger:2.1.1"
    val fragmentation = "me.yokeyword:fragmentationx:1.0.2"

    //bugly
    val crashreport = "com.tencent.bugly:crashreport_upgrade:1.3.6"
    val nativecrashreport = "com.tencent.bugly:nativecrashreport:3.6.0.1"
    val tinker = "com.tencent.tinker:tinker-android-lib:1.9.9"

    //json
    val gson = "com.google.code.gson:gson:2.8.0"

    //jsoup
    val jsoup = "org.jsoup:jsoup:1.10.2"
    val jsoup_annotations = "com.github.fcannizzaro:jsoup-annotations:1.0.3"

    //network
    val retrofit = "com.squareup.retrofit2:retrofit:${DepVer.retrofit}"
    val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${DepVer.retrofit}"
    val retrofit_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${DepVer.retrofit}"
    val okhttp = "com.squareup.okhttp3:okhttp:${DepVer.okhttp}"
    val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${DepVer.okhttp}"

    //image
    val glide = "com.github.bumptech.glide:glide:${DepVer.glide}"
    val glide_transformations = "jp.wasabeef:glide-transformations:4.3.0"
    val glide_compiler = "com.github.bumptech.glide:compiler:${DepVer.glide}"
    val glide_loader_okhttp3 = "com.github.bumptech.glide:okhttp3-integration:${DepVer.glide}"

    //rx
    val rxandroid = "io.reactivex.rxjava2:rxandroid:2.0.2"
    val rxjava = "io.reactivex.rxjava2:rxjava:2.1.6"
    val rxpermissions2 = "com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar"
    val rxdownload = "com.github.ssseasonnn:RxDownload:1.0.9"
    val rxcache = "com.github.VictorAlbertos.RxCache:runtime:1.8.3-2.x"
    val rxcache_jolyglot = "com.github.VictorAlbertos.Jolyglot:gson:0.0.4"

    //router
    val router = "com.alibaba:arouter-api:1.5.0"
    val router_compiler = "com.alibaba:arouter-compiler:1.2.2"

    //di
    val butterknife = "com.jakewharton:butterknife:${DepVer.butterknife}"
    val butterknife_compiler = "com.jakewharton:butterknife-compiler:${DepVer.butterknife}"
    val butterknife_gradle = "com.jakewharton:butterknife-gradle-plugin:${DepVer.butterknife}"
    val dagger2 = "com.google.dagger:dagger:${DepVer.dagger2}"
    val dagger2_compiler = "com.google.dagger:dagger-compiler:${DepVer.dagger2}"

    //test
    val junit = "junit:junit:4.12"
    val androidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
    val runner = "com.android.support.test:runner:0.5"
    val mockito_core = "org.mockito:mockito-core:1.+"
    val timber = "com.jakewharton.timber:timber:4.1.2"

    //umeng
    val umeng = "com.umeng.analytics:analytics:6.1.1"

    //canary
    val blockcanary_android = "com.github.markzhai:blockcanary-android:${DepVer.blockcanary}"
    val blockcanary_no_op = "com.github.markzhai:blockcanary-no-op:${DepVer.blockcanary}"
    val leakcanary_android = "com.squareup.leakcanary:leakcanary-android:${DepVer.leakcanary}"
    val leakcanary_no_op = "com.squareup.leakcanary:leakcanary-android-no-op:${DepVer.leakcanary}"

    //VasDolly
    val vasdolly = "com.leon.channel:helper:1.1.7"
}