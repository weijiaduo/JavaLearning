package com.wjd.sword;

import java.util.LinkedList;
import java.util.Random;

/**
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
 * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 * 我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。
 */
public class GetMedianInStream {

    private static LinkedList<Integer> list = new LinkedList<>();

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            int num = random.nextInt(1000);
            insert(num);
        }
        System.out.println(list);
        System.out.println(getMedian());
    }

    public static void insert(Integer num) {
        int i = list.size() - 1;
        for (; i >= 0 ; i--) {
            if (list.get(i) <= num) {
                break;
            }
        }
        list.add(i + 1, num);
    }

    public static Double getMedian() {
        Double res;
        int size = list.size();
        if (size % 2 == 0) {
            res = new Double((list.get(size / 2 - 1) + list.get(size / 2)) / 2.0);
        } else {
            res = new Double(list.get(list.size() / 2));
        }
        return res;
    }

}
