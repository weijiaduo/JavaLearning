package com.pattern.prototype;

public interface Product extends Cloneable {

    /**
     * 产品使用的方法
     * @param s
     */
    void use(String s);

    /**
     * 产品克隆的方法
     * @return 克隆出来的新对象
     */
    Product createClone();
}
