package com.aikon.wht.service;

import com.aikon.wht.dao.extend.CategoryExtendMapper;
import com.aikon.wht.dao.extend.MidIndividualCategoryExtendMapper;
import com.aikon.wht.dao.extend.PhotoExtendMapper;
import com.aikon.wht.entity.*;
import com.aikon.wht.enums.StatusEnum;
import com.aikon.wht.model.CategoryDetailModel;
import com.aikon.wht.model.Response;
import com.aikon.wht.model.UploadFileModel;
import com.aikon.wht.utils.Converter;
import com.aikon.wht.utils.IdEncrypter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author haitao.wang
 */
@Service
public class CategoryService {


    @Autowired
    CategoryExtendMapper categoryExtendMapper;

    @Autowired
    PhotoExtendMapper photoExtendMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    PhotoService photoService;

    @Autowired
    MidIndividualCategoryExtendMapper midIndividualCategoryExtendMapper;


    /**
     * 获取分类详情.
     *
     * @param categoryId
     * @param individualId
     * @return CategoryDetailModel
     */
    public CategoryDetailModel getCategoryDetail(Integer categoryId, Integer individualId) {
        CategoryDetailModel categoryDetailModel = new CategoryDetailModel();
        Category category = categoryExtendMapper.selectByPrimaryKey(categoryId);
        if (category == null) {
            return categoryDetailModel;
        }
        categoryDetailModel.setName(category.getName());
        categoryDetailModel.setCategoryId(categoryId);
        categoryDetailModel.setDescription(category.getDescription());
        UploadFileModel imageModel = new UploadFileModel();
        imageModel.setKey(category.getPhotoKey());
        imageModel.setUrl(commonService.getImageUrlByKey(category.getPhotoKey()));
        categoryDetailModel.setImageModel(imageModel);
        List<Integer> onWatchCategoryIds = getOnWatchCategoryIds(individualId);
        categoryDetailModel.setOnWatch(CollectionUtils.isNotEmpty(onWatchCategoryIds) && onWatchCategoryIds.contains(categoryId));
        return categoryDetailModel;
    }

    /**
     * 更新或插入分类及分类图片.
     *
     * @param categoryDetailModel
     * @param individualId
     * @return Response
     */
    public Response upsertCategory(CategoryDetailModel categoryDetailModel, Integer individualId) {
        Category category = new Category();
        Photo photo = new Photo();
        if (categoryDetailModel.getCategoryId() == null) {
            category.setCreatorId(individualId);
            category.setName(categoryDetailModel.getName());
            category.setStatus(StatusEnum.VALID.getCode());
            category.setDescription(categoryDetailModel.getDescription());
            category.setPhotoKey(categoryDetailModel.getImageModel().getKey());
            categoryExtendMapper.insertSelectiveExt(category);
        } else {
            category.setName(categoryDetailModel.getName());
            category.setPhotoKey(categoryDetailModel.getImageModel().getKey());
            category.setDescription(categoryDetailModel.getDescription());
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.or().andIdEqualTo(categoryDetailModel.getCategoryId()).andStatusEqualTo(StatusEnum.VALID.getCode());
            categoryExtendMapper.updateByExampleSelective(category, categoryExample);
        }
        return Response.SUCCESS;
    }

