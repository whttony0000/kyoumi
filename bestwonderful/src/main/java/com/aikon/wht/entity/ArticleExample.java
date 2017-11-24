package com.aikon.wht.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("createTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("creatorId is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creatorId is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(Integer value) {
            addCriterion("creatorId =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(Integer value) {
            addCriterion("creatorId <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(Integer value) {
            addCriterion("creatorId >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("creatorId >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(Integer value) {
            addCriterion("creatorId <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(Integer value) {
            addCriterion("creatorId <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<Integer> values) {
            addCriterion("creatorId in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<Integer> values) {
            addCriterion("creatorId not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(Integer value1, Integer value2) {
            addCriterion("creatorId between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("creatorId not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("memo is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("memo is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("memo =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("memo <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("memo >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("memo >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("memo <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("memo <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("memo like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("memo not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("memo in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("memo not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("memo between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("memo not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNull() {
            addCriterion("categoryId is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("categoryId is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(Integer value) {
            addCriterion("categoryId =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(Integer value) {
            addCriterion("categoryId <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(Integer value) {
            addCriterion("categoryId >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("categoryId >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(Integer value) {
            addCriterion("categoryId <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("categoryId <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List<Integer> values) {
            addCriterion("categoryId in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List<Integer> values) {
            addCriterion("categoryId not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(Integer value1, Integer value2) {
            addCriterion("categoryId between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("categoryId not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andTagIdIsNull() {
            addCriterion("tagId is null");
            return (Criteria) this;
        }

        public Criteria andTagIdIsNotNull() {
            addCriterion("tagId is not null");
            return (Criteria) this;
        }

        public Criteria andTagIdEqualTo(Integer value) {
            addCriterion("tagId =", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotEqualTo(Integer value) {
            addCriterion("tagId <>", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdGreaterThan(Integer value) {
            addCriterion("tagId >", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tagId >=", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdLessThan(Integer value) {
            addCriterion("tagId <", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdLessThanOrEqualTo(Integer value) {
            addCriterion("tagId <=", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdIn(List<Integer> values) {
            addCriterion("tagId in", values, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotIn(List<Integer> values) {
            addCriterion("tagId not in", values, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdBetween(Integer value1, Integer value2) {
            addCriterion("tagId between", value1, value2, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tagId not between", value1, value2, "tagId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andLikeCntIsNull() {
            addCriterion("likeCnt is null");
            return (Criteria) this;
        }

        public Criteria andLikeCntIsNotNull() {
            addCriterion("likeCnt is not null");
            return (Criteria) this;
        }

        public Criteria andLikeCntEqualTo(Integer value) {
            addCriterion("likeCnt =", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntNotEqualTo(Integer value) {
            addCriterion("likeCnt <>", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntGreaterThan(Integer value) {
            addCriterion("likeCnt >", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("likeCnt >=", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntLessThan(Integer value) {
            addCriterion("likeCnt <", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntLessThanOrEqualTo(Integer value) {
            addCriterion("likeCnt <=", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntIn(List<Integer> values) {
            addCriterion("likeCnt in", values, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntNotIn(List<Integer> values) {
            addCriterion("likeCnt not in", values, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntBetween(Integer value1, Integer value2) {
            addCriterion("likeCnt between", value1, value2, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntNotBetween(Integer value1, Integer value2) {
            addCriterion("likeCnt not between", value1, value2, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntIsNull() {
            addCriterion("bookmarkCnt is null");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntIsNotNull() {
            addCriterion("bookmarkCnt is not null");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntEqualTo(Integer value) {
            addCriterion("bookmarkCnt =", value, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntNotEqualTo(Integer value) {
            addCriterion("bookmarkCnt <>", value, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntGreaterThan(Integer value) {
            addCriterion("bookmarkCnt >", value, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("bookmarkCnt >=", value, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntLessThan(Integer value) {
            addCriterion("bookmarkCnt <", value, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntLessThanOrEqualTo(Integer value) {
            addCriterion("bookmarkCnt <=", value, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntIn(List<Integer> values) {
            addCriterion("bookmarkCnt in", values, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntNotIn(List<Integer> values) {
            addCriterion("bookmarkCnt not in", values, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntBetween(Integer value1, Integer value2) {
            addCriterion("bookmarkCnt between", value1, value2, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andBookmarkCntNotBetween(Integer value1, Integer value2) {
            addCriterion("bookmarkCnt not between", value1, value2, "bookmarkCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntIsNull() {
            addCriterion("shareCnt is null");
            return (Criteria) this;
        }

        public Criteria andShareCntIsNotNull() {
            addCriterion("shareCnt is not null");
            return (Criteria) this;
        }

        public Criteria andShareCntEqualTo(Integer value) {
            addCriterion("shareCnt =", value, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntNotEqualTo(Integer value) {
            addCriterion("shareCnt <>", value, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntGreaterThan(Integer value) {
            addCriterion("shareCnt >", value, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("shareCnt >=", value, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntLessThan(Integer value) {
            addCriterion("shareCnt <", value, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntLessThanOrEqualTo(Integer value) {
            addCriterion("shareCnt <=", value, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntIn(List<Integer> values) {
            addCriterion("shareCnt in", values, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntNotIn(List<Integer> values) {
            addCriterion("shareCnt not in", values, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntBetween(Integer value1, Integer value2) {
            addCriterion("shareCnt between", value1, value2, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andShareCntNotBetween(Integer value1, Integer value2) {
            addCriterion("shareCnt not between", value1, value2, "shareCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntIsNull() {
            addCriterion("commentCnt is null");
            return (Criteria) this;
        }

        public Criteria andCommentCntIsNotNull() {
            addCriterion("commentCnt is not null");
            return (Criteria) this;
        }

        public Criteria andCommentCntEqualTo(Integer value) {
            addCriterion("commentCnt =", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntNotEqualTo(Integer value) {
            addCriterion("commentCnt <>", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntGreaterThan(Integer value) {
            addCriterion("commentCnt >", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("commentCnt >=", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntLessThan(Integer value) {
            addCriterion("commentCnt <", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntLessThanOrEqualTo(Integer value) {
            addCriterion("commentCnt <=", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntIn(List<Integer> values) {
            addCriterion("commentCnt in", values, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntNotIn(List<Integer> values) {
            addCriterion("commentCnt not in", values, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntBetween(Integer value1, Integer value2) {
            addCriterion("commentCnt between", value1, value2, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntNotBetween(Integer value1, Integer value2) {
            addCriterion("commentCnt not between", value1, value2, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntIsNull() {
            addCriterion("readCnt is null");
            return (Criteria) this;
        }

        public Criteria andReadCntIsNotNull() {
            addCriterion("readCnt is not null");
            return (Criteria) this;
        }

        public Criteria andReadCntEqualTo(Integer value) {
            addCriterion("readCnt =", value, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntNotEqualTo(Integer value) {
            addCriterion("readCnt <>", value, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntGreaterThan(Integer value) {
            addCriterion("readCnt >", value, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("readCnt >=", value, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntLessThan(Integer value) {
            addCriterion("readCnt <", value, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntLessThanOrEqualTo(Integer value) {
            addCriterion("readCnt <=", value, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntIn(List<Integer> values) {
            addCriterion("readCnt in", values, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntNotIn(List<Integer> values) {
            addCriterion("readCnt not in", values, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntBetween(Integer value1, Integer value2) {
            addCriterion("readCnt between", value1, value2, "readCnt");
            return (Criteria) this;
        }

        public Criteria andReadCntNotBetween(Integer value1, Integer value2) {
            addCriterion("readCnt not between", value1, value2, "readCnt");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}