package com.zjlanyun.lyapp.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author: shun
 * @date: 2017-03-18 15:53
 * @Desc:
 */
@Entity
public class IrModel {
    @Id
    private long id;
    private String model;
    private String name;
    private String alias;
    private String info;
    private String primary_key;
    private String torder;
    private String check_cmd;
    private String uncheck_cmd;
    private String create_cmd;
    private String write_cmd;
    private String unlink_cmd;
    private long create_uid;
    private long write_uid;
    private String write_date;
    private String create_date;
    @Generated(hash = 1938792946)
    public IrModel(long id, String model, String name, String alias, String info,
            String primary_key, String torder, String check_cmd, String uncheck_cmd,
            String create_cmd, String write_cmd, String unlink_cmd, long create_uid,
            long write_uid, String write_date, String create_date) {
        this.id = id;
        this.model = model;
        this.name = name;
        this.alias = alias;
        this.info = info;
        this.primary_key = primary_key;
        this.torder = torder;
        this.check_cmd = check_cmd;
        this.uncheck_cmd = uncheck_cmd;
        this.create_cmd = create_cmd;
        this.write_cmd = write_cmd;
        this.unlink_cmd = unlink_cmd;
        this.create_uid = create_uid;
        this.write_uid = write_uid;
        this.write_date = write_date;
        this.create_date = create_date;
    }
    @Generated(hash = 1891277013)
    public IrModel() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getModel() {
        return this.model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAlias() {
        return this.alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getInfo() {
        return this.info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getPrimary_key() {
        return this.primary_key;
    }
    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }
    public String getTorder() {
        return this.torder;
    }
    public void setTorder(String torder) {
        this.torder = torder;
    }
    public String getCheck_cmd() {
        return this.check_cmd;
    }
    public void setCheck_cmd(String check_cmd) {
        this.check_cmd = check_cmd;
    }
    public String getUncheck_cmd() {
        return this.uncheck_cmd;
    }
    public void setUncheck_cmd(String uncheck_cmd) {
        this.uncheck_cmd = uncheck_cmd;
    }
    public String getCreate_cmd() {
        return this.create_cmd;
    }
    public void setCreate_cmd(String create_cmd) {
        this.create_cmd = create_cmd;
    }
    public String getWrite_cmd() {
        return this.write_cmd;
    }
    public void setWrite_cmd(String write_cmd) {
        this.write_cmd = write_cmd;
    }
    public String getUnlink_cmd() {
        return this.unlink_cmd;
    }
    public void setUnlink_cmd(String unlink_cmd) {
        this.unlink_cmd = unlink_cmd;
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
    public String getWrite_date() {
        return this.write_date;
    }
    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }
    public String getCreate_date() {
        return this.create_date;
    }
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

}
