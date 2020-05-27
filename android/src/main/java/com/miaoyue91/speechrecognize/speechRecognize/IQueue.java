package com.miaoyue91.speechrecognize.speechRecognize;

public class IQueue<T> {

    private int maxSize;
    private Object[] arr;
    private int pointer;
    private int size;

    public IQueue(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new Object[maxSize];
        this.pointer = 0;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public T[] getArray(T[] a) {
        if (a.length != size){
            throw new IllegalArgumentException("a.length != size");
        }
        int ai = 0;
        if(size < maxSize){
            for (int p_i = 0; p_i < pointer; p_i++) {
                a[ai++] = (T)arr[p_i];
            }
            return a;
        }
        for (int p_i = pointer; p_i < maxSize; p_i++) {
            a[ai++] = (T)arr[p_i];
        }
        for (int p_i = 0; p_i < pointer; p_i++) {
            a[ai++] = (T)arr[p_i];
        }
        return a;
    }

    public void push(T value) {
        if (this.pointer == this.maxSize) {
            this.pointer = 0;
        }
        this.arr[this.pointer] = value;
        this.pointer += 1;
        if (this.size < this.maxSize) {
            this.size += 1;
        }
    }
}
