package com.zjlanyun.lyapp.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zjlanyun.lyapp.adapter.ExpandableItemAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */
public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {
    public String title;
    public String subTitle;
    public String menu_code;
    public String model_id;
    public int act_id;
    public boolean bEnd;
    public Level0Item(String title, String subTitle,String menu_code,String model_id,int act_id,boolean bEnd) {
        this.subTitle = subTitle;
        this.title = title;
        this.menu_code = menu_code;
        this.act_id = act_id;
        this.bEnd = bEnd;
    }
    public Level0Item(String title,String menu_code,int act_id,boolean bEnd) {
        this.title = title;
        this.menu_code = menu_code;
        this.act_id = act_id;
        this.bEnd = bEnd;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
