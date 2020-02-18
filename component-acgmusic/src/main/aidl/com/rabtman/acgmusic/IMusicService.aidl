package com.rabtman.acgmusic;

import com.rabtman.acgmusic.IMusicStatusListener;

interface IMusicService {

    void play();

    void pause();

    void seekTo(int position);

    void next(String url);

    int getCurPosition();

    oneway void setMusicStatusListener(IMusicStatusListener listener);
}
