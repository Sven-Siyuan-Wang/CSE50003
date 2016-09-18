package com.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MultiThread {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// define thread number
		//int NumOfThread = 5;
		int NumOfThread = Integer.valueOf(args[1]);
		// read data from txt
		Scanner fileIn = new Scanner(new File(args[0]));
		ArrayList<Integer> array = new ArrayList<Integer>();
		while(fileIn.hasNextInt()){
			array.add(fileIn.nextInt());
		}
		fileIn.close();
		// TODO: partition the array list into N part
		ArrayList<ArrayList> subArrays = new ArrayList<>();
		int n = array.size();

		for(int i=0; i<NumOfThread ;i++){

			subArrays.add(new ArrayList<>(array.subList((int)(1.0*i*n/NumOfThread),(int)(1.0*(i+1)*n/NumOfThread))));

		}

		// TODO: run SimpleThread with N threads

		// SimpleThread[] threads = new SimpleThread[NumOfThread];
		// for(int i=0;i<NumOfThread;i++){
		// 	threads[i] = new SimpleThread((ArrayList)subArrays.get(i));
		// }


		// for(int i=0;i<NumOfThread;i++){
		// 	threads[i].start();
		// }
		// for(int i=0;i<NumOfThread;i++){
		// 	threads[i].join();
		// }
		ArrayList<SimpleThread> threads = new ArrayList<SimpleThread>();
		for(int i=0; i<NumOfThread; i++){
			SimpleThread thread = new SimpleThread((ArrayList)subArrays.get(i));
			threads.add(thread);
		}

		for(SimpleThread t:threads){
			t.start();
		}
		for(SimpleThread t: threads){
			t.join();
		}




		//TODO: get the N max values
		ArrayList<Integer> subMax = new ArrayList<>();
		for(SimpleThread t: threads){
			subMax.add(t.getMax());
			System.out.print(t.getMax()+" ");
		}

		// for(int i=0; i<NumOfThread; i++){
		// 	System.out.println(threads[i].getMax()); }

		System.out.println("Max Value: "+Collections.max(subMax));
	// TODO: show the N max values

	// TODO: get the max value from N max values

	// TODO: show the max value

}
}

//extend thread
class SimpleThread extends Thread {
	private ArrayList<Integer> list;
	private int max;

	public int getMax() {
		return max;
	}

	SimpleThread(ArrayList<Integer> array) {
		list = array;
	}

	public void run() {
		// TODO: implement actions here
		max = Collections.max(list);
	}
}