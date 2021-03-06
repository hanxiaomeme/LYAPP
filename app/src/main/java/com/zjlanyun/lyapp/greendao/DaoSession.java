package com.zjlanyun.lyapp.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zjlanyun.lyapp.greendao.IrActWindow;
import com.zjlanyun.lyapp.greendao.IrConfig;
import com.zjlanyun.lyapp.greendao.IrExtend;
import com.zjlanyun.lyapp.greendao.IrModel;
import com.zjlanyun.lyapp.greendao.IrModelAccess;
import com.zjlanyun.lyapp.greendao.IrModelFields;
import com.zjlanyun.lyapp.greendao.IrSearchFields;
import com.zjlanyun.lyapp.greendao.IrUiMenu;

import com.zjlanyun.lyapp.greendao.IrActWindowDao;
import com.zjlanyun.lyapp.greendao.IrConfigDao;
import com.zjlanyun.lyapp.greendao.IrExtendDao;
import com.zjlanyun.lyapp.greendao.IrModelDao;
import com.zjlanyun.lyapp.greendao.IrModelAccessDao;
import com.zjlanyun.lyapp.greendao.IrModelFieldsDao;
import com.zjlanyun.lyapp.greendao.IrSearchFieldsDao;
import com.zjlanyun.lyapp.greendao.IrUiMenuDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig irActWindowDaoConfig;
    private final DaoConfig irConfigDaoConfig;
    private final DaoConfig irExtendDaoConfig;
    private final DaoConfig irModelDaoConfig;
    private final DaoConfig irModelAccessDaoConfig;
    private final DaoConfig irModelFieldsDaoConfig;
    private final DaoConfig irSearchFieldsDaoConfig;
    private final DaoConfig irUiMenuDaoConfig;

    private final IrActWindowDao irActWindowDao;
    private final IrConfigDao irConfigDao;
    private final IrExtendDao irExtendDao;
    private final IrModelDao irModelDao;
    private final IrModelAccessDao irModelAccessDao;
    private final IrModelFieldsDao irModelFieldsDao;
    private final IrSearchFieldsDao irSearchFieldsDao;
    private final IrUiMenuDao irUiMenuDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        irActWindowDaoConfig = daoConfigMap.get(IrActWindowDao.class).clone();
        irActWindowDaoConfig.initIdentityScope(type);

        irConfigDaoConfig = daoConfigMap.get(IrConfigDao.class).clone();
        irConfigDaoConfig.initIdentityScope(type);

        irExtendDaoConfig = daoConfigMap.get(IrExtendDao.class).clone();
        irExtendDaoConfig.initIdentityScope(type);

        irModelDaoConfig = daoConfigMap.get(IrModelDao.class).clone();
        irModelDaoConfig.initIdentityScope(type);

        irModelAccessDaoConfig = daoConfigMap.get(IrModelAccessDao.class).clone();
        irModelAccessDaoConfig.initIdentityScope(type);

        irModelFieldsDaoConfig = daoConfigMap.get(IrModelFieldsDao.class).clone();
        irModelFieldsDaoConfig.initIdentityScope(type);

        irSearchFieldsDaoConfig = daoConfigMap.get(IrSearchFieldsDao.class).clone();
        irSearchFieldsDaoConfig.initIdentityScope(type);

        irUiMenuDaoConfig = daoConfigMap.get(IrUiMenuDao.class).clone();
        irUiMenuDaoConfig.initIdentityScope(type);

        irActWindowDao = new IrActWindowDao(irActWindowDaoConfig, this);
        irConfigDao = new IrConfigDao(irConfigDaoConfig, this);
        irExtendDao = new IrExtendDao(irExtendDaoConfig, this);
        irModelDao = new IrModelDao(irModelDaoConfig, this);
        irModelAccessDao = new IrModelAccessDao(irModelAccessDaoConfig, this);
        irModelFieldsDao = new IrModelFieldsDao(irModelFieldsDaoConfig, this);
        irSearchFieldsDao = new IrSearchFieldsDao(irSearchFieldsDaoConfig, this);
        irUiMenuDao = new IrUiMenuDao(irUiMenuDaoConfig, this);

        registerDao(IrActWindow.class, irActWindowDao);
        registerDao(IrConfig.class, irConfigDao);
        registerDao(IrExtend.class, irExtendDao);
        registerDao(IrModel.class, irModelDao);
        registerDao(IrModelAccess.class, irModelAccessDao);
        registerDao(IrModelFields.class, irModelFieldsDao);
        registerDao(IrSearchFields.class, irSearchFieldsDao);
        registerDao(IrUiMenu.class, irUiMenuDao);
    }
    
    public void clear() {
        irActWindowDaoConfig.clearIdentityScope();
        irConfigDaoConfig.clearIdentityScope();
        irExtendDaoConfig.clearIdentityScope();
        irModelDaoConfig.clearIdentityScope();
        irModelAccessDaoConfig.clearIdentityScope();
        irModelFieldsDaoConfig.clearIdentityScope();
        irSearchFieldsDaoConfig.clearIdentityScope();
        irUiMenuDaoConfig.clearIdentityScope();
    }

    public IrActWindowDao getIrActWindowDao() {
        return irActWindowDao;
    }

    public IrConfigDao getIrConfigDao() {
        return irConfigDao;
    }

    public IrExtendDao getIrExtendDao() {
        return irExtendDao;
    }

    public IrModelDao getIrModelDao() {
        return irModelDao;
    }

    public IrModelAccessDao getIrModelAccessDao() {
        return irModelAccessDao;
    }

    public IrModelFieldsDao getIrModelFieldsDao() {
        return irModelFieldsDao;
    }

    public IrSearchFieldsDao getIrSearchFieldsDao() {
        return irSearchFieldsDao;
    }

    public IrUiMenuDao getIrUiMenuDao() {
        return irUiMenuDao;
    }

}
