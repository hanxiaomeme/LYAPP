package com.zjlanyun.lyapp.adapter;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.entity.Level0Item;
import com.zjlanyun.lyapp.entity.Level1Item;

import java.util.List;

/**
 * Created by MDZZ on 2019-02-27.
 */

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;//主目录
    public static final int TYPE_LEVEL_1 = 1;//一级子目录
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
    }

    @Override
    protected void convert(final BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()){
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                holder.setText(R.id.title, lv0.title)
                        .setText(R.id.sub_title, lv0.subTitle);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.tv, "ID: "+lv1.model_id + "\nName: " + lv1.model_name);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        int pos = holder.getAdapterPosition();
//                        // 先获取到当前 item 的父 positon，再移除自己
//                        int positionAtAll = getParentPositionInAll(pos);
//                        remove(pos);
//                        if (positionAtAll != -1) {
//                            IExpandable multiItemEntity = (IExpandable) getData().get(positionAtAll);
//                            if (!hasSubItems(multiItemEntity)) {
//                                remove(positionAtAll);
//                            }
//                        }
                    }
                });
                break;

        }
    }
}
