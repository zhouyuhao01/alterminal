package com.speedystone.greendaodemo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import aiterminal.android.chdmc.com.aiterminal.bean.NewsListItemBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NEWS_LIST_ITEM_BEAN".
*/
public class NewsListItemBeanDao extends AbstractDao<NewsListItemBean, Void> {

    public static final String TABLENAME = "NEWS_LIST_ITEM_BEAN";

    /**
     * Properties of entity NewsListItemBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property NewsId = new Property(0, String.class, "newsId", false, "NEWS_ID");
        public final static Property NewsContent = new Property(1, String.class, "newsContent", false, "NEWS_CONTENT");
        public final static Property NewsTitle = new Property(2, String.class, "newsTitle", false, "NEWS_TITLE");
        public final static Property NewsImage = new Property(3, String.class, "newsImage", false, "NEWS_IMAGE");
        public final static Property NewsUrl = new Property(4, String.class, "newsUrl", false, "NEWS_URL");
    }


    public NewsListItemBeanDao(DaoConfig config) {
        super(config);
    }
    
    public NewsListItemBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NEWS_LIST_ITEM_BEAN\" (" + //
                "\"NEWS_ID\" TEXT," + // 0: newsId
                "\"NEWS_CONTENT\" TEXT," + // 1: newsContent
                "\"NEWS_TITLE\" TEXT," + // 2: newsTitle
                "\"NEWS_IMAGE\" TEXT," + // 3: newsImage
                "\"NEWS_URL\" TEXT);"); // 4: newsUrl
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NEWS_LIST_ITEM_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NewsListItemBean entity) {
        stmt.clearBindings();
 
        String newsId = entity.getNewsId();
        if (newsId != null) {
            stmt.bindString(1, newsId);
        }
 
        String newsContent = entity.getNewsContent();
        if (newsContent != null) {
            stmt.bindString(2, newsContent);
        }
 
        String newsTitle = entity.getNewsTitle();
        if (newsTitle != null) {
            stmt.bindString(3, newsTitle);
        }
 
        String newsImage = entity.getNewsImage();
        if (newsImage != null) {
            stmt.bindString(4, newsImage);
        }
 
        String newsUrl = entity.getNewsUrl();
        if (newsUrl != null) {
            stmt.bindString(5, newsUrl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NewsListItemBean entity) {
        stmt.clearBindings();
 
        String newsId = entity.getNewsId();
        if (newsId != null) {
            stmt.bindString(1, newsId);
        }
 
        String newsContent = entity.getNewsContent();
        if (newsContent != null) {
            stmt.bindString(2, newsContent);
        }
 
        String newsTitle = entity.getNewsTitle();
        if (newsTitle != null) {
            stmt.bindString(3, newsTitle);
        }
 
        String newsImage = entity.getNewsImage();
        if (newsImage != null) {
            stmt.bindString(4, newsImage);
        }
 
        String newsUrl = entity.getNewsUrl();
        if (newsUrl != null) {
            stmt.bindString(5, newsUrl);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public NewsListItemBean readEntity(Cursor cursor, int offset) {
        NewsListItemBean entity = new NewsListItemBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // newsId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // newsContent
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // newsTitle
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // newsImage
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // newsUrl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NewsListItemBean entity, int offset) {
        entity.setNewsId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setNewsContent(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNewsTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNewsImage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNewsUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(NewsListItemBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(NewsListItemBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(NewsListItemBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}