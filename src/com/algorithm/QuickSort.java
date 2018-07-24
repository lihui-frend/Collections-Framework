package com.algorithm;

import java.util.Arrays;

public class QuickSort {

    public static void sort(int arr[],int low,int high){

        if (low<high){
            int middle = getMiddle(arr,low,high);
            sort(arr,0,middle-1);
            sort(arr,middle+1,high);
        }
    }

    public static int getMiddle(int arr[],int low,int high ){
        int index = arr[low];
        while (low<high){
            while (low<high && arr[high]>=index){
                high--;
            }
            arr[low]=arr[high];
            while (low<high && arr[low]<=index){
                low++;
            }
            arr[high]=arr[low];
        }
        arr[low] = index;
        return low;
    }


    public static void main(String args[]){
        int arr[] = {3,-1,88,4,33,10000,999};
        sort(arr,0,arr.length-1);
        System.out.print(Arrays.toString(arr));
    }
}
