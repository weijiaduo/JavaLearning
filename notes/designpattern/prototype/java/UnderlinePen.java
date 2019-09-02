package com.pattern.prototype;

/**
 * 为输出字符串添加下划线，输出效果如下：
 *
 * "Strong message product"
 * ~~~~~~~~~~~~~~~~~~~~~~~~
 *
 */
public class UnderlinePen implements Product {

    // 下划符号
    private char ch;

    public UnderlinePen(char ch) {
        this.ch = ch;
    }

    /**
     * 输出字符串左右添加双引号，下边添加下划符号
     * @param s 输出字符串
     */
    @Override
    public void use(String s) {
        int len = s.getBytes().length;
        System.out.println("\"" + s + "\"");
        for (int i = 0; i < len + 2; i++) {
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
