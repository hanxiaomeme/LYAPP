package com.zjlanyun.lyapp.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.activity.TreeActivity;
import com.zjlanyun.lyapp.entity.Level0Item;
import com.zjlanyun.lyapp.entity.Level1Item;
import com.zjlanyun.lyapp.entity.LevelEndItem;

import java.util.List;
import java.util.logging.Logger;

import es.dmoral.toasty.Toasty;

import static com.zjlanyun.lyapp.utils.UtilConstants.ACTIVITY_INTENT_ACTID;
import static com.zjlanyun.lyapp.utils.UtilConstants.ACTIVITY_INTENT_BILLSNAME;
import static com.zjlanyun.lyapp.utils.UtilConstants.ACTIVITY_INTENT_MODELID;

/**
 * Created by MDZZ on 2019-02-27.
 */

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();
    public static final int TYPE_LEVEL_0 = 0;//主目录
    public static final int TYPE_LEVEL_1 = 1;//一级子目录
    public static final int TYPE_LEVEL_2 = 2;//二级子目录
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
        addItemType(TYPE_LEVEL_2, R.layout.item_expandable_lv2);
    }

    @Override
    protected void convert(final BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()){
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                holder.setText(R.id.title, lv0.title)
                        .setText(R.id.sub_title, lv0.subTitle);
                if (!lv0.bEnd)
                    holder.setImageResource(R.id.iv, lv0.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);
                else
                    holder.setImageResource(R.id.iv,0);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (!lv0.bEnd){
                            if (lv0.isExpanded()) {
                                collapse(pos);
                            }
                            else {
                                expand(pos);
                            }
                        }
                        else{
                            Toasty.info(mContext,"进入列表页",Toast.LENGTH_LONG,true).show();
                            Intent intent = new Intent(mContext, TreeActivity.class);
                            intent.putExtra(ACTIVITY_INTENT_BILLSNAME,lv0.title);
                            intent.putExtra(ACTIVITY_INTENT_ACTID,lv0.act_id);
                            mContext.startActivity(intent);
                        }



                    }


                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.title, lv1.title )
                        .setText(R.id.sub_title, lv1.subTitle);
                if (!lv1.bEnd)
                    holder.setImageResource(R.id.iv, lv1.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);
                else
                    holder.setImageResource(R.id.iv,0);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getAdapterPosition();
                        if (!lv1.bEnd){

                            if (lv1.isExpanded()) {
                                collapse(pos);
                            }
                            else {
                                expand(pos);
                            }
                        }
                        else{
                            Toasty.info(mContext,"进入列表页",Toast.LENGTH_LONG,true).show();
                            Intent intent = new Intent(mContext, TreeActivity.class);
                            intent.putExtra(ACTIVITY_INTENT_BILLSNAME,lv1.title);
                            intent.putExtra(ACTIVITY_INTENT_ACTID,lv1.act_id);
                            mContext.startActivity(intent);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_2:
                final LevelEndItem lvEnd = (LevelEndItem) item;
                holder.setText(R.id.title, lvEnd.title);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toasty.info(mContext,"进入列表页",Toast.LENGTH_LONG,true).show();
                        Intent intent = new Intent(mContext, TreeActivity.class);
                        intent.putExtra(ACTIVITY_INTENT_BILLSNAME,lvEnd.title);
                        intent.putExtra(ACTIVITY_INTENT_ACTID,lvEnd.act_id);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }


}
