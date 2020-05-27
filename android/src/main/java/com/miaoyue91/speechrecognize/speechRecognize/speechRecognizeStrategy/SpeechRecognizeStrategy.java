package com.miaoyue91.speechrecognize.speechRecognize.speechRecognizeStrategy;

import org.tensorflow.lite.Interpreter;

public abstract class SpeechRecognizeStrategy {

    public abstract String getTFLiteFilePathOfVAD();

    public abstract String getTFLiteFilePathOfAM();

    public abstract String getTFLiteFilePathOfLM();

    public abstract int VADPredict(Interpreter interpreter, float[] data);

    public abstract float[] AMPredict(Interpreter interpreter, float[] data);

    public abstract int LMPredict(Interpreter interpreter, float[] data);

    public abstract boolean finalDecide(int data);

}
