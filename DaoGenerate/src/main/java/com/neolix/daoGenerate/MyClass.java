package com.neolix.daoGenerate;

import java.util.ArrayList;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.lixiangers.dingji.dao");

        schema.enableKeepSectionsByDefault();
        schema.enableActiveEntitiesByDefault();

        Entity goods = schema.addEntity("Goods");

        goods.addStringProperty("id").primaryKey();
        goods.addStringProperty("name");
        goods.addStringProperty("des");
        goods.addStringProperty("unit");
        goods.addIntProperty("price");
        goods.addStringProperty("imageList");

        goods.implementsSerializable();

        new DaoGenerator().generateAll(schema, "E:\\currentWork\\Dingji\\app\\src-dao");
    }
}
