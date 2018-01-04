package com.aikon.wht.service;

import com.aikon.wht.dao.extend.IndividualExtendMapper;
import com.aikon.wht.dao.extend.MidIndividualIndividualExtendMapper;
import com.aikon.wht.dao.extend.PhotoExtendMapper;
import com.aikon.wht.dao.extend.RolePermExtendMapper;
import com.aikon.wht.entity.*;
import com.aikon.wht.enums.IndividualStatusEnum;
import com.aikon.wht.enums.RoleEnum;
import com.aikon.wht.enums.StatusEnum;
import com.aikon.wht.model.IndividualDetail;
import com.aikon.wht.model.Response;
import com.aikon.wht.utils.Converter;
import com.aikon.wht.utils.IdEncrypter;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 个人service.
 *
 * @author haitao.wang
 */
@Service
@Slf4j
public class IndividualService {

    @Autowired
    IndividualExtendMapper individualExtendMapper;

    @Autowired
    PhotoExtendMapper photoExtendMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MidIndividualIndividualExtendMapper midIndividualIndividualExtendMapper;

    @Autowired
    RolePermExtendMapper rolePermExtendMapper;

    @Autowired
    CommonService commonService;

    /**
     * 保存个人信息.
     *
     * @param individual
     * @return Response
     */
    public Response<Individual> saveIndividual(Individual individual) {
        if (individual.getId() ==null) {
            individualExtendMapper.insertSelectiveExt(individual);
        } else {
            individualExtendMapper.updateByPrimaryKeySelective(individual);
        }
        return new Response<>(individual);
    }

