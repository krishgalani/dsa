package data_structures;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class IPQ<T> {
    List<Integer> heap;
    List<Integer> ki_to_hi;
    List<Integer> hi_to_ki;
    BiMap<T,Integer> key_to_ki; 
    ArrayDeque<Integer> kiQ;
    Comparator<Integer> comparator;
    public IPQ(Comparator<Integer> comparator){
        this.heap = new ArrayList<>();
        this.ki_to_hi = new ArrayList<>();
        this.hi_to_ki = new ArrayList<>();
        this.kiQ = new ArrayDeque<>();
        this.key_to_ki = HashBiMap.create();
        this.comparator = comparator;
    }
    /* 
     * kv_pairs a unique mapping of key to priority  
     */
    public IPQ(Comparator<Integer> comparator, Map<T,Integer> kv_pairs){
        this(comparator);
        int i=0;
        for(T key : kv_pairs.keySet()){
            key_to_ki.put(key,i);
            heap.add(kv_pairs.get(key));
            ki_to_hi.add(i);
            hi_to_ki.add(i);
            i++;
        }
        build();
    }
    //Helper Functions
    public boolean isLowerPriority(int i, int j){
        return comparator.compare(heap.get(i), heap.get(j)) > 0;
    }
    public void swap(int i, int j){
        Collections.swap(heap, i, j);
        Collections.swap(ki_to_hi, hi_to_ki.get(i), hi_to_ki.get(j));
        Collections.swap(hi_to_ki, i, j);
    }
    public void upheap(int i) {
        if(i < heap.size()){
            while (i > 0) {
                int parent = (i - 1) / 2;
                if (isLowerPriority(i,parent)) {
                    break;
                }
                // Bubble up
                swap(i,parent); 
                i = parent;
            }
        }
    }
    public void downheap(int i){
        int n = heap.size();
        if(i >= 0){
            while(i < n/2){ // i is not a leaf
                int leftChild = 2*i+1;
                int rightChild = 2*i+2;
                int importantChild = leftChild;
                if(rightChild < n && isLowerPriority(leftChild, rightChild)){
                    importantChild = rightChild;
                }
                if(isLowerPriority(importantChild, i)) break;
                swap(i,importantChild);                
                i = importantChild;
            }
        }
    }
    //Main Functions
    //insertion
    public void insert(T key, int value) {
        // Check for duplicate keys
        if (key_to_ki.containsKey(key)) {
            throw new IllegalArgumentException("Key already exists: " + key);
        } 
        heap.add(value);
        // Add the key and mappings
        int key_index;
        if(!kiQ.isEmpty()){
            key_index = kiQ.pop();
            ki_to_hi.set(key_index, heap.size()-1);
        } else {
            key_index = ki_to_hi.size();
            ki_to_hi.add(heap.size()-1);
        }
        key_to_ki.put(key,key_index);
        hi_to_ki.add(key_index);
        // Restore the heap property
        upheap(heap.size() - 1);
    }
    //removal
    public void remove(T key){
        if (!key_to_ki.containsKey(key)) {
            throw new IllegalArgumentException("Key doesn't exist: " + key);
        } 
        int key_index = key_to_ki.get(key);
        int heap_index = ki_to_hi.get(key_index);
        swap(heap_index,heap.size()-1);
        heap.remove(heap.size()-1);
        hi_to_ki.remove(hi_to_ki.size()-1);
        ki_to_hi.set(key_index,-1);
        kiQ.add(key_index);
        key_to_ki.remove(key);
        if(!heap.isEmpty()){
            downheap(heap_index);
        }
    }
    //update
    public void updatePriority(T key, int value){
        if (!key_to_ki.containsKey(key)) {
            throw new IllegalArgumentException("Key doesn't exist: " + key);
        } 
        int key_index = key_to_ki.get(key);
        int heap_index = ki_to_hi.get(key_index);
        heap.set(heap_index,value);
        upheap(heap_index);
        downheap(heap_index);
    }
    //pop
    public T pop(){
        if(heap.isEmpty()){
            throw new NoSuchElementException("Cannot pop an empty heap");
        }
        T key = peek();
        remove(key);
        return key;
    }
    //peek
    public T peek(){
        if(isEmpty()){
            throw new NoSuchElementException("Cannot peek an empty heap");
        }
        return key_to_ki.inverse().get(hi_to_ki.get(0));
    }
    //build
    public void build(){
        for(int i=heap.size()/2-1; i >= 0; i--){
            downheap(i);
        }
    }
    public int getPriority(T key){
       return heap.get(findHeapIndex(key));
    }
    public boolean decreaseKey(T key, int newValue){
        if (!key_to_ki.containsKey(key)) {
            throw new IllegalArgumentException("Key doesn't exist: " + key);
        } 
        if(getPriority(key) > newValue){
            updatePriority(key, newValue);
            return true;
        }
        return false;
    }
    public boolean increaseKey(T key, int newValue){
        if (!key_to_ki.containsKey(key)) {
            throw new IllegalArgumentException("Key doesn't exist: " + key);
        } 
        if(getPriority(key) < newValue){
            updatePriority(key, newValue);
            return true;
        }
        return false;

    }
    public int findHeapIndex(T key){
        if (!key_to_ki.containsKey(key)) {
            throw new IllegalArgumentException("Key doesn't exist: " + key);
        } 
        return ki_to_hi.get(key_to_ki.get(key));
    }
    public boolean containsKey(T key){
        return key_to_ki.containsKey(key);
    }
    public boolean isEmpty(){
        return key_to_ki.isEmpty();
    }
}


