package com.zjlanyun.lyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.ToastUtils;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.greendao.IrModelFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: shun
 * @date: 2017-03-31 8:12
 * @Desc:
 */

public class Many2OneAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, Object>> mList;
    private LayoutInflater layoutInflater;
    private List<IrModelFields> irModelFieldsList;
    private Context mContext;

    public final class ListItemView {
        public LinearLayout item;
        public View view;
    }

    public Many2OneAdapter(Context context, ArrayList<HashMap<String, Object>> mList, List<IrModelFields> irModelFieldsList) {
        this.mList = mList;
        this.irModelFieldsList = irModelFieldsList;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView listItemView;
        if (convertView == null) {
            listItemView = new ListItemView();
            convertView = layoutInflater.inflate(R.layout.many2one_item, null);
            listItemView.item = (LinearLayout) convertView.findViewById(R.id.item);
            listItemView.view = layoutInflater.inflate(R.layout.many2one_item_2col, null);
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
        listItemView.item.removeAllViews();

        int index = 1; //当前布局列
        RelativeLayout layout = null; //行布局
        for (int i = 0; i < irModelFieldsList.size(); i++) {
            if (irModelFieldsList.get(i).getVisiable()) {
                String value = "";
                if (mList.get(position).get(irModelFieldsList.get(i).getName()) != null) {
                    value = mList.get(position).get(irModelFieldsList.get(i).getName()).toString();
                } else {
                    ToastUtils.showLong("字段：" + irModelFieldsList.get(i).getName() + "，未查询出来");
                }
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
                    listItemView.item.addView(textView);
                    index = 1;
                } else {
                    if (index == 1) {
                        //第1列布局
                        layout = new RelativeLayout(mContext);
                        TextView tv1 = new TextView(mContext);
                        tv1.setText(title + value);
                        layout.addView(tv1);
                        listItemView.item.addView(layout);
                        index = 2;
                    } else {
                        //第2列布局
                        TextView tv2 = new TextView(mContext);
                        tv2.setText(title + value);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layout.addView(tv2, lp);
                        index = 1;
                    }
                }
            }
        }
        return convertView;
    }
}
