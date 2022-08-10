package com.example.helloworldgrpc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingExample {

    public String hexToBinary(String hex) {
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        // or String s = BigInteger(hex, 16).toString(2);
        int decimal = Integer.parseInt("010110111",2);
        String hexStr = Integer.toString(decimal,16);
        return bin;
    }

    public void sortAndGetPreviousIndex(){
        int[] x = { 5, 1, 3, 9};
        String[] s = {"w", "p", "z","t"};
        String s1 = "ABCD";
        List<String> b = new ArrayList<>();
        char[] ch = s1.toCharArray();
        int[] dummy = new int[x.length];

//       for (int i = 0; i < x.length; i++) { dummy[i] = x[i]; }
        System.arraycopy(x, 0, dummy, 0, x.length);//copy of an array

        System.out.println("listed "+Arrays.toString(dummy));//To print an array
        Arrays.sort(x);//for sorting i.e.,{1,3,5,9}
        for (int k : x) {
            for (int j = 0; j < dummy.length; j++) {
                System.out.println(k);
                if (k == dummy[j]) {
                    //match
                    // int index = Arrays.asList(dummy).indexOf(dummy[j]);//To get the index of matched element
                    b.add(s[j]);
                    break;
                }
            }
        }
        System.out.println("array new "+b);
/* To get the sum of the values with previous sorted index flow way
       int sum = 0;
        for(int f : x){
            sum +=f;
        }
        System.out.println(sum);*/
    }

    public void performOperationDependingofStringElements() {
        int[] a = {9,5,7,3,1,4};
        String s = "ABBABA";
        char[] ch = s.toCharArray();
        System.out.println(Arrays.toString(ch));//[A, B, B, A, B, A]
        int plus=0,minus=0,total=0;
        if (ch.length == a.length) {
            for (int i=0; i<a.length; i++) {
                if (ch[i] == 'A') {
                    plus += a[i];
                } else if (ch[i] =='B') {
                    minus += a[i];
                }
            }
            System.out.println("checking "+plus+"..."+minus);//16...13
            total = (plus-minus)*100;
            System.out.println(total);//300
        }
    }

    public void getPossiblePermutations() {
//3 -> 1,2,3,(1,1)(1,2)(1,3)(2,2)(2,3)(3,3)(1,1,1)(1,1,2)(1,1,3)(1,2,2)(1,3,3)(2,3,3)(2,2,2)(3,3,3)(1,2,3)(2,3,3)(2,2,3)
        //abcd -> a b c d ab ac ad abc abd acd abcd bc bd bcd cd
        // n - number of elements & r - no. of elements in a combination
        //abc a b c aa ab ac bb bc cc aaa bbb ccc aab aac abc bbc bcc
        int n=3;
        int comb = 0;
        //for (int r=2; r>0; r--) {
        int r=3;
        while (r>0) {
            comb += factorial(n)/(factorial(r)*factorial(n-r));
            r--;
            System.out.println(comb);
        }
        System.out.println(comb);
    }

    public void getHighestValFromSortedArrayAndGetAnotherArrayElementWithSameIndex() {

        int[] a = {9,5,7,3,1,4};
        int[] b = {19,23,45,65,74,12};
        int[] dupA = new int[a.length];

        System.arraycopy(a,0,dupA,0,a.length);
        Arrays.sort(a);//[1,3,4,5,7,9]
        System.out.println(Arrays.toString(dupA)+Arrays.toString(a));

        int total=0;
        if (a.length == b.length) {
            for (int x=a.length-1; x>=a.length-3; x--) {//a.length-3 refers 3 last/highest places
                for (int i=0; i<dupA.length; i++) {
                    if (a[x] == dupA[i]) {
                        total += b[i];
                        break;
                    }
                }
            }
            System.out.println(total);
        }
    }
    public int factorial(int n){
        int total =1;
/*        for (int i =2; i<=n; i++) {
            total = total * i;
        }*/
        int k = 1;
        while(k<=n) {
            total = total*k;
            k++;
        }
        return total;
    }
    public void combination_nCr(int n, int r) {
        int comb = 0;
        comb = factorial(n)/(factorial(r)*factorial(n-r));
    }

    //tree shape with parent-child
    public void binaryHeap(){
        int[] a = {1,6,4,5,2,3};
        int left =0 ,right=0;
 /*int[] left = new int[a.length/2];  int[] right = new int[a.length/2];*/
        int[] siblings = new int[a.length/2];
        for (int i =0; i<a.length/2; i++){
            System.out.println("No"+i+"is "+a[i]);
            if((2*i+1)<a.length) { left= a[2*i+1];} else {left=0;}
            if((2*i+2)<a.length) { right = a[2*i+2];} else {right =0;}
            System.out.println(left+":"+right);
            if(a[i] == siblings[0]){
                System.out.println("Yes 1 ");
            }
            if(a[i] == siblings[1]){
                System.out.println("Yes 2 ");
            }
            int j=0;
            while(j<a.length/2){
                if (j == 0){
                    System.out.println("Yes 0");
                    siblings[j] = left;
                    siblings[j+1] =right;
                }
/*             if(j==2 && (siblings[1]==a[j])){
                            System.out.println("Yes 2");
                siblings[j] = left;
                siblings[j+1] =right;
            }*/
                j++;
            }
            System.out.println("siblings "+Arrays.toString(siblings));
        }
/*output : No0is 1
6:4
Yes 0
siblings [6, 4, 0]
No1is 6
5:2
Yes 1
Yes 0
siblings [5, 2, 0]
No2is 4
3:0
Yes 0
siblings [3, 0, 0]*/
    }
}
