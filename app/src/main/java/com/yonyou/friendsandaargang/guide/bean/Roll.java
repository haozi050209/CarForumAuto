package com.yonyou.friendsandaargang.guide.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/16.
 */

public class Roll {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"action":"浠曦回复了\"热议\"","traceDate":"2018-03-15 17:15:34"},{"action":"浠曦回复了\"测试话题\"","traceDate":"2018-03-15 17:15:26"},{"action":"浠曦发布了\"要制定\"","traceDate":"2018-03-15 16:01:44"},{"action":"浠曦发布了\"侧视\"","traceDate":"2018-03-15 15:56:02"},{"action":"大嘴猴🐒zzc发布了\"动物园集合！\"","traceDate":"2018-03-16 11:25:41"},{"action":"大嘴猴🐒zzc发布了\"帖子我也要上头条！！！\"","traceDate":"2018-03-16 11:19:53"},{"action":"大嘴猴🐒zzc发布了\"让我上个头条吧！\"","traceDate":"2018-03-16 11:17:02"},{"action":"大嘴猴🐒zzc回复了\"查的哈佛扯废话就\"","traceDate":"2018-03-15 13:55:33"},{"action":"大嘴猴🐒zzc回复了\"查的哈佛扯废话就\"","traceDate":"2018-03-15 13:55:16"},{"action":"大嘴猴🐒zzc回复了\"查的哈佛扯废话就\"","traceDate":"2018-03-15 13:52:28"},{"action":"大嘴猴🐒zzc回复了\"查的哈佛扯废话就\"","traceDate":"2018-03-15 13:49:49"},{"action":"浠曦2号发布了\"第五个话题了！\"","traceDate":"2018-03-15 17:28:23"},{"action":"浠曦2号回复了\"话题话题\"","traceDate":"2018-03-15 17:21:06"},{"action":"浠曦2号发布了\"话题话题\"","traceDate":"2018-03-15 17:20:50"},{"action":"浠曦2号回复了\"测试话题\"","traceDate":"2018-03-15 17:16:07"},{"action":"浠曦2号发布了\"测试话题\"","traceDate":"2018-03-15 16:32:52"},{"action":"浠曦2号回复了\"热议\"","traceDate":"2018-03-15 16:31:25"},{"action":"浠曦2号发布了\"热议\"","traceDate":"2018-03-15 16:31:16"},{"action":"浠曦2号发布了\"零配件\"","traceDate":"2018-03-15 16:29:51"},{"action":"浠曦2号发布了\"零配件\"","traceDate":"2018-03-15 16:29:17"},{"action":"浠曦2号回复了\"话题\"","traceDate":"2018-03-15 16:28:08"},{"action":"浠曦2号发布了\"测试哈哈\"","traceDate":"2018-03-15 16:27:22"},{"action":"浠曦2号发布了\"测试\"","traceDate":"2018-03-15 16:17:55"},{"action":"浠曦2号赞了\"要制定\"","traceDate":"2018-03-15 16:17:19"},{"action":"浠曦2号发布了\"话题\"","traceDate":"2018-03-15 16:14:28"},{"action":"浠曦2号回复了\"要制定\"","traceDate":"2018-03-15 16:09:23"}]
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
         * action : 浠曦回复了"热议"
         * traceDate : 2018-03-15 17:15:34
         */

        private String action;
        private String traceDate;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getTraceDate() {
            return traceDate;
        }

        public void setTraceDate(String traceDate) {
            this.traceDate = traceDate;
        }
    }
}
