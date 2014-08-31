package com.lixiangers.dingji.protocol.domain;

import java.util.List;

public class QueryOrderListRequest {
    private List<Integer> status;//[0, 1, 2]  要列出的订单的状态, 若为[], 则查询所有状态的订单
    private String keyword;// : 搜索关键字
    private int start;// int 起始索引, 可空, 默认为0
    private int count;//  int 查询数量, 默认为10

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
