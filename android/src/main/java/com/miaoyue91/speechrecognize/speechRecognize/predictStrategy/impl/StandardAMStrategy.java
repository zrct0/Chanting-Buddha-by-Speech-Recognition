package com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.impl;


import com.miaoyue91.speechrecognize.speechRecognize.BuddhaNames;
import com.miaoyue91.speechrecognize.speechRecognize.SpeechRecognizeMatch;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.AMStrategy;

import org.tensorflow.lite.Interpreter;

public class StandardAMStrategy implements AMStrategy {
    @Override
    public String getTFPath(BuddhaNames.BuddhaName name) {
        return name.getName() + "/AcousticModel_32Dense.tflite";
    }

    @Override
    public float[] predict(Interpreter interpreter, float[] data) {
        float[][][][] feature = SpeechRecognizeMatch.featureReshape(SpeechRecognizeMatch.extractSpeechFeaturesNative(data));
        float[][] output = new float[1][32];
        interpreter.run(feature, output);
        return output[0];
    }
}
