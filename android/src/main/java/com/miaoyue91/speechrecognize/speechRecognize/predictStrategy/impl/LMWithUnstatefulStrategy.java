package com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.impl;






import com.miaoyue91.speechrecognize.speechRecognize.BuddhaNames;
import com.miaoyue91.speechrecognize.speechRecognize.predictStrategy.LMStrategy;

import org.tensorflow.lite.Interpreter;


public class LMWithUnstatefulStrategy implements LMStrategy {


    public LMWithUnstatefulStrategy() {

    }

    @Override
    public String getTFPath(BuddhaNames.BuddhaName name) {
        return name.getName() + "/LanguageModel_unstateful.tflite";
    }

    @Override
    public Integer predict(Interpreter interpreter, float[] data) {
        float[][][] input = reshape(data);
        float[][] output = new float[1][1];
        interpreter.run(input, output);
        return Math.round(output[0][0]);
    }

    //将数组由1维转变为3维的数组
    private static float[][][] reshape(float[] data){
        float[][][] reshape = new float[1][1][160];
        reshape[0][0] = data;
        return reshape;
    }
}
