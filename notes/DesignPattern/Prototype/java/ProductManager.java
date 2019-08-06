package com.pattern.prototype;

import java.util.HashMap;

public class ProductManager {

    // 保存产品原型实例
    private HashMap<String, Product> products = new HashMap<>();

    /**
     * 注册产品原型实例
     * @param name 原型名称
     * @param product 原型实例
     */
    public synchronized void register(String name, Product product) {
        products.put(name, product);
    }

    /**
     * 通过原型名称获取产品新实例对象
     * @param name 产品原型名称
     * @return 产品新实例对象
     */
    public Product getProduct(String name) {
        Product product = products.get(name);
        return product.createClone();
    }

}
