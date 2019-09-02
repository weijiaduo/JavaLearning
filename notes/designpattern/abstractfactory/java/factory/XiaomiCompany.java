package com.pattern.abstractfactory.factory;

import com.pattern.abstractfactory.product.XiaomiComputer;
import com.pattern.abstractfactory.product.XiaomiMobilePhone;

public class XiaomiCompany extends AbstractCompany {

    @Override
    public XiaomiComputer createComputer() {
        return new XiaomiComputer();
    }

    @Override
    public XiaomiMobilePhone createMobilePhone() {
        return new XiaomiMobilePhone();
    }
}
