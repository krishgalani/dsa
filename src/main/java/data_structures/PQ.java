package data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class PQ {
    public List<Integer> heap;
    public Comparator<Integer> comparator;
    public PQ(Comparator<Integer> c){
        heap = new ArrayList<>();
        comparator = c;
    }
    public PQ(Comparator<Integer> c, int[] keys){
        this(c);
        //build_heap
        for(int i : keys){
            heap.add(i);
        }
        build_heap();
    }
    boolean inBounds(int i){
        return 0 <= i && i < heap.size();
    }
    int size(){
        return heap.size();
    }
    boolean isLowerPriority(int a, int b){
        return comparator.compare(a,b) <= 0;
    }
    void upheap(int i){
        if(inBounds(i)){
            while(i > 0){
                int parent = (i-1)/2;
                if(isLowerPriority(heap.get(i), heap.get(parent))) break;
                Collections.swap(heap,i,parent);
                i = parent;
            } 
        }
    }
    void downheap(int i){
        if(inBounds(i)){
            while(i < heap.size()/2){
                int leftChild = 2*i+1;
                int rightChild = 2*i+2;
                int importantChild = leftChild;
                if(rightChild < heap.size() && isLowerPriority(heap.get(leftChild), heap.get(rightChild))){
                    importantChild = rightChild;
                }
                if(isLowerPriority(heap.get(importantChild), heap.get(i))) break;
                Collections.swap(heap,importantChild,i);
                i = importantChild;
            }
        }
    }
    public void build_heap(){
        for(int i=heap.size()/2-1; i>=0; i--){
            downheap(i);
        }
    }
    public int peek(){
        if(heap.isEmpty()){
            throw new NoSuchElementException("Cannot peek an empty heap");
        }
        return heap.get(0);
    }
    public int pop(){
        if(heap.isEmpty()){
            throw new NoSuchElementException("Cannot pop an empty heap");
        }
        int ret = peek();
        Collections.swap(heap,0,heap.size()-1);
        heap.remove(heap.size()-1);
        if(!heap.isEmpty()){
            downheap(0);
        }
        return ret;
    }
    public void add(int i){
        heap.add(i);
        upheap(heap.size()-1);
    }
    public boolean empty() {
        return heap.isEmpty();
    }
    public int pushpop(int i){
        if(isLowerPriority(heap.get(0),i)){
            int top = heap.get(0);
            heap.set(0,i);
            downheap(0);
            return top;
        } else {
            return i;
        }
    }
}