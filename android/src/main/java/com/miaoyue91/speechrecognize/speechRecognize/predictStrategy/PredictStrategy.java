package com.miaoyue91.speechrecognize.speechRecognize.predictStrategy;

import com.miaoyue91.speechrecognize.speechRecognize.BuddhaNames;

import org.tensorflow.lite.Interpreter;

public interface PredictStrategy<R, D>{

    String getTFPath(BuddhaNames.BuddhaName name);

    R predict(Interpreter interpreter, D data);
}