    public List<CategoryDetailModel> getCategoryList(Boolean showImage, Integer individualId) {
        List<CategoryDetailModel> categoryList = Lists.newArrayList();
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andStatusEqualTo(StatusEnum.VALID.getCode());
        List<Category> categories = categoryExtendMapper.selectByExample(categoryExample);
        if (CollectionUtils.isEmpty(categories)) {
            return categoryList;
        }
        List<Integer> categoryIds = Lists.newArrayList(categories.size());
        List<Integer> onWatchCategoryIds = null;
        if (individualId != null) {
            onWatchCategoryIds = getOnWatchCategoryIds(individualId);
        }
        List<Integer> finalOnWatchCategoryIds = onWatchCategoryIds;
        Boolean hasOnWatchCategory = CollectionUtils.isNotEmpty(finalOnWatchCategoryIds);
        categories.stream().forEach(
                category -> {
                    categoryIds.add(category.getId());
                    CategoryDetailModel categoryDetailModel = new CategoryDetailModel();
                    categoryDetailModel.setCategoryId(category.getId());
                    categoryDetailModel.setCategoryEid(IdEncrypter.encodeId(category.getId()));
                    categoryDetailModel.setDescription(category.getDescription());
                    categoryDetailModel.setName(category.getName());
                    if (showImage) {
                        UploadFileModel uploadFileModel = new UploadFileModel();
                        uploadFileModel.setKey(category.getPhotoKey());
                        uploadFileModel.setUrl(commonService.getImageUrlByKey(category.getPhotoKey()));
                        categoryDetailModel.setImageModel(uploadFileModel);
                    }
                    if (hasOnWatchCategory && finalOnWatchCategoryIds.contains(category.getId())) {
                        categoryDetailModel.setOnWatch(true);
                    } else {
                        categoryDetailModel.setOnWatch(false);
                    }
                    categoryList.add(categoryDetailModel);
                }
        );
        if (hasOnWatchCategory) {
            categoryList.sort(Comparator.comparing(CategoryDetailModel::getOnWatch).reversed());
        }
        return categoryList;
    }

    public List<Integer> getOnWatchCategoryIds(Integer individualId) {
        MidIndividualCategoryExample midExample = new MidIndividualCategoryExample();
        midExample.or().andIndividualIdEqualTo(individualId).andStatusEqualTo(StatusEnum.VALID.getCode());
        List<MidIndividualCategory> midIndividualCategories = midIndividualCategoryExtendMapper.selectByExample(midExample);
        if (CollectionUtils.isEmpty(midIndividualCategories)) {
            return Collections.EMPTY_LIST;
        }
        return midIndividualCategories.stream().map(MidIndividualCategory::getCategoryId).collect(Collectors.toList());
    }

    public List<Category> getOnWatchCategories(Integer individualId) {
        CategoryExample example = new CategoryExample();
        example.or().andIdIn(this.getOnWatchCategoryIds(individualId)).andStatusEqualTo(StatusEnum.VALID.getCode());
        return Optional.ofNullable(categoryExtendMapper.selectByExample(example)).orElse(Collections.EMPTY_LIST);
    }

    public List<CategoryDetailModel> getOnWatchCategoryList(Integer individualId) {
        return this.getConverter().convert(this.getOnWatchCategories(individualId));
    }

    public List<CategoryDetailModel> getOnWatchCategoryList(Integer watcherId, Integer currentPage, Integer pageSize) {
        Integer offset = (currentPage - 1) * pageSize;
        List<Category> categories = categoryExtendMapper.getOnWatchCategoryList(watcherId,offset,pageSize);
        return this.getConverter().convert(Optional.ofNullable(categories).orElse(Collections.EMPTY_LIST));
    }

    private Converter<Category, CategoryDetailModel> getConverter() {
        return new Converter<>(category -> {
            CategoryDetailModel detailModel = new CategoryDetailModel();
            detailModel.setCategoryEid(IdEncrypter.encodeId(category.getId()));
            detailModel.setDescription(category.getDescription());
            detailModel.setName(category.getName());
            UploadFileModel uploadFileModel = new UploadFileModel();
            uploadFileModel.setKey(category.getPhotoKey());
            uploadFileModel.setUrl(commonService.getImageUrlByKey(category.getPhotoKey()));
            detailModel.setImageModel(uploadFileModel);
            return detailModel;
        });
    }



    public Integer getOnWatchCategoryCnt(Integer individualId) {
        MidIndividualCategoryExample midExample = new MidIndividualCategoryExample();
        midExample.or().andIndividualIdEqualTo(individualId).andStatusEqualTo(StatusEnum.VALID.getCode());
        return midIndividualCategoryExtendMapper.countByExample(midExample);
    }

    public Response<Object> watchCategory(Integer categoryId, Boolean watch, Integer individualId) {
        MidIndividualCategory midIndividualCategory = new MidIndividualCategory();
        midIndividualCategory.setCategoryId(categoryId);
        midIndividualCategory.setStatus(watch ? StatusEnum.VALID.getCode() : StatusEnum.INVALID.getCode());
        midIndividualCategory.setIndividualId(individualId);
        midIndividualCategory.setCreatorId(individualId);
        midIndividualCategoryExtendMapper.upsert(midIndividualCategory);
        return new Response<>();
    }
}
