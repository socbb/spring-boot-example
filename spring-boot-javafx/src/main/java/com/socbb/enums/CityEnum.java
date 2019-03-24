package com.socbb.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum CityEnum {

    UNKNOW(-1, "not", "not", "未知"),
    BJP(0,"bji","BJP","北京"),
    SHH(1,"sha","SHH","上海"),
    TJP(2,"tji","TJP","天津"),
    CQW(3,"cqi","CQW","重庆"),
    CSQ(4,"csh","CSQ","长沙"),
    CCT(5,"cch","CCT","长春"),
    CDW(6,"cdu","CDW","成都"),
    FZS(7,"fzh","FZS","福州"),
    GZQ(8,"gzh","GZQ","广州"),
    GIW(9,"gya","GIW","贵阳"),
    HHC(10,"hht","HHC","呼和浩特"),
    HBB(11,"heb","HBB","哈尔滨"),
    HFH(12,"hfe","HFH","合肥"),
    HZH(13,"hzh","HZH","杭州"),
    VUQ(14,"hko","VUQ","海口"),
    JNK(15,"jna","JNK","济南"),
    KMM(16,"kmi","KMM","昆明"),
    LSO(17,"lsa","LSO","拉萨"),
    LZJ(18,"lzh","LZJ","兰州"),
    NNZ(19,"nni","NNZ","南宁"),
    NJH(20,"nji","NJH","南京"),
    NCG(21,"nch","NCG","南昌"),
    SYT(22,"sya","SYT","沈阳"),
    SJP(23,"sjz","SJP","石家庄"),
    TYV(24,"tyu","TYV","太原"),
    WMR(25,"wlq","WMR","乌鲁木齐南"),
    WHN(26,"wha","WHN","武汉"),
    XNO(27,"xni","XNO","西宁"),
    XAY(28,"xan","XAY","西安"),
    YIJ(29,"ych","YIJ","银川"),
    ZZF(30,"zzh","ZZF","郑州"),
    SZQ(31,"szh","SZQ","深圳"),
    XMS(32,"xme","XMS","厦门"),
    ;

    public int id;

    public String shortName;

    public String letterName;

    public String name;

    CityEnum(int id, String shortName, String letterName, String name) {
        this.id = id;
        this.shortName = shortName;
        this.letterName = letterName;
        this.name = name;
    }

    public static int id(String name){
        if (StringUtils.isBlank(name)) {
            return UNKNOW.id;
        }
        CityEnum[] values = CityEnum.values();
        Optional<CityEnum> first = Arrays.stream(values).filter(positionEnum -> name.equalsIgnoreCase(positionEnum.name)).findFirst();
        return first.orElse(UNKNOW).id;
    }

    public static String name(int id){
        CityEnum[] values = CityEnum.values();
        Optional<CityEnum> first = Arrays.stream(values).filter(positionEnum -> positionEnum.id == id).findFirst();
        return first.orElse(UNKNOW).name;
    }

    public static String name(String letterName){
        CityEnum[] values = CityEnum.values();
        Optional<CityEnum> first = Arrays.stream(values).filter(e -> e.letterName.equalsIgnoreCase(letterName)).findFirst();
        return first.orElse(UNKNOW).name;
    }
}
