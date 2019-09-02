package com.pattern.factorymethod.product;

public class Circle extends Shape {

    public Circle(){}

    @Override
    public void draw() {
        System.out.println("Draw a circle shape!");
    }

}
