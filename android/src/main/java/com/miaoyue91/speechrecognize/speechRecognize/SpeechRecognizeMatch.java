package com.miaoyue91.speechrecognize.speechRecognize;

public class SpeechRecognizeMatch {
    //提取语音特征(MFCC)
    public static native float[] extractSpeechFeaturesNative(float[] data);

    //将数组由2维转变为1维数组
    public static float[] flat2DimArray(float[][] data){
        float[] flatArray = new float[160];
        int index = 0;
        for(int i = 0; i< 5; i++){
            for(int j = 0; j< 32; j++){
                flatArray[index++] = data[i][j];
            }
        }
        return flatArray;
    }

    //将特征数组由1维转变为1*5*39*1的数组
    public static float[][][][] featureReshape(float[] feature){
        float[][][][] featureReshaped = new float[1][5][39][1];
        int index = 0;
        for(int i = 0; i< 5; i++){
            for(int j = 0; j< 39; j++){
                featureReshaped[0][i][j][0] = feature[index++];
            }
        }
        return featureReshaped;
    }

    //将short型数组转换为float型数组
    public static float[] shortArray2floatArray(short[] shortArray){
        float[] floatArray = new float[shortArray.length];
        for(int i=0; i<1024;i++){
            floatArray[i] = shortArray[i];
        }
        return floatArray;
    }

    //归一化
    public static void normalization(float[] data)
    {
        float mean = mean(data);
        float std = std(data);
        for(int i=0;i<1024; i++){
            data[i] = (data[i] - mean) / std;
        }
    }

    //求均方差
    public static float std(float[] x) {
        return (float) Math.sqrt(Variance(x));
    }

    //求方差
    public static float Variance(float[] x) {
        int m = x.length;
        float dAve = mean(x);
        float dVar = 0;
        for (int i = 0; i < m; i++) {
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return dVar / m;
    }

    //求平均值
    public static float mean(float[] x) {
        int m = x.length;
        float sum = 0;
        for (int i = 0; i < m; i++) {
            sum += x[i];
        }
        return sum / m;
    }
}
