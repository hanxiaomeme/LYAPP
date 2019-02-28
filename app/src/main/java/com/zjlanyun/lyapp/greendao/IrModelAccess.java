package com.zjlanyun.lyapp.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author: shun
 * @date: 2017-03-18 17:01
 * @Desc:
 */
@Entity
public class IrModelAccess {
    @Id
    private long id;
    private long model_id;
    private String name;
    private boolean perm_read;
    private boolean perm_write;
    private boolean perm_create;
    private boolean perm_unlink;
    private boolean perm_check;
    private boolean perm_uncheck;
    private long create_uid;
    private long write_uid;
    private String create_date;
    private String write_date;
    private long group_id;
    private long user_id;
    @Generated(hash = 2136439497)
    public IrModelAccess(long id, long model_id, String name, boolean perm_read,
            boolean perm_write, boolean perm_create, boolean perm_unlink,
            boolean perm_check, boolean perm_uncheck, long create_uid,
            long write_uid, String create_date, String write_date, long group_id,
            long user_id) {
        this.id = id;
        this.model_id = model_id;
        this.name = name;
        this.perm_read = perm_read;
        this.perm_write = perm_write;
        this.perm_create = perm_create;
        this.perm_unlink = perm_unlink;
        this.perm_check = perm_check;
        this.perm_uncheck = perm_uncheck;
        this.create_uid = create_uid;
        this.write_uid = write_uid;
        this.create_date = create_date;
        this.write_date = write_date;
        this.group_id = group_id;
        this.user_id = user_id;
    }
    @Generated(hash = 1686194663)
    public IrModelAccess() {
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
    public boolean getPerm_read() {
        return this.perm_read;
    }
    public void setPerm_read(boolean perm_read) {
        this.perm_read = perm_read;
    }
    public boolean getPerm_write() {
        return this.perm_write;
    }
    public void setPerm_write(boolean perm_write) {
        this.perm_write = perm_write;
    }
    public boolean getPerm_create() {
        return this.perm_create;
    }
    public void setPerm_create(boolean perm_create) {
        this.perm_create = perm_create;
    }
    public boolean getPerm_unlink() {
        return this.perm_unlink;
    }
    public void setPerm_unlink(boolean perm_unlink) {
        this.perm_unlink = perm_unlink;
    }
    public boolean getPerm_check() {
        return this.perm_check;
    }
    public void setPerm_check(boolean perm_check) {
        this.perm_check = perm_check;
    }
    public boolean getPerm_uncheck() {
        return this.perm_uncheck;
    }
    public void setPerm_uncheck(boolean perm_uncheck) {
        this.perm_uncheck = perm_uncheck;
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
    public long getGroup_id() {
        return this.group_id;
    }
    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }
    public long getUser_id() {
        return this.user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

}
