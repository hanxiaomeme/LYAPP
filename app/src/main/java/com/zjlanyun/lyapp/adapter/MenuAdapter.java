package com.zjlanyun.lyapp.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.bean.MenuBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by MDZZ on 2019-02-19.
 */

public class MenuAdapter extends BaseQuickAdapter<MenuBean,BaseViewHolder>{

    public MenuAdapter(int layoutResId, @Nullable List<MenuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuBean item) {
        TextView tx = helper.getView(R.id.item_check_list_fname_tv);
        tx.setText("111111111111111");
       // helper.setText(R.id.item_check_list_fname_tv, item.getDate());
        helper.setText(R.id.item_check_list_fmodle_tv, item.getCode());

    }
}
