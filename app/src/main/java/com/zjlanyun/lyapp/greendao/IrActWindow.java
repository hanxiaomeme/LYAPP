package com.zjlanyun.lyapp.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author: shun
 * @date: 2017-03-17 13:46
 * @Desc:
 */
@Entity
public class IrActWindow {
    @Id
    private long id;
    private String name;
    private long model_id;
    private String view_mode;
    private String target;
    private String tdomain;
    private long create_uid;
    private long write_uid;
    private String create_date;
    private String write_date;
    @Generated(hash = 1670947798)
    public IrActWindow(long id, String name, long model_id, String view_mode,
            String target, String tdomain, long create_uid, long write_uid,
            String create_date, String write_date) {
        this.id = id;
        this.name = name;
        this.model_id = model_id;
        this.view_mode = view_mode;
        this.target = target;
        this.tdomain = tdomain;
        this.create_uid = create_uid;
        this.write_uid = write_uid;
        this.create_date = create_date;
        this.write_date = write_date;
    }
    @Generated(hash = 261585196)
    public IrActWindow() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getModel_id() {
        return this.model_id;
    }
    public void setModel_id(long model_id) {
        this.model_id = model_id;
    }
    public String getView_mode() {
        return this.view_mode;
    }
    public void setView_mode(String view_mode) {
        this.view_mode = view_mode;
    }
    public String getTarget() {
        return this.target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public String getTdomain() {
        return this.tdomain;
    }
    public void setTdomain(String tdomain) {
        this.tdomain = tdomain;
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



    }


    

