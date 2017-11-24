package com.aikon.wht.entity;

import java.util.Date;

public class SubComment {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer creatorId;

    private Integer status;

    private String content;

    private Integer commentId;

    private Integer targetIndividualId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getTargetIndividualId() {
        return targetIndividualId;
    }

    public void setTargetIndividualId(Integer targetIndividualId) {
        this.targetIndividualId = targetIndividualId;
    }
}