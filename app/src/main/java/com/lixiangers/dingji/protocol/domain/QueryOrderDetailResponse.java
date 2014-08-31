package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.model.Goodses;

import java.util.Date;
import java.util.List;

public class QueryOrderDetailResponse {
    private int status;
    private String receiver_name;
    private String receiver_mobile;
    private String receiver_province;
    private String receiver_city;
    private String receiver_district;
    private String receiver_detail_address;
    private String id;
    private String order_no;

    private List<OrderRoute> route;
    private List<Goodses> products;


    public int getStatus() {
        return status;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public String getReceiver_detail_address() {
        return receiver_detail_address;
    }

    public String getId() {
        return id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public List<OrderRoute> getRoute() {
        return route;
    }

    public List<Goodses> getProducts() {
        return products;
    }

    public String getWholeDetailAddress() {
        return String.format("%s%s%s%s", getReceiver_province(), getReceiver_city(), getReceiver_district(), getReceiver_detail_address());
    }

    public static class OrderRoute {
        private int status;
        private Date action_time;
        private String description;

        public int getStatus() {
            return status;
        }

        public Date getAction_time() {
            return action_time;
        }

        public String getDescription() {
            return description;
        }
    }
}
