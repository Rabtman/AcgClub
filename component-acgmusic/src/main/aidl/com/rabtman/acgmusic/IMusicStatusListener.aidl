package com.rabtman.acgmusic;

import com.rabtman.acgmusic.mvp.model.entity.MusicInfo;

interface IMusicStatusListener {

    void onMusicPrepared(in MusicInfo info);

    void onMusicReady(int duration,boolean playNow);

    void onPlayStatusChange(boolean isPlaying);

    void onProgress(int curPosition);

    void onMusicEnd();

    void onFail();

    void onClosed();
}