package com.jwt.springsecurity.config;


import java.util.Arrays;

// [1,2,1,3,4]
// [24,12,24,8,6]
public class Test {
    public static void main(String args[]){
        int[] input = {1,2,1,3,4};

//        prefix: [1,2,2,6,24] suffix: [24,24,12,12,4]
        int length = input.length;
        int[] output = new int[length];

        int[] inputSuffix = new int[length];
        inputSuffix[length-1] = input[length-1];

        for(int i=length-2; i>=0; i--){
            inputSuffix[i] = input[i]*inputSuffix[i+1];
        }

        System.out.println(Arrays.toString(inputSuffix));

        int[] inputPrefix = new int[length];
        inputPrefix[0] = input[0];

        for(int i=1; i<length; i++){
            inputPrefix[i] = input[i]*inputPrefix[i-1];
        }

        System.out.println(Arrays.toString(inputPrefix));

        output[0] = inputSuffix[1];
        output[length-1] = inputPrefix[length-2];
        for(int i=1; i<=length-2; i++){
            output[i] = inputPrefix[i-1] * inputSuffix[i+1];
        }


        System.out.println(Arrays.toString(output));



    }
}
