package com.zjlanyun.lyapp.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author: shun
 * @date: 2017-03-16 8:43
 * @Desc:
 */
@Entity
public class IrUiMenu {
    @Id
    private long id;
    private String name;
    private long parent_id;
    private boolean active;
    private long act_id;
    private long sequence;
    private long create_uid;
    private long write_uid;
    private String create_date;
    private String write_date;
    private long trantype_id;
    private boolean multi_level_approve;
    private int packet_id;
    private int menu_type;
    @Generated(hash = 279059265)
    public IrUiMenu(long id, String name, long parent_id, boolean active,
            long act_id, long sequence, long create_uid, long write_uid,
            String create_date, String write_date, long trantype_id,
            boolean multi_level_approve, int packet_id, int menu_type) {
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
        this.active = active;
        this.act_id = act_id;
        this.sequence = sequence;
        this.create_uid = create_uid;
        this.write_uid = write_uid;
        this.create_date = create_date;
        this.write_date = write_date;
        this.trantype_id = trantype_id;
        this.multi_level_approve = multi_level_approve;
        this.packet_id = packet_id;
        this.menu_type = menu_type;
    }
    @Generated(hash = 1842355676)
    public IrUiMenu() {
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
    public long getParent_id() {
        return this.parent_id;
    }
    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }
    public boolean getActive() {
        return this.active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public long getAct_id() {
        return this.act_id;
    }
    public void setAct_id(long act_id) {
        this.act_id = act_id;
    }
    public long getSequence() {
        return this.sequence;
    }
    public void setSequence(long sequence) {
        this.sequence = sequence;
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
    public long getTrantype_id() {
        return this.trantype_id;
    }
    public void setTrantype_id(long trantype_id) {
        this.trantype_id = trantype_id;
    }
    public boolean getMulti_level_approve() {
        return this.multi_level_approve;
    }
    public void setMulti_level_approve(boolean multi_level_approve) {
        this.multi_level_approve = multi_level_approve;
    }
    public int getPacket_id() {
        return this.packet_id;
    }
    public void setPacket_id(int packet_id) {
        this.packet_id = packet_id;
    }
    public int getMenu_type() {
        return this.menu_type;
    }
    public void setMenu_type(int menu_type) {
        this.menu_type = menu_type;
    }
  
}
