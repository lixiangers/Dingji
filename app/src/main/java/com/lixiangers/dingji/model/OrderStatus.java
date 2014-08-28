package com.lixiangers.dingji.model;

public enum OrderStatus {
    NONE {
        @Override
        public int getIndex() {
            return -2;
        }
    },
    CANCEL {
        @Override
        public int getIndex() {
            return -1;
        }
    }, SUBMIT_ORDER {
        @Override
        public int getIndex() {
            return 0;
        }
    }, START_TRANSPORT {
        @Override
        public int getIndex() {
            return 1;
        }
    }, SUCCESS {
        @Override
        public int getIndex() {
            return 2;
        }
    };

    public abstract int getIndex();

    public static OrderStatus findOrderStatusByIndex(int index) {
        OrderStatus orderStatus = OrderStatus.NONE;
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getIndex() == index) {
                orderStatus = status;
                break;
            }
        }
        return orderStatus;
    }
}
