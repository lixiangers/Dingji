package com.lixiangers.dingji.model;

public enum OrderStatus {
    NONE {
        @Override
        public int getIndex() {
            return -2;
        }

        @Override
        public String getDes() {
            return null;
        }
    },
    CANCEL {
        @Override
        public int getIndex() {
            return -1;
        }

        @Override
        public String getDes() {
            return "订单已取消";
        }
    }, SUBMIT_ORDER {
        @Override
        public int getIndex() {
            return 0;
        }

        @Override
        public String getDes() {
            return "下单成功";
        }
    }, START_TRANSPORT {
        @Override
        public int getIndex() {
            return 1;
        }

        @Override
        public String getDes() {
            return "开始配送";
        }
    }, SUCCESS {
        @Override
        public int getIndex() {
            return 2;
        }

        @Override
        public String getDes() {
            return "配送成功";
        }
    };

    public abstract int getIndex();

    public abstract String getDes();

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
