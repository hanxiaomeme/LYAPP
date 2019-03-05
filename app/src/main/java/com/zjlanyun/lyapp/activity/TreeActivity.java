package com.zjlanyun.lyapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aihook.alertview.AlertView;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.adapter.TreeAdapter;
import com.zjlanyun.lyapp.greendao.IrActWindow;
import com.zjlanyun.lyapp.greendao.IrModelFields;
import com.zjlanyun.lyapp.greendao.IrSearchFields;
import com.zjlanyun.lyapp.http.HttpRequest;
import com.zjlanyun.lyapp.http.SimpleHttpListener;
import com.zjlanyun.lyapp.utils.ButtonUtils;
import com.zjlanyun.lyapp.utils.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.zjlanyun.lyapp.common.Common.getIrActWindowNameByID;
import static com.zjlanyun.lyapp.common.Common.getIrSearchFields;
import static com.zjlanyun.lyapp.common.Common.getModelId;
import static com.zjlanyun.lyapp.common.Common.getPrimaryKey;
import static com.zjlanyun.lyapp.common.Common.getirModelFieldsList;
import static com.zjlanyun.lyapp.config.ActionConfig.ACTION_CREATE;
import static com.zjlanyun.lyapp.config.ActionConfig.ACTION_DELETE;
import static com.zjlanyun.lyapp.config.ActionConfig.ACTION_GETTREEDATE;
import static com.zjlanyun.lyapp.config.ActionConfig.VIEW_TYPE_FORM;
import static com.zjlanyun.lyapp.config.ActionConfig.VIEW_TYPE_TREE;
import static com.zjlanyun.lyapp.config.WebConfig.SERVER_IP;
import static com.zjlanyun.lyapp.config.WebConfig.URL_GETTREELIST;
import static com.zjlanyun.lyapp.utils.UtilConstants.ACTIVITY_INTENT_ACTID;
import static com.zjlanyun.lyapp.utils.UtilConstants.ACTIVITY_INTENT_ACTION;
import static com.zjlanyun.lyapp.utils.UtilConstants.ACTIVITY_INTENT_BILLSNAME;
import static com.zjlanyun.lyapp.utils.UtilConstants.ACTIVITY_INTENT_MODELID;
import static com.zjlanyun.lyapp.utils.UtilConstants.ACTIVITY_PHONE_SCANQRCODE;
import static com.zjlanyun.lyapp.utils.UtilConstants.REQUESTCODE_CREATE;


public class TreeActivity extends BaseActivity {

