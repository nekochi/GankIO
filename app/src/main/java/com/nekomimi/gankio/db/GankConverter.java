package com.nekomimi.gankio.db;

import android.text.TextUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class GankConverter implements PropertyConverter<List<String>,String> {


    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (TextUtils.isEmpty(databaseValue)){
            return null;
        } else {
            List<String> lista = Arrays.asList(databaseValue.split(","));
            return lista;
        }
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if(entityProperty==null){
            return null;
        }else {
            StringBuilder sb= new StringBuilder();
            for(String link:entityProperty){
                sb.append(link);
                sb.append(",");
            }
            if (sb.length()>0){
                sb.substring(0,sb.length()-1);
            }
            return sb.toString();
        }
    }
}