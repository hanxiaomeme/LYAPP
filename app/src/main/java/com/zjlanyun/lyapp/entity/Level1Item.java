package com.zjlanyun.lyapp.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zjlanyun.lyapp.adapter.ExpandableItemAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Level1Item  implements MultiItemEntity {
    public String model_id;
    public String model_name;

    public Level1Item(String model_id, String model_name) {
        this.model_id = model_id;
        this.model_name = model_name;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }

}