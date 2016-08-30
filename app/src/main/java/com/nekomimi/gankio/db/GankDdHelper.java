package com.nekomimi.gankio.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nekomimi.gankio.bean.DaoMaster;
import com.nekomimi.gankio.bean.DaoSession;
import com.nekomimi.gankio.bean.GankEntity;
import com.nekomimi.gankio.bean.GankEntityDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by hongchi on 2016-5-9.
 * File description :
 */
public class GankDdHelper {
    private static final String GankDbName = "gank.db";

    private static GankDdHelper mInstance;

    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private GankEntityDao mGankEntityDao;
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
        mGankEntityDao = mDaoSession.getGankEntityDao();
    }

    public List query(String id)
    {
        Query query = mGankEntityDao.queryBuilder().where(GankEntityDao.Properties._id.eq(id)).build();
        return query.list();
    }

    public List<GankEntity> queryAll()
    {
        return mGankEntityDao.loadAll();
    }

    public void add(GankEntity item)
    {
        mGankEntityDao.insertOrReplace(item);
    }

    public void delete(String id)
    {
        List<GankEntity> result = query(id);
        if(result != null && result.size() != 0)
        {
            for (GankEntity item : result)
            {
                mGankEntityDao.deleteByKey(item.getId());
            }
        }
    }
}
