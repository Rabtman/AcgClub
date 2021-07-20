package com.rabtman.acgclub.base;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.leon.channel.helper.ChannelReaderUtil;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.business.base.BaseApplication;
import com.rabtman.common.utils.SystemUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;

/**
 * @author Rabtman
 */

public class AcgClubApp extends BaseApplication {

    private String mChannel = "official";

    @Override
    public void onCreate() {
        super.onCreate();

        boolean defaultProcess = SystemUtils.getCurProcessName()
                .equals(getPackageName());

        //获取渠道包值
        String channel = ChannelReaderUtil.getChannel(this);
        if (!TextUtils.isEmpty(channel)) {
            mChannel = channel;
        }

        //bugly
        BuglyStrategy strategy = new BuglyStrategy();
        strategy.setAppChannel(mChannel);
        strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setUploadProcess(defaultProcess);
        Bugly.init(this, BuildConfig.BUGLY_APP_ID, BuildConfig.APP_DEBUG, strategy);
        Bugly.setIsDevelopmentDevice(this, BuildConfig.APP_DEBUG);

        if (defaultProcess) {
            onDefaultProcessCreate();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 在默认进程中进行操作
     */
    public void onDefaultProcessCreate() {
        //umeng初始化
        MobclickAgent
                .startWithConfigure(
                        new UMAnalyticsConfig(this, com.rabtman.business.BuildConfig.UMENG_APP_KEY,
                                mChannel));
        initFeedback();
    }

    //阿里用户反馈
    private void initFeedback() {
        FeedbackAPI.init(this, BuildConfig.FEEDBACK_APP_KEY, BuildConfig.FEEDBACK_APP_SECRET);
    }
}
