package com.rabtman.acgmusic;

import com.rabtman.acgmusic.mvp.model.entity.MusicInfo;

interface IMusicStatusListener {

    void onPrepareComplete(in MusicInfo info);

    void onMusicReady(int duration,boolean playNow);

    void onProgress(int curPosition);

    void onFail();

    void onCompleted();
}