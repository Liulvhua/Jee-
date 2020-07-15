package com.jidian.demo.controller;

import com.jidian.demo.DTO.BidUnitDto;
import com.jidian.demo.DTO.BiddingInfoDto;
import com.jidian.demo.DTO.TenderInfoDto;
import com.jidian.demo.DTO.UserInfoDto;
import com.jidian.demo.model.BidUnit;
import com.jidian.demo.model.JidianCtory;
import com.jidian.demo.service.BidUnitService;
import com.jidian.demo.service.JidiancService;
import com.jidian.demo.vo.OptionVo;
import com.jidian.demo.vo.PagingVo;
import com.jidian.demo.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bidUnit")
public class BidUnitController {
    @Autowired
    private BidUnitService bidUnitService;
    //招标单位登录
    @PostMapping("/login")
    public ResponseVo login(@RequestBody UserInfoDto userInfoDto){
        return new ResponseVo("登录成功",bidUnitService.login(userInfoDto.getUsername(),
                userInfoDto.getPassword()),200);
    }
    //招标单位注册
    @PostMapping("/register")
    public OptionVo register(@RequestBody BidUnitDto bidUnitDto){
        this.bidUnitService.register(bidUnitDto);
       return new OptionVo("注册成功",200);
    }
    @GetMapping("/getBidUnit")
    //分页获取招标单位信息
    public ResponseVo getAll(@RequestParam(name = "page",defaultValue = "0") Integer page,
                             @RequestParam(name = "size",defaultValue = "20") Integer size){

        PagingVo<BidUnit> result = new PagingVo<>(bidUnitService.getList(page, size));
        return new ResponseVo("获取成功",result,200);
    }
    @Autowired
    private JidiancService jidiancService;
    //分页查询厂家信息列表
    @GetMapping("getList/c")
    public ResponseVo getListc(@RequestParam(name = "page", defaultValue = "0")Integer page,
                                @RequestParam(name = "size", defaultValue = "10")Integer size){
        Page page1 = jidiancService.getListc(page, size);
        PagingVo<JidianCtory> pagingVo = new PagingVo<>(page1);
        return new ResponseVo("查询成功", pagingVo, 200);
    }

    //发布招标信息
    @PostMapping("/post")
    public OptionVo postInfo(@RequestBody BiddingInfoDto biddingInfoDto){
        this.bidUnitService.insert(biddingInfoDto);
        return new OptionVo("已发布", 200);
    }
    //更新招标信息
    @PostMapping("/updateBiddingInfo")
    public OptionVo updateBiddingInfo(@RequestBody BiddingInfoDto biddingInfoDto){
        this.bidUnitService.update(biddingInfoDto);
        return new OptionVo("更新成功",200);
    }
    //分页查看
    //投标信息发布
    @PostMapping("/post/TenderInfo")
    public OptionVo postTenderInfo(@RequestBody TenderInfoDto tenderInfoDto){
        this.bidUnitService.postTenderInfo(tenderInfoDto);
        return new OptionVo("已发布",200);
    }
    //获取投标信息
    @GetMapping("/getDetailTendering")
    public ResponseVo getDetailTendering(){
        return new ResponseVo("获取成功", bidUnitService.detailTenderInfo(), 200);
    }
}
