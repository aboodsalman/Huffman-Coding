package com.phase3.game;

public class MinHeap {
    private static final int DEF_MAX_HEAP_SIZE = 256;
    private int size;
    private TNode elements[];

    public MinHeap() {
        setup(DEF_MAX_HEAP_SIZE);
    }

    private void setup(int MaxSize) {
        elements = new TNode[MaxSize];
        size=-1;
    }

    public void insert(TNode value) {
        if(isFull()) return;
        elements[++size] = value;
        int i=size, parent = (int)Math.ceil(((double)size)/2)-1;

        while(i>0) {
            if(elements[parent].compareTo(elements[i])>0) {
                swap(parent, i);
                i=parent;
                parent = (int)Math.ceil(((double)parent)/2)-1;
            }
            else return;
        }
    }

    public TNode pull() {
        if (size == -1)
            return null;

        TNode root = elements[0]; // The smallest element is at the root
        elements[0] = elements[size]; // Replace root with the last element
        size--;
        minHeapify();
        return root;
    }

    public void minHeapify() {
        for(int j=(int)(size-1)/2; j>=0; j--) {
            minHeapify(j);
        }
    }

    private void minHeapify(int i) {
        int largest = i, l = i*2+1, r = i*2+2;
        if(l<=size && elements[l].compareTo(elements[largest])<0) largest = l;
        if(r<=size && elements[r].compareTo(elements[largest])<0) largest = r;
        if(largest!=i) {
            swap(i, largest);
            minHeapify(largest);
        }
    }

    private void swap(int parent, int i) {
        TNode x = elements[parent];
        elements[parent] = elements[i];
        elements[i] = x;
    }

    public boolean isFull() {
        return size==DEF_MAX_HEAP_SIZE-1;
    }
    public boolean isEmpty(){
        return size==-1;
    }
    public boolean hasOneElement(){
        return size==0;
    }

}

