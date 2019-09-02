package com.pattern.factorymethod.factory;

import com.pattern.factorymethod.product.Circle;

public class CircleFactory implements ShapeFactory {

    @Override
    public Circle createShape() {
        return new Circle();
    }

}
