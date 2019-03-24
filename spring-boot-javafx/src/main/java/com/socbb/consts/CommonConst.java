package com.socbb.consts;

public interface CommonConst {

    public static final String URL = "https://kyfw.12306.cn/";

    /**
     * 查询接口
     */
    public static final String GET_TICKET = URL + "otn/leftTicket/query?leftTicketDTO.train_date={train_date}&leftTicketDTO.from_station={from_station}&leftTicketDTO.to_station={to_station}&purpose_codes={purpose_codes}";

    public static final String GET_IMAGE = URL + "passport/captcha/captcha-image64?login_site=E&module=login&rand=sjrand";

}
