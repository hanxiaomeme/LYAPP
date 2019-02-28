package com.zjlanyun.lyapp.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author: shun
 * @date: 2017-08-11 17:56
 * @Desc:
 */
@Entity
public class IrConfig {
    @Unique
    private String name;
    private String val;
    private String description;
    @Generated(hash = 1377600128)
    public IrConfig(String name, String val, String description) {
        this.name = name;
        this.val = val;
        this.description = description;
    }
    @Generated(hash = 1825976726)
    public IrConfig() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVal() {
        return this.val;
    }
    public void setVal(String val) {
        this.val = val;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
