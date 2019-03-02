package com.zjlanyun.lyapp.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zjlanyun.lyapp.adapter.ExpandableItemAdapter;

/**
 * Created by MDZZ on 2019-02-28.
 */

public class Level1Item extends AbstractExpandableItem<LevelEndItem> implements MultiItemEntity {

    public String title;
    public String subTitle;

    public String menu_code;
    public String model_id;
    public int act_id;
    public boolean bEnd;
    public Level1Item( String title, String subTitle,String menu_code,String model_id,int act_id,boolean bEnd) {
        this.subTitle = subTitle;
        this.title = title;
        this.menu_code = menu_code;
        this.act_id = act_id;
    }
    public Level1Item( String title,String menu_code,int act_id,boolean bEnd) {
        this.title = title;
        this.menu_code = menu_code;
        this.act_id = act_id;
        this.bEnd = bEnd;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return  ExpandableItemAdapter.TYPE_LEVEL_1;
    }
}
