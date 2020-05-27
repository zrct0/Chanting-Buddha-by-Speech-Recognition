package com.miaoyue91.speechrecognize.speechRecognize.speechRecognizeStrategy.impl;


import com.miaoyue91.speechrecognize.speechRecognize.BuddhaNames;
import com.miaoyue91.speechrecognize.speechRecognize.finalDecideStrategy.FinalDecideStrategy;
import com.miaoyue91.speechrecognize.speechRecognize.finalDecideStrategy.impl.QueueDecisionStrategy;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.AMStrategy;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.LMStrategy;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.VADStrategy;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.impl.LMWithUnstatefulStrategy;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.impl.StandardAMStrategy;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.impl.StandardVADStrategy;
import com.miaoyue91.speechrecognize.speechRecognize.speechRecognizeStrategy.SpeechRecognizeStrategy;

import org.tensorflow.lite.Interpreter;

public class UnstatefulStrategy extends SpeechRecognizeStrategy {

    private BuddhaNames.BuddhaName mBuddhaName;
    private VADStrategy mStrategyOfVAD;
    private AMStrategy mStrategyOfAM;
    private LMStrategy mStrategyOfLM;
    private FinalDecideStrategy mFinalDecideStrategy;

    public UnstatefulStrategy(BuddhaNames.BuddhaName name) {
        mBuddhaName = name;
        mStrategyOfVAD = new StandardVADStrategy();
        mStrategyOfAM = new StandardAMStrategy();
        mStrategyOfLM = new LMWithUnstatefulStrategy();
        mFinalDecideStrategy = new QueueDecisionStrategy(6);
    }

    @Override
    public String getTFLiteFilePathOfVAD() {
        return mStrategyOfVAD.getTFPath(mBuddhaName);
    }

    @Override
    public String getTFLiteFilePathOfAM() {
        return mStrategyOfAM.getTFPath(mBuddhaName);
    }

    @Override
    public String getTFLiteFilePathOfLM() {
        return mStrategyOfLM.getTFPath(mBuddhaName);
    }

    @Override
    public int VADPredict(Interpreter interpreter, float[] data) {
        return mStrategyOfVAD.predict(interpreter, data);
    }

    @Override
    public float[] AMPredict(Interpreter interpreter, float[] data) {
        return mStrategyOfAM.predict(interpreter, data);
    }

    @Override
    public int LMPredict(Interpreter interpreter, float[] data) {
        return mStrategyOfLM.predict(interpreter, data);
    }

    @Override
    public boolean finalDecide(int data) {
        return mFinalDecideStrategy.finalDecide(data);
    }
}
