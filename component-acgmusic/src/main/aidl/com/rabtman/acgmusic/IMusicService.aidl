package com.rabtman.acgmusic;

import com.rabtman.acgmusic.IMusicStatusListener;
import com.rabtman.acgmusic.mvp.model.entity.MusicInfo;

interface IMusicService {

    void play();

    void pause();

    void seekTo(int position);

    void next();

    int getCurPosition();

    boolean isPlaying();

    MusicInfo getMusicInfo();

    oneway void setMusicStatusListener(IMusicStatusListener listener);
}
