package algorithms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSort {
    List<Integer> nums;
    public List<Integer> mergeSort(List<Integer> nums){
        this.nums=nums;
        List<Integer> ret = new ArrayList<>();
        if(nums.isEmpty()) return ret;
        return mergeSort(0,nums.size()-1);
    }
    public List<Integer> mergeSort(int l, int r){
        if(l == r){
            return Arrays.asList(nums.get(l));
        }   
        int mid = l + (r-l)/2;
        List<Integer> left = mergeSort(l,mid);
        List<Integer> right = mergeSort(mid+1,r);
        return merge(left,right);
    }
    public List<Integer> merge(List<Integer> left, List<Integer> right){
        int l = 0;
        int r = 0;
        List<Integer> sorted = new ArrayList<>();
        while(l < left.size() || r < right.size()){
            if(l == left.size()){
                sorted.add(right.get(r));
                r++;
            } else if (r == right.size()){
                sorted.add(left.get(l));
                l++;
            } else if (left.get(l) < right.get(r)){
                sorted.add(left.get(l));
                l++;
            } else /*left[l] >= right[r]*/{
                sorted.add(right.get(r));
                r++;
            }
        }
        return sorted;
    }
}
