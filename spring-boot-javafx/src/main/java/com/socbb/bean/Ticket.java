package com.socbb.bean;

import com.alibaba.fastjson.JSONObject;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.socbb.annotation.TableField;
import lombok.Data;

/**
 * 车票对象
 */
@Data
public class Ticket extends RecursiveTreeObject<Ticket> {

    @TableField("id")
    private String train_no;

    @TableField("车次")
    private String checi;

    @TableField("出发地")
    private String startLocation;

    @TableField("目的地")
    private String endLocation;

    @TableField("出发时间")
    private String startTime;

    @TableField("到达时间")
    private String endTime;

    @TableField("历时")
    private String time;

    @TableField("软卧")
    private String softLie;

    @TableField("硬卧")
    private String hardLie;

    @TableField("一等座")
    private String oneSeat;

    @TableField("二等座")
    private String twoSeat;

    @TableField("无座")
    private String noSeat;

    @TableField("其他")
    private String other;

    @TableField("备注")
    private String remark;

    public Ticket() {
    }

    public void setValue(String[] arr, JSONObject map) {
        setTrain_no(arr[2]);
        setCheci(arr[3]);
        setStartLocation(map.getString(arr[6]));
        setEndLocation(map.getString(arr[7]));
        setEndTime(arr[9]);
        setTime(arr[10]);
        setNoSeat(arr[20]);
        setTwoSeat(arr[21]);
        setOneSeat(arr[22]);
    }
}
