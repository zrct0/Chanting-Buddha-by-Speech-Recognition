package com.miaoyue91.speechrecognize.speechRecognize.finalDecideStrategy.impl;


import com.miaoyue91.speechrecognize.speechRecognize.IQueue;
import com.miaoyue91.speechrecognize.speechRecognize.finalDecideStrategy.FinalDecideStrategy;

public class QueueDecisionStrategy implements FinalDecideStrategy {

    private IQueue<Integer> mDecisionQueue;
    private int queueSize;
    public QueueDecisionStrategy(int queueSize) {
        if(queueSize % 2 == 1){
            throw new IllegalArgumentException("Queue length must be even");
        }
        this.queueSize = queueSize;
        mDecisionQueue = new IQueue(queueSize);
    }

    @Override
    public boolean finalDecide(int data) {
        mDecisionQueue.push(data);
        if(mDecisionQueue.getSize() < queueSize){
            return false;
        }
        Integer[] qarray = mDecisionQueue.getArray(new Integer[mDecisionQueue.getSize()]);
        for (int i=0; i<queueSize; i++){
            if(i < queueSize / 2 && qarray[i] == 1){
                return false;
            }
            if(i >= queueSize / 2 && qarray[i] == 0){
                return false;
            }
        }
        return true;
    }

}
