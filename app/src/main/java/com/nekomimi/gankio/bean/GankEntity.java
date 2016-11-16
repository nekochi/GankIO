package com.nekomimi.gankio.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.nekomimi.gankio.db.GankConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.List;

/**
 * Created by hongchi on 2016-3-3.
 * File description :
 */
@Entity
public  class GankEntity implements Parcelable {
    @Id
    private Long id;
    @Property(nameInDb = "GANKID")
    private String _id;
    @Property(nameInDb = "_NS")
    private String _ns;
    @Property(nameInDb = "CREATEDAT")
    private String createdAt;
    @Property(nameInDb = "DESC")
    private String desc;
    @Property(nameInDb = "PUBLISHEDAT")
    private String publishedAt;
    @Property(nameInDb = "TYPE")
    private String type;
    @Property(nameInDb = "URL")
    private String url;
    @Property(nameInDb = "USED")
    private String used;
    @Property(nameInDb = "WHO")
    private String who;
    /**
     * images : ["http://img.gank.io/bbd6baf4-01ba-4040-956c-32b5f7d9c2e8"]
     * source : web
     */
    @Property(nameInDb = "SOURCE")
    private String source;

    @Convert(converter = GankConverter.class, columnType = String.class)
    private List<String> images;

    @Generated(hash = 563868214)
    public GankEntity(Long id, String _id, String _ns, String createdAt, String desc,
            String publishedAt, String type, String url, String used, String who,
            String source, List<String> images) {
        this.id = id;
        this._id = _id;
        this._ns = _ns;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
        this.source = source;
        this.images = images;
    }

    @Generated(hash = 598526695)
    public GankEntity() {
    }

    protected GankEntity(Parcel in) {
        _id = in.readString();
        _ns = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readString();
        who = in.readString();
    }

    public static final Creator<GankEntity> CREATOR = new Creator<GankEntity>() {
        @Override
        public GankEntity createFromParcel(Parcel in) {
            return new GankEntity(in);
        }

        @Override
        public GankEntity[] newArray(int size) {
            return new GankEntity[size];
        }
    };

    public void set_id(String _id) {
        this._id = _id;
    }

    public void set_ns(String _ns) {
        this._ns = _ns;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String get_id() {
        return _id;
    }

    public String get_ns() {
        return _ns;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getWho() {
        return who;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getUsed() {
        return this.used;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(_ns);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(used);
        dest.writeString(who);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }



}