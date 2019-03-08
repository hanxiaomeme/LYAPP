package com.zjlanyun.lyapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aihook.alertview.AlertView;
import com.aihook.alertview.OnItemClickListener;
import com.aihook.tableview.lib.TableDataAdapter;
import com.aihook.tableview.lib.TableFixHeaders;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.nohttp.rest.Response;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.adapter.Many2OneAdapter;
import com.zjlanyun.lyapp.greendao.IrActWindow;
import com.zjlanyun.lyapp.greendao.IrExtend;
import com.zjlanyun.lyapp.greendao.IrModelAccess;
import com.zjlanyun.lyapp.greendao.IrModelAccessDao;
import com.zjlanyun.lyapp.greendao.IrModelFields;
import com.zjlanyun.lyapp.greendao.IrModelFieldsDao;
import com.zjlanyun.lyapp.greendao.IrUiMenu;
import com.zjlanyun.lyapp.http.HttpRequest;
import com.zjlanyun.lyapp.http.SimpleHttpListener;
import com.zjlanyun.lyapp.utils.ButtonUtils;
import com.zjlanyun.lyapp.utils.DBHelper;
import com.zjlanyun.lyapp.utils.SPData;
import com.zjlanyun.lyapp.utils.StringUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.zjlanyun.lyapp.common.Common.getFromBodyModelAccess;
import static com.zjlanyun.lyapp.common.Common.getFromBodyPrimaryKey;
import static com.zjlanyun.lyapp.common.Common.getHeardFormAllFields;
import static com.zjlanyun.lyapp.common.Common.getHeardFormAllStoreFields;
import static com.zjlanyun.lyapp.config.ActionConfig.ACTION_READ;
import static com.zjlanyun.lyapp.config.ActionConfig.PARAM_KEYNAME;
import static com.zjlanyun.lyapp.config.ActionConfig.PARAM_KEYVALUE;
import static com.zjlanyun.lyapp.config.ActionConfig.PARAM_MODELID;
import static com.zjlanyun.lyapp.config.LogConfig.FORM_POST_BILL_TAG;
import static com.zjlanyun.lyapp.config.SPConfig.SP_UID;
import static com.zjlanyun.lyapp.config.UtilConstants.ACTIVITY_INTENT_ACTID;
import static com.zjlanyun.lyapp.config.UtilConstants.ACTIVITY_INTENT_ACTION;
import static com.zjlanyun.lyapp.config.UtilConstants.ACTIVITY_INTENT_KEYNAME;
import static com.zjlanyun.lyapp.config.UtilConstants.ACTIVITY_INTENT_KEYVALUE;
import static com.zjlanyun.lyapp.config.UtilConstants.ACTIVITY_INTENT_MODELID;
import static com.zjlanyun.lyapp.config.UtilConstants.REQUESTCODE_CAMERA_CHOICE;
import static com.zjlanyun.lyapp.config.UtilConstants.REQUESTCODE_CAMERA_FIELD;
import static com.zjlanyun.lyapp.config.WebConfig.URL_FORM;
import static com.zjlanyun.lyapp.config.UtilConstants.FIELD_AUTOCHOISEBILL;
import static com.zjlanyun.lyapp.config.UtilConstants.FIELD_CANINPUT;
import static com.zjlanyun.lyapp.config.UtilConstants.FIELD_MANY2ONECONTRAST;
import static com.zjlanyun.lyapp.config.UtilConstants.FIELD_NOTCLEAN;
import static com.zjlanyun.lyapp.config.UtilConstants.FIELD_STOPMANY2ONECONTRAST;
import static com.zjlanyun.lyapp.config.UtilConstants.MULIT_SELECTION;
import static com.zjlanyun.lyapp.config.UtilConstants.NOT_DUPLICATED;
import static com.zjlanyun.lyapp.config.UtilConstants.SINGLE_SELECTION;


/***
 * 配置表体视图
 */
public class FormActivity extends BaseActivity {


    /**
     * 日志TAG
     */
    private static final String TAG = "FormActivity";
    /**
     *
     */
    private final static String SCAN_ACTION = "scan.rcv.message";
    /**
     * 表体选择界面
     */
    private static int BODY = 1;
    /**
     * 表头选择界面
     */
    private static int HEARD = 2;
    /**
     * 表体字段添加区域
     */
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    /**
     * 表头字段添加区域
     */
    @BindView(R.id.ll_wrap)
    LinearLayout ll_wrap;
    /**
     * 表头字段添加区域
     */
    @BindView(R.id.sv_wrap)
    ScrollView sv_wrap;
    /**
     * 表头表体
     */
    @BindView(R.id.btn_1)
    Button btn1;
    /**
     * 审核
     */
    @BindView(R.id.btn_2)
    Button btn2;
    /**
     * 反审
     */
    @BindView(R.id.btn_3)
    Button btn3;
    /**
     * 保存到表格
     */
    @BindView(R.id.btn_4)
    Button btn4;
    /**
     * 表体表格
     */
    @BindView(R.id.table)
    TableFixHeaders table;

    /**
     * 顶部toolbar布局
     */
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * toolbar父布局
     */
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    /**
     * 外层布局
     */
    @BindView(R.id.main_relayout)
    RelativeLayout mainRelayout;


    MenuItem addMenuItem;

    private String deviceModel = Build.MODEL; //设备名称

    /**
     * 选单需要带入数据的map
     */
    private Map<String, String> userInputMap; //保存用户输入的信息的值
    /**
     * 白色的新版手持机,大江使用
     */
    private SharedPreferences sp;
    /**
     * 白色的新版手持机,大江使用
     */
    private Intent intent;

    /**
     *
     */
    private IrModelFields irModelFieldsOne2Many = null;
    /**
     * 表体所有字段
     */
    private List<IrModelFields> irModelFieldsOne2ManyList = null;
    /**
     * 表体所有字段List的长度
     */
    private int bodyListSize = 0;
    /**
     *
     */
    private List<IrModelFields> irModelFieldsOne2ManyListTemp = null;

    /**
     * 菜单视图模型
     */
    private IrUiMenu irUiMenu = null;
    /**
     * one2many主键字段
     */
    private IrModelFields irModelFieldsOne2ManyKey;
    /**
     * 表体删除内容的主键值
     */
    private ArrayList<String> entryDeleteItems;
    /**
     *
     */
    private IrActWindow irActWindow;
    /**
     * 窗口ID
     */
    private long act_id = 0;
    /**
     * 模型ID
     */
    private long model_id = 0;
    /**
     * 现表中需要与选单返回的json数据对比的字段
     */
    private String unique_field = "";
    /**
     * 表头字段list，不含one2many
     */
    private List<IrModelFields> irModelFieldsList;
    /**
     * 字段map，初始化好的字段
     */
    private HashMap<Long, IrModelFields> fieldsMap;
    /**
     * 表格适配器
     */
    private TableDataAdapter tableDataAdapter;
    /**
     * 表格数据
     */
    private ArrayList<ArrayList<String>> tableListData;
    /**
     * 表格数据
     */
    private ArrayList<ArrayList<String>> choiceListData;

    /**
     * 当前设备是否是PDA
     */
    private boolean isPDA = false;
    /**
     * 是否使用扫描
     */
    private boolean isUseScan = false;
    /**
     * 当前扫描字段ID
     */
    private long curFieldId = 0;

    /**
     * //表头显示的数据
     */
    private HashMap<String, Object> headerShowMap;
    /**
     * 表体显示的数据
     */
    private List<HashMap<String, Object>> mList;
    /**
     * //表体提交的数据
     */
    private List<HashMap<String, Object>> bodyList;

    /**
     * 字段ID对应的控件
     */
    private HashMap<Long, Object> itemWidget;
    /**
     * 当前行号
     */
    private int curRow = -1;

    /**
     * 供点击选择的数据
     */
    private HashMap<Long, String[]> many2oneListMap;
    /**
     * 用于保存关联字段选择之后保存的值位置
     */
    private HashMap<Long, Integer> selectList;
    /**
     * 保存提示的数据，通过提示数据可以显示在其他地方
     */
    private HashMap<Long, ArrayList<HashMap<String, Object>>> many2oneList;
    /**
     *
     */
    private boolean originList = false;
    /**
     * 保存many2one请求的列表实例
     */
    private HashMap<Long, Many2One> many2oneHashMap;
    /**
     * 保存many2one字段是否是用户点击获得 还是手动输入
     */
    private HashMap<Long, Boolean> many2oneIsOnClickMap;
    /**
     * 用于获取more之后显示的内容
     */
    private HashMap<Long, ArrayList<String>> many2oneMoreListMap;
    /**
     *
     */
    private LayoutInflater layoutInflater;
    /**
     *
     */
    private Dialog dialog;
    /**
     * 用于关联的改变字段
     */
    private HashMap<Long, HashMap<String, HashMap<String, Object>>> changeMap;
    /**
     * 用于保存关联字段选择之后保存的值位置
     */
    private HashMap<Long, Integer> changeSelectList;
    /**
     * 保存many2one字段获取更多数据
     */
    private HashMap<Long, ArrayList<HashMap<String, Object>>> many2oneMoreList;
    /**
     * 表体临时值
     */
    private HashMap<String, Object> bodyItem;
    /**
     * 表头临时值
     */
    private HashMap<String, Object> headerItem;
    /**
     * 表体有更新的数据key
     */
    private ArrayList<String> bodyUpdateList;

    /**
     * 未保存
     */
    private boolean notSave = false;
    /**
     * 保存many2one初始化的对象
     */
    private HashMap<Long, InitMany2One> many2oneFieldHashMap;
    /**
     * 保存many2one默认值
     */
    private HashMap<Long, Object> many2oneDefValue;
    /**
     * 记录many2one字段是否从更多列表点击进来的
     */
    private HashMap<Long, Boolean> many2oneOriginMore;
    /**
     * 选单功能配置参数
     */
    private IrExtend irExtendChoiceBill = null;

    /**
     * 是否正在请求中
     */
    private boolean isRequest = false;
    /**
     * 有文字改变监听
     */
    private boolean isTextChanged = false;
    /**
     * 正在扫描中
     */
    private boolean isScaning = false;
    /**
     * 是否点击表体信息
     */
    private boolean isClickBody = false;
    /**
     * 是否点击表格
     */
    private boolean isClickTable = false;
    /**
     * 是否由保存得来
     */
    private boolean isSaveTable = false;
    /**
     * 单据是否已经审核
     */
    private boolean isCheck = false;
    /**
     * 是否展示更多
     */
    private boolean isShowHint = true;
    /**
     * 是否点击获得many2one字段值
     */
    private boolean isMany2OneOnClick = false;
    /**
     * 是否从更多点击获取值
     */
    private boolean isMoreListEnter = false;
    /**
     * 单据中是否有扫描字段
     */
    private boolean isHaveScanFields = false;
    /**
     * 是否允许请求更多数据
     */
    private boolean isGetMore = false;
    /**
     * 选单是否填充数据
     */
    private boolean choiceBillTianChong = false;
    /**
     * 该字段是否为自动选单
     */
    private boolean isAutoBill = false;
    /**
     * 单据中是否有需要在表格中合计的字段
     */
    private boolean isSummary = false;
    /**
     * many2one输入框中内容初始长度
     */
    private int many2oneTvLength = 0;
    /**
     * 字段id与字段名对应的map
     */
    private HashMap<Long, String> idNameMap;
    /**
     * 字段id与字段描述对应的map
     */
    private HashMap<Long, String> idDescMap;
    /**
     * 字段是否需要保存
     */
    private HashMap<Long, Boolean> idSveMap;
    /**
     * 字段是否需要必填
     */
    private HashMap<Long, Boolean> idRequired;
    /**
     * 表体字段描述字段ip对应map
     */
    private HashMap<String, Long> descIdBodyMap;