    /**
     * 根据mail获取个人信息.
     *
     * @param mail
     * @return Individual
     */
    public Individual getIndividualByMail(String mail) {
        IndividualExample example = new IndividualExample();
        example.or().andMailEqualTo(mail).andStatusNotEqualTo(IndividualStatusEnum.INVALID.getCode());
        List<Individual> individualList = individualExtendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(individualList)) {
            return null;
        }
        else {
            return individualList.get(0);
        }
    }

    /**
     * mail是否已注册.
     *
     * @param email
     * @return Boolean
     */
    public Boolean mailExist(String email) {
        IndividualExample individualExample = new IndividualExample();
        individualExample.or().andMailEqualTo(email).andStatusNotEqualTo(IndividualStatusEnum.INVALID.getCode());
        int emailCnt = individualExtendMapper.countByExample(individualExample);
        return emailCnt > 0;
    }

    /**
     * 姓名是否已注册.
     *
     * @param name
     * @return boolean
     */
    public boolean nameExist(String name) {
        IndividualExample individualExample = new IndividualExample();
        individualExample.or().andNameEqualTo(name).andStatusNotEqualTo(IndividualStatusEnum.INVALID.getCode());
        int nameCnt = individualExtendMapper.countByExample(individualExample);
        return nameCnt > 0;
    }

    /**
     * 获取个人信息.
     *
     * @param individualId
     * @return Individual
     */
    public Individual getIndividualById(Integer individualId) {
        Individual individual = individualExtendMapper.selectByPrimaryKey(individualId);
        if (individual == null) {
            return  null;
        }
        if (!StatusEnum.VALID.getCode().equals(individual.getStatus())) {
            return null;
        }
        return individual;
    }

    /**
     * 更新个人得分.
     *
     * @param individualId
     * @param score
     */
    public void updateScore(Integer individualId,Integer score) {
        Individual individual = this.getIndividualById(individualId);
        if (individual == null) {
            return;
        }
        Individual individual2Update = new Individual();
        individual2Update.setId(individualId);
        individual2Update.setScore(individual.getScore() + score);
        individualExtendMapper.updateByPrimaryKeySelective(individual2Update);
    }

    /**
     * 获取个人详情.
     *
     * @param individualId
     * @return Response
     */
    public Response<IndividualDetail> getIndividualDetail(Integer individualId) {
        Individual individual = this.getIndividualById(individualId);
        if (individual == null) {
            return new Response<>(0, "查无此人");
        }
        IndividualDetail detail = this.getConverter().convert(individual);
         detail.setWatchCategoryCnt(categoryService.getOnWatchCategoryCnt(individualId));
         detail.setWatchIndividualCnt(this.getOnWatchIndividualCnt(individualId));
        List<Integer> fanIds = this.getFansIds(individualId);
        detail.setFanCnt(fanIds.size());
        detail.setOnWatch(fanIds.contains(individualId));
        return new Response<>(detail);
    }

    /**
     * 获取个人关注de人数.
     *
     * @param individualId
     * @return Integer
     */
    public Integer getOnWatchIndividualCnt(Integer individualId) {
        MidIndividualIndividualExample example = new MidIndividualIndividualExample();
        example.or().andWatcherIdEqualTo(individualId).andStatusEqualTo(StatusEnum.VALID.getCode());
        return midIndividualIndividualExtendMapper.countByExample(example);
    }

    /**
     * 获取粉丝数.
     *
     * @param individualId
     * @return Integer
     */
    public Integer getFanCnt(Integer individualId) {
        MidIndividualIndividualExample example = new MidIndividualIndividualExample();
        example.or().andWatchedIdEqualTo(individualId).andStatusEqualTo(StatusEnum.VALID.getCode());
        return midIndividualIndividualExtendMapper.countByExample(example);
    }

    /**
     * 获取粉丝列表.
     *
     * @param individualId
     * @return List
     */
    public List<MidIndividualIndividual> getFans(Integer individualId) {
        MidIndividualIndividualExample example = new MidIndividualIndividualExample();
        example.or().andWatchedIdEqualTo(individualId).andStatusEqualTo(StatusEnum.VALID.getCode());
        List<MidIndividualIndividual> midIndividualIndividuals = midIndividualIndividualExtendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(midIndividualIndividuals)) {
            return Collections.EMPTY_LIST;
        }
        return midIndividualIndividuals;
    }


    /**
     * 获取粉丝详情列表.
     *
     * @param individualId
     * @return List
     */
    public List<Individual> getFanDetails(Integer individualId) {
        return individualExtendMapper.getFanDetails(individualId);
    }

    /**
     * 获取粉丝id列表.
     *
     * @param individualId
     * @return List
     */
    public List<Integer> getFansIds(Integer individualId) {
        return this.getFans(individualId).stream().map(MidIndividualIndividual::getWatchedId).collect(Collectors.toList());
    }

    public List<Integer> getOnWatchIndividualIds(Integer individualId) {
        MidIndividualIndividualExample example = new MidIndividualIndividualExample();
        example.or().andWatcherIdEqualTo(individualId).andStatusEqualTo(StatusEnum.VALID.getCode());
        List<MidIndividualIndividual> midIndividualIndividuals = midIndividualIndividualExtendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(midIndividualIndividuals)) {
            return Collections.EMPTY_LIST;
        }
        return midIndividualIndividuals.stream().map(MidIndividualIndividual::getWatchedId).collect(Collectors.toList());
    }

    /**
     * {@link Individual}
     *
     * =>
     *
     * {@link IndividualDetail}
     *
     * @return Converter
     */
    public Converter<Individual, IndividualDetail> getConverter() {
        return new Converter<>(individual -> {
            IndividualDetail detail = new IndividualDetail();
            detail.setIndividualEid(IdEncrypter.encodeId(individual.getId()));
            detail.setName(individual.getName());
            detail.setDescription(individual.getDescription());
            detail.setSex(individual.getSex());
            detail.setScore(individual.getScore());
            detail.setPhotoKey(individual.getPhotoKey());
            detail.setPhotoUrl(commonService.getImageUrlByKey(individual.getPhotoKey()));
            detail.setMailMd5Hash(individual.getMailMd5Hash());
            detail.setBirthday(individual.getBirthday());
            return detail;
        });
    }

    /**
     * 关注个人操作.
     *
     * @param watchedId
     * @param watcherId
     * @param onWatch
     * @return Response
     */
    public Response<Object> watchIndividual(Integer watchedId, Integer watcherId, Boolean onWatch) {
        MidIndividualIndividualExample example = new MidIndividualIndividualExample();
        example.or().andWatchedIdEqualTo(watchedId).andWatcherIdEqualTo(watcherId);
        List<MidIndividualIndividual> midIndividualIndividuals = midIndividualIndividualExtendMapper.selectByExample(example);
        if (onWatch && CollectionUtils.isEmpty(midIndividualIndividuals)) {
            MidIndividualIndividual midIndividualIndividual = new MidIndividualIndividual();
            midIndividualIndividual.setWatchedId(watchedId);
            midIndividualIndividual.setCreatorId(watcherId);
            midIndividualIndividual.setStatus(StatusEnum.VALID.getCode());
            midIndividualIndividual.setWatcherId(watcherId);
            midIndividualIndividualExtendMapper.insertSelective(midIndividualIndividual);
            return Response.SUCCESS;
        }
        MidIndividualIndividual midIndividualIndividual2Update = midIndividualIndividuals.get(0);
        midIndividualIndividual2Update.setStatus(onWatch ? StatusEnum.VALID.getCode():StatusEnum.INVALID.getCode());
        midIndividualIndividualExtendMapper.updateByPrimaryKeySelective(midIndividualIndividual2Update);
        return Response.SUCCESS;
    }


    /**
     * 获取关注个人详情列表.
     *
     * @param watcherId
     * @param currentPage
     * @param pageSize
     * @return List
     */
    public List<IndividualDetail> getOnWatchIndividualList(Integer watcherId, Integer currentPage, Integer pageSize) {
        Integer offset = (currentPage - 1 ) * pageSize;
        return this.getConverter().convert(Optional.ofNullable(individualExtendMapper.getOnWatchIndividualList(watcherId,offset,pageSize)).orElse(Collections.EMPTY_LIST));
    }

    /**
     * 获取粉丝详情列表.
     *
     * @param watchedId
     * @param currentPage
     * @param pageSize
     * @return List
     */
    public List<IndividualDetail> getFanList(Integer watchedId, Integer currentPage, Integer pageSize) {
        Integer offset = (currentPage - 1 ) * pageSize;
        return this.getConverter().convert(Optional.ofNullable(individualExtendMapper.getFanList(watchedId,offset,pageSize)).orElse(Collections.EMPTY_LIST));
    }

    /**
     * 激活账户.
     *
     * @param mailMd5Hash
     * @return Response
     */
    public Response<Individual> activeAccount(String mailMd5Hash) {
        IndividualExample example = new IndividualExample();
        example.or().andMailMd5HashEqualTo(mailMd5Hash);
        List<Individual> individuals = individualExtendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(individuals)) {
            return new Response<>(-1, "验证失败，您的账号不存在。");
        }
        Individual individual = individuals.get(0);
        if (!RoleEnum.TMP_USER.getCode().equals(individual.getRole())) {
            return new Response<>(0, "您已验证过，请登录");
        }
        individual.setRole(RoleEnum.ACTIVE_USER.getCode());
        individualExtendMapper.updateByPrimaryKeySelective(individual);
        return new Response<>(individual);
    }

    public Set<String> getPermSet(Integer role) {
        RolePermExample example = new RolePermExample();
        example.or().andRoleEqualTo(role);
        List<RolePerm> rolePerms = rolePermExtendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(rolePerms)) {
            return Collections.EMPTY_SET;
        }
        RolePerm rolePerm = rolePerms.get(0);

        return (Set<String>) Splitter.on(",").split(rolePerm.getPerm());
    }
}
