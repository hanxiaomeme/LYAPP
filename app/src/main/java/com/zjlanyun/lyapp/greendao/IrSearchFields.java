package com.zjlanyun.lyapp.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author: shun
 * @date: 2017-05-11 16:11
 * @Desc:
 */
@Entity
public class IrSearchFields {
    @Id
    private long id;
    private long act_id;
    private String name;
    private String field_desc;
    private boolean scan;
    private boolean auto_submit;
    private String operator;
    private long sequence;
    private String field_type;
    private String field_rel_model_id;
    private String rel_table_field_name;
    @Generated(hash = 1262914959)
    public IrSearchFields(long id, long act_id, String name, String field_desc,
            boolean scan, boolean auto_submit, String operator, long sequence,
            String field_type, String field_rel_model_id,
            String rel_table_field_name) {
        this.id = id;
        this.act_id = act_id;
        this.name = name;
        this.field_desc = field_desc;
        this.scan = scan;
        this.auto_submit = auto_submit;
        this.operator = operator;
        this.sequence = sequence;
        this.field_type = field_type;
        this.field_rel_model_id = field_rel_model_id;
        this.rel_table_field_name = rel_table_field_name;
    }
    @Generated(hash = 628248968)
    public IrSearchFields() {
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getField_desc() {
        return this.field_desc;
    }
    public void setField_desc(String field_desc) {
        this.field_desc = field_desc;
    }
    public boolean getScan() {
        return this.scan;
    }
    public void setScan(boolean scan) {
        this.scan = scan;
    }
    public boolean getAuto_submit() {
        return this.auto_submit;
    }
    public void setAuto_submit(boolean auto_submit) {
        this.auto_submit = auto_submit;
    }
    public String getOperator() {
        return this.operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public long getSequence() {
        return this.sequence;
    }
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
    public String getField_type() {
        return this.field_type;
    }
    public void setField_type(String field_type) {
        this.field_type = field_type;
    }
    public String getField_rel_model_id() {
        return this.field_rel_model_id;
    }
    public void setField_rel_model_id(String field_rel_model_id) {
        this.field_rel_model_id = field_rel_model_id;
    }
    public String getRel_table_field_name() {
        return this.rel_table_field_name;
    }
    public void setRel_table_field_name(String rel_table_field_name) {
        this.rel_table_field_name = rel_table_field_name;
    }


}
