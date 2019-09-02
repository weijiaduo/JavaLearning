package com.pattern.abstractfactory.product;

public abstract class AbstractComputer {

    /**
     * 价格
     */
    protected Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
