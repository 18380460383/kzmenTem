package com.kzmen.sczxjf;

import com.kzmen.sczxjf.bean.entitys.SelectEntity;
import com.kzmen.sczxjf.interfaces.EnIdentifyShow;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/31.
 */
public abstract class EnMoneySelectEntity extends SelectEntity implements EnIdentifyShow{
    public abstract float getMoney();
}
