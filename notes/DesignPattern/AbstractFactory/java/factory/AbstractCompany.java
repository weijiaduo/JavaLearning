package com.pattern.abstractfactory.factory;

import com.pattern.abstractfactory.product.AbstractComputer;
import com.pattern.abstractfactory.product.AbstractMobilePhone;

public abstract class AbstractCompany {

    /**
     * 生产电脑产品
     *
     * @return 电脑产品
     */
    public abstract AbstractComputer createComputer();

    /**
     * 生产手机产品
     *
     * @return 手机产品
     */
    public abstract AbstractMobilePhone createMobilePhone();

}
