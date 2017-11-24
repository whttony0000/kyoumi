package com.aikon.wht.controller.category;

import com.aikon.wht.annotations.IndividualInfo;
import com.aikon.wht.controller.BaseController;
import com.aikon.wht.exception.InactiveException;
import com.aikon.wht.exception.NoPermissionException;
import com.aikon.wht.model.CategoryDetailModel;
import com.aikon.wht.model.Page;
import com.aikon.wht.model.Response;
import com.aikon.wht.service.CategoryService;
import com.aikon.wht.utils.IdEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author haitao.wang
 */
@Controller
public class CategoryController extends BaseController {

    @Autowired
    CategoryService categoryService;

    /**
     * 新增分类页.
     *
     * @param modelMap
     * @return String
     */
    @RequestMapping("/addCategory")
    String add(ModelMap modelMap) throws NoPermissionException {
        super.isAdmin();
        modelMap.addAttribute("inner_page", "editCategory.ftl");
        modelMap.addAttribute("isEdit", 0);
        modelMap.addAttribute("categoryId", 0L);
        return "template";
    }

    /**
     * 编辑分类页.
     *
     * @param modelMap
     * @param categoryId
     * @return String
     */
    @RequestMapping("/editCategory")
    String edit(ModelMap modelMap,Integer categoryId) {
        modelMap.addAttribute("inner_page", "editCategory.ftl");
        modelMap.addAttribute("isEdit", 1);
        modelMap.addAttribute("categoryId", categoryId);
        return "template";
    }

    /**
     * 分类详情页.
     *
     * @param modelMap
     * @param categoryEid
     * @return String
     */
    @RequestMapping("/categoryDetail")
    String categoryDetail(ModelMap modelMap, String categoryEid) {
        modelMap.addAttribute("inner_page", "categoryDetail.ftl");
        modelMap.addAttribute("categoryEid", categoryEid);
        return "template";
    }

    /**
     * 获取分类详情.
     *
     * @param categoryEid
     * @return Response
     */
    @RequestMapping("/getCategoryDetail")
    @ResponseBody
    Response<CategoryDetailModel> getCategoryDetail(String categoryEid,@IndividualInfo Integer individualId) {
        Integer categoryId = IdEncrypter.decodeId(categoryEid);
        CategoryDetailModel categoryDetailModel = categoryService.getCategoryDetail(categoryId,individualId);
        return new Response<>(categoryDetailModel);
    }

    /**
     * 获取分类列表.
     *
     * @return Response
     */
    @RequestMapping("/getCategoryListWithImage")
    @ResponseBody
    Response<List<CategoryDetailModel>> getCategoryListWithImage(@IndividualInfo Integer individualId) {
        List<CategoryDetailModel> categoryList = categoryService.getCategoryList(true,individualId);
        return new Response<>(categoryList);
    }

    /**
     * 获取分类列表.
     *
     * @return Response
     */
    @RequestMapping("/getCategoryList")
    @ResponseBody
    Response<List<CategoryDetailModel>> getCategoryList() {
        List<CategoryDetailModel> categoryList = categoryService.getCategoryList(false, null);
        return new Response<>(categoryList);
    }

    @RequestMapping("/upsertCategory")
    @ResponseBody
    Response upsertCategory(@RequestBody CategoryDetailModel categoryDetailModel, @IndividualInfo Integer individualId) throws NoPermissionException {
        super.isAdmin();
        Response response = categoryService.upsertCategory(categoryDetailModel,individualId);
        return response;
    }


    @RequestMapping("categoryList")
    String categoryList(ModelMap modelMap) {
        modelMap.addAttribute("inner_page", "categoryList.ftl");
        return "template";
    }

    @RequestMapping("/watchCategory")
    @ResponseBody
    Response<Object> watchCategory(String categoryEid,Boolean watch, @IndividualInfo Integer individualId) throws InactiveException {
        super.isTmpUser();
        Integer categoryId = IdEncrypter.decodeId(categoryEid);
        return categoryService.watchCategory(categoryId,watch, individualId);
    }

    @RequestMapping("/getOnWatchCategoryList")
    @ResponseBody
    Response<Page<CategoryDetailModel>> getOnWatchCategoryList(String watcherEid, Integer currentPage, Integer pageSize){
        Integer watcherId = IdEncrypter.decodeId(watcherEid);
        return new Response<>(new Page(categoryService.getOnWatchCategoryList(watcherId, currentPage, pageSize), categoryService.getOnWatchCategoryCnt(watcherId)));
    }

}
