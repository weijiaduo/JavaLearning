package com.pattern.abstractfactory.factory;

import com.pattern.abstractfactory.product.HuaweiComputer;
import com.pattern.abstractfactory.product.HuaweiMobilePhone;

public class HuaweiCompany extends AbstractCompany {

    @Override
    public HuaweiComputer createComputer() {
        return new HuaweiComputer();
    }

    @Override
    public HuaweiMobilePhone createMobilePhone() {
        return new HuaweiMobilePhone();
    }
}
