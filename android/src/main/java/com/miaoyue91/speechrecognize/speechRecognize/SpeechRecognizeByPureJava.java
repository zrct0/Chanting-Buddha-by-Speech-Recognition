package com.miaoyue91.speechrecognize.speechRecognize;

import android.content.res.AssetManager;


import com.miaoyue91.speechrecognize.speechRecognize.speechRecognizeStrategy.SpeechRecognizeStrategy;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;

public class SpeechRecognizeByPureJava extends SpeechRecognizeForPredict {

    AssetManager mAssetManager;
    private Interpreter mInterpreterVAD;
    private Interpreter mInterpreterAM;
    private Interpreter mInterpreterLM;
    private IQueue<float[]> mAMQueue;

    public SpeechRecognizeByPureJava(SpeechRecognizeStrategy strategy) {
        super(strategy);
        mAMQueue = new IQueue(5);
    }

    @Override
    public void loadTFModel(AssetManager assetManager) throws IOException {
        mAssetManager = assetManager;
        Interpreter.Options options = new Interpreter.Options();
        mInterpreterVAD = new Interpreter(loadModelFile(assetManager, "tflite/" + mStrategy.getTFLiteFilePathOfVAD()), options);
        mInterpreterAM = new Interpreter(loadModelFile(assetManager, "tflite/" + mStrategy.getTFLiteFilePathOfAM()), options);
        mInterpreterLM = new Interpreter(loadModelFile(assetManager, "tflite/" + mStrategy.getTFLiteFilePathOfLM()), options);
        notifyLoadTFModelFinished();
    }

    @Override
    public void predict(short[] data) {
        float[] soundBytes = SpeechRecognizeMatch.shortArray2floatArray(data);
        int epd = mStrategy.VADPredict(mInterpreterVAD, soundBytes);
        if(epd == 1){
            float[] rp = mStrategy.AMPredict(mInterpreterAM, soundBytes);
            mAMQueue.push(rp);
            if(mAMQueue.getSize() == 5){
                float [] rps = SpeechRecognizeMatch.flat2DimArray(mAMQueue.getArray(new float[mAMQueue.getSize()][]));
                int ctor = mStrategy.LMPredict(mInterpreterLM, rps);
                notifyLMResultCome(ctor);
                if(mStrategy.finalDecide(ctor)){
                    notifyCounterChange();
                }
            }
        }
    }

    @Override
    public void reset() {

    }

}
