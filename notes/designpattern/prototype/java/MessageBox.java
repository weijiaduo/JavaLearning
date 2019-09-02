package com.pattern.prototype;

/***
 * 为输出字符串添加输出框，输出效果如下：
 *
 * ***********************
 * * Warning box product *
 * ***********************
 *
 */
public class MessageBox implements Product {

    // 输出框符号
    private char ch;

    public MessageBox(char ch) {
        this.ch = ch;
    }

    /**
     * 为输出字符串添加一个输出框
     * @param s 输出字符串
     */
    @Override
    public void use(String s) {
        int len = s.getBytes().length;
        for (int i = 0; i < len + 4; i++) {
            System.out.print(ch);
        }
        System.out.println();
        System.out.println(ch + " " + s + " " + ch);
        for (int i = 0; i < len + 4; i++) {
            System.out.print(ch);
        }
        System.out.println();
    }

    @Override
    public Product createClone() {
        Product p = null;
        try {
            p = (Product) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }
}
