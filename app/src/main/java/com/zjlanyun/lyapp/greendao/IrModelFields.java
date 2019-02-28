package com.zjlanyun.lyapp.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author: shun
 * @date: 2017-03-17 13:53
 * @Desc:
 */
@Entity
public class IrModelFields {
    @Id
    private long id;
    private long model_id;
    private String name;
    private String field_desc;
    private String ttype;
    private String relation;
    private String relation_field;
    private String rel_display_field;
    private String rel_change_field;
    private String change_save_field;
    private String change_display_field;
    private String depend_field;
    private String select_cmd;
    private boolean tcompute;
    private String compute_cmd;
    private boolean readonly;
    private boolean required;
    private boolean scan;
    private long relation_model_id;
    private boolean store;
    private String def_value;
    private boolean primary_key;
    private String tdomain;
    private long sequence;
    private long colspan;
    private String view_type;
    private boolean visiable;
    private long cache_min;
    private long create_uid;
    private long write_uid;
    private String create_date;
    private String write_date;
    private String location;
    private boolean is_total;
    private boolean is_show_more_list;
    private boolean show_in_list;
    @Generated(hash = 1662331256)
    public IrModelFields(long id, long model_id, String name, String field_desc,
            String ttype, String relation, String relation_field,
            String rel_display_field, String rel_change_field,
            String change_save_field, String change_display_field,
            String depend_field, String select_cmd, boolean tcompute,
            String compute_cmd, boolean readonly, boolean required, boolean scan,
            long relation_model_id, boolean store, String def_value,
            boolean primary_key, String tdomain, long sequence, long colspan,
            String view_type, boolean visiable, long cache_min, long create_uid,
            long write_uid, String create_date, String write_date, String location,
            boolean is_total, boolean is_show_more_list, boolean show_in_list) {
        this.id = id;
        this.model_id = model_id;
        this.name = name;
        this.field_desc = field_desc;
        this.ttype = ttype;
        this.relation = relation;
        this.relation_field = relation_field;
        this.rel_display_field = rel_display_field;
        this.rel_change_field = rel_change_field;
        this.change_save_field = change_save_field;
        this.change_display_field = change_display_field;
        this.depend_field = depend_field;
        this.select_cmd = select_cmd;
        this.tcompute = tcompute;
        this.compute_cmd = compute_cmd;
        this.readonly = readonly;
        this.required = required;
        this.scan = scan;
        this.relation_model_id = relation_model_id;
        this.store = store;
        this.def_value = def_value;
        this.primary_key = primary_key;
        this.tdomain = tdomain;
        this.sequence = sequence;
        this.colspan = colspan;
        this.view_type = view_type;
        this.visiable = visiable;
        this.cache_min = cache_min;
        this.create_uid = create_uid;
        this.write_uid = write_uid;
        this.create_date = create_date;
        this.write_date = write_date;
        this.location = location;
        this.is_total = is_total;
        this.is_show_more_list = is_show_more_list;
        this.show_in_list = show_in_list;
    }
    @Generated(hash = 469679025)
    public IrModelFields() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getModel_id() {
        return this.model_id;
    }
    public void setModel_id(long model_id) {
        this.model_id = model_id;
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
    public String getTtype() {
        return this.ttype;
    }
    public void setTtype(String ttype) {
        this.ttype = ttype;
    }
    public String getRelation() {
        return this.relation;
    }
    public void setRelation(String relation) {
        this.relation = relation;
    }
    public String getRelation_field() {
        return this.relation_field;
    }
    public void setRelation_field(String relation_field) {
        this.relation_field = relation_field;
    }
    public String getRel_display_field() {
        return this.rel_display_field;
    }
    public void setRel_display_field(String rel_display_field) {
        this.rel_display_field = rel_display_field;
    }
    public String getRel_change_field() {
        return this.rel_change_field;
    }
    public void setRel_change_field(String rel_change_field) {
        this.rel_change_field = rel_change_field;
    }
    public String getChange_save_field() {
        return this.change_save_field;
    }
    public void setChange_save_field(String change_save_field) {
        this.change_save_field = change_save_field;
    }
    public String getChange_display_field() {
        return this.change_display_field;
    }
    public void setChange_display_field(String change_display_field) {
        this.change_display_field = change_display_field;
    }
    public String getDepend_field() {
        return this.depend_field;
    }
    public void setDepend_field(String depend_field) {
        this.depend_field = depend_field;
    }
    public String getSelect_cmd() {
        return this.select_cmd;
    }
    public void setSelect_cmd(String select_cmd) {
        this.select_cmd = select_cmd;
    }
    public boolean getTcompute() {
        return this.tcompute;
    }
    public void setTcompute(boolean tcompute) {
        this.tcompute = tcompute;
    }
    public String getCompute_cmd() {
        return this.compute_cmd;
    }
    public void setCompute_cmd(String compute_cmd) {
        this.compute_cmd = compute_cmd;
    }
    public boolean getReadonly() {
        return this.readonly;
    }
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
    public boolean getRequired() {
        return this.required;
    }
    public void setRequired(boolean required) {
        this.required = required;
    }
    public boolean getScan() {
        return this.scan;
    }
    public void setScan(boolean scan) {
        this.scan = scan;
    }
    public long getRelation_model_id() {
        return this.relation_model_id;
    }
    public void setRelation_model_id(long relation_model_id) {
        this.relation_model_id = relation_model_id;
    }
    public boolean getStore() {
        return this.store;
    }
    public void setStore(boolean store) {
        this.store = store;
    }
    public String getDef_value() {
        return this.def_value;
    }
    public void setDef_value(String def_value) {
        this.def_value = def_value;
    }
    public boolean getPrimary_key() {
        return this.primary_key;
    }
    public void setPrimary_key(boolean primary_key) {
        this.primary_key = primary_key;
    }
    public String getTdomain() {
        return this.tdomain;
    }
    public void setTdomain(String tdomain) {
        this.tdomain = tdomain;
    }
    public long getSequence() {
        return this.sequence;
    }
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
    public long getColspan() {
        return this.colspan;
    }
    public void setColspan(long colspan) {
        this.colspan = colspan;
    }
    public String getView_type() {
        return this.view_type;
    }
    public void setView_type(String view_type) {
        this.view_type = view_type;
    }
    public boolean getVisiable() {
        return this.visiable;
    }
    public void setVisiable(boolean visiable) {
        this.visiable = visiable;
    }
    public long getCache_min() {
        return this.cache_min;
    }
    public void setCache_min(long cache_min) {
        this.cache_min = cache_min;
    }
    public long getCreate_uid() {
        return this.create_uid;
    }
    public void setCreate_uid(long create_uid) {
        this.create_uid = create_uid;
    }
    public long getWrite_uid() {
        return this.write_uid;
    }
    public void setWrite_uid(long write_uid) {
        this.write_uid = write_uid;
    }
    public String getCreate_date() {
        return this.create_date;
    }
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
    public String getWrite_date() {
        return this.write_date;
    }
    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public boolean getIs_total() {
        return this.is_total;
    }
    public void setIs_total(boolean is_total) {
        this.is_total = is_total;
    }
    public boolean getIs_show_more_list() {
        return this.is_show_more_list;
    }
    public void setIs_show_more_list(boolean is_show_more_list) {
        this.is_show_more_list = is_show_more_list;
    }
    public boolean getShow_in_list() {
        return this.show_in_list;
    }
    public void setShow_in_list(boolean show_in_list) {
        this.show_in_list = show_in_list;
    }

}
