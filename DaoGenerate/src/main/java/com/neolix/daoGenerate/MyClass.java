package com.neolix.daoGenerate;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.lixiangers.dingji.dao");

        schema.enableKeepSectionsByDefault();
        schema.enableActiveEntitiesByDefault();

        Entity acceptOrder = schema.addEntity("Goods");
        acceptOrder.addIdProperty().autoincrement();
        acceptOrder.addLongProperty("goodsId");
        acceptOrder.addStringProperty("name");

        acceptOrder.implementsSerializable();

        new DaoGenerator().generateAll(schema, "E:\\currentWork\\Dingji\\app\\src-dao");
    }
}
