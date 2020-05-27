package com.miaoyue91.speechrecognize.utils.RangeValue;

public interface RangeValueCutoffStrategy {
    float onLarger(float currentValue, float maxValue);
    float onSmaller(float currentValue, float minValue);
}
