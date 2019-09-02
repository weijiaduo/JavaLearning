package com.pattern.factorymethod.factory;

import com.pattern.factorymethod.product.Shape;

public interface ShapeFactory {

    /**
     * 产品工厂方法
     * @return 产品
     */
    Shape createShape();

}
