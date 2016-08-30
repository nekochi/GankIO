package com.nekomimi.gankio.HomeTask;

import android.content.Context;

import com.nekomimi.gankio.base.BasePresenter;
import com.nekomimi.gankio.base.BaseView;
import com.nekomimi.gankio.bean.GankEntity;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public interface HomeContract {

    interface View extends BaseView<Presenter>{
        void updata(List<GankEntity> dataList);

        void dataReset();

        void loadmore(List<GankEntity> dataList);

        void changeDate(int day);

        void changePage(int num);

        void setFresh(boolean flag);
    }

    interface Presenter extends BasePresenter{
        void data(HomeActivity.State type, int pageNum, int page);

        void day(Calendar calendar);

        void star(Context context, int pageNum, int page);
    }
}
