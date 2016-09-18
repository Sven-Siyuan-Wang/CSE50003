package com.example;

import java.util.Scanner;

/**
 * Created by WSY on 3/2/16.
 */
public class ComplexNum {
    public static void main(String[] args) {



        System.out.println("Please input your first complex number a+bi\nReal number a:");
        double[] first = input();
        System.out.println("Please input your second complex number a+bi\nReal number a:");
        double[] second = input();


        double[] add;
        double[] sub;
        double[] mul;
        double[] div;

        add = add(first,second);
        sub = sub(first, second);
        mul = mul(first, second);
        div = div(first, second);


        System.out.println("Add: "+print(add));
        System.out.println("Sub: "+print(sub));
        System.out.println("Mul: "+print(mul));
        System.out.println("Div: "+print(div));


    }

    private static double[] add(double[] first, double[] second) {
        return new double[]{first[0]+second[0],first[1]+second[1]};
    }
    private static double[] sub(double[] first, double[] second) {
        return new double[]{first[0]-second[0],first[1]-second[1]};
    }
    private static double[] mul(double[] first, double[] second) {
        return new double[]{first[0]*second[0]-first[1]*second[1],first[0]*second[1]+first[1]*second[0]};
    }

    private static double[] div(double[] first, double[] second){
        double denominator = second[0]*second[0]+second[1]*second[1];
        double[] second_conj = new double[]{second[0]/denominator,(0-second[1])/denominator};
        return mul(first,second_conj);
    }

    public static double[] input(){
        double a=0;
        double b=0;
        try{
            Scanner scanIn = new Scanner(System.in);
            a = scanIn.nextDouble();
            System.out.println("b for complex part bi:");
            b = scanIn.nextDouble();

        }catch(Exception ex){
            System.out.println("Invalid input!");

        }
        return new double[]{a,b};

    }
    public static String print(double[] c){
        c[0]=Math.round(c[0]*100.0)/100.0;
        c[1]=Math.round(c[1]*100.0)/100.0;
        if(c[1]>0) return ""+c[0]+"+"+c[1]+"i";
        else if(c[1]==0) return ""+c[0];
        else return ""+c[0]+c[1]+"i";
    }
}
