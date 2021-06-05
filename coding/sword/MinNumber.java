package com.wjd.sword;

import java.util.Arrays;

/**
 * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
 *
 */
public class MinNumber {

    public static void main(String[] args) {
        int[] nums1 = {1,6,5,4};
        System.out.println(printMinNumber1(nums1));
        int[] nums2 = {1,3,2,4,6};
        System.out.println(printMinNumber2(nums2));
    }

    public static String printMinNumber1(int[] numbers) {
        if (numbers == null) {
            return null;
        }

        String[] numStr = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            numStr[i] = Integer.toString(numbers[i]);
        }

        Arrays.sort(numStr, (String o1, String o2) -> {
                    String c1 = o1 + o2;
                    String c2 = o2 + o1;
                    return c1.compareTo(c2);
                }
        );

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numStr.length; i++) {
            sb.append(numStr[i]);
        }
        return sb.toString();
    }

    public static String printMinNumber2(int[] numbers) {
        if (numbers == null) {
            return null;
        }

        quickSort(numbers, 0, numbers.length-1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
        }
        return sb.toString();
    }

    private static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            int mid = partSort(numbers, start, end);
            quickSort(numbers, start, mid - 1);
            quickSort(numbers, mid + 1, end);
        }
    }

    private static int partSort(int[] numbers, int start, int end) {
        int lp = start, rp = end;
        int x = numbers[start];
        while (lp < rp) {
            while (lp < rp && compare(numbers[rp], x)) {
                rp--;
            }
            while (lp < rp && compare(x, numbers[lp])) {
                lp++;
            }
            if (lp < rp) {
                int temp = numbers[lp];
                numbers[lp] = numbers[rp];
                numbers[rp] = temp;
            }
        }
        if (lp != start) {
            int temp = numbers[lp];
            numbers[lp] = numbers[start];
            numbers[start] = temp;
        }
        return lp;
    }

    private static boolean compare(int num1, int num2) {
        String n1 = Integer.toString(num1) + Integer.toString(num2);
        String n2 = Integer.toString(num2) + Integer.toString(num1);
        return n1.compareTo(n2) >= 0 ? true : false;
    }

}