    /**
     *
     */
    private HashMap<Long, Boolean> isWithDateMap = new HashMap<>();


    /**
     *
     */
    private HashMap<Long, Boolean> isGetMoreData;

    /**
     * 主键名
     */
    private String PKeyName = "";
    /**
     * 主键值
     */
    private String PKeyValue = "";

    /**
     * 多选框选中的值
     */
    private String muiteDate = "";

    /**
     * 当前焦点所在字段的ID
     */
    private long focus_field_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        initPDA();
        initView();
        initData();
        initToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(irActWindow.getName());
    }

    /**
     * 初始化toolbar菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_form, menu);
        addMenuItem = menu.findItem(R.id.menu_post);
        addMenuItem.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_post:
                postBills();
                break;
            case android.R.id.home:
                //此处加入提示框 防止误操作
                back();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 提交单据
     */
    private void postBills() {
        new AlertView("提示", "确定提交吗？", "取消", new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    if (!saveHeaderData() || !checkBodyFieldInput()) {
                        return;
                    }
                    //移除不需要保存的字段
                    if (bodyList.size() > 0) {
                        for (int i = 0; i < bodyList.size(); i++) {
                            for (int j = 0; j < bodyListSize; j++) {
                                if (!irModelFieldsOne2ManyList.get(j).getStore()) {
                                    bodyList.get(i).remove(irModelFieldsOne2ManyList.get(j).getName());
                                }
                            }
                        }
                    }
                    SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;
                    HashMap<String, Object> param = new HashMap<>();
                    param.put("header", JSON.toJSONString(headerItem, feature));
                    param.put("body", JSON.toJSONString(bodyList, feature));
                    param.put("entryDeleteItems", JSON.toJSONString(entryDeleteItems, feature));
                    param.put("bodyUpdateList", JSON.toJSONString(bodyUpdateList, feature));
                    param.put("model_id", model_id);
                    param.put("action", intent.getStringExtra("action"));
                    Logger.t(FORM_POST_BILL_TAG).d(JSON.toJSONString(param, feature));
                }
            }
        }).show();
    }

    /**
     * 再提交审核之前，检查表体是否有必填字段未输入
     * @return
     */
    public boolean checkBodyFieldInput() {
        //提示信息
        String msg = "";
        for (int i = 0; i < bodyListSize; i++) {
            IrModelFields field = irModelFieldsOne2ManyList.get(i);
            if (field.getVisiable()) {
                if (field.getRequired()) {
                    for (int j = 0; j < bodyList.size(); j++) {
                        HashMap<String, Object> map = bodyList.get(j);
                        String value = "";
                        if (map.containsKey(field.getName())) {
                          value = map.get(field.getName()).toString();
                        }
                        if (TextUtils.isEmpty(value)) {
                            msg = "表体 " + field.getField_desc() + " 必须填写，保存失败";
                            Toasty.error(mContext, msg, Toast.LENGTH_SHORT, true).show();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * 保存表头数据
     * @return
     */
    private boolean saveHeaderData() {
        boolean result = true;
        QueryBuilder<IrModelFields> qb4 = DBHelper.getDaoSession(mContext).getIrModelFieldsDao().queryBuilder();
        qb4.where(IrModelFieldsDao.Properties.Model_id.eq(model_id),
                IrModelFieldsDao.Properties.View_type.eq("form"), IrModelFieldsDao.Properties.Ttype.notEq("one2many"))
                .orderAsc(IrModelFieldsDao.Properties.Sequence);
        List<IrModelFields> irModelFieldsList2 = qb4.list();
        for (int i = 0; i < irModelFieldsList2.size(); i++) {
            IrModelFields field = irModelFieldsList2.get(i);

            if (field.getVisiable()) {
                AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(field.getId());
                String value = actv.getText().toString();
                if (field.getRequired() && value.equals("")) {
                    Toasty.error(mContext, "表头 " + field.getField_desc() + " 必须输入", Toast.LENGTH_SHORT, true).show();

                    result = false;
                    break;
                }
                headerShowMap.put(field.getName(), value);
            } else {
                if (intent.getStringExtra("action").equals("create") && TextUtils.isEmpty(field.getRel_change_field())) {
                    //如果不显示 并且默认值还是为空的话，不处理
                    if (TextUtils.isEmpty(field.getDef_value())) {
                    } else {
                        //如果不显示 并且默认值为空的话，处理
                        headerItem.put(field.getName(), setDefValue(field.getDef_value(), field, 0));

                    }
                }
            }

            //移除不保存的字段
            if (!field.getStore()) {
                headerItem.remove(field.getName());
            }
        }
        return result;
    }

    /**
     * 退出当前界面
     */
    private void back() {
        finish();
    }

    //初始化
    private void initView() {
        intent = getIntent();
        act_id = intent.getIntExtra(ACTIVITY_INTENT_ACTID, 0);
        model_id = intent.getLongExtra(ACTIVITY_INTENT_MODELID, 0);

        fieldsMap = new HashMap<>();
        headerShowMap = new HashMap<>();
        itemWidget = new HashMap<>();
        mList = new ArrayList<>();
        bodyList = new ArrayList<>();
        tableListData = new ArrayList<>();
        many2oneListMap = new HashMap<>();
        many2oneMoreListMap = new HashMap<>();
        changeMap = new HashMap<>();
        changeSelectList = new HashMap<>();
        many2oneMoreList = new HashMap<>();
        bodyItem = new HashMap<>();
        headerItem = new HashMap<>();
        entryDeleteItems = new ArrayList<>();

        bodyUpdateList = new ArrayList<>();
        layoutInflater = LayoutInflater.from(mContext);
        selectList = new HashMap<>();
        many2oneList = new HashMap<>();
        many2oneHashMap = new HashMap<>();
        many2oneFieldHashMap = new HashMap<>();
        many2oneDefValue = new HashMap<>();
        many2oneIsOnClickMap = new HashMap<>();
        idNameMap = new HashMap<>();
        idDescMap = new HashMap<>();
        idSveMap = new HashMap<>();
        idRequired = new HashMap<>();
        descIdBodyMap = new HashMap<>();
        many2oneOriginMore = new HashMap<>();
        irModelFieldsOne2ManyList = new ArrayList<>();
        isGetMoreData = new HashMap<>();
        userInputMap = new HashMap<>();

        //获取当前视图
        irActWindow = DBHelper.getDaoSession(mContext).getIrActWindowDao().load(act_id);
        //获取One2Many字段
        QueryBuilder<IrModelFields> qb = DBHelper.getDaoSession(mContext).getIrModelFieldsDao().queryBuilder();
        qb.where(IrModelFieldsDao.Properties.Model_id.eq(intent.getLongExtra("model_id", 0)),
                IrModelFieldsDao.Properties.View_type.eq("form"), IrModelFieldsDao.Properties.Ttype.eq("one2many"),
                IrModelFieldsDao.Properties.Visiable.eq(1)).limit(1);
        if (qb.list().size() > 0) {
            //获取One2Many的字段
            irModelFieldsOne2Many = qb.list().get(0);
            QueryBuilder<IrModelFields> qb1 = DBHelper.getDaoSession(mContext).getIrModelFieldsDao().queryBuilder();
            qb1.where(IrModelFieldsDao.Properties.Model_id.eq(irModelFieldsOne2Many.getRelation_model_id()),
                    IrModelFieldsDao.Properties.View_type.eq("form")).orderAsc(IrModelFieldsDao.Properties.Sequence);
            irModelFieldsOne2ManyListTemp = qb1.list();
            //复制一份，在新的上面做处理
            for (IrModelFields f : irModelFieldsOne2ManyListTemp) {
                IrModelFields irModelFields = JSON.parseObject(JSON.toJSONString(f), IrModelFields.class);
                irModelFieldsOne2ManyList.add(irModelFields);
            }
            bodyListSize = irModelFieldsOne2ManyList.size();
        } else {
            btn1.setEnabled(false);
            btn4.setEnabled(false);
        }

        //如果是新建进入的，那就获取默认值，填充
        if (intent.getStringExtra("action").equals("create")) {
            initHeaderView();
            if (irModelFieldsOne2ManyList != null && irModelFieldsOne2ManyList.size() > 0) {
                initBodyView();
            }
            getApiDefault();
        }

        //查询表体所有字段
        for (int i = 0; i < bodyListSize; i++) {
            IrModelFields ir = irModelFieldsOne2ManyList.get(i);
            idNameMap.put(ir.getId(), ir.getName());//表体id字段对应map
            idDescMap.put(ir.getId(), ir.getField_desc());//表体id字段描述对应map
            isWithDateMap.put(ir.getId(), false);//是否点击map
            idSveMap.put(ir.getId(), ir.getStore());//id-是否需要保存
            idRequired.put(ir.getId(), ir.getRequired());//id-是否必填
            descIdBodyMap.put(ir.getName(), ir.getId());
            isGetMoreData.put(ir.getId(), false);

        }
        //查询表头所有字段
        List<IrModelFields> irModelHeardFieldsList = getHeardFormAllStoreFields(mContext, model_id);
        for (int i = 0; i < irModelHeardFieldsList.size(); i++) {
            IrModelFields ir = irModelHeardFieldsList.get(i);
            idNameMap.put(ir.getId(), ir.getName());//表体id字段对应map
            idDescMap.put(ir.getId(), ir.getField_desc());//表体id字段描述对应map
            isWithDateMap.put(ir.getId(), false);//是否点击map
            idSveMap.put(ir.getId(), ir.getStore());//id-是否需要保存
            idRequired.put(ir.getId(), ir.getRequired());//id-是否必填
            isGetMoreData.put(ir.getId(), false);
        }
    }


    //初始化表头字段视图
    private void initHeaderView() {
        irModelFieldsList = getHeardFormAllFields(mContext, model_id);
        int index = 1; //当前布局列
        View view = null;
        //渲染表头字段
        for (int i = 0; i < irModelFieldsList.size(); i++) {
            IrModelFields irModelFields = irModelFieldsList.get(i);
            if (irModelFields.getVisiable()) {
                //显示可见字段
                if (irModelFields.getColspan() == 2) {
                    //占用两列
                    index = 1;
                    view = LayoutInflater.from(mContext).inflate(R.layout.form_activity_item_1col, null);
                    initFiledView(view, irModelFields, 0, 0);
                    ll_wrap.addView(view);
                } else {
                    //占用一列
                    if (index == 1) {
                        //左列
                        index = 2;
                        view = LayoutInflater.from(mContext).inflate(R.layout.form_activity_item_2col, null);
                        initFiledView(view, irModelFields, 1, 0);
                        ll_wrap.addView(view);
                    } else {
                        //右列
                        index = 1;
                        initFiledView(view, irModelFields, 2, 0);
                        LinearLayout ll_view2 = (LinearLayout) view.findViewById(R.id.ll_view2);
                        ll_view2.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                //设置隐藏的字段值
                if (!TextUtils.isEmpty(irModelFields.getDef_value())) {
                    headerItem.put(irModelFields.getName(), setDefValue(irModelFields.getDef_value(), irModelFields, 0));
                }
                fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中
            }
        }

        if (irModelFieldsList.size() == 0) {
            btn2.setEnabled(false);
            btn3.setEnabled(false);

        }
    }


    //初始化表体字段视图
    private void initBodyView() {
        int size = 0;//循环长度
        //获取表体权限
        if (getFromBodyModelAccess(mContext, irModelFieldsOne2Many.getRelation_model_id()) != null) {
//            irModelAccessOne2Many = getFromBodyModelAccess(mContext, irModelFieldsOne2Many.getRelation_model_id());
        }
        //获取主键字段
        if (getFromBodyPrimaryKey(mContext, irModelFieldsOne2Many.getRelation_model_id()) != null) {
            irModelFieldsOne2ManyKey = getFromBodyPrimaryKey(mContext, irModelFieldsOne2Many.getRelation_model_id());
        }

        //有One2Many字段则配置表格
        ArrayList<String> tableHeader = new ArrayList<>();
        int index = 1; //当前布局列
        View view = null;
        //渲染One2Many所有字段
        for (int i = 0; i < bodyListSize; i++) {
            IrModelFields irModelFields = irModelFieldsOne2ManyList.get(i);
            if (irModelFields.getVisiable()) {
                //显示可见字段
                tableHeader.add(irModelFields.getField_desc());
                if (irModelFields.getColspan() == 2) {
                    //占用两列
                    index = 1;
                    view = LayoutInflater.from(mContext).inflate(R.layout.form_activity_item_1col, null);
                    initFiledView(view, irModelFields, 0, 1);
                    ll_main.addView(view);
                } else {
                    //占用一列
                    if (index == 1) {
                        //左列
                        index = 2;
                        view = LayoutInflater.from(mContext).inflate(R.layout.form_activity_item_2col, null);
                        initFiledView(view, irModelFields, 1, 1);
                        ll_main.addView(view);
                    } else {
                        //右列
                        index = 1;
                        initFiledView(view, irModelFields, 2, 1);
                        LinearLayout ll_view2 = (LinearLayout) view.findViewById(R.id.ll_view2);
                        ll_view2.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中
            }
        }
        //初始化表体的表格
        if (tableHeader.size() > 0) {
            tableListData.add(0, tableHeader);
        }

        if (tableListData.size() > 0) {
            tableDataAdapter = new TableDataAdapter(mContext, tableListData, 28, 14);
            table.setAdapter(tableDataAdapter);
        }
        //点击编辑表格内数据 && irModelAccessOne2Many.getPerm_write()

        table.setOnCellClickListener(new TableFixHeaders.OnCellClickListener() {
            @Override
            public void onItemClick(View view, int row, int column) {
                ll_main.setVisibility(View.VISIBLE);
                bodyItem = bodyList.get(row);
                curRow = row;
                isClickBody = true;
                isClickTable = true;
                //从表格内点击进来的数据，不需要将many2oneIsOnClickMap变为false
                for (Long key : many2oneIsOnClickMap.keySet()) {
                    isWithDateMap.put(key, true);
                    many2oneIsOnClickMap.put(key, true);
                }
                for (int i = 0; i < bodyListSize; i++) {
                    IrModelFields irModelFields = irModelFieldsOne2ManyList.get(i);
                    if (irModelFields.getVisiable()) {
                        AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(irModelFields.getId());
                        if (irModelFields.getTtype().equals("float")) {
                            actv.setText(StringUtils.formatNumber(String.valueOf(mList.get(row).get(irModelFields.getName()))));
                        } else {
                            actv.setText(String.valueOf(mList.get(row).get(irModelFields.getName())));
                        }
                        actv.dismissDropDown();
                    }
                }
                isClickBody = false;
            }
        });


        //长按删除
        if (!isCheck) {
            table.setOnCellLongClickListener(new TableFixHeaders.OnCellLongClickListener() {
                @Override
                public void onCellLongClick(View view, int row, int column) {
                    final int index = row;
                    new AlertView("提示", "是否删除该条记录？",
                            "取消", new String[]{"删除该行"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                if (bodyList.get(index).get(irModelFieldsOne2ManyKey.getName()) != null) {
                                    entryDeleteItems.add(String.valueOf(bodyList.get(index).get(irModelFieldsOne2ManyKey.getName())));
                                    bodyUpdateList.remove(String.valueOf(bodyList.get(index).get(irModelFieldsOne2ManyKey.getName())));
                                }
                                tableListData.remove(index + 1);
                                mList.remove(index);
                                bodyList.remove(index);
                                tableDataAdapter.notifyDataSetChanged();
                                cleanBodyForm();
                            }
                        }
                    }).show();

                }
            });
        }


    }

    //初始化表单数据
    private void initData() {
        //点击某条记录进来，进行读取操作
        if (intent.getStringExtra(ACTIVITY_INTENT_ACTION).equals(ACTION_READ)) {
            PKeyName = intent.getStringExtra(ACTIVITY_INTENT_KEYNAME);
            PKeyValue = intent.getStringExtra(ACTIVITY_INTENT_KEYVALUE);
            HashMap<String, Object> param = new HashMap<>();
            param.put(PARAM_KEYVALUE, PKeyValue);
            param.put(PARAM_KEYNAME, PKeyName);
            param.put(PARAM_MODELID, model_id);
            HttpRequest httpRequest = new HttpRequest(mContext, URL_FORM, "getFormData", JSON.toJSONString(param),
                    true, true);
            httpRequest.setmWhat(-1);
            httpRequest.send(new SimpleHttpListener<JSONObject>() {
                @Override
                public void onFailed(int what, Response<JSONObject> response) {
                    super.onFailed(what, response);
                    //数据获取失败
                    finish();
                }

                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    super.onSucceed(what, response);
                    if (what == -1) {
                        JSONObject header_show = response.get().optJSONObject("data").optJSONObject("header_show");
                        Iterator itShow = header_show.keys();
                        while (itShow.hasNext()) {
                            String key = (String) itShow.next();
                            Object value = header_show.opt(key);
                            headerShowMap.put(key, value);
                        }
                        JSONObject header = response.get().optJSONObject("data").optJSONObject("header");
                        Iterator it = header.keys();
                        while (it.hasNext()) {
                            String key = (String) it.next();
                            Object value = header.opt(key);
                            headerItem.put(key, value);
                        }
                        if (irModelFieldsOne2ManyList != null) {
                            JSONArray body_show = response.get().optJSONObject("data").optJSONArray("body_show");
                            JSONArray body = response.get().optJSONObject("data").optJSONArray("body");
                            for (int i = 0; i < body_show.length(); i++) {
                                JSONObject item = body_show.optJSONObject(i);
                                ArrayList<String> tabledata = new ArrayList<>();
                                HashMap<String, Object> map = new HashMap<>();
                                for (int j = 0; j < bodyListSize; j++) {
                                    IrModelFields irModelFields = irModelFieldsOne2ManyList.get(j);
                                    if (irModelFields.getVisiable()) {
                                        if (irModelFields.getTtype().equals("float")) {
                                            tabledata.add(StringUtils.formatNumber(String.valueOf(item.opt(irModelFields.getName()))));
                                            map.put(irModelFields.getName(), item.opt(irModelFields.getName()));
                                        } else {
                                            tabledata.add(String.valueOf(item.opt(irModelFields.getName())));
                                            map.put(irModelFields.getName(), item.opt(irModelFields.getName()));
                                        }
                                    }
                                }
                                mList.add(map);
                                tableListData.add(tabledata);
                            }

                            //保存到临时值中bodyList
                            for (int i = 0; i < body.length(); i++) {
                                JSONObject item = body.optJSONObject(i);
                                HashMap<String, Object> map = new HashMap<>();
                                Iterator itBody = item.keys();
                                while (itBody.hasNext()) {
                                    String key = (String) itBody.next();
                                    Object value = item.opt(key);
                                    map.put(key, value);
                                }
                                bodyList.add(map);
                            }

                            if (irModelFieldsOne2ManyList != null && bodyListSize > 0) {
                                initBodyView();
                            }

                            //表体默认值
                            if (!isCheck) {
                                JSONObject body_default = response.get().optJSONObject("data").optJSONObject("body_default");
                                for (int i = 0; i < bodyListSize; i++) {
                                    IrModelFields irModelFields = irModelFieldsOne2ManyList.get(i);
                                    if (irModelFields.getDef_value().indexOf("api_") > -1 && irModelFields.getDef_value().indexOf("_api") > -1) {
                                        irModelFields.setDef_value(body_default.optString(String.valueOf(irModelFields.getId())));
                                        if (irModelFields.getVisiable()) {
                                            AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(irModelFields.getId());
                                            actv.setText(irModelFields.getDef_value());
                                            setUserInputMap(irModelFields.getName(), irModelFields.getDef_value());
                                        } else {
                                            bodyItem.put(irModelFields.getName(), body_default.optString(String.valueOf(irModelFields.getId())));
                                        }
                                    } else if (irModelFields.getTtype().equals("many2one") && !irModelFields.getDef_value().equals("")) {
                                        many2oneDefValue.put(irModelFields.getId(), body_default.optString(String.valueOf(irModelFields.getId())));
                                        bodyItem.put(irModelFields.getName(), body_default.optString(String.valueOf(irModelFields.getId())));
                                    }
                                }
                            }
                        }
                        initHeaderView();
                    }
                }
            });
        }
    }

    /**
     * 默认值设置
     *
     * @param value         默认值
     * @param irModelFields 字段
     * @param p             0表头，1表体
     * @return 默认值
     */
    //默认值设置
    private String setDefValue(String value, IrModelFields irModelFields, int p) {
        if (p == 0 && intent.getStringExtra("action").equals("read")) {
            //表头在读取单据时默认值的设置
            String val = "";
            if (irModelFields.getVisiable()) {
                if (headerShowMap.get(irModelFields.getName()) != null)
                    val = headerShowMap.get(irModelFields.getName()).toString();
                if (irModelFields.getTtype().equals("float")) {
                    val = StringUtils.formatNumber(val);
                }
            } else {
                val = headerItem.get(irModelFields.getName()).toString();
            }
            if (irModelFields.getTtype().equals("date")) {
                val = val.equals("") ? "" : val.substring(0, 10);
            }
            return val;
        } else {
            if (irModelFields.getTtype().equals("many2one")) {
                if (p == 0) {
                    headerItem.put(irModelFields.getName(), many2oneDefValue.get(irModelFields.getId()));
                } else {
                    bodyItem.put(irModelFields.getName(), many2oneDefValue.get(irModelFields.getId()));
                }
            }
            String string;
            Date day = new Date();
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前时间
            if (value.indexOf("client.getDatetime") != -1) {
                string = df1.format(day);
            } else if (value.indexOf("client.getDate") != -1) {
                string = df2.format(day);
            } else if (value.indexOf("client.getUsername") != -1) {
                string = SPData.getUserinfo().getString(SP_UID, "");
            } else if (value.indexOf("fun.") != -1) {
                string = "";
            } else if (value.indexOf("api_") != -1 && value.indexOf("_api") != -1) {
                string = "";
            } else if (value.indexOf("client.getBanci") != -1) {
                string = SPData.getConfig().getString("shift", "");
            } else {
                string = value;
            }
            //为选单表体保存默认值
            if (p == 1) {
                setUserInputMap(irModelFields.getName(), string);
            }
            return string;
        }
    }


    //初始化PDA
    private void initPDA() {
        //判断是否为PDA
    }


    /**
     * 处理扫描后的结果
     *
     * @param code 扫描获得的值
     */
    //处理扫描后的结果
    private void getScanResult(final String code) {
        if (!TextUtils.isEmpty(code)) {
            isScaning = true;
            IrModelFields field = fieldsMap.get(curFieldId);
            final AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(field.getId());
            //获得扫描结果，判断这个字段是否有带入字段，如果有，则请求接口，将带入字段的值进行请求
            List<IrModelFields> changeFields;
            try {
                changeFields = many2oneFieldHashMap.get(field.getId()).getChangeFields();
            } catch (Exception e) {
                changeFields = null;
            }
            //如果changeFields长度大于0 则代表该字段有被其他字段监听，需要带出其他字段的值
            if (changeFields != null && changeFields.size() > 0) {
                HttpRequest httpRequest = new HttpRequest(mContext, URL_FORM, true);
                httpRequest.getRequest().add("action", "scanWithFields");//动作
                httpRequest.getRequest().add("scan_field", field.getName());//被扫描的字段名
                httpRequest.getRequest().add("scan_field_id", field.getId());//被扫描的字段id
                httpRequest.getRequest().add("model_id", model_id);//表头id
                httpRequest.getRequest().add("scan_field_value", code);//扫描到的值
                httpRequest.send(new SimpleHttpListener<JSONObject>() {
                    @Override
                    public void onSucceed(int what, Response<JSONObject> response) {
                        super.onSucceed(what, response);
                        try {
                            //获取表头字段所有ID
                            List<Long> heardIDList = new ArrayList<>();
                            for (int j = 0; j < irModelFieldsList.size(); j++) {
                                IrModelFields irModelFields = irModelFieldsList.get(j);
                                heardIDList.add(irModelFields.getId());
                            }
                            JSONArray json = response.get().getJSONArray("data");
                            actv.setText(code);
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject item = (JSONObject) json.get(i);
                                //需要显示在界面上的字段
                                if (item.getBoolean("visiable")) {
                                    //这个map用来记录id为key的字段是否要在点击保存的时候去后台重新请求数据，true代表不用啦
                                    many2oneIsOnClickMap.put(item.optLong("field_id"), true);
                                    //这个map用来记录id为key的字段是否是带过来的数据，如果是的话，就为true
                                    isWithDateMap.put(item.optLong("field_id"), true);
                                    AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(item.optLong("field_id"));
                                    actv.setText(item.optString("show_data"));
                                }
                                //如果是表头字段，则带入表头，否则带入表体
                                //表头字段，增加在表头
                                if (heardIDList.contains(item.optLong("field_id"))) {
                                    headerItem.put(item.optString("field_name"), item.optString("save_data"));
                                }
                                //表体字段，增加在表体
                                else {
                                    bodyItem.put(item.optString("field_name"), item.optString("save_data"));
                                }
                            }
                            //弹出更多
                            if (fieldsMap.get(curFieldId).getIs_show_more_list()) {
                                many2oneFieldHashMap.get(curFieldId).getMany2OneMore();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
            } else {
                actv.setText(code);
            }
        } else {
            isScaning = false;
        }
    }

    /**
     * 初始化字段视图
     *
     * @param view          视图
     * @param irModelFields 字段
     * @param type          0占两列，1、2各占1列
     * @param p             0表头，1表体
     */
    //初始化字段视图
    private void initFiledView(View view, IrModelFields irModelFields, int type, int p) {
        switch (irModelFields.getTtype()) {
            case "string":
                initString(view, irModelFields, type, p);
                break;
            case "int":
                initInt(view, irModelFields, type, p);
                break;
            case "float":
                initFloat(view, irModelFields, type, p);
                break;
            case "many2one":
                //many2oneFieldHashMap ：id，many2one类
                many2oneFieldHashMap.put(irModelFields.getId(), new InitMany2One(view, irModelFields, type, p));
                //初始将值设为false
                many2oneIsOnClickMap.put(irModelFields.getId(), false);
                break;
            case "date":
                initDate(view, irModelFields, type, p);
                break;
            case "datetime":
                initDatetime(view, irModelFields, type, p);
                break;
            case "text":
                initText(view, irModelFields, type, p);
                break;
        }
        initScan(view, irModelFields, type, p);
    }
    
    /**
     * 字段属性控制
     */
    private void fieldsControl(IrModelFields irModelFields, AutoCompleteTextView at_text) {
        //显示，不是只读，必填，保存，
        if (irModelFields.getVisiable() && irModelFields.getRequired() && irModelFields.getStore()) {
            at_text.setHint("必填");
            at_text.setHintTextColor(getResources().getColor(R.color.red));
        }
    }

    //初始化字符串字段string
    private void initString(View view, final IrModelFields irModelFields, int type, final int p) {
        TextView tv_title = null;
        AutoCompleteTextView at_text = null;
        if (type == 0) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text);
        } else if (type == 1) {
            tv_title = (TextView) view.findViewById(R.id.tv_title1);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text1);
        } else if (type == 2) {
            tv_title = (TextView) view.findViewById(R.id.tv_title2);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text2);
        }
        final AutoCompleteTextView at = at_text;
        at_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    focus_field_id = irModelFields.getId();
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        tv_title.setText(irModelFields.getField_desc());
        if (irModelFields.getLocation().equals(MULIT_SELECTION) && irModelFields.getVisiable() && irModelFields.getRelation_model_id() != 0) {
            //String类型的字段配置了多选
            tv_title.setText(irModelFields.getField_desc() + "▼");
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //执行多选操作
                    HashMap<String, Object> param = new HashMap<>();
                    HashMap<String, Object> depend = new HashMap<>();
                    if (!TextUtils.isEmpty(irModelFields.getDepend_field()) && !TextUtils.isEmpty(irModelFields.getSelect_cmd())) {
                        String[] depends = irModelFields.getDepend_field().split(",");
                        for (int i = 0; i < depends.length; i++) {
                            QueryBuilder<IrModelFields> qb = DBHelper.getDaoSession(mContext).getIrModelFieldsDao().queryBuilder();
                            qb.where(IrModelFieldsDao.Properties.Model_id.eq(irModelFields.getModel_id()), IrModelFieldsDao.Properties.Name.eq(depends[i])
                                    , IrModelFieldsDao.Properties.View_type.eq("form"));
                            IrModelFields dependField = qb.limit(1).list().get(0);
                            //依赖的是自己，则把界面上填的值传入
                            if (dependField.getId() == irModelFields.getId()) {
                                AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(dependField.getId());
                                if (!TextUtils.isEmpty(actv.getText().toString())) {
                                    depend.put(depends[i], actv.getText().toString());
                                }
                            } else {
                                if (p == 0) {
                                    //表头
                                    if (headerItem.get(dependField.getName()) != null)
                                        depend.put(depends[i], headerItem.get(dependField.getName()));
                                } else if (p == 1) {
                                    //表体
                                    if (bodyItem.get(dependField.getName()) != null) {
                                        depend.put(depends[i], bodyItem.get(dependField.getName()));
                                    }
                                }
                            }
                        }
                        if (depend.size() > 0) {
                            param.put("depend", JSON.toJSONString(depend));
                        }
                    }
                    //请求获取更多数据
                    param.put("field_id", irModelFields.getId());
                    HttpRequest httpRequest = new HttpRequest(mContext, URL_FORM, "getMany2oneMore", JSON.toJSONString(param), true, true);
                    httpRequest.send(new SimpleHttpListener<JSONObject>() {
                        @Override
                        public void onSucceed(int what, Response<JSONObject> response) {
                            super.onSucceed(what, response);
                            try {
                                JSONObject json = new JSONObject(response.get().toString());
                                final JSONObject[] data = {json.optJSONObject("data")};
                                JSONArray list = data[0].optJSONArray("list");
                                final String[] show = new String[list.length()];
                                final boolean[] isCheck = new boolean[list.length()];
                                for (int i = 0; i < list.length(); i++) {
                                    isCheck[i] = false;
                                }
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject item = (JSONObject) list.get(i);
                                    show[i] = item.optString(irModelFields.getRel_display_field());
                                }
                                AlertDialog.Builder ab = new AlertDialog.Builder(FormActivity.this);
                                ab.setTitle("请选择");
                                ab.setMultiChoiceItems(show, null, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                        isCheck[i] = b;
                                        if (b) {
                                            if (TextUtils.isEmpty(muiteDate)) {
                                                muiteDate = show[i];
                                            } else {
                                                muiteDate = muiteDate + "," + show[i];
                                            }
                                        }
                                    }
                                });

                                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                at.setText(muiteDate);
                                                muiteDate = "";
                                            }
                                        }
                                );
                                ab.create().show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }
            });
        }
        at_text.setVisibility(View.VISIBLE);
        itemWidget.put(irModelFields.getId(), at_text);
        fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中
        if (irModelFields.getReadonly() || isCheck) {
            tv_title.setText(tv_title.getText().toString() + "：");
            //如果字段配置了任何情况下可输入，那么就在任何情况下都可输入，审核除外
            if (irModelFields.getLocation().equals(FIELD_CANINPUT) && !isCheck) {
                at_text.setFocusableInTouchMode(true);
            } else {
                at_text.setFocusableInTouchMode(false);
                at_text.setBackgroundResource(R.drawable.bg_transparent);
                at_text.setFocusable(false);
            }
        }
        //保存临时值
        at_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notSave = true;
                if (p == 0) {
                    headerItem.put(irModelFields.getName(), s.toString());
                } else {
                    setUserInputMap(irModelFields.getName(), s.toString());
                    bodyItem.put(irModelFields.getName(), s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        at_text.setText(setDefValue(irModelFields.getDef_value(), irModelFields, p));
        fieldsControl(irModelFields, at_text);
    }

    //初始化int
    private void initInt(View view, final IrModelFields irModelFields, int type, final int p) {
        TextView tv_title = null;
        AutoCompleteTextView at_text = null;
        if (type == 0) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text);
        } else if (type == 1) {
            tv_title = (TextView) view.findViewById(R.id.tv_title1);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text1);

        } else if (type == 2) {
            tv_title = (TextView) view.findViewById(R.id.tv_title2);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text2);
        }
        at_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    focus_field_id = irModelFields.getId();
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        tv_title.setText(irModelFields.getField_desc());
        at_text.setVisibility(View.VISIBLE);
        at_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        itemWidget.put(irModelFields.getId(), at_text);
        fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中
        if (irModelFields.getReadonly() || isCheck) {
            tv_title.setText(tv_title.getText().toString() + "：");
            at_text.setFocusableInTouchMode(false);
            at_text.setBackgroundResource(R.drawable.bg_transparent);
            at_text.setFocusable(false);
        }
        //保存临时值
        at_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notSave = true;
                if (p == 0) {
                    headerItem.put(irModelFields.getName(), s.toString().equals("") ? "0" : s.toString());
                } else {
                    setUserInputMap(irModelFields.getName(), s.toString());
                    bodyItem.put(irModelFields.getName(), s.toString().equals("") ? "0" : s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        at_text.setText(setDefValue(irModelFields.getDef_value(), irModelFields, p));
        fieldsControl(irModelFields, at_text);
    }

    //初始化float
    private void initFloat(View view, final IrModelFields irModelFields, int type, final int p) {
        TextView tv_title = null;
        AutoCompleteTextView at_text = null;
        if (type == 0) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text);
        } else if (type == 1) {
            tv_title = (TextView) view.findViewById(R.id.tv_title1);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text1);
        } else if (type == 2) {
            tv_title = (TextView) view.findViewById(R.id.tv_title2);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text2);
        }
        tv_title.setText(irModelFields.getField_desc());
        at_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    focus_field_id = irModelFields.getId();
                    Logger.t("当前焦点的ID").d(focus_field_id);
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        at_text.setVisibility(View.VISIBLE);
        at_text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        itemWidget.put(irModelFields.getId(), at_text);
        fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中
        if (irModelFields.getReadonly() || isCheck) {
            tv_title.setText(tv_title.getText().toString() + "：");
            at_text.setFocusableInTouchMode(false);
            at_text.setBackgroundResource(R.drawable.bg_transparent);
            at_text.setFocusable(false);
        }
        at_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notSave = true;
                if (p == 0) {
                    headerItem.put(irModelFields.getName(), s.toString().equals("") ? "0" : s.toString());
                } else {
                    setUserInputMap(irModelFields.getName(), s.toString());
                    bodyItem.put(irModelFields.getName(), s.toString().equals("") ? "0" : s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        at_text.setText(setDefValue(irModelFields.getDef_value(), irModelFields, p));
        fieldsControl(irModelFields, at_text);
    }

    //初始化短日期字段date
    private void initDate(View view, final IrModelFields irModelFields, int type, final int p) {
        TextView tv_title = null;
        AutoCompleteTextView at_text = null;
        if (type == 0) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text);
        } else if (type == 1) {
            tv_title = (TextView) view.findViewById(R.id.tv_title1);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text1);
        } else if (type == 2) {
            tv_title = (TextView) view.findViewById(R.id.tv_title2);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text2);
        }
        tv_title.setText(irModelFields.getField_desc());
        at_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    focus_field_id = irModelFields.getId();
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        at_text.setVisibility(View.VISIBLE);
        itemWidget.put(irModelFields.getId(), at_text);
        fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中
        if (irModelFields.getReadonly() || isCheck) {
            tv_title.setText(tv_title.getText().toString() + "：");
            at_text.setFocusableInTouchMode(false);
            at_text.setBackgroundResource(R.drawable.bg_transparent);
            at_text.setFocusable(false);
        } else {
            at_text.setFocusableInTouchMode(false);
            at_text.setFocusable(false);
            final AutoCompleteTextView finalAt_text = at_text;
            at_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showTimePicker(irModelFields.getId(), false, String.valueOf(finalAt_text.getText()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //保存临时值
        at_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notSave = true;
                if (p == 0) {
                    headerItem.put(irModelFields.getName(), s.toString());
                } else {
                    setUserInputMap(irModelFields.getName(), s.toString());
                    bodyItem.put(irModelFields.getName(), s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        at_text.setText(setDefValue(irModelFields.getDef_value(), irModelFields, p));
        fieldsControl(irModelFields, at_text);
    }

    //初始化长日期字段datetime
    private void initDatetime(View view, final IrModelFields irModelFields, int type, final int p) {
        TextView tv_title = null;
        AutoCompleteTextView at_text = null;
        if (type == 0) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text);
        } else if (type == 1) {
            tv_title = (TextView) view.findViewById(R.id.tv_title1);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text1);
        } else if (type == 2) {
            tv_title = (TextView) view.findViewById(R.id.tv_title2);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text2);
        }
        tv_title.setText(irModelFields.getField_desc());
        at_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    focus_field_id = irModelFields.getId();
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        at_text.setVisibility(View.VISIBLE);
        itemWidget.put(irModelFields.getId(), at_text);
        fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中
        if (irModelFields.getReadonly() || isCheck) {
            tv_title.setText(tv_title.getText().toString() + "：");
            at_text.setFocusableInTouchMode(false);
            at_text.setBackgroundResource(R.drawable.bg_transparent);
            at_text.setFocusable(false);
        } else {
            at_text.setFocusableInTouchMode(false);
            at_text.setFocusable(false);
            final AutoCompleteTextView finalAt_text = at_text;
            at_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showTimePicker(irModelFields.getId(), true, finalAt_text.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //保存临时值
        at_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notSave = true;
                if (p == 0) {
                    headerItem.put(irModelFields.getName(), s.toString());
                } else {
                    setUserInputMap(irModelFields.getName(), s.toString());
                    bodyItem.put(irModelFields.getName(), s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        at_text.setText(setDefValue(irModelFields.getDef_value(), irModelFields, p));
        fieldsControl(irModelFields, at_text);
    }

    //初始化字符串字段Text
    private void initText(View view, final IrModelFields irModelFields, int type, final int p) {
        TextView tv_title = null;
        AutoCompleteTextView at_text = null;
        if (type == 0) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_textarea);
        } else if (type == 1) {
            tv_title = (TextView) view.findViewById(R.id.tv_title1);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_textarea1);
        } else if (type == 2) {
            tv_title = (TextView) view.findViewById(R.id.tv_title2);
            at_text = (AutoCompleteTextView) view.findViewById(R.id.at_textarea2);
        }
        at_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    focus_field_id = irModelFields.getId();
                    Logger.t("当前焦点的ID").d(focus_field_id);
                } else {
                }
            }
        });
        tv_title.setText(irModelFields.getField_desc());
        at_text.setVisibility(View.VISIBLE);
        itemWidget.put(irModelFields.getId(), at_text);
        fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中
        if (irModelFields.getReadonly() || isCheck) {
            tv_title.setText(tv_title.getText().toString() + "：");
            at_text.setFocusableInTouchMode(false);
            at_text.setBackgroundResource(R.drawable.bg_transparent);
            at_text.setFocusable(false);
        }
        //保存临时值
        at_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notSave = true;
                if (p == 0) {
                    headerItem.put(irModelFields.getName(), s.toString());
                } else {
                    setUserInputMap(irModelFields.getName(), s.toString());
                    bodyItem.put(irModelFields.getName(), s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        at_text.setText(setDefValue(irModelFields.getDef_value(), irModelFields, p));
        fieldsControl(irModelFields, at_text);
    }

    //初始化扫描
    private void initScan(View view, IrModelFields irModelFields, int colType, int p) {
        if (irModelFields.getScan() && colType == 0 && !isCheck) {
            isHaveScanFields = true;
            final Button btn_scan = (Button) view.findViewById(R.id.btn_scan);
            btn_scan.setVisibility(View.VISIBLE);
            btn_scan.setTag(irModelFields.getId());
            btn_scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isUseScan = true;
                    curFieldId = (long) v.getTag();
                    openScan();
                }
            });
        }

    }


    /**
     * 打开扫描界面扫描条形码或二维码扫描字段
     */
    private void openScan() {
        if (!ButtonUtils.isFastDoubleClick((int) curFieldId)) {
            Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
            openCameraIntent.putExtra("from", FormActivity.class.getName().toString());
            startActivityForResult(openCameraIntent, REQUESTCODE_CAMERA_FIELD);
        }
    }

    /**
     * 打开扫描界面扫描条形码或二维码选单
     */
    private void ChoiceDocument() {
        Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
        openCameraIntent.putExtra("from", FormActivity.class.getName().toString());
        startActivityForResult(openCameraIntent, REQUESTCODE_CAMERA_CHOICE);
    }

    //表头、表体切换
    @OnClick(R.id.btn_1)
    public void f1() {
        if (btn1.isEnabled()) {
            KeyboardUtils.hideSoftInput(this);
            if (sv_wrap.getVisibility() == View.GONE) {
                sv_wrap.setVisibility(View.VISIBLE);
                ll_main.setVisibility(View.GONE);
                table.setVisibility(View.GONE);
                btn1.setText("表体");
            } else {
                sv_wrap.setVisibility(View.GONE);
                if (!isCheck)
                    ll_main.setVisibility(View.VISIBLE);
                table.setVisibility(View.VISIBLE);
                btn1.setText("表头");
            }

        }
    }

    //审核
    @OnClick(R.id.btn_2)
    public void f2() {
        Toasty.success(mContext, "审核", Toast.LENGTH_LONG, true).show();
    }

    //反审核
    @OnClick(R.id.btn_3)
    public void f3() {
        Toasty.success(mContext, "反审核", Toast.LENGTH_LONG, true).show();
    }

    //保存表格数据
    @OnClick(R.id.btn_4)
    public void f4() {
        saveBodyItemData();
    }

    private void saveBodyItemData() {
        //遍历保存控件是否由点击获取至的map
        Iterator getMsgKrey = many2oneIsOnClickMap.entrySet().iterator();
        List<HashMap> verifyList = new ArrayList<>();
        while (getMsgKrey.hasNext()) {
            Map.Entry entry = (Map.Entry) getMsgKrey.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            HashMap<String, String> veriftMap = new HashMap<>();
            //为FALSE（不是由点击获得）,则封装入map，传入后台，进行保存值的获取
            if (!(Boolean) value) {
                //kongjian列表中存在 并且 需要保存
                if (itemWidget.containsKey(key) && idSveMap.get(Long.parseLong(key.toString()))) {
                    AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(Long.parseLong(key.toString()));
                    veriftMap.put("id", key + "");//空间id
                    veriftMap.put("value", actv.getText().toString());//控件上显示的值
                    veriftMap.put("name", idNameMap.get(key));//控件对应的字段名称
                    veriftMap.put("desc", idDescMap.get(key));//控件对应的字段描述
                    if (TextUtils.isEmpty(actv.getText().toString()) && !idRequired.get(key)) {
                        //该many2One字段输入的值为空 并且不需要必填，则不需要重新请求
                    } else {
                        verifyList.add(veriftMap);
                    }
                }

            }
        }
        if (verifyList.size() == 0) {
            //数据填充完毕，保存
            savaTableData();
        }
        else {
            //发送请求，进行数据获取
            HttpRequest httpRequest = new HttpRequest(mContext, URL_FORM, true);
            httpRequest.getRequest().add("verify_fields", JSON.toJSONString(verifyList));
            httpRequest.getRequest().add("action", "getMany2oneData");
            httpRequest.getRequest().add("model_id", model_id);
            httpRequest.send(new SimpleHttpListener<JSONObject>() {
                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    super.onSucceed(what, response);
                    try {
                        JSONObject json = response.get().getJSONObject("data");
                        String status = json.optString("status");
                        //请求数据返回成功
                        if (status.equals("200")) {
                            JSONArray jsonArray = json.optJSONArray("save_data");
                            //若有需要重新请求数据的控件，则对保存map重新进行赋值
                            int Length = jsonArray.length();
                            for (int i = 0; i < Length; i++) {
                                JSONObject item = (JSONObject) jsonArray.get(i);
                                //若返回的value值为空，则代表用户输入的数据有错误，导致数据库无法查询出保存的值
                                if (TextUtils.isEmpty(item.optString("value"))) {
                                    if (item.optInt("model_id") != model_id) {
                                        Toasty.info(mContext, "表体 " + idDescMap.get(item.optLong("id")) + "不存在", Toast.LENGTH_SHORT, true).show();
                                    } else {
                                        Toasty.info(mContext, "表头 " + idDescMap.get(item.optLong("id")) + "不存在", Toast.LENGTH_SHORT, true).show();
                                    }
                                    return;
                                } else {
                                    //更新保存值,表头表体分开保存
                                    if (item.optInt("model_id") != model_id) {
                                        bodyItem.put(item.optString("name"), item.optString("value"));
                                    } else {
                                        headerItem.put(item.optString("name"), item.optString("value"));
                                    }
                                }
                            }
                            //数据填充完毕，保存
                            savaTableData();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    // 保存表格数据
    public void savaTableData() {
        if (irModelFieldsOne2ManyList != null && btn4.isEnabled()) {
            KeyboardUtils.hideSoftInput(this);
            //保存one2many字段的数据
            final ArrayList<String> item = new ArrayList<>();
            final HashMap<String, Object> map = new HashMap<>();
            final HashMap<String, Object> bodyMap = new HashMap<>(bodyItem);
            boolean cancel = false; //检查是否必填的判断
            String msg = ""; //提示信息
            boolean compute = false; //是否有计算字段
            final ArrayList<HashMap<String, Object>> computeFields = new ArrayList<>();
            for (int i = 0; i < bodyListSize; i++) {
                IrModelFields field = irModelFieldsOne2ManyList.get(i);
                if (field.getVisiable()) {
                    AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(field.getId());
                    String value = actv.getText().toString();
                    if (field.getRequired()) {
                        if (value.equals("")) {
                            cancel = true;
                            msg = "表体 " + field.getField_desc() + " 必须填写，保存失败";
                            break;
                        } else if (bodyMap.get(field.getName()) == null) {
                            cancel = true;
                            msg = "表体 " + field.getField_desc() + "：" + value + " 必须填写，保存失败";
                            break;
                        }
                    }
                    item.add(value);
                    map.put(field.getName(), value);
                }
                //不显示，必填,
                else if (!field.getVisiable() && field.getRequired() && !TextUtils.isEmpty(field.getRel_change_field())) {
                    String rel_change_field = field.getRel_change_field();
                    String[] changeList = rel_change_field.split(",");
                    String selectFieldsName = "";
                    int changeListLength = changeList.length;
                    for (int j = 0; j < changeListLength; j++) {
                        //获取监听字段的id
                        Long id = descIdBodyMap.get(changeList[j]);
                        //通过监听字段的id获取监听字段的描述
                        if (TextUtils.isEmpty(selectFieldsName))
                            selectFieldsName = idDescMap.get(id);
                        else
                            selectFieldsName = selectFieldsName + " 或者 " + idDescMap.get(id);
                        ;
                    }
                    if (!bodyMap.containsKey(field.getName())) {
                        cancel = true;
                        msg = "请手动选择 " + selectFieldsName;
                        break;
                    } else {
                        if (TextUtils.isEmpty(bodyMap.get(field.getName()) + "")) {
                            cancel = true;
                            msg = "请手动选择 " + selectFieldsName;
                            break;
                        }
                    }
                } else {
                    if (curRow == -1 && TextUtils.isEmpty(field.getRel_change_field())) {
                        bodyMap.put(field.getName(), setDefValue(field.getDef_value(), field, 1));
                    }
                }
                if (field.getTcompute()) {
                    HashMap<String, Object> m = new HashMap<>();
                    m.put("name", field.getName());
                    m.put("index", item.size() - 1);
                    computeFields.add(m);
                    compute = true;
                }
            }
            if (cancel) {
                Toasty.error(mContext, msg, Toast.LENGTH_SHORT, true).show();
                return;
            }
            //有需要计算的字段
            if (compute) {
                HashMap<String, Object> param = new HashMap<>();
                param.put("bodyItem", JSON.toJSONString(bodyMap));
                param.put("model_id", irModelFieldsOne2Many.getRelation_model_id());
                HttpRequest httpRequest = new HttpRequest(mContext, URL_FORM, "getCompute", JSON.toJSONString(param), true, true);
                httpRequest.setmWhat(-10);
                httpRequest.send(new SimpleHttpListener<JSONObject>() {
                    @Override
                    public void onSucceed(int what, Response<JSONObject> response) {
                        super.onSucceed(what, response);
                        if (what == -10) {
                            JSONObject data = response.get().optJSONObject("data");
                            int computeFieldsSize = computeFields.size();
                            for (int i = 0; i < computeFieldsSize; i++) {
                                String fieldname = String.valueOf(computeFields.get(i).get("name"));
                                bodyMap.put(fieldname, data.optString(fieldname));
                                map.put(fieldname, data.optString(fieldname));
                                item.set((Integer) computeFields.get(i).get("index"), StringUtils.formatNumber(data.optString(fieldname)));
                            }
                            if (curRow > -1) {
                                //有修改行
                                tableListData.set(curRow + 1, item);
                                mList.set(curRow, map);
                                bodyList.set(curRow, bodyMap);
                                if (bodyMap.get(irModelFieldsOne2ManyKey.getName()) != null) {
                                    if (!bodyUpdateList.contains(String.valueOf(bodyMap.get(irModelFieldsOne2ManyKey.getName()))))
                                        bodyUpdateList.add(String.valueOf(bodyMap.get(irModelFieldsOne2ManyKey.getName())));
                                }
                            } else {

                                tableListData.add(1, item);
                                mList.add(0, map);
                                bodyList.add(0, bodyMap);

                            }
                            tableDataAdapter = new TableDataAdapter(mContext, tableListData, 28, 14);
                            table.setAdapter(tableDataAdapter);
                            cleanBodyForm();
                        }
                    }
                });
            } else {
                if (curRow > -1) {
                    //有修改行
                    tableListData.set(curRow + 1, item);
                    mList.set(curRow, map);
                    bodyList.set(curRow, bodyMap);
                    if (bodyMap.get(irModelFieldsOne2ManyKey.getName()) != null) {
                        if (!bodyUpdateList.contains(String.valueOf(bodyMap.get(irModelFieldsOne2ManyKey.getName()))))
                            bodyUpdateList.add(String.valueOf(bodyMap.get(irModelFieldsOne2ManyKey.getName())));
                    }
                } else {
                    tableListData.add(1, item);
                    mList.add(0, map);
                    bodyList.add(0, bodyMap);
                }
                tableDataAdapter = new TableDataAdapter(mContext, tableListData, 28, 14);
                table.setAdapter(tableDataAdapter);
                cleanBodyForm();
            }
            notSave = true;
            isClickTable = false;
        }
    }


    //清除表体内容
    private void cleanBodyForm() {
        KeyboardUtils.hideSoftInput(this);
        HashMap<String, Object> oldNotCleanMap = new HashMap<>();
        for (int i = 0; i < bodyListSize; i++) {
            IrModelFields field = irModelFieldsOne2ManyList.get(i);
            if (field.getVisiable() && (field.getLocation().equals(FIELD_NOTCLEAN))) {
                oldNotCleanMap.put(field.getName(), bodyItem.get(field.getName()));
            }
        }
        bodyItem = oldNotCleanMap;
        curRow = -1;

        for (int i = 0; i < bodyListSize; i++) {
            isSaveTable = true;
            IrModelFields field = irModelFieldsOne2ManyList.get(i);
            if (field.getVisiable() && (!field.getLocation().equals(FIELD_NOTCLEAN))) {
                AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(field.getId());
                isScaning = false;
                actv.setText(setDefValue(field.getDef_value(), field, 1));
            }
        }
        isSaveTable = false;
    }





    //重写键盘监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_CAMERA_FIELD && resultCode == RESULT_OK && data != null) {
            //扫描字段
            getScanResult(data.getStringExtra("result"));

        } else if (requestCode == REQUESTCODE_CAMERA_CHOICE && resultCode == RESULT_OK && data != null) {
            //选单
        }
    }


    @Override
    protected void onDestroy() {
        irModelFieldsOne2Many = null;
        if (irModelFieldsOne2ManyList != null)
            irModelFieldsOne2ManyList.clear();

        irModelFieldsOne2ManyKey = null;
        if (entryDeleteItems != null)
            entryDeleteItems.clear();
        irActWindow = null;
        if (irModelFieldsList != null)
            irModelFieldsList.clear();
        if (fieldsMap != null)
            fieldsMap.clear();
        if (tableListData != null)
            tableListData.clear();
        if (headerShowMap != null)
            headerShowMap.clear();
        if (mList != null)
            mList.clear();
        bodyList.clear();
        if (itemWidget != null)
            itemWidget.clear();
        if (many2oneListMap != null)
            many2oneListMap.clear();
        if (many2oneList != null)
            many2oneList.clear();
        if (many2oneMoreListMap != null)
            many2oneMoreListMap.clear();
        if (changeMap != null)
            changeMap.clear();
        if (changeSelectList != null)
            changeSelectList.clear();
        if (many2oneMoreList != null)
            many2oneMoreList.clear();
        if (bodyItem != null)
            bodyItem.clear();
        if (headerItem != null)
            headerItem.clear();
        bodyUpdateList.clear();
        if (selectList != null)
            selectList.clear();
        if (many2oneFieldHashMap != null)
            many2oneFieldHashMap.clear();
        if (many2oneDefValue != null)
            many2oneDefValue.clear();
        if (many2oneOriginMore != null)
            many2oneOriginMore.clear();

        super.onDestroy();

    }

    /**
     * 获取通过接口设置的默认值
     */
    private void getApiDefault() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("model_id", model_id);
        HttpRequest httpRequest = new HttpRequest(mContext, URL_FORM, "getApiDefault", JSON.toJSONString(param), true, true);

        httpRequest.getRequest().add("appVersionName", AppUtils.getAppVersionName(AppUtils.getAppPackageName()));
        httpRequest.send(new SimpleHttpListener<JSONObject>() {
            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                super.onFailed(what, response);
                Toasty.error(mContext, getString(R.string.error_getDefault), Toast.LENGTH_SHORT, true).show();
                finish();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                super.onSucceed(what, response);
                JSONObject data = response.get().optJSONObject("data");
                PKeyName = data.optString("primary_key");
                PKeyValue = data.optInt("primary_key_value") + "";
                headerItem.put(data.optString("primary_key"), data.optInt("primary_key_value"));
                //表头
                if (intent.getStringExtra("action").equals("create")) {
                    for (int i = 0; i < irModelFieldsList.size(); i++) {
                        IrModelFields irModelFields = irModelFieldsList.get(i);
                        if (irModelFields.getDef_value().indexOf("api_") > -1 && irModelFields.getDef_value().indexOf("_api") > -1) {
                            irModelFields.setDef_value(data.optString(String.valueOf(irModelFields.getId())));
                            if (irModelFields.getVisiable()) {
                                AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(irModelFields.getId());
                                actv.setText(data.optString(String.valueOf(irModelFields.getId())));
                            } else {
                                headerItem.put(irModelFields.getName(), data.optString(String.valueOf(irModelFields.getId())));
                            }
                        } else if (irModelFields.getTtype().equals("many2one") && !irModelFields.getDef_value().equals("")) {
                            many2oneDefValue.put(irModelFields.getId(), data.optString(String.valueOf(irModelFields.getId())));
                            headerItem.put(irModelFields.getName(), data.optString(String.valueOf(irModelFields.getId())));
                        }
                    }
                }
                //表体
                if (irModelFieldsOne2ManyList != null) {
                    for (int i = 0; i < bodyListSize; i++) {
                        IrModelFields irModelFields = irModelFieldsOne2ManyList.get(i);
                        if (irModelFields.getDef_value().indexOf("api_") > -1 && irModelFields.getDef_value().indexOf("_api") > -1) {
                            irModelFields.setDef_value(data.optString(String.valueOf(irModelFields.getId())));
                            if (irModelFields.getVisiable()) {
                                AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(irModelFields.getId());
                                actv.setText(irModelFields.getDef_value());
                                setUserInputMap(irModelFields.getName(), irModelFields.getDef_value());
                            } else {
                                bodyItem.put(irModelFields.getName(), data.optString(String.valueOf(irModelFields.getId())));
                            }
                        } else if (irModelFields.getTtype().equals("many2one") && !irModelFields.getDef_value().equals("")) {
                            many2oneDefValue.put(irModelFields.getId(), data.optString(String.valueOf(irModelFields.getId())));
                            bodyItem.put(irModelFields.getName(), data.optString(String.valueOf(irModelFields.getId())));
                        }
                    }
                }
            }
        });
    }

    /**
     * 时间选择器
     *
     * @param id       控件id
     * @param isSecond 是否需要显示时分秒
     * @param dateStr  当前时间字符串
     * @throws ParseException
     */
    public void showTimePicker(final long id, final boolean isSecond, String dateStr) throws ParseException {
//        boolean[] timer;
//        if (isSecond) {
//            timer = new boolean[]{true, true, true, true, true, true};
//        } else {
//            timer = new boolean[]{true, true, true, false, false, false};
//        }
//        Calendar startDate = Calendar.getInstance();
//        Calendar endDate = Calendar.getInstance();
//        Calendar calendar = Calendar.getInstance();
//        if (!TextUtils.isEmpty(dateStr) && !isSecond) {
////            calendar.setTime(TimeUtils.string2Date(dateStr, "yyyy-MM-dd"));
//        } else if (!TextUtils.isEmpty(dateStr) && isSecond) {
////            calendar.setTime(TimeUtils.string2Date(dateStr, "yyyy-MM-dd HH:mm:ss"));
//        }
//        //结束年份是当前年份+5
////        int endYear = Integer.parseInt(TimeUtils.date2String(TimeUtils.string2Date(dateStr, "yyyy"), "yyyy")) + 5;
//      int endYear = 2030;
//        //设置起始年份为2010-1-1
//        startDate.set(2010, 0, 1);
//        //设置结束年份为（当前年份+5）-12-31
//        endDate.set(endYear, 11, 31);
//        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                AutoCompleteTextView view = (AutoCompleteTextView) itemWidget.get(id);
//                if (isSecond) {
//                    view.setText(TimeUtils.date2String(date, "yyyy-MM-dd HH:mm:ss"));
//                } else {
//                    view.setText(TimeUtils.date2String(date, "yyyy-MM-dd"));
//                }
//            }
//        })
//                .setType(timer)// 默认全部显示
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确认")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText("选择时间")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(false)//是否循环滚动
//                .setTitleColor(0xFF23BEFF)//标题文字颜色
//                .setSubmitColor(0xFF23BEFF)//确定按钮文字颜色
//                .setCancelColor(0xFF23BEFF)//取消按钮文字颜色
//                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
//                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
//                .setDate(calendar)
//                .build();
//        pvTime.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 将界面已经存在的信息放入map,作为选单带入数据
     */
    private void setUserInputMap(String key, String value) {
        //默认只带入FStockID 以及 FStockPlaceID
        List<String> needCopyArray = new ArrayList<String>();
        needCopyArray.add("FStockID");
        needCopyArray.add("FStockPlaceID");
        if (needCopyArray.contains(key))
            userInputMap.put(key, value);
    }


    //初始化many2one字段
    public class InitMany2One {
        private View view;
        private IrModelFields irModelFields;
        private int type;
        private int p;
        private List<IrModelFields> changeFields;
        private AutoCompleteTextView at_text;
        private List<IrModelFields> relation_fields;

        public InitMany2One(View view, IrModelFields irModelFields, int type, int p) {
            this.view = view;
            this.irModelFields = irModelFields;
            this.type = type;
            this.p = p;
            init();
        }

        private void init() {
            TextView tv_title = null;
            if (type == 0) {
                tv_title = (TextView) view.findViewById(R.id.tv_title);
                at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text);
            } else if (type == 1) {
                tv_title = (TextView) view.findViewById(R.id.tv_title1);
                at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text1);
            } else if (type == 2) {
                tv_title = (TextView) view.findViewById(R.id.tv_title2);
                at_text = (AutoCompleteTextView) view.findViewById(R.id.at_text2);
            }
            at_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        focus_field_id = irModelFields.getId();
                        Logger.t("当前焦点的ID").d(focus_field_id);
                    } else {
                        // 此处为失去焦点时的处理内容
                    }
                }
            });
            many2oneOriginMore.put(irModelFields.getId(), false);
            at_text.setText(setDefValue(irModelFields.getDef_value(), irModelFields, p));
            fieldsControl(irModelFields, at_text);
            at_text.setVisibility(View.VISIBLE);
            itemWidget.put(irModelFields.getId(), at_text);
            fieldsMap.put(irModelFields.getId(), irModelFields); //加入到全局字段hashmap中

            if (irModelFields.getReadonly() || isCheck) {
                tv_title.setText(irModelFields.getField_desc() + "：");
                at_text.setFocusableInTouchMode(false);
                at_text.setBackgroundResource(R.drawable.bg_transparent);
                at_text.setFocusable(false);
            } else {
                //many2one类型字段  点击展示更多dialog 监听
                tv_title.setText(irModelFields.getField_desc() + " ▼");
                tv_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideSoftInput(mActivity);
                        //弹出展示更多dialog
                        getMany2OneMore();
                    }
                });
            }


            //查询关联模型内的字段
            QueryBuilder<IrModelFields> qb = DBHelper.getDaoSession(mContext).getIrModelFieldsDao().queryBuilder();
            qb.where(IrModelFieldsDao.Properties.Model_id.eq(irModelFields.getRelation_model_id())).orderAsc(IrModelFieldsDao.Properties.Sequence);
            relation_fields = qb.list();

            //获取关联改变的字段
            QueryBuilder<IrModelFields> qb2 = DBHelper.getDaoSession(mContext).getIrModelFieldsDao().queryBuilder();
            qb2.where(IrModelFieldsDao.Properties.Model_id.eq(irModelFields.getModel_id()), IrModelFieldsDao.Properties.View_type.eq("form"),
                    IrModelFieldsDao.Properties.Rel_change_field.like("%" + irModelFields.getName() + "%"));

            changeFields = qb2.list();
            //过滤掉模糊查询出来不匹配的
            for (int j = 0; j < changeFields.size(); j++) {
                IrModelFields field = changeFields.get(j);
                String[] fields = field.getRel_change_field().split(",");
                if (!Arrays.asList(fields).contains(irModelFields.getName())) {
                    changeFields.remove(j);
                }
            }

            //保存临时值
            at_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //不是点击,手动输入
                    List<IrModelFields> changeFields = many2oneFieldHashMap.get(irModelFields.getId()).getChangeFields();
                    if (!isMany2OneOnClick) {
                        //不是带过来的数据
                        if (!isWithDateMap.get(irModelFields.getId())) {
                            many2oneIsOnClickMap.put(irModelFields.getId(), false);
                            if (changeFields.size() > 0) {
                                for (int i = 0; i < changeFields.size(); i++) {
                                    //&& changeFields.get(i).getStore()
                                    if (changeFields.get(i).getVisiable()) {
                                        AutoCompleteTextView actx = (AutoCompleteTextView) itemWidget.get(changeFields.get(i).getId());
                                        //设置成true，是让该字段变成带出字段
                                        isWithDateMap.put(changeFields.get(i).getId(), true);
                                        //设置成true 主要是让该字段不去请求
                                        isGetMoreData.put(changeFields.get(i).getId(), true);
                                        actx.setText("");
                                    }
                                    if (bodyItem.containsKey(changeFields.get(i).getName()))
                                        bodyItem.remove(changeFields.get(i).getName());
                                }
                            }
                        }
                        //带过来的数据
                        else {
                            //将是否有带过来数据的map重新变为false
                            isWithDateMap.put(irModelFields.getId(), false);
                            setUserInputMap(irModelFields.getName(), s.toString());
                        }
                    }
                    //是点击 为表体字段
                    else {
                        isMany2OneOnClick = false;
                        if (changeFields.size() > 0) {
                            for (int i = 0; i < changeFields.size(); i++) {
                                if (changeFields.get(i).getTtype().equals("many2one")) {
                                    many2oneIsOnClickMap.put(changeFields.get(i).getId(), true);
                                    isWithDateMap.put(changeFields.get(i).getId(), true);
                                }
                            }
                        }
                    }
                    //展示更多。 有文字改变监听。 是否点击表体信息。 是否由更多得来.是否有保存得来
                    if (isShowHint && !isTextChanged && !isClickBody && !many2oneOriginMore.get(irModelFields.getId()) && !isSaveTable) {
                        //是否是因为修改了主字段，导致其他字段因值改变而进入change监听
                        if (!isGetMoreData.get(irModelFields.getId())) {
                            isTextChanged = true;
                            if ((count > 0 || before > 0) && !originList && !many2oneOriginMore.get(irModelFields.getId())) {
                                if (many2oneHashMap.get(irModelFields.getId()) == null) {
                                    many2oneHashMap.put(irModelFields.getId(), new Many2One(irModelFields, at_text, p));
                                }
                                many2oneHashMap.get(irModelFields.getId()).getData();
                            }
                            //获取提示数据
                            originList = false;
                        } else {
                            isGetMoreData.put(irModelFields.getId(), false);
                        }
                    }

                    //把填写的内容临时保存在一个map中，点击"保存"按钮时保存到另外一个map中
                    if (s.toString().equals("")) {
                        if (p == 0) {
                            headerItem.put(irModelFields.getName(), "");
                        } else {
                            bodyItem.put(irModelFields.getName(), "");
                            setUserInputMap(irModelFields.getName(), "");
                        }
                    } else {
                        if (changeSelectList.get(irModelFields.getId()) != null && many2oneOriginMore.get(irModelFields.getId())) {
                            if (p == 0) {
                                headerItem.put(irModelFields.getName(), many2oneMoreList.get(irModelFields.getId()).get(changeSelectList.get(irModelFields.getId())).get(irModelFields.getRelation_field()));
                            } else {
                                Object msg = many2oneMoreList.get(irModelFields.getId()).get(changeSelectList.get(irModelFields.getId())).get(irModelFields.getRelation_field());
                                setUserInputMap(irModelFields.getName(), s.toString());
                                bodyItem.put(irModelFields.getName(), msg);
                            }
                        }
                    }
                    if (isMoreListEnter) {
                        getIntoData();
                        isMoreListEnter = false;
                    }
                    many2oneOriginMore.put(irModelFields.getId(), false);
                    changeSelectList.remove(irModelFields.getId());
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
        }

        /**
         * 带入数据
         */
        private void getIntoData() {
            //处理关联改变字段，点击表体的某条记录时不处理
            if (changeFields.size() > 0 && !isClickBody) {
                for (int i = 0; i < changeFields.size(); i++) {
                    IrModelFields f = changeFields.get(i);
                    many2oneOriginMore.put(f.getId(), true);

                    //判断字段有没有对应
                    if (changeSelectList.get(irModelFields.getId()) != null && many2oneOriginMore.get(irModelFields.getId())) {
                        //保存值
                        if (!TextUtils.isEmpty(f.getChange_save_field())) {
                            if (p == 0) {
                                headerItem.put(f.getName(), many2oneMoreList.get(irModelFields.getId()).get(changeSelectList.get(irModelFields.getId())).get(f.getChange_save_field()));
                            } else {
                                bodyItem.put(f.getName(), many2oneMoreList.get(irModelFields.getId()).get(changeSelectList.get(irModelFields.getId())).get(f.getChange_save_field()));
                            }
                        } else {
                            if (p == 0) {
                                headerItem.put(f.getName(), headerItem.get(irModelFields.getName()));
                            } else {
                                bodyItem.put(f.getName(), bodyItem.get(irModelFields.getName()));
                            }
                        }

                        //判断是否要显示
                        if (f.getVisiable()) {
                            AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(f.getId());
                            //显示值
                            if (!TextUtils.isEmpty(f.getChange_display_field())) {
                                actv.setText(String.valueOf(many2oneMoreList.get(irModelFields.getId()).
                                        get(changeSelectList.get(irModelFields.getId())).get(f.getChange_display_field())));
                            } else {
                                actv.setText(String.valueOf(many2oneMoreList.get(irModelFields.getId()).
                                        get(changeSelectList.get(irModelFields.getId())).get(f.getChange_save_field())));
                            }
                        }
                    }
                }
            }

        }

        //获取many2one类型字段的更多数据，可带入其他字段的值
        public void getMany2OneMore() {
            HashMap<String, Object> param = new HashMap<>();
            //获取更多的时候处理依赖字段
            HashMap<String, Object> depend = new HashMap<>();
            if (!TextUtils.isEmpty(irModelFields.getDepend_field()) && !TextUtils.isEmpty(irModelFields.getSelect_cmd())) {
                String[] depends = irModelFields.getDepend_field().split(",");
                for (int i = 0; i < depends.length; i++) {
                    QueryBuilder<IrModelFields> qb = DBHelper.getDaoSession(mContext).getIrModelFieldsDao().queryBuilder();
                    qb.where(IrModelFieldsDao.Properties.Model_id.eq(irModelFields.getModel_id()), IrModelFieldsDao.Properties.Name.eq(depends[i])
                            , IrModelFieldsDao.Properties.View_type.eq("form"));
                    IrModelFields dependField = qb.limit(1).list().get(0);
                    //依赖的是自己，则把界面上填的值传入
                    if (dependField.getId() == irModelFields.getId()) {
                        AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(dependField.getId());
                        if (!TextUtils.isEmpty(actv.getText().toString())) {
                            depend.put(depends[i], actv.getText().toString());
                        }
                    } else {
                        if (p == 0) {
                            //表头
                            if (headerItem.get(dependField.getName()) != null)
                                depend.put(depends[i], headerItem.get(dependField.getName()));
                        } else if (p == 1) {
                            //表体
                            if (bodyItem.get(dependField.getName()) != null) {
                                depend.put(depends[i], bodyItem.get(dependField.getName()));
                            }
                        }
                    }
                }
                if (depend.size() > 0) {
                    param.put("depend", JSON.toJSONString(depend));
                }
            }
            //请求获取更多数据
            param.put("field_id", irModelFields.getId());
            HttpRequest httpRequest = new HttpRequest(mContext, URL_FORM, "getMany2oneMore", JSON.toJSONString(param), true, true);
            httpRequest.send(new SimpleHttpListener<JSONObject>() {
                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    super.onSucceed(what, response);
                    JSONObject data = response.get().optJSONObject("data");
                    com.alibaba.fastjson.JSONArray list = JSON.parseArray(data.optString("list"));
                    //获取更多
                    ArrayList<HashMap<String, Object>> rowList = new ArrayList<>();
                    ArrayList<String> showList = new ArrayList<>();
                    final ArrayList<HashMap<String, Object>> changeItemList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        HashMap<String, Object> row = new HashMap<>();
                        com.alibaba.fastjson.JSONObject item = list.getJSONObject(i);
                        for (int j = 0; j < relation_fields.size(); j++) {
                            row.put(relation_fields.get(j).getName(), item.get(relation_fields.get(j).getName()));
                        }
                        changeItemList.add(row);
                        rowList.add(row);
                        showList.add(String.valueOf(row.get(irModelFields.getRel_display_field())));
                    }
                    many2oneMoreListMap.put(irModelFields.getId(), showList);
                    many2oneMoreList.put(irModelFields.getId(), changeItemList);

                    Many2OneAdapter many2OneAdapter = new Many2OneAdapter(mContext, rowList, relation_fields);
                    View view = layoutInflater.inflate(R.layout.many2one_list, null);
                    ListView lv = (ListView) view.findViewById(R.id.lv);
                    lv.setAdapter(many2OneAdapter);
                    dialog = new AlertDialog.Builder(mContext).setTitle("选择" + irModelFields.getField_desc()).setView(view).create();
                    dialog.show();
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            dialog.dismiss();
                            //点击更多 出现列表，点击列表中具体某一条数据！
                            //属性位置设置为autochoisebill 表示自动选单
                            many2oneIsOnClickMap.put(irModelFields.getId(), true);
                            isMany2OneOnClick = true;
                            isMoreListEnter = true;
                            many2oneOriginMore.put(irModelFields.getId(), true);
                            changeSelectList.put(irModelFields.getId(), position);
                            at_text.setText(many2oneMoreListMap.get(irModelFields.getId()).get(position));
                            at_text.dismissDropDown();
                            //字段中配置autochoisebill，每当选择了MANYONE2 自动实现选单功能
                            String[] location = irModelFields.getLocation().split(",");
                            if (location[0].equals(FIELD_AUTOCHOISEBILL)) {

                            }
                        }
                    });
                }
            });
        }

        public List<IrModelFields> getChangeFields() {
            return changeFields;
        }

        public List<IrModelFields> getRelation_fields() {
            return relation_fields;
        }
    }

    //初始化获取many2one匹配到的数据
    public class Many2One {
        private IrModelFields irModelFields;
        private AutoCompleteTextView actv;
        private String[] strings;
        private ArrayAdapter<String> arrayAdapter; //提示适配器
        private int p;

        public Many2One(final IrModelFields irModelFields, AutoCompleteTextView actv, int initP) {
            this.irModelFields = irModelFields;
            this.actv = actv;
            this.p = initP;
            //点击之后
            actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //保存选中的位置
                    selectList.put(irModelFields.getId(), position);
                    //是否点击：是  用于onTextChange监听判断 字段改变是来自用户手动输入 还是 用户选择点击
                    isMany2OneOnClick = true;
                    //保存该字段是用户手动输入还有选择点击
                    many2oneIsOnClickMap.put(irModelFields.getId(), true);
                    originList = true;
                    //如果该字段关联监听了其余字段，那么要对其余字段也做处理
                    List<IrModelFields> changeFields = many2oneFieldHashMap.get(irModelFields.getId()).getChangeFields();
                    if (changeFields.size() > 0) {
                        for (int i = 0; i < changeFields.size(); i++) {
                            if (changeFields.get(i).getTtype().equals("many2one")) {
                                //对其余字段是否点击 设置为true
                                many2oneIsOnClickMap.put(changeFields.get(i).getId(), true);
                                //这个map是说明这些数据是通过带入的数据，从而在onTextChange中不需要处理
                                isWithDateMap.put(changeFields.get(i).getId(), true);
                            }
                        }
                    }
                    show(position);

                }
            });
        }

        //保存获取到的值和通过改变获取到的值
        private void show(int position) {
            if (p == 0) {
                headerItem.put(irModelFields.getName(), many2oneList.get(irModelFields.getId()).get(position).get(irModelFields.getRelation_field()));
            } else {
                setUserInputMap(irModelFields.getName(), String.valueOf(actv.getText()));
                bodyItem.put(irModelFields.getName(), many2oneList.get(irModelFields.getId()).get(position).get(irModelFields.getRelation_field()));
            }
            //处理关联改变字段
            List<IrModelFields> changeFields = many2oneFieldHashMap.get(irModelFields.getId()).getChangeFields();
            if (changeFields.size() > 0) {
                for (int i = 0; i < changeFields.size(); i++) {
                    IrModelFields f = changeFields.get(i);
                    //保存值
                    if (!TextUtils.isEmpty(f.getChange_save_field())) {
                        if (many2oneList.get(irModelFields.getId()).get(position).get(f.getChange_save_field()) != null) {
                            if (p == 0) {
                                headerItem.put(f.getName(), many2oneList.get(irModelFields.getId()).get(position).get(f.getChange_save_field()));
                            } else {
                                setUserInputMap(f.getName(), String.valueOf(actv.getText()));
                                bodyItem.put(f.getName(), many2oneList.get(irModelFields.getId()).get(position).get(f.getChange_save_field()));
                            }
                        } else {
//                            ToastUtils.showLongToast("关联模型中字段 " + f.getChange_save_field() + " 的值不存在");
                            Toasty.info(mContext, "关联模型中字段 " + f.getChange_save_field() + " 的值不存在", Toast.LENGTH_SHORT, true).show();

                            if (p == 0) {
                                headerItem.put(f.getName(), "");
                            } else {
                                setUserInputMap(f.getName(), "");
                                bodyItem.put(f.getName(), "");
                            }
                        }
                    } else {
                        if (p == 0) {
                            headerItem.put(f.getName(), headerItem.get(irModelFields.getName()));
                        } else {
                            setUserInputMap(f.getName(), String.valueOf(actv.getText()));
                            bodyItem.put(f.getName(), bodyItem.get(irModelFields.getName()));
                        }
                    }
                    //判断是否要显示
                    if (f.getVisiable()) {
                        AutoCompleteTextView actv = (AutoCompleteTextView) itemWidget.get(f.getId());
                        //显示值
                        if (!TextUtils.isEmpty(f.getChange_display_field())) {
                            if (many2oneList.get(irModelFields.getId()).get(position).get(f.getChange_display_field()) != null) {
                                actv.setText(String.valueOf(many2oneList.get(irModelFields.getId()).get(position).get(f.getChange_display_field())));
                            } else {
//                                ToastUtils.showLongToast("关联模型中字段 " + f.getChange_save_field() + " 的值不存在");
                                Toasty.info(mContext, "关联模型中字段 " + f.getChange_save_field() + " 的值不存在", Toast.LENGTH_SHORT, true).show();
                                actv.setText("");
                            }
                        } else {
                            if (many2oneList.get(irModelFields.getId()).get(position).get(f.getChange_save_field()) != null) {
                                actv.setText(String.valueOf(many2oneList.get(irModelFields.getId()).get(position).get(f.getChange_save_field())));
                            }
                        }
                    }
                }
            }
        }

        //从服务器获取匹配到的数据
        private void getData() {
            HashMap<String, Object> param = new HashMap<>();
            param.put("searchValue", actv.getText().toString());
            param.put("field_id", irModelFields.getId());
            param.put("model_id", model_id);
            if (!isRequest) {
                isRequest = true;
                HttpRequest httpRequest = new HttpRequest(mContext, URL_FORM, "getMany2oneList", JSON.toJSONString(param), true, false);
                httpRequest.setmConnectTimeout(5000);
                httpRequest.send(new SimpleHttpListener<JSONObject>() {
                    @Override
                    public void onSucceed(int what, Response<JSONObject> response) {
                        super.onSucceed(what, response);
                        if (!originList) {
                            com.alibaba.fastjson.JSONObject json = JSON.parseObject(response.get().toString());
                            com.alibaba.fastjson.JSONArray list = json.getJSONObject("data").getJSONArray("list");
                            if (list.size() > 0) {
                                int index = -1;
                                strings = new String[list.size()];
                                ArrayList<HashMap<String, Object>> changeItemList = new ArrayList<>();
                                List<IrModelFields> relation_fields = many2oneFieldHashMap.get(irModelFields.getId()).getRelation_fields();
                                for (int i = 0; i < list.size(); i++) {
                                    HashMap<String, Object> row = new HashMap<>();
                                    com.alibaba.fastjson.JSONObject item = list.getJSONObject(i);
                                    strings[i] = item.getString(irModelFields.getRel_display_field());
                                    for (int j = 0; j < relation_fields.size(); j++) {
                                        row.put(relation_fields.get(j).getName(), item.get(relation_fields.get(j).getName()));
                                    }
                                    changeItemList.add(row);
                                    //拿到第一条匹配的数据
                                    if (strings[i].equals(actv.getText().toString()) && index < 0) {
                                        index = i;
                                    }
                                }
                                many2oneList.put(irModelFields.getId(), changeItemList);

                                arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, strings);
                                actv.setAdapter(arrayAdapter);
                                actv.setThreshold(1);
                                if (isScaning) {
                                    actv.dismissDropDown();
                                } else {
                                    if (!isUseScan) {
                                        actv.showDropDown();
                                    } else {
                                        isUseScan = false;
                                    }
                                }

                                if (index > -1) {
                                    //show(index);
                                } else {
                                    if (p == 0) {
                                        headerItem.remove(irModelFields.getName());
                                    } else {
                                        bodyItem.remove(irModelFields.getName());
                                    }
                                }
                            } else {
                                actv.dismissDropDown();
                            }
                        } else {
                            actv.dismissDropDown();
                        }
                        originList = false;
                        isRequest = false;
                        isTextChanged = false;
                        isScaning = false;

                    }

                    @Override
                    public void onFailed(int what, Response<JSONObject> response) {
                        super.onFailed(what, response);
                        if (originList) {
                            //有点过提示
                            originList = false;
                            actv.dismissDropDown();
                        }
                        isRequest = false;
                        isTextChanged = false;
                        isScaning = false;
                    }
                });
            }
        }


    }


}
