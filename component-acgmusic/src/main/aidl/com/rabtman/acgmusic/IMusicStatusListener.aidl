package com.rabtman.acgmusic;

interface IMusicStatusListener {

    void onPrepareComplete(int duration);

    void onProgress(int curPosition);

    void onCompleted();
}