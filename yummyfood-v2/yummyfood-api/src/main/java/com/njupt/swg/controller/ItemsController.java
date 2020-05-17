package com.njupt.swg.controller;

import com.njupt.swg.pojo.Items;
import com.njupt.swg.pojo.ItemsImg;
import com.njupt.swg.pojo.ItemsParam;
import com.njupt.swg.pojo.ItemsSpec;
import com.njupt.swg.service.IItemService;
import com.njupt.swg.utils.CommonJsonResult;
import com.njupt.swg.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author swg.
 * @Date 2020/5/2 15:16
 * @CONTACT 317758022@qq.com
 * @DESC
 */
@Api(value = "商品相关接口",tags = "商品相关接口")
@RestController
@RequestMapping("/items")
@Slf4j
public class ItemsController {
    @Autowired
    private IItemService itemService;

    @ApiOperation(value = "根据商品ID获取商品详情",notes = "根据商品ID获取商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public CommonJsonResult info(
            @ApiParam(name = "itemId",value = "商品ID",required = true)
            @PathVariable String itemId){
        if(StringUtils.isBlank(itemId)){
            return CommonJsonResult.errorMsg("");
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);
        return CommonJsonResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查看商品评价等级",notes = "查看商品评价等级",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public CommonJsonResult commentLevel(
            @ApiParam(name = "itemId",value = "商品ID",required = true)
            @RequestParam String itemId){
        if(StringUtils.isBlank(itemId)){
            return CommonJsonResult.errorMsg("");
        }
        return CommonJsonResult.ok(itemService.queryCommentCounts(itemId));
    }

    @ApiOperation(value = "商品的评价分页列表",notes = "商品的评价分页列表",httpMethod = "GET")
    @GetMapping("/comments")
    public CommonJsonResult comments(
            @ApiParam(name = "itemId",value = "商品ID",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "评级的等级",required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page",value = "当前页",required = true)
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @ApiParam(name = "pageSize",value = "每页显示的数量",required = true)
            @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){
        if(StringUtils.isBlank(itemId)){
            return CommonJsonResult.errorMsg("");
        }
        return CommonJsonResult.ok(itemService.queryPagedComments(itemId,level,page,pageSize));
    }


    @ApiOperation(value = "商品搜索展示分页列表",notes = "商品搜索展示分页列表",httpMethod = "GET")
    @GetMapping("/search")
    public CommonJsonResult search(
            @ApiParam(name = "keywords",value = "搜索关键字",required = false)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "搜索的排序规则",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "当前页",required = true)
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @ApiParam(name = "pageSize",value = "每页显示的数量",required = true)
            @RequestParam(name = "pageSize",defaultValue = "20") Integer pageSize){
        if(StringUtils.isBlank(keywords)){
            return CommonJsonResult.errorMsg("");
        }
        return CommonJsonResult.ok(itemService.searchItems(keywords,sort,page,pageSize));
    }

    @ApiOperation(value = "根据三级分类获取商品列表",notes = "根据三级分类获取商品列表",httpMethod = "GET")
    @GetMapping("/catItems")
    public CommonJsonResult catItems(
            @ApiParam(name = "catId",value = "三级分类ID",required = false)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "搜索的排序规则",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "当前页",required = true)
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @ApiParam(name = "pageSize",value = "每页显示的数量",required = true)
            @RequestParam(name = "pageSize",defaultValue = "20") Integer pageSize){
        return CommonJsonResult.ok(itemService.searchCatItems(catId,sort,page,pageSize));
    }
}
