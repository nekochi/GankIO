package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoGankGenerator {
    private static final int dbVersion = 1;
    private static final String dbPackage = "com.nekomimi.gankio.db";
    private static final String dbPath = "./app/src/main/java-gen";

    private static final String GANKITEM = "GankItem";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(dbVersion,dbPackage);
        addGankItem(schema);
        new DaoGenerator().generateAll(schema,dbPath);
    }

    /**
     * @param schema
     */
    private static void addGankItem(Schema schema) {
        Entity gankItem = schema.addEntity(GANKITEM);
        gankItem.addIdProperty().autoincrement();
        gankItem.addStringProperty("gankId").notNull();
        gankItem.addDateProperty("createAt");
        gankItem.addStringProperty("Desc");
        gankItem.addStringProperty("publishedAt");
        gankItem.addStringProperty("type");
        gankItem.addStringProperty("url").notNull();
        gankItem.addStringProperty("who");
        gankItem.addBooleanProperty("used");
    }
}
