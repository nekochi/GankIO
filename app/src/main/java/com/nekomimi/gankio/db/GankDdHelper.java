package com.nekomimi.gankio.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import de.greenrobot.dao.query.Query;

/**
 * Created by hongchi on 2016-5-9.
 * File description :
 */
public class GankDdHelper {
    private static final String GankDbName = "gank";

    private static GankDdHelper mInstance;

    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private GankItemDao mGankItemDao;
    private Context mContext;
    private GankDdHelper(Context context)
    {
        this.mContext = context;
        setupDatabase();
    }

    public static GankDdHelper getInstance(Context context)
    {
        if(mInstance == null)
        {
            mInstance = new GankDdHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext,GankDbName,null);
        db = helper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        mGankItemDao = mDaoSession.getGankItemDao();
    }

    public List query(String id)
    {
        Query query = mGankItemDao.queryBuilder().where(GankItemDao.Properties.GankId.eq(id)).build();
        return query.list();
    }

    public void add(GankItem item)
    {
        mGankItemDao.insertOrReplace(item);
    }

    public void delete(String id)
    {
        List<GankItem> result = query(id);
        if(result != null && result.size() != 0)
        {
            for (GankItem item : result)
            {
                mGankItemDao.deleteByKey(item.getId());
            }
        }
    }
}
