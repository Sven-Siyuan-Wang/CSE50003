package com.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class MergeSortThreaded2 {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException  {
		// define thread number
		int NumOfThread = Integer.valueOf(args[1]);
		// read data from txt
		Scanner fileIn = new Scanner(new File(args[0]));
		ArrayList<Integer> array = new ArrayList<Integer>();
		while(fileIn.hasNextInt()){
			array.add(fileIn.nextInt());
		}
		fileIn.close();
		// TODO: partition the array into N part
		ArrayList<ArrayList<Integer>> subArrays = new ArrayList<>();
		int n = array.size();

		for(int i=0; i<NumOfThread ;i++){

			subArrays.add(new ArrayList<>(array.subList((int)(1.0*i*n/NumOfThread),(int)(1.0*(i+1)*n/NumOfThread))));

		}
		// start recording time
		long startTime = System.currentTimeMillis();

		// TODO: run MergeThread with N threads
		ArrayList<MergeThread> threads = new ArrayList<>();
		for(int i=0; i<NumOfThread; i++){
			MergeThread thread = new MergeThread(new ArrayList<>(subArrays.get(i)));
			//print(subArrays.get(i));
			threads.add(thread);
		}

		for(MergeThread t:threads){
			t.start();
		}
		// TODO: merge the N sorted array

		for(MergeThread t:threads){
			t.join();
		}

		ArrayList<ArrayList<Integer>> sortedArrays = new ArrayList<>();
		for(MergeThread t: threads){
			print(t.getInternal());
			sortedArrays.add(t.getInternal());
		}
		ArrayList<Integer> sortedList = mergeArrays(sortedArrays);
		// TODO: get the final sorted list

		// end recording time
		long endTime = System.currentTimeMillis();
		// show the time
		long runningTime = endTime - startTime;
		System.out.println("Running time is " + runningTime + " milliseconds");

		// TODO: print the sorted array value
		print(sortedList);
		System.out.println("\nFinish sorting!");
	}

	// TODO: merge sortedLists into a full sorted list
	public static ArrayList<Integer> mergeArrays(ArrayList< ArrayList<Integer>> sortedLists) {
		// create sorted list, name as mergedList
		ArrayList<Integer> mergedList = new ArrayList<Integer>();
		// TODO: merge the multiple sorted arrays
		boolean done = false;

		while(!done){
			int index=-1;
			int min = Integer.MAX_VALUE;
			for(int i=0; i<sortedLists.size(); i++) {
				ArrayList<Integer> temp = sortedLists.get(i);

				if(!temp.isEmpty() && temp.get(0)<min){
					min = temp.get(0);
					index = i;
					//System.out.println("update min");
				}


			}
			if(index == -1) done = true;  // index unchanged means no more values in lists

			else {
				//System.out.println("min:"+index);
				//System.out.println(sortedLists.get(index).get(0));
				mergedList.add(sortedLists.get(index).remove(0));
			}

		}
		return mergedList;
	}

	public static void print(ArrayList<Integer> array){
		for(Integer i: array){
			System.out.print(" "+i);
		}
		System.out.println();
	}

}


// extend thread
class MergeThread extends Thread {
	private ArrayList<Integer> list;

	public ArrayList<Integer> getInternal() {
		return list;
	}

	// TODO: implement merge sort here, recursive algorithm
	public void mergeSort(ArrayList<Integer> array) {
		Collections.sort(array);

	}


	MergeThread(ArrayList<Integer> array) {
		list = array;
	}

	public void run() {
		// called by object.start()
		//MergeSortThreaded2.print(list);
		mergeSort(list);
	}
}

