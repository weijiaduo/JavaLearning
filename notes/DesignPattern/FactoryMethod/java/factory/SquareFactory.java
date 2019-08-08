package com.pattern.factorymethod.factory;

import com.pattern.factorymethod.product.Square;

public class SquareFactory implements ShapeFactory {

    @Override
    public Square createShape() {
        return new Square();
    }
}
