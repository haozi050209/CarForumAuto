package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/15.
 */

public class Country {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","countryId":1,"country":"中国（中华人民共和国）"},{"digitalSignature":"","countryId":2,"country":"阿布哈兹"},{"digitalSignature":"","countryId":3,"country":"阿富汗"},{"digitalSignature":"","countryId":4,"country":"阿尔巴尼亚"},{"digitalSignature":"","countryId":5,"country":"阿尔及利亚"},{"digitalSignature":"","countryId":6,"country":"安道尔"},{"digitalSignature":"","countryId":7,"country":"安哥拉"},{"digitalSignature":"","countryId":8,"country":"安提瓜和巴布达"},{"digitalSignature":"","countryId":9,"country":"阿根廷"},{"digitalSignature":"","countryId":10,"country":"亚美尼亚"},{"digitalSignature":"","countryId":11,"country":"澳大利亚"},{"digitalSignature":"","countryId":12,"country":"奥地利"},{"digitalSignature":"","countryId":13,"country":"阿塞拜疆"},{"digitalSignature":"","countryId":14,"country":"巴哈马"},{"digitalSignature":"","countryId":15,"country":"巴林"},{"digitalSignature":"","countryId":16,"country":"孟加拉国"},{"digitalSignature":"","countryId":17,"country":"巴巴多斯"},{"digitalSignature":"","countryId":18,"country":"白俄罗斯"},{"digitalSignature":"","countryId":19,"country":"比利时"},{"digitalSignature":"","countryId":20,"country":"伯利兹"},{"digitalSignature":"","countryId":21,"country":"贝宁"},{"digitalSignature":"","countryId":22,"country":"不丹"},{"digitalSignature":"","countryId":23,"country":"玻利维亚"},{"digitalSignature":"","countryId":24,"country":"波黑"},{"digitalSignature":"","countryId":25,"country":"博茨瓦纳"},{"digitalSignature":"","countryId":26,"country":"巴西"},{"digitalSignature":"","countryId":27,"country":"文莱"},{"digitalSignature":"","countryId":28,"country":"保加利亚"},{"digitalSignature":"","countryId":29,"country":"布基纳法索"},{"digitalSignature":"","countryId":30,"country":"布隆迪"},{"digitalSignature":"","countryId":31,"country":"柬埔寨"},{"digitalSignature":"","countryId":32,"country":"喀麦隆"},{"digitalSignature":"","countryId":33,"country":"加拿大"},{"digitalSignature":"","countryId":34,"country":"佛得角"},{"digitalSignature":"","countryId":35,"country":"加泰罗尼亚（西班牙）"},{"digitalSignature":"","countryId":36,"country":"中非共和国"},{"digitalSignature":"","countryId":37,"country":"乍得"},{"digitalSignature":"","countryId":38,"country":"智利"},{"digitalSignature":"","countryId":39,"country":"哥伦比亚"},{"digitalSignature":"","countryId":40,"country":"科摩罗"},{"digitalSignature":"","countryId":41,"country":"刚果共和国"},{"digitalSignature":"","countryId":42,"country":"刚果民主共和国"},{"digitalSignature":"","countryId":43,"country":"库克群岛（新西兰）"},{"digitalSignature":"","countryId":44,"country":"哥斯达黎加"},{"digitalSignature":"","countryId":45,"country":"科特迪瓦"},{"digitalSignature":"","countryId":46,"country":"克罗地亚"},{"digitalSignature":"","countryId":47,"country":"古巴"},{"digitalSignature":"","countryId":48,"country":"塞浦路斯"},{"digitalSignature":"","countryId":49,"country":"捷克"},{"digitalSignature":"","countryId":50,"country":"丹麦"},{"digitalSignature":"","countryId":51,"country":"吉布提"},{"digitalSignature":"","countryId":52,"country":"顿涅茨克"},{"digitalSignature":"","countryId":53,"country":"多米尼克"},{"digitalSignature":"","countryId":54,"country":"多米尼加"},{"digitalSignature":"","countryId":55,"country":"厄瓜多尔"},{"digitalSignature":"","countryId":56,"country":"埃及"},{"digitalSignature":"","countryId":57,"country":"萨尔瓦多"},{"digitalSignature":"","countryId":58,"country":"赤道几内亚"},{"digitalSignature":"","countryId":59,"country":"厄立特里亚"},{"digitalSignature":"","countryId":60,"country":"爱沙尼亚"},{"digitalSignature":"","countryId":61,"country":"埃塞俄比亚"},{"digitalSignature":"","countryId":62,"country":"斐济"},{"digitalSignature":"","countryId":63,"country":"芬兰"},{"digitalSignature":"","countryId":64,"country":"法国"},{"digitalSignature":"","countryId":65,"country":"加蓬"},{"digitalSignature":"","countryId":66,"country":"冈比亚"},{"digitalSignature":"","countryId":67,"country":"格鲁吉亚"},{"digitalSignature":"","countryId":68,"country":"德国"},{"digitalSignature":"","countryId":69,"country":"加纳"},{"digitalSignature":"","countryId":70,"country":"希腊"},{"digitalSignature":"","countryId":71,"country":"格林纳达"},{"digitalSignature":"","countryId":72,"country":"危地马拉"},{"digitalSignature":"","countryId":73,"country":"几内亚"},{"digitalSignature":"","countryId":74,"country":"几内亚比绍"},{"digitalSignature":"","countryId":75,"country":"圭亚那"},{"digitalSignature":"","countryId":76,"country":"海地"},{"digitalSignature":"","countryId":77,"country":"洪都拉斯"},{"digitalSignature":"","countryId":78,"country":"匈牙利"},{"digitalSignature":"","countryId":79,"country":"冰岛"},{"digitalSignature":"","countryId":80,"country":"印度"},{"digitalSignature":"","countryId":81,"country":"印度尼西亚"},{"digitalSignature":"","countryId":82,"country":"伊朗"},{"digitalSignature":"","countryId":83,"country":"伊拉克"},{"digitalSignature":"","countryId":84,"country":"爱尔兰"},{"digitalSignature":"","countryId":85,"country":"以色列"},{"digitalSignature":"","countryId":86,"country":"意大利"},{"digitalSignature":"","countryId":87,"country":"牙买加"},{"digitalSignature":"","countryId":88,"country":"日本"},{"digitalSignature":"","countryId":89,"country":"约旦"},{"digitalSignature":"","countryId":90,"country":"哈萨克斯坦"},{"digitalSignature":"","countryId":91,"country":"肯尼亚"},{"digitalSignature":"","countryId":92,"country":"基里巴斯"},{"digitalSignature":"","countryId":93,"country":"韩国"},{"digitalSignature":"","countryId":94,"country":"科索沃"},{"digitalSignature":"","countryId":95,"country":"科威特"},{"digitalSignature":"","countryId":96,"country":"吉尔吉斯斯坦"},{"digitalSignature":"","countryId":97,"country":"老挝"},{"digitalSignature":"","countryId":98,"country":"拉脱维亚"},{"digitalSignature":"","countryId":99,"country":"黎巴嫩"},{"digitalSignature":"","countryId":100,"country":"莱索托"},{"digitalSignature":"","countryId":101,"country":"利比里亚"},{"digitalSignature":"","countryId":102,"country":"利比亚"},{"digitalSignature":"","countryId":103,"country":"列支敦士登"},{"digitalSignature":"","countryId":104,"country":"立陶宛"},{"digitalSignature":"","countryId":105,"country":"卢森堡"},{"digitalSignature":"","countryId":106,"country":"马其顿"},{"digitalSignature":"","countryId":107,"country":"马达加斯加"},{"digitalSignature":"","countryId":108,"country":"马拉维"},{"digitalSignature":"","countryId":109,"country":"马来西亚"},{"digitalSignature":"","countryId":110,"country":"马尔代夫"},{"digitalSignature":"","countryId":111,"country":"马耳他骑士团"},{"digitalSignature":"","countryId":112,"country":"马里"},{"digitalSignature":"","countryId":113,"country":"马耳他"},{"digitalSignature":"","countryId":114,"country":"马绍尔群岛"},{"digitalSignature":"","countryId":115,"country":"毛里塔尼亚"},{"digitalSignature":"","countryId":116,"country":"毛里求斯"},{"digitalSignature":"","countryId":117,"country":"墨西哥"},{"digitalSignature":"","countryId":118,"country":"密克罗尼西亚联邦"},{"digitalSignature":"","countryId":119,"country":"摩尔多瓦"},{"digitalSignature":"","countryId":120,"country":"摩纳哥"},{"digitalSignature":"","countryId":121,"country":"蒙古国"},{"digitalSignature":"","countryId":122,"country":"黑山"},{"digitalSignature":"","countryId":123,"country":"摩洛哥"},{"digitalSignature":"","countryId":124,"country":"莫桑比克"},{"digitalSignature":"","countryId":125,"country":"缅甸"},{"digitalSignature":"","countryId":126,"country":"纳戈尔诺-卡拉巴赫"},{"digitalSignature":"","countryId":127,"country":"纳米比亚"},{"digitalSignature":"","countryId":128,"country":"瑙鲁"},{"digitalSignature":"","countryId":129,"country":"尼泊尔"},{"digitalSignature":"","countryId":130,"country":"荷兰"},{"digitalSignature":"","countryId":131,"country":"新西兰"},{"digitalSignature":"","countryId":132,"country":"尼加拉瓜"},{"digitalSignature":"","countryId":133,"country":"尼日尔"},{"digitalSignature":"","countryId":134,"country":"尼日利亚"},{"digitalSignature":"","countryId":135,"country":"纽埃（新西兰）"},{"digitalSignature":"","countryId":136,"country":"北塞浦路斯"},{"digitalSignature":"","countryId":137,"country":"挪威"},{"digitalSignature":"","countryId":138,"country":"阿曼"},{"digitalSignature":"","countryId":139,"country":"巴基斯坦"},{"digitalSignature":"","countryId":140,"country":"帕劳"},{"digitalSignature":"","countryId":141,"country":"巴勒斯坦"},{"digitalSignature":"","countryId":142,"country":"巴拿马"},{"digitalSignature":"","countryId":143,"country":"巴布亚新几内亚"},{"digitalSignature":"","countryId":144,"country":"巴拉圭"},{"digitalSignature":"","countryId":145,"country":"朝鲜"},{"digitalSignature":"","countryId":146,"country":"秘鲁"},{"digitalSignature":"","countryId":147,"country":"菲律宾"},{"digitalSignature":"","countryId":148,"country":"波兰"},{"digitalSignature":"","countryId":149,"country":"葡萄牙"},{"digitalSignature":"","countryId":150,"country":"德涅斯特河沿岸"},{"digitalSignature":"","countryId":151,"country":"邦特兰"},{"digitalSignature":"","countryId":152,"country":"卡塔尔"},{"digitalSignature":"","countryId":153,"country":"罗马尼亚"},{"digitalSignature":"","countryId":154,"country":"俄罗斯"},{"digitalSignature":"","countryId":155,"country":"卢旺达"},{"digitalSignature":"","countryId":156,"country":"圣基茨和尼维斯"},{"digitalSignature":"","countryId":157,"country":"圣卢西亚"},{"digitalSignature":"","countryId":158,"country":"圣文森特和格林纳丁斯"},{"digitalSignature":"","countryId":159,"country":"萨摩亚"},{"digitalSignature":"","countryId":160,"country":"圣马力诺"},{"digitalSignature":"","countryId":161,"country":"圣多美和普林西比"},{"digitalSignature":"","countryId":162,"country":"沙特阿拉伯"},{"digitalSignature":"","countryId":163,"country":"塞内加尔"},{"digitalSignature":"","countryId":164,"country":"塞尔维亚"},{"digitalSignature":"","countryId":165,"country":"塞舌尔"},{"digitalSignature":"","countryId":166,"country":"塞拉利昂"},{"digitalSignature":"","countryId":167,"country":"新加坡"},{"digitalSignature":"","countryId":168,"country":"斯洛伐克"},{"digitalSignature":"","countryId":169,"country":"斯洛文尼亚"},{"digitalSignature":"","countryId":170,"country":"所罗门群岛"},{"digitalSignature":"","countryId":171,"country":"索马里"},{"digitalSignature":"","countryId":172,"country":"索马里兰"},{"digitalSignature":"","countryId":173,"country":"南非"},{"digitalSignature":"","countryId":174,"country":"南奥塞梯"},{"digitalSignature":"","countryId":175,"country":"南苏丹"},{"digitalSignature":"","countryId":176,"country":"西班牙"},{"digitalSignature":"","countryId":177,"country":"斯里兰卡"},{"digitalSignature":"","countryId":178,"country":"苏丹"},{"digitalSignature":"","countryId":179,"country":"苏里南"},{"digitalSignature":"","countryId":180,"country":"斯威士兰"},{"digitalSignature":"","countryId":181,"country":"瑞典"},{"digitalSignature":"","countryId":182,"country":"瑞士"},{"digitalSignature":"","countryId":183,"country":"叙利亚"},{"digitalSignature":"","countryId":184,"country":"塔吉克斯坦"},{"digitalSignature":"","countryId":185,"country":"坦桑尼亚"},{"digitalSignature":"","countryId":186,"country":"泰国"},{"digitalSignature":"","countryId":187,"country":"东帝汶"},{"digitalSignature":"","countryId":188,"country":"多哥"},{"digitalSignature":"","countryId":189,"country":"汤加"},{"digitalSignature":"","countryId":190,"country":"特立尼达和多巴哥"},{"digitalSignature":"","countryId":191,"country":"突尼斯"},{"digitalSignature":"","countryId":192,"country":"土耳其"},{"digitalSignature":"","countryId":193,"country":"土库曼斯坦"},{"digitalSignature":"","countryId":194,"country":"图瓦卢"},{"digitalSignature":"","countryId":195,"country":"乌干达"},{"digitalSignature":"","countryId":196,"country":"乌克兰"},{"digitalSignature":"","countryId":197,"country":"阿联酋"},{"digitalSignature":"","countryId":198,"country":"英国"},{"digitalSignature":"","countryId":199,"country":"美国"},{"digitalSignature":"","countryId":200,"country":"乌拉圭"},{"digitalSignature":"","countryId":201,"country":"乌兹别克斯坦"},{"digitalSignature":"","countryId":202,"country":"瓦努阿图"},{"digitalSignature":"","countryId":203,"country":"梵蒂冈"},{"digitalSignature":"","countryId":204,"country":"委内瑞拉"},{"digitalSignature":"","countryId":205,"country":"越南"},{"digitalSignature":"","countryId":206,"country":"西撒哈拉"},{"digitalSignature":"","countryId":207,"country":"也门"},{"digitalSignature":"","countryId":208,"country":"赞比亚"},{"digitalSignature":"","countryId":209,"country":"津巴布韦"}]
     */

    private int returnCode;
    private String returnMsg;
    private List<ContentBean> content;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * digitalSignature :
         * countryId : 1
         * country : 中国（中华人民共和国）
         */

        private String digitalSignature;
        private String countryId;
        private String country;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
