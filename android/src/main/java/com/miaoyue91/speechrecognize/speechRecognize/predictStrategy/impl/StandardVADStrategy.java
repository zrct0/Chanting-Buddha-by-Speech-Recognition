package com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.impl;


import com.miaoyue91.speechrecognize.speechRecognize.BuddhaNames;
import com.miaoyue91.speechrecognize.speechRecognize.SpeechRecognizeMatch;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.VADStrategy;

import org.tensorflow.lite.Interpreter;

public class StandardVADStrategy implements VADStrategy {
    @Override
    public String getTFPath(BuddhaNames.BuddhaName name) {
        return "VAD.tflite";
    }

    @Override
    public Integer predict(Interpreter interpreter, float[] data) {
        SpeechRecognizeMatch.normalization(data);
        float[][] output = new float[1][1];
        interpreter.run(data, output);
        return Math.round(output[0][0]);
    }
}
