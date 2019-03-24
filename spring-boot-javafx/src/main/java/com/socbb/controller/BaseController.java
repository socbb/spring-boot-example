package com.socbb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.socbb.bean.Ticket;
import com.socbb.consts.CommonConst;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取验证码
     * @return
     */
    public String getImage(){
        ResponseEntity<String> forEntity = restTemplate.getForEntity(CommonConst.GET_IMAGE, String.class);
        if (forEntity.getStatusCode() == HttpStatus.OK) {
            String body = forEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            String image = jsonObject.getString("image");
            return image;
        }
        return null;
    }

    /**
     * 查询车票接口
     *
     * @return
     */
    public List<Ticket> getTicket(String trainDate, String fromStation, String toStation, String purposeCodes) {
        Map<String, Object> param = new HashMap<>();
        param.put("train_date", trainDate);
        param.put("from_station", fromStation);
        param.put("to_station", toStation);
        param.put("purpose_codes", purposeCodes);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(CommonConst.GET_TICKET, String.class, param);
        if (forEntity.getStatusCode() == HttpStatus.OK) {
            String body = forEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            Integer httpstatus = jsonObject.getInteger("httpstatus");
            if (200 == httpstatus) {
                JSONObject object = jsonObject.getJSONObject("data");
                if (object == null) {
                    return null;
                }
                JSONArray result = object.getJSONArray("result");
                if (result == null || result.size() <= 0) {
                    return null;
                }
                JSONObject map = object.getJSONObject("map");
                if (map == null) {
                    return null;
                }
                List<Ticket> tickets = new ArrayList<>(result.size());
                result.forEach(re -> {
                    if (re != null) {
                        String s = re.toString();
                        if (StringUtils.isNotBlank(s)) {
                            String[] split = StringUtils.split(s, "|");
                            if (ArrayUtils.isNotEmpty(split)) {
                                Ticket ticket = new Ticket();
                                ticket.setValue(split, map);
                                tickets.add(ticket);
                            }
                        }
                    }
                });
                return tickets;
            } else {
                System.out.println("获取查票接口失败");
            }
        }
        return null;
    }
}
