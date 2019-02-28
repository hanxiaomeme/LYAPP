package com.zjlanyun.lyapp.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 作者：韩笑 on 2017/7/17 0017 13:18
 * <p>
 * 作用：
 */

@Entity
public class IrExtend {
    @Id
    private long id;
    private long act_id;
    private String method;
    private long model_id;
    private String field;
    private String unique_field;
    private String filter_type;
    private boolean show_list;
    @Generated(hash = 1518963176)
    public IrExtend(long id, long act_id, String method, long model_id,
            String field, String unique_field, String filter_type,
            boolean show_list) {
        this.id = id;
        this.act_id = act_id;
        this.method = method;
        this.model_id = model_id;
        this.field = field;
        this.unique_field = unique_field;
        this.filter_type = filter_type;
        this.show_list = show_list;
    }
    @Generated(hash = 2036254933)
    public IrExtend() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getAct_id() {
        return this.act_id;
    }
    public void setAct_id(long act_id) {
        this.act_id = act_id;
    }
    public String getMethod() {
        return this.method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public long getModel_id() {
        return this.model_id;
    }
    public void setModel_id(long model_id) {
        this.model_id = model_id;
    }
    public String getField() {
        return this.field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getUnique_field() {
        return this.unique_field;
    }
    public void setUnique_field(String unique_field) {
        this.unique_field = unique_field;
    }
    public String getFilter_type() {
        return this.filter_type;
    }
    public void setFilter_type(String filter_type) {
        this.filter_type = filter_type;
    }
    public boolean getShow_list() {
        return this.show_list;
    }
    public void setShow_list(boolean show_list) {
        this.show_list = show_list;
    }

}
