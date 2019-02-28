package com.zjlanyun.lyapp.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "IR_UI_MENU".
*/
public class IrUiMenuDao extends AbstractDao<IrUiMenu, Long> {

    public static final String TABLENAME = "IR_UI_MENU";

    /**
     * Properties of entity IrUiMenu.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Parent_id = new Property(2, long.class, "parent_id", false, "PARENT_ID");
        public final static Property Active = new Property(3, boolean.class, "active", false, "ACTIVE");
        public final static Property Act_id = new Property(4, long.class, "act_id", false, "ACT_ID");
        public final static Property Sequence = new Property(5, long.class, "sequence", false, "SEQUENCE");
        public final static Property Create_uid = new Property(6, long.class, "create_uid", false, "CREATE_UID");
        public final static Property Write_uid = new Property(7, long.class, "write_uid", false, "WRITE_UID");
        public final static Property Create_date = new Property(8, String.class, "create_date", false, "CREATE_DATE");
        public final static Property Write_date = new Property(9, String.class, "write_date", false, "WRITE_DATE");
        public final static Property Trantype_id = new Property(10, long.class, "trantype_id", false, "TRANTYPE_ID");
        public final static Property Multi_level_approve = new Property(11, boolean.class, "multi_level_approve", false, "MULTI_LEVEL_APPROVE");
        public final static Property Packet_id = new Property(12, int.class, "packet_id", false, "PACKET_ID");
        public final static Property Menu_type = new Property(13, int.class, "menu_type", false, "MENU_TYPE");
    }


    public IrUiMenuDao(DaoConfig config) {
        super(config);
    }
    
    public IrUiMenuDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"IR_UI_MENU\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"PARENT_ID\" INTEGER NOT NULL ," + // 2: parent_id
                "\"ACTIVE\" INTEGER NOT NULL ," + // 3: active
                "\"ACT_ID\" INTEGER NOT NULL ," + // 4: act_id
                "\"SEQUENCE\" INTEGER NOT NULL ," + // 5: sequence
                "\"CREATE_UID\" INTEGER NOT NULL ," + // 6: create_uid
                "\"WRITE_UID\" INTEGER NOT NULL ," + // 7: write_uid
                "\"CREATE_DATE\" TEXT," + // 8: create_date
                "\"WRITE_DATE\" TEXT," + // 9: write_date
                "\"TRANTYPE_ID\" INTEGER NOT NULL ," + // 10: trantype_id
                "\"MULTI_LEVEL_APPROVE\" INTEGER NOT NULL ," + // 11: multi_level_approve
                "\"PACKET_ID\" INTEGER NOT NULL ," + // 12: packet_id
                "\"MENU_TYPE\" INTEGER NOT NULL );"); // 13: menu_type
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"IR_UI_MENU\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, IrUiMenu entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getParent_id());
        stmt.bindLong(4, entity.getActive() ? 1L: 0L);
        stmt.bindLong(5, entity.getAct_id());
        stmt.bindLong(6, entity.getSequence());
        stmt.bindLong(7, entity.getCreate_uid());
        stmt.bindLong(8, entity.getWrite_uid());
 
        String create_date = entity.getCreate_date();
        if (create_date != null) {
            stmt.bindString(9, create_date);
        }
 
        String write_date = entity.getWrite_date();
        if (write_date != null) {
            stmt.bindString(10, write_date);
        }
        stmt.bindLong(11, entity.getTrantype_id());
        stmt.bindLong(12, entity.getMulti_level_approve() ? 1L: 0L);
        stmt.bindLong(13, entity.getPacket_id());
        stmt.bindLong(14, entity.getMenu_type());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, IrUiMenu entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getParent_id());
        stmt.bindLong(4, entity.getActive() ? 1L: 0L);
        stmt.bindLong(5, entity.getAct_id());
        stmt.bindLong(6, entity.getSequence());
        stmt.bindLong(7, entity.getCreate_uid());
        stmt.bindLong(8, entity.getWrite_uid());
 
        String create_date = entity.getCreate_date();
        if (create_date != null) {
            stmt.bindString(9, create_date);
        }
 
        String write_date = entity.getWrite_date();
        if (write_date != null) {
            stmt.bindString(10, write_date);
        }
        stmt.bindLong(11, entity.getTrantype_id());
        stmt.bindLong(12, entity.getMulti_level_approve() ? 1L: 0L);
        stmt.bindLong(13, entity.getPacket_id());
        stmt.bindLong(14, entity.getMenu_type());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public IrUiMenu readEntity(Cursor cursor, int offset) {
        IrUiMenu entity = new IrUiMenu( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.getLong(offset + 2), // parent_id
            cursor.getShort(offset + 3) != 0, // active
            cursor.getLong(offset + 4), // act_id
            cursor.getLong(offset + 5), // sequence
            cursor.getLong(offset + 6), // create_uid
            cursor.getLong(offset + 7), // write_uid
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // create_date
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // write_date
            cursor.getLong(offset + 10), // trantype_id
            cursor.getShort(offset + 11) != 0, // multi_level_approve
            cursor.getInt(offset + 12), // packet_id
            cursor.getInt(offset + 13) // menu_type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, IrUiMenu entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setParent_id(cursor.getLong(offset + 2));
        entity.setActive(cursor.getShort(offset + 3) != 0);
        entity.setAct_id(cursor.getLong(offset + 4));
        entity.setSequence(cursor.getLong(offset + 5));
        entity.setCreate_uid(cursor.getLong(offset + 6));
        entity.setWrite_uid(cursor.getLong(offset + 7));
        entity.setCreate_date(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setWrite_date(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setTrantype_id(cursor.getLong(offset + 10));
        entity.setMulti_level_approve(cursor.getShort(offset + 11) != 0);
        entity.setPacket_id(cursor.getInt(offset + 12));
        entity.setMenu_type(cursor.getInt(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(IrUiMenu entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(IrUiMenu entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(IrUiMenu entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
