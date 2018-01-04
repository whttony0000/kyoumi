package com.aikon.wht.controller.individual;

import com.aikon.wht.annotations.IndividualInfo;
import com.aikon.wht.controller.BaseController;
import com.aikon.wht.entity.Individual;
import com.aikon.wht.exception.InactiveException;
import com.aikon.wht.exception.InvalidInputException;
import com.aikon.wht.model.ArticleModel;
import com.aikon.wht.model.IndividualDetail;
import com.aikon.wht.model.Page;
import com.aikon.wht.model.Response;
import com.aikon.wht.service.ArticleService;
import com.aikon.wht.service.IndividualService;
import com.aikon.wht.utils.IdEncrypter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人controller.
 *
 * @author haitao.wang
 */
@Controller
public class IndividualController extends BaseController{

    @Autowired
    IndividualService individualService;

    @Autowired
    ArticleService articleService;

    /**
     * 个人详情页.
     *
     * @param modelMap
     * @param individualEid
     * @param currentIndividual
     * @return String
     */
    @RequestMapping("individualDetail")
    String individualDetail(ModelMap modelMap,String individualEid,@IndividualInfo Individual currentIndividual) {
        Integer individualId = IdEncrypter.decodeId(individualEid);
        modelMap.addAttribute("isSelf", individualId.equals(currentIndividual.getId()) ? 1 : 0);
        Individual individual = individualService.getIndividualById(individualId);
        modelMap.addAttribute("individualEid", individualEid);
        modelMap.addAttribute("mailCode", individual.getMailMd5Hash());
        modelMap.addAttribute("inner_page", "individualDetail.ftl");
        return "template";
    }

    /**
     * 个人信息编辑页.
     *
     * @param modelMap
     * @param individualId
     * @return String
     * @throws InactiveException
     */
    @RequestMapping("editIndividual")
    String editIndividual(ModelMap modelMap, @IndividualInfo Integer individualId) throws InactiveException {
        super.isTmpUser();
        String individualEid = IdEncrypter.encodeId(individualId);
        modelMap.addAttribute("individualEid", individualEid);
        modelMap.addAttribute("inner_page", "editIndividual.ftl");
        return "template";
    }

    /**
     * 保存个人信息操作.
     *
     * @param individual
     * @param individualId
     * @return Response
     * @throws InactiveException
     * @throws InvalidInputException
     */
    @RequestMapping("/saveIndividual")
    @ResponseBody
    Response<Individual> saveIndividual(@RequestBody Individual individual, @IndividualInfo Integer individualId) throws InactiveException, InvalidInputException {
        super.isTmpUser();
        super.checkInput(individual, Lists.newArrayList("name","description"));
        if (individual == null || individualId == null) {
            return new Response(-1, "保存失败");
        }
        individual.setId(individualId);
        return individualService.saveIndividual(individual);
    }

    /**
     * 个人详情数据.
     *
     * @param individualEid
     * @return Response
     * @throws InactiveException
     */
    @RequestMapping("/getIndividualDetail")
    @ResponseBody
    Response<IndividualDetail> getIndividualDetail(String individualEid) throws InactiveException {
        super.isTmpUser();
        Integer individualId = IdEncrypter.decodeId(individualEid);
        return individualService.getIndividualDetail(individualId);
    }

    /**
     * 关注个人操作.
     *
     * @param individualEid
     * @param onWatch
     * @param watcherId
     * @return Response
     * @throws InactiveException
     */
    @RequestMapping("/watchIndividual")
    @ResponseBody
    Response<Object> watchIndividual(String individualEid,Boolean onWatch,@IndividualInfo Integer watcherId) throws InactiveException {
        super.isTmpUser();
        Integer watchedId = IdEncrypter.decodeId(individualEid);
        return individualService.watchIndividual(watchedId, watcherId, onWatch);

    }

    /**
     * 关注个人列表.
     *
     * @param watcherEid
     * @param currentPage
     * @param pageSize
     * @return Response
     */
    @RequestMapping("/getOnWatchIndividualList")
    @ResponseBody
    Response<Page<IndividualDetail>> getOnWatchIndividualList(String watcherEid, Integer currentPage, Integer pageSize) {
        Integer watcherId = IdEncrypter.decodeId(watcherEid);
        return new Response<>(new Page<>(individualService.getOnWatchIndividualList(watcherId, currentPage, pageSize), individualService.getOnWatchIndividualCnt(watcherId)));
    }

    /**
     * 粉丝列表.
     *
     * @param watchedEid
     * @param currentPage
     * @param pageSize
     * @return Response
     */
    @RequestMapping("/getFanList")
    @ResponseBody
    Response<Page<IndividualDetail>> getFanList(String watchedEid, Integer currentPage, Integer pageSize) {
        Integer watchedId = IdEncrypter.decodeId(watchedEid);
        return new Response<>(new Page<>(individualService.getFanList(watchedId,currentPage,pageSize),individualService.getFanCnt(watchedId)));
    }


    /**
     * 个人收藏列表.
     *
     * @param individualEid
     * @param currentPage
     * @param pageSize
     * @return Response
     */
    @RequestMapping("/getIndividualBookmarkList")
    @ResponseBody
    Response<Page<ArticleModel>> getIndividualBookmarkList(String individualEid,Integer currentPage,Integer pageSize) {
        Integer individualId = IdEncrypter.decodeId(individualEid);
        return new Response<>(new Page<>(this.articleService.getIndividualBookmarkList(individualId,currentPage,pageSize),this.articleService.getIndividualBookmarkCnt(individualId)));
    }
}
