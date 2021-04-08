package com.example.demo.pojo;

import java.io.Serializable;

public class ResponseData implements Serializable {
    public static final String STATUS_OK = "001";
    public static final String STATUS_FAIL = "002";
    private String status;  // 状态码
    private String msg;     // 结果信息
    private Object data;   // 返回数据

    public ResponseData() {
    }

    public ResponseData(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
