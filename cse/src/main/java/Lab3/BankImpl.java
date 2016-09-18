package Lab3;// package Week3;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BankImpl {
	private int numberOfCustomers;	// the number of customers
	private int numberOfResources;	// the number of resources

	private int[] available; 	// the available amount of each resource
	private int[][] maximum; 	// the maximum demand of each customer
	private int[][] allocation;	// the amount currently allocated
	private int[][] need;		// the remaining needs of each customer
	
	public BankImpl (int[] resources, int numberOfCustomers) {
		// TODO: set the number of resources
		// TODO: set the number of customers

		// TODO: set the value of bank resources to available

		// TODO: set the array size for maximum, allocation, and need
		this.numberOfCustomers = numberOfCustomers;
		numberOfResources = resources.length;
		available = Arrays.copyOf(resources,resources.length);
		maximum = new int[numberOfCustomers][numberOfResources];
		allocation = new int[numberOfCustomers][numberOfResources];
		need = new int[numberOfCustomers][numberOfResources];



	}
	
	public int getNumberOfCustomers() {
		// TODO: return numberOfCustomers
		return  numberOfCustomers;
	}

	public void addCustomer(int customerNumber, int[] maximumDemand) {
		// TODO: initialize the maximum, allocation, need for this customer
		//allocation is all zeros by default

		// TODO: check if the customer's maximum demand exceeds bank's available resource
        boolean okay = true;
        for(int i=0; i<maximumDemand.length;i++ ){
            if(maximumDemand[i]>available[i]){
                okay = false;
                break;

            }

        }
		// TODO: set value for maximum and need
		if(okay){
            maximum[customerNumber] = Arrays.copyOf(maximumDemand,maximumDemand.length);
            need[customerNumber] = Arrays.copyOf(maximumDemand,maximumDemand.length);
        }
        else System.out.println("The customer's max need exceeds bank's available resource!");
    }

	public void getState() {
        System.out.println("Bank State:");
        // TODO: print available
        System.out.println("Available:");
        for(int i: available) System.out.print(i+" ");
        System.out.println("\n");

		// TODO: print allocation
        System.out.println("Allocation:");
        for(int i=0;i<allocation.length;i++){
            for(int j=0; j<allocation[i].length; j++){
                System.out.print(allocation[i][j] + " ");
            }
            System.out.println();

        }
        System.out.println();

        // TODO: print max
        System.out.println("Max:");
        for(int i=0;i<maximum.length;i++){
            for(int j=0; j<maximum[i].length; j++){
                System.out.print(maximum[i][j] + " ");
            }
            System.out.println();

        }
        System.out.println();

		// TODO: print need
        System.out.println("Need:");
        for(int i=0;i<need.length;i++){
            for(int j=0; j<need[i].length; j++){
                System.out.print(need[i][j] + " ");
            }
            System.out.println();

        }
        System.out.println();
    }

	public synchronized boolean requestResources(int customerNumber, int[] request) {
		// print the request
        System.out.println("Customer "+customerNumber+" request:");
        for(int i:request) System.out.print(i+" ");
        System.out.println();
        System.out.println();

		// TODO: check if request larger than need
        for(int i=0; i<request.length; i++){
            if(request[i]>need[customerNumber][i]) {
                System.out.println("Failed. Request>Need");
                return false;
            }
        }

		// TODO: check if request larger than available
        for(int i=0; i<request.length; i++){
            if(request[i]>available[i]) {
                System.out.println("Failed. Request > Available");
                return false;
            }
        }

		// TODO: check if the state is safe or not
        if(!checkSafe(customerNumber,request)) {
            System.out.println("Failed. The request is not safe.");
            return false;
        }


		// TODO: if it is safe, allocate the resources to customer customerNumber 

        for(int i=0; i<request.length; i++){
            available[i] -= request[i];
            allocation[customerNumber][i] += request[i];
            need[customerNumber][i] -= request[i];
        }
		// TODO: return state
        return true;

	}

	public synchronized void releaseResources(int customerNumber, int[] release) {
		// print the release

		// TODO: release the resources from customer customerNumber 

	}

	private synchronized boolean checkSafe(int customerNumber, int[] request) {
		// TODO: check if the state is safe
		// TODO: initialize a finish vector
        boolean[] finish = new boolean[numberOfCustomers];

		// TODO: copy the available matrix to temp_available
        // TODO: subtract request from temp_available
        // TODO: temporarily subtract request from need
        // TODO: temporarily add request to allocation
        int[] temp_avail = Arrays.copyOf(available,available.length);
        int[][] temp_alloc = Arrays.copyOf(allocation,allocation.length);
        temp_avail = vectorMinus(temp_avail,request);
        int[][] temp_need = Arrays.copyOf(need,numberOfCustomers);
        temp_need[customerNumber] = vectorMinus(temp_need[customerNumber], request);
        temp_alloc[customerNumber] = vectorAdd(allocation[customerNumber],request);
        int[] work = Arrays.copyOf(temp_avail,numberOfResources);

		// TODO: if customer request exceed maximum, return false
        for(int i=0; i<numberOfResources;i++){
            if(request[i]>need[customerNumber][i]) return false;

        }

		// TODO: check if the Bank's algorithm can finish based on safety algorithm
		// (see P332, Section 7.5.3.1, Operating System Concepts with Java, Eighth Edition)

        boolean possible = true;
        while(possible){
            possible = false;
            for(int i=0; i<numberOfCustomers; i++){
                if(finish[i]==false && vectorComp(temp_need[i],work)){

                    possible = true;
                    work = vectorAdd(work,temp_alloc[i]);
                    finish[i] = true;

                }
            }
        }

		// TODO: restore the value of need and allocation for the customer
		// TODO: go through the finish to see if all value is true
		// TODO: return state
        for(boolean b: finish){
            if(!b) return false;
        }
        return true;
	}

    private int[] vectorAdd(int[] a, int[] b){
        int[] sum = new int[a.length];
        for(int i=0; i<a.length; i++) sum[i] = a[i]+b[i];
        return sum;
    }
    private int[] vectorMinus(int[] a, int[] b){
        int[] sum = new int[a.length];
        for(int i=0; i<a.length; i++) sum[i] = a[i]-b[i];
        return sum;
    }

    private boolean vectorComp(int[] a, int[] b){
        for(int i=0; i<a.length; i++){
            if(a[i]>b[i]) return false;
        }
        return true;
    }
}