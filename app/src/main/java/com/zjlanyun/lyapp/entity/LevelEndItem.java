package com.zjlanyun.lyapp.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zjlanyun.lyapp.adapter.ExpandableItemAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class LevelEndItem implements MultiItemEntity {
    public String title;
    public String menu_code;
    public int act_id;

    public LevelEndItem(String title,String menu_code,int act_id) {
        this.menu_code = menu_code;
        this.act_id = act_id;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_2;
    }


}