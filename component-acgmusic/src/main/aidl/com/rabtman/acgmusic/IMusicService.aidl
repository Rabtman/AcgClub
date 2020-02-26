package com.rabtman.acgmusic;

import com.rabtman.acgmusic.IMusicStatusListener;
import com.rabtman.acgmusic.mvp.model.entity.MusicInfo;

interface IMusicService {

    void play();

    void pause();

    void seekTo(int position);

    void next();

    int getCurPosition();

    int getDuration();

    boolean isPlaying();

    MusicInfo getMusicInfo();

    oneway void registerMusicStatusListener(IMusicStatusListener listener);

    oneway void unregisterMusicStatusListener();
}
