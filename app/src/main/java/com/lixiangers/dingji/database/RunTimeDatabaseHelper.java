package com.lixiangers.dingji.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lixiangers.dingji.dao.DaoMaster;
import com.lixiangers.dingji.dao.DaoSession;
import com.lixiangers.dingji.util.Constant;


public class RunTimeDatabaseHelper {
    private static RunTimeDatabaseHelper helper;
    private static DaoSession daoSession;

    public static RunTimeDatabaseHelper initial(Context context) {
        if (helper == null) {
            helper = new RunTimeDatabaseHelper();
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constant.RUNTIME_DB_NAME, null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            daoSession = daoMaster.newSession();
        }

        return helper;
    }

//    public synchronized boolean insertAcceptOrder(AcceptOrder event) {
//        AcceptOrderDao eventDao = daoSession.getAcceptOrderDao();
//        return eventDao.insert(event) > 0;
//    }
//
//    public synchronized List<AcceptOrder> queryNoAcceptOrder() {
//        AcceptOrderDao eventDao = daoSession.getAcceptOrderDao();
//        return eventDao.queryBuilder().where(AcceptOrderDao.Properties.Status.eq(OrderStatus.NoAccept.ordinal()),
//                AcceptOrderDao.Properties.CreateTime.between(TimeUtil.today().toDate(), TimeUtil.tomorrow().toDate())).
//                orderAsc(AcceptOrderDao.Properties.CreateTime).list();
//    }
//
//    public synchronized List<AcceptOrder> queryHasBeenAcceptOrder() {
//        AcceptOrderDao eventDao = daoSession.getAcceptOrderDao();
//        return eventDao.queryBuilder().where(AcceptOrderDao.Properties.Status.eq(OrderStatus.HasBeenAccept.ordinal()),
//                AcceptOrderDao.Properties.CreateTime.between(TimeUtil.today().toDate(), TimeUtil.tomorrow().toDate())).
//                orderAsc(AcceptOrderDao.Properties.ResponseTime).list();
//    }
//
//    public synchronized List<AcceptOrder> queryProcessedOrder() {
//        AcceptOrderDao eventDao = daoSession.getAcceptOrderDao();
//        return eventDao.queryBuilder().where(AcceptOrderDao.Properties.Status.eq(OrderStatus.Processed.ordinal()),
//                AcceptOrderDao.Properties.CreateTime.between(TimeUtil.today().toDate(), TimeUtil.tomorrow().toDate())).
//                orderDesc(AcceptOrderDao.Properties.ProcessTime).list();
//    }
//
//    public synchronized boolean isExistOrder(long id) {
//        AcceptOrderDao eventDao = daoSession.getAcceptOrderDao();
//        QueryBuilder<AcceptOrder> queryBuilder = eventDao.queryBuilder();
//        queryBuilder.where(AcceptOrderDao.Properties.OrderId.eq(id));
//        return queryBuilder.list().size() > 0;
//    }
//
//    public synchronized AcceptOrder queryOrderById(long id) {
//        AcceptOrderDao eventDao = daoSession.getAcceptOrderDao();
//        QueryBuilder<AcceptOrder> queryBuilder = eventDao.queryBuilder();
//        queryBuilder.where(AcceptOrderDao.Properties.OrderId.eq(id));
//        List<AcceptOrder> list = queryBuilder.list();
//        return (list == null || list.size() == 0) ? null : list.get(0);
//    }
//
//    public synchronized void overdueOrderById(long id) {
//        updateOrderStatusById(id, OrderStatus.Overdue);
//    }
//
//    public synchronized void cancelOrderById(long id) {
//        updateOrderStatusById(id, OrderStatus.Cancel);
//    }
//
//    private synchronized void updateOrderStatusById(long id, OrderStatus orderStatus) {
//        AcceptOrder order = queryOrderById(id);
//        if (order == null)
//            return;
//
//        order.setStatus(orderStatus.ordinal());
//        updateOrder(order);
//    }
//
//    public synchronized void updateOrder(AcceptOrder acceptOrder) {
//        AcceptOrderDao eventDao = daoSession.getAcceptOrderDao();
//        eventDao.update(acceptOrder);
//    }
//
//    public synchronized void deleteAcceptOrder(AcceptOrder event) {
//        AcceptOrderDao eventDao = daoSession.getAcceptOrderDao();
//        eventDao.delete(event);
//    }
//
//    private synchronized void insertShipment(Shipment shipment) {
//        ShipmentDao shipmentDao = daoSession.getShipmentDao();
//        shipmentDao.insert(shipment);
//    }
//
//    public synchronized void completePickUp(Shipment shipment) {
//        AcceptOrder order = queryOrderById(shipment.getOrderId());
//        if (order == null)
//            return;
//
//        order.setProcessTime(DateTime.now().toDate());
//        order.setStatus(OrderStatus.Processed.ordinal());
//        updateOrder(order);
//        insertShipment(shipment);
//    }
//
//    public synchronized List<Shipment> queryShipment() {
//        ShipmentDao shipmentDao = daoSession.getShipmentDao();
//        return shipmentDao.queryBuilder().list();
//    }
//
//    public synchronized Shipment queryShipmentByOrderId(long orderId) {
//        ShipmentDao shipmentDao = daoSession.getShipmentDao();
//        return shipmentDao.queryBuilder().where(ShipmentDao.Properties.OrderId.eq(orderId)).unique();
//    }
//
//    public synchronized List<Shipment> queryNotUploadShipment() {
//        ShipmentDao shipmentDao = daoSession.getShipmentDao();
//        QueryBuilder<Shipment> queryBuilder = shipmentDao.queryBuilder();
//        queryBuilder.where(ShipmentDao.Properties.IsUpload.eq(false));
//        return queryBuilder.list();
//    }
//
//    public synchronized void updateShipment(Shipment shipment) {
//        ShipmentDao shipmentDao = daoSession.getShipmentDao();
//        shipmentDao.update(shipment);
//    }
//
//    public synchronized void insertDelivery(Delivery delivery) {
//        DeliveryDao deliveryDao = daoSession.getDeliveryDao();
//        deliveryDao.insert(delivery);
//    }
//
//    public synchronized void updateDelivery(Delivery delivery) {
//        DeliveryDao deliveryDao = daoSession.getDeliveryDao();
//        deliveryDao.update(delivery);
//    }
//
//    public synchronized long getDeliveryCountByBarcode(String barcode) {
//        DeliveryDao deliveryDao = daoSession.getDeliveryDao();
//        return deliveryDao.queryBuilder().where(DeliveryDao.Properties.Barcode.eq(barcode)).count();
//    }
//
//    public synchronized long getSuccessDeliveryCount() {
//        DeliveryDao deliveryDao = daoSession.getDeliveryDao();
//        return deliveryDao.queryBuilder().where(DeliveryDao.Properties.FailureReason.eq(Delivery.NOT_FAILURE_REASON)).count();
//    }
//
//    public synchronized long getFailureDeliveryCount() {
//        DeliveryDao deliveryDao = daoSession.getDeliveryDao();
//        return deliveryDao.queryBuilder().where(DeliveryDao.Properties.FailureReason.notEq(Delivery.NOT_FAILURE_REASON)).count();
//    }
//
//    public synchronized long getFailureDeliveryCountByTime(DateTime startTime, DateTime endTime) {
//        DeliveryDao deliveryDao = daoSession.getDeliveryDao();
//        return deliveryDao.queryBuilder().where(DeliveryDao.Properties.FailureReason.notEq(Delivery.NOT_FAILURE_REASON),
//                DeliveryDao.Properties.DeliveryTime.between(startTime.withTimeAtStartOfDay().toDate(), endTime.withTimeAtStartOfDay().toDate())).count();
//    }
//
//    public synchronized long getSuccessDeliveryCountByTime(DateTime startTime, DateTime endTime) {
//        DeliveryDao deliveryDao = daoSession.getDeliveryDao();
//        return deliveryDao.queryBuilder().where(DeliveryDao.Properties.FailureReason.eq(Delivery.NOT_FAILURE_REASON),
//                DeliveryDao.Properties.DeliveryTime.between(startTime.withTimeAtStartOfDay().toDate(), endTime.withTimeAtStartOfDay().toDate())).count();
//    }
//
//    public synchronized long getFailureShipmentCountByTime(DateTime startTime, DateTime endTime) {
//        ShipmentDao shipmentDao = daoSession.getShipmentDao();
//        return shipmentDao.queryBuilder().where(ShipmentDao.Properties.FailureReason.notEq(Delivery.NOT_FAILURE_REASON),
//                ShipmentDao.Properties.ShipmentTime.between(startTime.withTimeAtStartOfDay().toDate(), endTime.withTimeAtStartOfDay().toDate())).count();
//    }
//
//    public synchronized long getSuccessShipmentCountByTime(DateTime startTime, DateTime endTime) {
//        ShipmentDao shipmentDao = daoSession.getShipmentDao();
//        return shipmentDao.queryBuilder().where(ShipmentDao.Properties.FailureReason.eq(Delivery.NOT_FAILURE_REASON),
//                ShipmentDao.Properties.ShipmentTime.between(startTime.withTimeAtStartOfDay().toDate(), endTime.withTimeAtStartOfDay().toDate())).count();
//    }
//
//    public synchronized DateTime getLastGetVerifyCodeTimeBy(String phoneNumber) {
//        VerifyCodeRecordDao recordDao = daoSession.getVerifyCodeRecordDao();
//        VerifyCodeRecord record = recordDao.queryBuilder().where(VerifyCodeRecordDao.Properties.Phone.eq(phoneNumber)).unique();
//        if (record == null)
//            return new DateTime(0);
//        else
//            return new DateTime(record.getSendTime());
//    }
//
//    public synchronized void updateOrInsertRecord(String phoneNumber, Date date) {
//        VerifyCodeRecordDao recordDao = daoSession.getVerifyCodeRecordDao();
//        VerifyCodeRecord record = recordDao.queryBuilder().where(VerifyCodeRecordDao.Properties.Phone.eq(phoneNumber)).unique();
//        if (record == null) {
//            VerifyCodeRecord verifyCodeRecord = new VerifyCodeRecord(phoneNumber, date);
//            recordDao.insert(verifyCodeRecord);
//        } else {
//            record.setSendTime(date);
//            recordDao.update(record);
//        }
//    }
//
//    public synchronized void insertOrUpdateMessage(MessageRecord messageRecord) {
//        MessageRecordDao recordDao = daoSession.getMessageRecordDao();
//        MessageRecord record = queryMessageBy(messageRecord.getOrderId());
//        if (record == null)
//            recordDao.insert(messageRecord);
//        else {
//            record.setIsRead(false);
//            record.setMessage(messageRecord.getMessage());
//            record.setHeaderImageUrl(messageRecord.getHeaderImageUrl());
//            record.setCreateTime(messageRecord.getCreateTime());
//            recordDao.update(record);
//        }
//    }
//
//    private synchronized MessageRecord queryMessageBy(long orderId) {
//        MessageRecordDao recordDao = daoSession.getMessageRecordDao();
//        MessageRecord record = recordDao.queryBuilder().where(MessageRecordDao.Properties.OrderId.eq(orderId)).unique();
//        return record;
//    }
//
//    public synchronized void setMessageHasRead(long orderId) {
//        MessageRecordDao recordDao = daoSession.getMessageRecordDao();
//        MessageRecord record = recordDao.queryBuilder().where(MessageRecordDao.Properties.OrderId.eq(orderId)).unique();
//        record.setIsRead(true);
//        recordDao.update(record);
//    }
//
//    public synchronized List<MessageViewModel> queryMessageOfTheLastThreeDays() {
//        List<MessageViewModel> models = new ArrayList<MessageViewModel>();
//        MessageViewModel model;
//        Cursor cursor = null;
//        String sql = "SELECT A.*,B.SHIPPER_NAME,B.SHIPPER_TEL_PHONE,B.SHIPPER_ADDRESS FROM MESSAGE_RECORD as A LEFT JOIN ACCEPT_ORDER as B \n" +
//                " ON A.ORDER_ID=B.ORDER_ID WHERE A.CREATE_TIME>=" + TimeUtil.tomorrow().minusDays(3).toDate().getTime()
//                + " AND A.CREATE_TIME<=" + TimeUtil.tomorrow().toDate().getTime() + " ORDER BY A.CREATE_TIME DESC";
//        try {
//            cursor = daoSession.getDatabase().rawQuery(sql, null);
//
//            while (cursor.moveToNext()) {
//                String headerImageUrl = cursor.getString(cursor.getColumnIndex("HEADER_IMAGE_URL"));
//                String message = cursor.getString(cursor.getColumnIndex("MESSAGE"));
//                DateTime createTime = new DateTime(cursor.getLong(cursor.getColumnIndex("CREATE_TIME")));
//
//                String shipperName = cursor.getString(cursor.getColumnIndex("SHIPPER_NAME"));
//                String shipperTelPhone = cursor.getString(cursor.getColumnIndex("SHIPPER_TEL_PHONE"));
//                String shipperAddress = cursor.getString(cursor.getColumnIndex("SHIPPER_ADDRESS"));
//                long orderId = cursor.getLong(cursor.getColumnIndex("ORDER_ID"));
//                boolean isRead = cursor.getInt(cursor.getColumnIndex("IS_READ")) == 1;
//
//                model = new MessageViewModel(headerImageUrl, message, createTime, shipperName, shipperAddress, shipperTelPhone, orderId, isRead);
//                models.add(model);
//
//            }
//        } catch (RuntimeException ex) {
//            ex.printStackTrace();
//        } finally {
//            cursor.close();
//        }
//
//        return models;
//    }
//
//    public synchronized long getUnReadMessageCount() {
//        MessageRecordDao recordDao = daoSession.getMessageRecordDao();
//        return recordDao.queryBuilder().where(MessageRecordDao.Properties.IsRead.eq(false)).count();
//    }

//

//    public synchronized List<RemindEvent> queryRemindEventGreaterThanOrEqualTo(DateTime time) {
//        RemindEventDao eventDao = daoSession.getRemindEventDao();
//        QueryBuilder<RemindEvent> queryBuilder = eventDao.queryBuilder();
//        DateTime startTime = time.withTimeAtStartOfDay();
//        DateTime endTime = time.withTimeAtStartOfDay().plusDays(1);
//        queryBuilder.where(RemindEventDao.Properties.DateTime.ge(time.toDate())).orderAsc(RemindEventDao.Properties.DateTime);
//        return queryBuilder.list();
//    }
//
//    public synchronized void deleteAcceptOrder(RemindEvent event) {
//        RemindEventDao eventDao = daoSession.getRemindEventDao();
//        eventDao.delete(event);
//    }
//
//    public synchronized void deleteRemindEventLessThan(DateTime time) {
//        RemindEventDao eventDao = daoSession.getRemindEventDao();
//        List<RemindEvent> remindEvents = queryRemindEventLessThan(time);
//        eventDao.deleteInTx(remindEvents);
//    }
//
//    public synchronized void updateRemindEvent(RemindEvent event) {
//        RemindEventDao eventDao = daoSession.getRemindEventDao();
//        eventDao.update(event);
//    }
//
//    private synchronized List<RemindEvent> queryRemindEventLessThan(DateTime time) {
//        RemindEventDao eventDao = daoSession.getRemindEventDao();
//        QueryBuilder<RemindEvent> queryBuilder = eventDao.queryBuilder();
//        queryBuilder.where(RemindEventDao.Properties.DateTime.lt(time.toDate()));
//        return queryBuilder.list();
//    }
}
