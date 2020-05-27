package com.miaoyue91.speechrecognize.speechRecognize;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;


import com.miaoyue91.speechrecognize.speechRecognize.speechRecognizeStrategy.SpeechRecognizeStrategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public abstract class SpeechRecognizeForPredict extends SpeechRecognize {

    static {
        try {
            System.loadLibrary("cpp_speech_features");
        } catch (Exception e) {
            Log.e("SpeechRecognize", "System.loadLibrary(\"cpp_speech_features\") fail:" + e.getMessage());
        }
    }


    protected final SpeechRecognizeStrategy mStrategy;
    private List<SpeechRecognizeListener> mSpeechRecognizeListers;
    private List<LMResultLister> mLMResultListers;

    public SpeechRecognizeForPredict(SpeechRecognizeStrategy strategy){
        mStrategy = strategy;
        mSpeechRecognizeListers = new ArrayList<SpeechRecognizeListener>();
        mLMResultListers = new ArrayList<LMResultLister>();
    }

    public abstract void loadTFModel(AssetManager assetManager)throws IOException;

    public abstract void predict(short[] data);

    public abstract void reset();

    public void addSpeechRecognizeLister(SpeechRecognizeListener lister){
        mSpeechRecognizeListers.add(lister);
    }

    public void removeSpeechRecognizeLister(SpeechRecognizeListener lister){
        mSpeechRecognizeListers.remove(lister);
    }

    @Override
    public void addLMResultLister(LMResultLister lister) {
            mLMResultListers.add(lister);
    }

    @Override
    public void removeLMResultLister(LMResultLister lister) {
            mLMResultListers.remove(lister);
    }

    public void notifyCounterChange(){
        for (SpeechRecognizeListener listen:mSpeechRecognizeListers) {
            listen.onCounterChange();
        }
    }

    public void notifyLoadTFModelFinished(){
        for (SpeechRecognizeListener listen:mSpeechRecognizeListers) {
            listen.onLoadTFModelFinished();
        }
    }

    public void notifyLMResultCome(int r){
        for (LMResultLister listen:mLMResultListers) {
            listen.onLMResultCome(r);
        }
    }

    protected static MappedByteBuffer loadModelFile(AssetManager assets, String modelFilename) throws IOException {
        AssetFileDescriptor fileDescriptor = assets.openFd(modelFilename);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


}
