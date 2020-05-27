package com.miaoyue91.speechrecognize.utils.RangeValue;

public class RangeValue {

    private float maxValue;
    private float minValue;
    private float currentValue;
    private RangeValueCutoffStrategy mCutoffStrategy;

    public RangeValue(float maxValue, float minValue, float currentValue, RangeValueCutoffStrategy cutoffStrategy) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.currentValue = currentValue;
        this.mCutoffStrategy = cutoffStrategy;
    }


    public void add(Float v) {
        currentValue += v;
        if(currentValue > maxValue){
            currentValue = mCutoffStrategy.onLarger(currentValue, maxValue);
        }
    }

    public void dec(Float v) {
        currentValue -= v;
        if(currentValue < minValue){
            currentValue = mCutoffStrategy.onLarger(currentValue, minValue);
        }
    }

    public float getValue(){
        return currentValue;
    }
}
