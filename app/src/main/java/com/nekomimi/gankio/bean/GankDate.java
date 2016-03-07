package com.nekomimi.gankio.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongchi on 2016-2-17.
 * File description :
 */
public class GankDate {


    /**
     * category : ["iOS","Android","瞎推荐","拓展资源","福利","休息视频"]
     * error : false
     * results : {"Android":[{"_id":"56cc6d23421aa95caa707a69","_ns":"ganhuo","createdAt":"2015-08-06T07:15:52.65Z","desc":"类似Link Bubble的悬浮式操作设计","publishedAt":"2015-08-07T03:57:48.45Z","type":"Android","url":"https://github.com/recruit-lifestyle/FloatingView","used":true,"who":"mthli"},{"_id":"56cc6d23421aa95caa707a6f","_ns":"ganhuo","createdAt":"2015-08-07T01:33:07.815Z","desc":"Android开发中，有哪些让你觉得相见恨晚的方法、类或接口？","publishedAt":"2015-08-07T03:57:47.317Z","type":"Android","url":"http://www.zhihu.com/question/33636939","used":true,"who":"lxxself"},{"_id":"56cc6d23421aa95caa707c69","_ns":"ganhuo","createdAt":"2015-08-06T15:00:38.350Z","desc":"使用注解来处理Activity的状态恢复","publishedAt":"2015-08-07T03:57:48.76Z","type":"Android","url":"https://github.com/tom91136/Akatsuki","used":true,"who":"鲍永章"},{"_id":"56cc6d23421aa95caa707c71","_ns":"ganhuo","createdAt":"2015-08-07T02:19:44.342Z","desc":"Android Lollipop联系人之PinnedListView简单使用","publishedAt":"2015-08-07T03:57:48.142Z","type":"Android","url":"https://git.oschina.net/way/PinnedHeaderListView","used":true,"who":"有时放纵"},{"_id":"56cc6d23421aa95caa707c78","_ns":"ganhuo","createdAt":"2015-08-06T14:58:28.171Z","desc":"图片可以自动滚动的ImageView，可以实现视差效果。","publishedAt":"2015-08-07T03:57:48.73Z","type":"Android","url":"https://github.com/Q42/AndroidScrollingImageView","used":true,"who":"鲍永章"}],"iOS":[{"_id":"56cc6d1d421aa95caa707769","_ns":"ganhuo","createdAt":"2015-08-07T01:32:51.588Z","desc":"LLVM 简介","publishedAt":"2015-08-07T03:57:48.70Z","type":"iOS","url":"http://adriansampson.net/blog/llvm.html","used":true,"who":"CallMeWhy"},{"_id":"56cc6d23421aa95caa707a6b","_ns":"ganhuo","createdAt":"2015-08-06T14:45:18.733Z","desc":"基于TextKit的UILabel，支持超链接和自定义表达式。","publishedAt":"2015-08-07T03:57:47.242Z","type":"iOS","url":"https://github.com/molon/MLLabel","used":true,"who":"鲍永章"},{"_id":"56cc6d23421aa95caa707bea","_ns":"ganhuo","createdAt":"2015-08-07T01:33:30.871Z","desc":"Swift 和 C 函数","publishedAt":"2015-08-07T03:57:48.83Z","type":"iOS","url":"http://chris.eidhof.nl/posts/swift-c-interop.html","used":true,"who":"CallMeWhy"},{"_id":"56cc6d23421aa95caa707c77","_ns":"ganhuo","createdAt":"2015-08-07T01:34:00.984Z","desc":"Arrays Linked Lists 和性能比较","publishedAt":"2015-08-07T03:57:48.174Z","type":"iOS","url":"http://airspeedvelocity.net/2015/08/03/arrays-linked-lists-and-performance/","used":true,"who":"CallMeWhy"}],"休息视频":[{"_id":"56cc6d23421aa95caa707c68","_ns":"ganhuo","createdAt":"2015-08-06T13:06:17.211Z","desc":"听到就心情大好的歌，简直妖魔哈哈哈哈哈，原地址\nhttp://v.youku.com/v_show/id_XMTQxODA5NDM2.html","publishedAt":"2015-08-07T03:57:48.104Z","type":"休息视频","url":"http://www.zhihu.com/question/21778055/answer/19905413?utm_source=weibo&utm_medium=weibo_share&utm_content=share_answer&utm_campaign=share_button","used":true,"who":"lxxself"}],"拓展资源":[{"_id":"56cc6d23421aa95caa707bdf","_ns":"ganhuo","createdAt":"2015-08-07T01:36:06.932Z","desc":"Display GitHub code in tree format","publishedAt":"2015-08-07T03:57:48.81Z","type":"拓展资源","url":"https://github.com/buunguyen/octotree","used":true,"who":"lxxself"}],"瞎推荐":[{"_id":"56cc6d23421aa95caa707bd0","_ns":"ganhuo","createdAt":"2015-08-07T01:52:30.267Z","desc":"程序员的电台FmM，这个页面chrome插件有问题啊哭，我写了回删除不了啊","publishedAt":"2015-08-07T03:57:48.84Z","type":"瞎推荐","url":"https://cmd.fm/","used":true,"who":"lxxself"}],"福利":[{"_id":"56cc6d23421aa95caa707c52","_ns":"ganhuo","createdAt":"2015-08-07T01:21:06.112Z","desc":"8.7\u2014\u2014（1）","publishedAt":"2015-08-07T03:57:47.310Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutscfcqtcj20dw0i0q4l.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d23421aa95caa707c56","_ns":"ganhuo","createdAt":"2015-08-07T01:21:33.518Z","desc":"8.7\u2014\u2014（2）","publishedAt":"2015-08-07T03:57:47.229Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutsd0pgiwj20go0p0djn.jpg","used":true,"who":"张涵宇"}]}
     */

