package com.miaoyue91.speechrecognize.speechRecognize;

import android.content.res.AssetManager;

import java.io.IOException;

public abstract class SpeechRecognize {

    public abstract void loadTFModel(AssetManager assetManager)throws IOException;

    public abstract void reset();

    public abstract void addSpeechRecognizeLister(SpeechRecognizeListener lister);

    public abstract void removeSpeechRecognizeLister(SpeechRecognizeListener lister);

    public abstract void addLMResultLister(LMResultLister lister);

    public abstract void removeLMResultLister(LMResultLister lister);

    public interface SpeechRecognizeListener {
        void onCounterChange();
        void onLoadTFModelFinished();
    }

    public interface LMResultLister {
        void onLMResultCome(int r);
    }
}
