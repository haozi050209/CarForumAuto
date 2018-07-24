package com.yonyou.friendsandaargang.forum.bean;

/**
 * Created by shibing on 18/3/23.
 */

public class ForumDetalis {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","postId":2,"authorId":4,"author":"大嘴猴🐵","avatar":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/avatar_20180322165602.png?Expires=1521800120&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=SAR0%2F0DongP%2BZrahqu47QiKPLZA%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"forumId":1004,"level":0,"forumName":"二手车论坛","brandName":"长安福特","title":"让我来发表第一个话题吧！","replyNumber":3,"readNumber":26,"thumbupNumber":0,"itemType":3,"isDeleted":0,"isAdminDeleted":0,"followed":0,"thumbuped":0,"favorite":0,"postDate":"2018-03-23 00:58:27"}
     */

   /* private int returnCode;
    private String returnMsg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }*/

    //public static class ContentBean {
    /**
     * digitalSignature :
     * postId : 2
     * authorId : 4
     * author : 大嘴猴🐵
     * avatar : https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/avatar_20180322165602.png?Expires=1521800120&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=SAR0%2F0DongP%2BZrahqu47QiKPLZA%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
     * realNameAuth : 10081001
     * forumId : 1004
     * level : 0
     * forumName : 二手车论坛
     * brandName : 长安福特
     * title : 让我来发表第一个话题吧！
     * replyNumber : 3
     * readNumber : 26
     * thumbupNumber : 0
     * itemType : 3
     * isDeleted : 0
     * isAdminDeleted : 0
     * followed : 0
     * thumbuped : 0
     * favorite : 0
     * postDate : 2018-03-23 00:58:27
     */

    private String digitalSignature;
    private int postId;
    private String authorId;
    private String author;
    private String avatar;
    private int realNameAuth;
    private String forumId;
    private String level;
    private String forumName;
    private String brandName;
    private String title;
    private String replyNumber;
    private String readNumber;
    private String thumbupNumber;
    private int itemType;
    private int isDeleted;
    private int isAdminDeleted;
    private int followed;
    private int thumbuped;
    private int favorite;
    private String postDate;

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRealNameAuth() {
        return realNameAuth;
    }

    public void setRealNameAuth(int realNameAuth) {
        this.realNameAuth = realNameAuth;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
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

    public String getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(String replyNumber) {
        this.replyNumber = replyNumber;
    }

    public String getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(String readNumber) {
        this.readNumber = readNumber;
    }

    public String getThumbupNumber() {
        return thumbupNumber;
    }

    public void setThumbupNumber(String thumbupNumber) {
        this.thumbupNumber = thumbupNumber;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getIsAdminDeleted() {
        return isAdminDeleted;
    }

    public void setIsAdminDeleted(int isAdminDeleted) {
        this.isAdminDeleted = isAdminDeleted;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public int getThumbuped() {
        return thumbuped;
    }

    public void setThumbuped(int thumbuped) {
        this.thumbuped = thumbuped;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    // }
}
