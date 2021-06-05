package com.wjd.sword;

/**
 * 求整数1～n中1出现的次数
 *
 */
public class NumberOf1Between1AndN {

    public static void main(String[] args) {
        int n = 10000896;
        long start = System.currentTimeMillis();
        System.out.println(getNumberOf1Between1AndN(n));
        long end = System.currentTimeMillis();
        System.out.println("暴力时间： " + (end-start) + " ms");

        start = System.currentTimeMillis();
        System.out.println(getNumberOf1Between1AndNByDigit(n));
        end = System.currentTimeMillis();
        System.out.println("快速时间： " + (end-start) + " ms");
    }

    /**
     * 暴力法
     *
     * @param n
     * @return
     */
    public static int getNumberOf1Between1AndN(int n){
        int num = 0;
        for (int i=1; i<=n ;i++){
            num+=getNumberOf1(i);
        }
        return num;
    }

    public static int getNumberOf1(int n){
        int count = 0;
        while (n > 0){
            if (n%10 == 1){
                count++;
            }
            n/=10;
        }
        return count;
    }

    /**
     * 分别计算个位，十位，百位...上1的个数
     *
     * @param n
     * @return
     */
    public static int getNumberOf1Between1AndNByDigit(int n) {
        int count = 0;
        for (int i = 1; i <= n; i *= 10) {
            /**
             * 例如 n=1204，
             * 查找十位时，rn=4，mn=0，ln=12;
             * 查找百位时，rn=4，mn=2，ln=1;
             */
            int rn = n % i, mn = n / i % 10, ln = n / i / 10;

            /**
             * 若mn=0，则以mn这个位置为1的数量有
             * ln*i个，例如 k=(0~ln-1)1(.*)
             */
            if (mn == 0) {
                count += ln * i;
            }

            /**
             * 若mn=1，则以mn这个位置为1的数量有
             * ln*i+rn+1，例如 k=(0~ln-1)1(0*)或 k=(0*)1(0~rn)
             */
            if (mn == 1) {
                count += ln * i + rn + 1;
            }

            /**
             * 若mn>=2，则以mn这个位置为1的数量有
             * (ln+1)*i，例如 k=(0~ln)1(.*)
             */
            if (mn >= 2) {
                count += (ln + 1) * i;
            }
        }
        return count;
    }

}