    private boolean error;
    private ResultsEntity results;
    private List<String> category;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(ResultsEntity results) {
        this.results = results;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public ResultsEntity getResults() {
        return results;
    }

    public List<String> getCategory() {
        return category;
    }

    public static class ResultsEntity {
        /**
         * _id : 56cc6d23421aa95caa707a69
         * _ns : ganhuo
         * createdAt : 2015-08-06T07:15:52.65Z
         * desc : 类似Link Bubble的悬浮式操作设计
         * publishedAt : 2015-08-07T03:57:48.45Z
         * type : Android
         * url : https://github.com/recruit-lifestyle/FloatingView
         * used : true
         * who : mthli
         */

        private List<GankEntity> Android;

        private List<GankEntity> iOS;


        private List<GankEntity> 休息视频;


        private List<GankEntity> 拓展资源;


        private List<GankEntity> 瞎推荐;


        private List<GankEntity> 福利;

        private List<GankEntity> 前端;

        public List<GankEntity> getAll()
        {
            List<GankEntity> result = new ArrayList<>();
            if(Android != null) result.addAll(getAndroid());
            if(iOS != null) result.addAll(getIOS());
            if(休息视频 != null) result.addAll(get休息视频());
            if(拓展资源 != null) result.addAll(get拓展资源());
            if(瞎推荐 != null) result.addAll(get瞎推荐());
            if(福利 != null) result.addAll(get福利());
            if(前端 != null) result.addAll(get前端());
            return result;
        }

        public void setAndroid(List<GankEntity> Android) {
            this.Android = Android;
        }

        public void setIOS(List<GankEntity> iOS) {
            this.iOS = iOS;
        }

        public void set休息视频(List<GankEntity> 休息视频) {
            this.休息视频 = 休息视频;
        }

        public void set拓展资源(List<GankEntity> 拓展资源) {
            this.拓展资源 = 拓展资源;
        }

        public void set瞎推荐(List<GankEntity> 瞎推荐) {
            this.瞎推荐 = 瞎推荐;
        }

        public void set福利(List<GankEntity> 福利) {
            this.福利 = 福利;
        }

        public void set前端(List<GankEntity> 前端) {
            this.前端 = 前端;
        }

        public List<GankEntity> getAndroid() {
            return Android;
        }

        public List<GankEntity> getIOS() {
            return iOS;
        }

        public List<GankEntity> get休息视频() {
            return 休息视频;
        }

        public List<GankEntity> get拓展资源() {
            return 拓展资源;
        }

        public List<GankEntity> get瞎推荐() {
            return 瞎推荐;
        }

        public List<GankEntity> get福利() {
            return 福利;
        }

        public List<GankEntity> get前端() {
            return 前端;
        }

    }
}