    private static final String TAG = "TreeActivity_ly";

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    private MenuItem addMenuItem;//导航栏新增按钮
    private MenuItem searchMenuItem;//导航栏搜索按钮
    private List<IrSearchFields> irSearchFieldsList; //搜索字段
    private int act_id = 0;//窗口ID
    private long model_id = 0; //模型ID
    private String title;//title名
    private boolean hasSearch = false; //是否有搜索功能
    private PopupWindow searchWindow;//搜索框POPWINDOW
    private Button btn_search, btn_cancel;//搜索按钮，取消按钮
    private LinearLayout search_layout;//搜索框布局
    private HashMap<Long, Object> itemWidget; //字段ID对应的控件
    private HashMap<Long, IrSearchFields> fieldsMap; //字段map，初始化好的字段
    private HashMap<String, Object> searchValue;
    private List<HashMap<String, Object>> mList;//返回的单据列表
    private long currentFocusFieldsID = 0;//当前焦点ID
    private boolean showLoading = true;
    private boolean isLoading = false;//是否正在加载数据
    private boolean isMore = true;//是否有更多数据
    private int page = 1;//当前页
    private List<IrModelFields> irModelFieldsList;
    private IrActWindow irActWindow;
    private TreeAdapter treeAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        ButterKnife.bind(this);
        initData();
        initUI();
        getData();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();
            }
        });

    }


    private void initUI() {
        //获取搜索字段
        irSearchFieldsList = getIrSearchFields(TreeActivity.this, act_id);
        //根据act_id获取model_id
        model_id = getModelId(TreeActivity.this, act_id);
        //获取模型内的字段
        irModelFieldsList = getirModelFieldsList(TreeActivity.this, model_id);
        //获取视图
        irActWindow = DBHelper.getDaoSession(mContext).getIrActWindowDao().load((long) act_id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(getIrActWindowNameByID(mContext, act_id).get(0).getName());
        if (irModelFieldsList.size() == 0) {
            Toasty.info(mContext, "单据尚未配置字段，请检查配置！", Toast.LENGTH_SHORT, true).show();
            return;
        }

        gridLayoutManager = new GridLayoutManager(TreeActivity.this, 1);
        rvList.setLayoutManager(gridLayoutManager);
        treeAdapter = new TreeAdapter(R.layout.tree_activity_item, null, irModelFieldsList);
        treeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        treeAdapter.isFirstOnly(true);
        treeAdapter.setTitle(actionBar.getTitle().toString());
        rvList.setAdapter(treeAdapter);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= gridLayoutManager.getItemCount() - 1) {
                        getData();
                    }
                }
            }
        });
        treeAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                onLongClickItem(position);
                return false;
            }
        });
        treeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toasty.info(mContext, "点击！", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                showSearchWindow(toolbar);
                break;
            case R.id.menu_add:
                onCreateBill();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //显示搜索视图
    @SuppressWarnings("deprecation")
    private void showSearchWindow(View parent) {
        if (searchWindow == null) {
            int height = 0;
            View view = LayoutInflater.from(mContext).inflate(R.layout.search_window, null);
            btn_search = (Button) view.findViewById(R.id.btn_search);
            btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
            search_layout = (LinearLayout) view.findViewById(R.id.search_layout);

            for (int i = 0; i < irSearchFieldsList.size(); i++) {
                final IrSearchFields irSearchFields = irSearchFieldsList.get(i);
                height += 34;
                View item = LayoutInflater.from(mContext).inflate(R.layout.form_activity_item_1col, null);
                TextView tv_title = (TextView) item.findViewById(R.id.tv_title);
                tv_title.setText(irSearchFields.getField_desc());
                final AutoCompleteTextView at_text = (AutoCompleteTextView) item.findViewById(R.id.at_text);
                at_text.setVisibility(View.VISIBLE);
                itemWidget.put(irSearchFields.getId(), at_text);
                fieldsMap.put(irSearchFields.getId(), irSearchFields); //加入到全局字段hashmap中
                searchValue.put(irSearchFields.getName(), "");
                search_layout.addView(item);
                at_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) {
                            currentFocusFieldsID = irSearchFields.getId();
                        }
                    }
                });
                at_text.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        searchValue.put(irSearchFields.getName(), s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                //监听扫描按钮
                at_text.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (irSearchFields.getScan()) {
                            currentFocusFieldsID = irSearchFields.getId();
                        }
                        return false;
                    }
                });
                //扫描
                if (irSearchFields.getScan()) {
                    HashMap<String, Object> map = new HashMap<>();
                    Button btn_scan = (Button) item.findViewById(R.id.btn_scan);
                    btn_scan.setVisibility(View.VISIBLE);
                    btn_scan.setTag(irSearchFields.getId());
                    btn_scan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (DeviceUtils.getModel()) {
                                case "APlus":
                                    break;
                                default:
                                    scanQRcodeByPhone();
                                    break;
                            }
                        }
                    });
                }
            }
            searchWindow = new PopupWindow(view, ScreenUtils.getScreenWidth(), SizeUtils.dp2px(height + 62));
        }
        searchWindow.setFocusable(true);
        searchWindow.setOutsideTouchable(false);
        searchWindow.showAsDropDown(parent, 0, 0);
        //搜索
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });

        //取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWindow.dismiss();
            }
        });
    }

    /**
     * 新建单据
     */
    private void onCreateBill() {
        if (irActWindow.getView_mode().indexOf(VIEW_TYPE_FORM) != -1) {
            Intent intent = new Intent(mContext, FormActivity.class);
            intent.putExtra(ACTIVITY_INTENT_MODELID, model_id);
            intent.putExtra(ACTIVITY_INTENT_ACTION, ACTION_CREATE);
            intent.putExtra(ACTIVITY_INTENT_ACTID, act_id);
            startActivityForResult(intent, REQUESTCODE_CREATE);
        }
    }

    /**
     * 点击单据
     */
    private void onItemClick() {
        if (irActWindow.getView_mode().indexOf(VIEW_TYPE_FORM) != -1) {
            Intent intent = new Intent(mContext, FormActivity.class);
            intent.putExtra(ACTIVITY_INTENT_MODELID, model_id);
            intent.putExtra(ACTIVITY_INTENT_ACTION, ACTION_CREATE);
            intent.putExtra(ACTIVITY_INTENT_ACTID, act_id);
            startActivityForResult(intent, REQUESTCODE_CREATE);
        }
    }

    //长按，删除
    private void onLongClickItem(final int position) {
        new AlertView(getString(R.string.app_name), getString(R.string.msg_delete_bill), getString(R.string.btn_cancel), new String[]{getString(R.string.btn_sure)},
                null, mContext, AlertView.Style.Alert, new com.aihook.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, final int p) {
                if (p == 0) {
                    HashMap<String, Object> param = new HashMap<>();
                    param.put("model_id", model_id);
                    param.put("key_name", getPrimaryKey(mContext, model_id, VIEW_TYPE_TREE).getName());
                    param.put("key_value", mList.get(position).get(getPrimaryKey(mContext, model_id, VIEW_TYPE_TREE).getName()));
                    HttpRequest httpRequest = new HttpRequest(mContext, URL_GETTREELIST, ACTION_DELETE, JSON.toJSONString(param),
                            true, true);
                    httpRequest.send(new SimpleHttpListener<JSONObject>() {
                        @Override
                        public void onSucceed(int what, Response<JSONObject> response) {
                            super.onSucceed(what, response);
                            Toasty.success(mContext, getString(R.string.msg_delete_suc), Toast.LENGTH_SHORT, true).show();
                            treeAdapter.remove(position);
                        }
                    });
                }
            }
        }).show();
    }

    //搜索开始
    private void startSearch() {
        showLoading = true;
        page = 1;
        getData();
        searchWindow.dismiss();
    }

    public void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra(ACTIVITY_INTENT_BILLSNAME);
        act_id = intent.getIntExtra("act_id", 0);
        itemWidget = new HashMap<>();
        fieldsMap = new HashMap<>();
        searchValue = new HashMap<>();
        mList = new ArrayList<>();

    }

    //获取数据
    private void getData() {
        if (isLoading) {
            isLoading = false;
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("searchValue", JSON.toJSONString(searchValue));
        param.put("page", page);
        param.put("model_id", model_id);
        param.put("act_id", act_id);
        HttpRequest httpRequest = new HttpRequest(mContext, URL_GETTREELIST, ACTION_GETTREEDATE, JSON.toJSONString(param), true, showLoading);
        showLoading = false;
        httpRequest.send(new SimpleHttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                super.onSucceed(what, response);
                mSwipeRefreshLayout.setRefreshing(false);
                mList.clear();
                if (page == 1) {
                    treeAdapter.setNewData(null);
                }
                try {
                    JSONArray list = response.get().getJSONObject("data").getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        HashMap<String, Object> map = new HashMap<>();
                        for (int j = 0; j < irModelFieldsList.size(); j++) {
                            map.put(irModelFieldsList.get(j).getName(), item.get(irModelFieldsList.get(j).getName()));
                        }
                        mList.add(map);
                    }
                    Logger.t(TAG).d(mList.size());
//                    if (page == 1){
//                        treeAdapter.setirModelFieldsList(irModelFieldsList);
//                        treeAdapter.addNewData(mList);
//                    }
//                    else {
//                        treeAdapter.closeLoadAnimation();
                    treeAdapter.addData(mList);
//                    }
                    page++;

                    if (page >= response.get().getJSONObject("data").getInt("pageCount")) {
                        isMore = true;
                    } else {
                        isMore = false;
                    }
                    isLoading = false;
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                super.onFailed(what, response);
                isLoading = false;
                isMore = true;
                treeAdapter.openLoadAnimation();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tree, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        addMenuItem = menu.findItem(R.id.menu_add);
        addMenuItem.setVisible(true);
        searchMenuItem.setVisible(true);
        return super.onCreateOptionsMenu(menu);

    }

    @OnClick(R.id.floating_action_button)
    public void onViewClicked() {
        rvList.scrollToPosition(0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ACTIVITY_PHONE_SCANQRCODE) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            if (!TextUtils.isEmpty(scanResult)) {
                ((AutoCompleteTextView) itemWidget.get(currentFocusFieldsID)).setText(scanResult);
            }


        }
    }

    /**
     * 通过手机扫描二维码
     */
    private void scanQRcodeByPhone() {
        getPermission();
        Intent intent = new Intent(TreeActivity.this, CaptureActivity.class);
        startActivityForResult(intent, ACTIVITY_PHONE_SCANQRCODE);
    }

    /**
     * 获取相机权限
     */
    public void getPermission() {
        final String[] permissionList = new String[]{
                Permission.CAMERA
        };
        AndPermission.with(this)
                .runtime()
                .permission(permissionList)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissionList)) {
//                            setPermission();
                        }
                    }
                })
                .start();

    }
}
