package com.miaoyue91.speechrecognize.speechRecognize;

import android.content.res.AssetManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.io.IOException;

public class SpeechRecognizeWithRecord extends SpeechRecognize {

    private SpeechRecognizeForPredict mRealSpeechRecognize;
    private boolean isInitialization;
    private volatile boolean isRunning;
    private Thread mRecordingThread;

    public SpeechRecognizeWithRecord(SpeechRecognizeForPredict realSpeechRecognizeForPredict) {
        mRealSpeechRecognize = realSpeechRecognizeForPredict;
        isInitialization = false;
    }

    @Override
    public void loadTFModel(AssetManager assetManager) throws IOException {
        mRealSpeechRecognize.loadTFModel(assetManager);
    }

    @Override
    public void reset() {
        mRealSpeechRecognize.reset();
    }

    @Override
    public void addSpeechRecognizeLister(SpeechRecognizeListener lister) {
        mRealSpeechRecognize.addSpeechRecognizeLister(lister);
    }

    @Override
    public void removeSpeechRecognizeLister(SpeechRecognizeListener lister) {
        mRealSpeechRecognize.removeSpeechRecognizeLister(lister);
    }

    @Override
    public void addLMResultLister(LMResultLister lister) {
        mRealSpeechRecognize.addLMResultLister(lister);
    }

    @Override
    public void removeLMResultLister(LMResultLister lister) {
        mRealSpeechRecognize.removeLMResultLister(lister);
    }


    public void start(){
        if(!isInitialization){
            initRecorder();
        }
        isRunning = true;
    }

    public void stop(){
        isRunning = false;
        mRecordingThread.interrupt();
    }

    public void suspend(){
        isRunning = false;
    }

    private void initRecorder() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
        mRecordingThread = new Thread(new RecordThread());
        mRecordingThread.start();
        isInitialization = true;
    }



    class RecordThread implements Runnable{

         private final static int SAMPLE_RATE = 16000;
         private final static int bufferSize = 1024;
         private final AudioRecord mAudioRecord;

         private short[] mRecordingBuffer;

         public RecordThread() {
             mRecordingBuffer = new short[bufferSize];
             mAudioRecord =   new AudioRecord(MediaRecorder.AudioSource.DEFAULT, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
             mAudioRecord.startRecording();
         }

         @Override
         public void run() {
             while(!Thread.currentThread().isInterrupted()){
                if(isRunning){
                    mAudioRecord.read(mRecordingBuffer, 0, bufferSize);
                    synchronized (mRealSpeechRecognize){
                        mRealSpeechRecognize.predict(mRecordingBuffer);
                    }
                }
             }
         }
     }
}
