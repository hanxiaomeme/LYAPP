package com.zjlanyun.lyapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.greendao.IrModelFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MDZZ on 2019-03-02.
 */

public class TreeAdapter extends BaseQuickAdapter<HashMap<String, Object>, BaseViewHolder> {
    private ArrayList<HashMap<String, Object>> mList;
    private LayoutInflater layoutInflater;
    private List<IrModelFields> irModelFieldsList;
    private String title;


    public TreeAdapter(int layoutResId, @Nullable List<HashMap<String, Object>> data, List<IrModelFields> irModelFieldsList) {

        super(layoutResId, data);
        this.irModelFieldsList = irModelFieldsList;
    }
    public TreeAdapter(int layoutResId, @Nullable List<HashMap<String, Object>> data) {
        super(layoutResId, data);
        this.irModelFieldsList = irModelFieldsList;
    }

    public void setirModelFieldsList(List<IrModelFields> irModelFieldsList) {
        this.irModelFieldsList = irModelFieldsList;

    }

    public void setTitle(String title) {
        this.title = title;
    }




    @Override
    protected void convert(BaseViewHolder helper, HashMap<String, Object> item) {
        int index = 1; //当前布局列
        RelativeLayout layout = null; //行布局
        ((LinearLayout) helper.getView(R.id.item)).removeAllViews();
        ((TextView)helper.getView(R.id.tx_no)).setText("NO."+(helper.getAdapterPosition()+1)+"  "+title);
        ((TextView)helper.getView(R.id.tx_title)).setText(title);
        if (item.containsKey("FStatus") && !item.get("FStatus").toString().equals("已审核") ){
            ((TextView)helper.getView(R.id.tx_title)).setVisibility(View.VISIBLE);
        }
        else {
            ((TextView)helper.getView(R.id.tx_title)).setVisibility(View.GONE);
        }
        for (int i = 0; i < irModelFieldsList.size(); i++) {
            if (irModelFieldsList.get(i).getVisiable()) {
                String value = item.get(irModelFieldsList.get(i).getName()).toString();
                //字段类型判断
                switch (irModelFieldsList.get(i).getTtype()) {
                    case "date":
                        value = value.equals("") ? "" : value.substring(0, 10);
                        break;
                    default:
                        break;
                }
                //判断字段是否有说明
                String title = "";
                if (!irModelFieldsList.get(i).getField_desc().equals("")) {
                    title = irModelFieldsList.get(i).getField_desc() + "：";
                }
                //跨2列
                if (irModelFieldsList.get(i).getColspan() == 2) {
                    TextView textView = new TextView(mContext);
                    textView.setText(title + value);
                    ((LinearLayout) helper.getView(R.id.item)).addView(textView);
                    index = 1;
                } else {
                    if (index == 1) {
                        //第1列布局
                        layout = new RelativeLayout(mContext);
                        TextView tv1 = new TextView(mContext);
                        layout.addView(tv1);
                        tv1.setText(title + value);
                        ((LinearLayout) helper.getView(R.id.item)).addView(layout);
                        index = 2;
                    } else {
                        //第2列布局
                        TextView tv2 = new TextView(mContext);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layout.addView(tv2, lp);
                        tv2.setText(title + value);
                        index = 1;

                    }
                }
            }
        }

    }


}
