package com.yonyou.friendsandaargang.info.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shibing on 18/4/13.
 */

public class Draft implements Serializable {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":156,"forumId":1002,"forumName":"人事行政","brandId":1001,"brandName":"长安铃木","title":"果然是反复看客户","content":"","draft":"[\n\n]","itemType":3,"postDate":"2018-04-13 15:44:39"},{"digitalSignature":"","postId":157,"forumId":1002,"forumName":"人事行政","brandId":1002,"brandName":"福特猛禽","title":"肉体上的更好就恢复高考后","content":"风格是非公经济汾河谷地护肤[图片][图片][图片]","draft":"[\n  {\n    \"bold\" : false,\n    \"title\" : \"风格是非公经济汾河谷地护肤\",\n    \"lineSpace\" : 0,\n    \"font\" : 16\n  },\n  {\n    \"bold\" : false,\n    \"title\" : \"post-attachment\\/61-20180413154636.png\",\n    \"lineSpace\" : 0,\n    \"font\" : 16,\n    \"image\" : \"image\"\n  },\n  {\n    \"bold\" : false,\n    \"title\" : \"\\n\",\n    \"lineSpace\" : 0,\n    \"font\" : 16\n  },\n  {\n    \"bold\" : false,\n    \"title\" : \"post-attachment\\/61-20180413154644.png\",\n    \"lineSpace\" : 0,\n    \"font\" : 16,\n    \"image\" : \"image\"\n  },\n  {\n    \"bold\" : false,\n    \"title\" : \"\\n\",\n    \"lineSpace\" : 0,\n    \"font\" : 16\n  },\n  {\n    \"bold\" : false,\n    \"title\" : \"post-attachment\\/61-20180413154652.png\",\n    \"lineSpace\" : 0,\n    \"font\" : 16,\n    \"image\" : \"image\"\n  },\n  {\n    \"bold\" : false,\n    \"title\" : \"\\n\",\n    \"lineSpace\" : 0,\n    \"font\" : 16\n  }\n]","itemType":3,"postDate":"2018-04-13 15:46:59"}]
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

    public static class ContentBean implements Serializable {
        /**
         * digitalSignature :
         * postId : 156
         * forumId : 1002
         * forumName : 人事行政
         * brandId : 1001
         * brandName : 长安铃木
         * title : 果然是反复看客户
         * content :
         * draft : [
         * <p>
         * ]
         * itemType : 3
         * postDate : 2018-04-13 15:44:39
         */

        private String digitalSignature;
        private String postId;
        private String forumId;
        private String forumName;
        private int brandId;
        private String brandName;
        private String title;
        private String content;
        private String draft;
        private int itemType;
        private String postDate;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getForumId() {
            return forumId;
        }

        public void setForumId(String forumId) {
            this.forumId = forumId;
        }

        public String getForumName() {
            return forumName;
        }

        public void setForumName(String forumName) {
            this.forumName = forumName;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDraft() {
            return draft;
        }

        public void setDraft(String draft) {
            this.draft = draft;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }
    }
}
