package com.nekomimi.gankio.HomeTask;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nekomimi.gankio.api.impl.AppGetAction;
import com.nekomimi.gankio.api.def.AppCallBack;
import com.nekomimi.gankio.bean.GankDate;
import com.nekomimi.gankio.bean.GankEntity;
import com.nekomimi.gankio.bean.GankItem;
import com.nekomimi.gankio.db.GankDdHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 * HomePresenter类
 */
public class HomePresenter implements HomeContract.Presenter {
    private static final String TAG = "HomePresenter";

    private final AppGetAction mAppGetAction;

    private final HomeContract.View mHomeView;

    public HomePresenter(@NonNull HomeContract.View view){
        this.mHomeView = view;
        this.mAppGetAction = AppGetAction.getInstance();
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void data(HomeActivity.State type, int pageNum, int page) {
        mAppGetAction.data(type.name(), String.valueOf(pageNum), String.valueOf(page), new AppCallBack<GankItem>() {
            @Override
            public void onSuccess(int result, GankItem data) {
                if (result == 0){
                    mHomeView.setFresh(false);
                    mHomeView.loadmore(data.getResults());
                    mHomeView.changePage(1);
                }
            }

            @Override
            public void onFailed(int result, String errorMsg) {
                mHomeView.setFresh(false);
            }
        });
    }

    @Override
    public void day(final Calendar calendar) {
        mAppGetAction.day(calendar, new AppCallBack<GankDate>() {
            @Override
            public void onSuccess(int result, GankDate data) {
                mHomeView.setFresh(false);
                if(result == 0 ){
                    mHomeView.loadmore(data.getResults().getAll());
                    mHomeView.changeDate(-1);
                }else {
                    mHomeView.changeDate(-1);
                    day(calendar);
                }
            }

            @Override
            public void onFailed(int result, String errorMsg) {
                mHomeView.setFresh(false);
            }
        });
    }

    @Override
    public void star(Context context, int pageNum, int page) {
        List<GankEntity> source = GankDdHelper.getInstance(context).queryAll();
        if(source.size() <= pageNum * (page-1)){
            mHomeView.setFresh(false);
            return;
        }
        List<GankEntity> data = new ArrayList<>();
        for (int i = pageNum * (page-1) ; i < (source.size()>pageNum*page ? page*pageNum : source.size()) ; i++){
            data.add(source.get(i));
        }
        mHomeView.loadmore(data);
        mHomeView.setFresh(false);
        mHomeView.changePage(1);
    }
}
