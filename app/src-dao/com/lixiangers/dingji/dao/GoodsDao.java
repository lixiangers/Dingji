package com.lixiangers.dingji.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lixiangers.dingji.dao.Goods;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table GOODS.
*/
public class GoodsDao extends AbstractDao<Goods, String> {

    public static final String TABLENAME = "GOODS";

    /**
     * Properties of entity Goods.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Des = new Property(2, String.class, "des", false, "DES");
        public final static Property Unit = new Property(3, String.class, "unit", false, "UNIT");
        public final static Property Price = new Property(4, Integer.class, "price", false, "PRICE");
        public final static Property ImageList = new Property(5, String.class, "imageList", false, "IMAGE_LIST");
    };

    private DaoSession daoSession;


    public GoodsDao(DaoConfig config) {
        super(config);
    }
    
    public GoodsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'GOODS' (" + //
                "'ID' TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'DES' TEXT," + // 2: des
                "'UNIT' TEXT," + // 3: unit
                "'PRICE' INTEGER," + // 4: price
                "'IMAGE_LIST' TEXT);"); // 5: imageList
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GOODS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Goods entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String des = entity.getDes();
        if (des != null) {
            stmt.bindString(3, des);
        }
 
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(4, unit);
        }
 
        Integer price = entity.getPrice();
        if (price != null) {
            stmt.bindLong(5, price);
        }
 
        String imageList = entity.getImageList();
        if (imageList != null) {
            stmt.bindString(6, imageList);
        }
    }

    @Override
    protected void attachEntity(Goods entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Goods readEntity(Cursor cursor, int offset) {
        Goods entity = new Goods( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // des
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // unit
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // price
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // imageList
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Goods entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDes(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUnit(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPrice(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setImageList(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Goods entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Goods entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
