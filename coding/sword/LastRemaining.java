package com.wjd.sword;

import java.util.Arrays;

/**
 * 有个游戏是这样的:首先,让小朋友们围成一个大圈。
 * 然后,他随机指定一个数m,让编号为0的小朋友开始报数。
 * 每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,
 * 并且不再回到圈中,从他的下一个小朋友开始,继续0...m-1报数....这样下去....
 * 直到剩下最后一个小朋友,可以不用表演,并且拿到牛客名贵的“名侦探柯南”典藏版(名额有限哦!!^_^)。
 * 请你试着想下,哪个小朋友会得到这份礼品呢？(注：小朋友的编号是从0到n-1)
 *
 */
public class LastRemaining {

    public static void main(String[] args) {
        int n = 15, m = 2;
        System.out.println(lastRemaining(n, m));
        System.out.println(lastRemaining2(n, m));
    }

    public static int lastRemaining(int n, int m) {
        if (n <= 0 || m <= 0) {
            return -1;
        }

        int[] numbers = new int[n];
        Arrays.fill(numbers, 1);
        int remain = n, index = 0;
        for (; remain > 0; remain--) {
            for (int i = 0; ; ) {
                if (numbers[index] == 1) {
                    if (++i == m) {
                        break;
                    }
                }
                index = (index + 1) % numbers.length;
            }
            numbers[index] = 0;
        }
        return index;
    }

    public static int lastRemaining2(int n, int m) {
        if (n <= 0 || m <= 0) {
            return -1;
        }

        int last = 0;
        for (int i = 2; i <= n; i++) {
            last = (last + m) % i;
        }

        return last;
    }
}
