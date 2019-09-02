package com.pattern.prototype;

public class PropotypeTest {

    public static void main(String[] args) {
        // 创建产品管理器
        ProductManager manager = new ProductManager();

        // 创建产品原型
        UnderlinePen underlinePen = new UnderlinePen('~');
        MessageBox messageBox = new MessageBox('*');

        // 注册产品原型到管理器
        manager.register("Strong Message", underlinePen);
        manager.register("Warning Box", messageBox);

        // 测试使用产品原型获取新对象
        Product p1 = manager.getProduct("Strong Message");
        p1.use("Strong message product");

        Product p2 = manager.getProduct("Warning Box");
        p2.use("Warning box product");
    }

}
