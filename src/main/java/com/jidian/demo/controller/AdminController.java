package com.jidian.demo.controller;

import com.jidian.demo.DTO.*;
import com.jidian.demo.model.BidUnit;
import com.jidian.demo.model.Message;
import com.jidian.demo.service.AdminService;
import com.jidian.demo.service.BidUnitService;
import com.jidian.demo.vo.OptionVo;
import com.jidian.demo.vo.PagingVo;
import com.jidian.demo.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private BidUnitService bidUnitService;

    //管理员登录
    @RequestMapping("/login")
    public ResponseVo login(@RequestBody UserInfoDto userInfoDto){
        return new ResponseVo("登录成功",adminService.login(userInfoDto.getUsername(), userInfoDto.getPassword()),200);
    }

    // 获取角色信息
    @GetMapping("/getUserInfo")
    public ResponseVo getUserInfo(@RequestParam String token, @RequestParam Long uid){
        return new ResponseVo("获取成功", adminService.getUserInfo(token, uid), 200);
    }

    //获取首页数据
    @GetMapping("/getHomePage")
    public ResponseVo getHomePage(){
        return new ResponseVo("获取成功", adminService.getHomePage(), 200);
    }
    //修改厂家信息
    @PostMapping("/updateC")
    public OptionVo updateC(@RequestBody JidiancDto jidiancDto){
        this.adminService.updateC(jidiancDto);
        return new OptionVo("修改成功",200);
    }
    //删除厂家信息
    @PostMapping("/deleteC")
    public OptionVo deleteC(@RequestParam Long id){
        this.adminService.deleteC(id);
        return new OptionVo("已删除",200);
    }
    //分页获取招标单位信息列表
    @GetMapping("/getList/BidUnit")
    public ResponseVo getList(@RequestParam(name = "page", defaultValue = "0")Integer page,
                              @RequestParam(name = "size", defaultValue = "10")Integer size){
        Page page1 = bidUnitService.getList(page, size);
        PagingVo<BidUnit> pagingVo = new PagingVo<>(page1);
        return new ResponseVo("返回成功", pagingVo, 200);
    }
    //修改招标单位信息
    @PostMapping("/update/BidUnit")
    public OptionVo updateBidUnit(@RequestBody BidUnitDto bidUnitDto){
        this.adminService.updateBidUnit(bidUnitDto);
        return new OptionVo("修改成功",200);
    }
    //删除招标单位信息
    @PostMapping("/delete/bidUnit")
    public OptionVo deleteBidUnit(@RequestParam Long id){
        this.adminService.deleteBidUnit(id);
        return new OptionVo("删除成功",200);
    }
    //分页查看留言
    @GetMapping("/getList/message")
    public ResponseVo getMessage(@RequestParam(name = "page", defaultValue = "0")Integer page,
                                @RequestParam(name = "size", defaultValue = "10")Integer size){
        Page page2 = adminService.getMessage(page, size);
        PagingVo<Message> pagingVo = new PagingVo<>(page2);
        return new ResponseVo("返回成功", pagingVo,200);
    }
    //通过id修改单条留言记录
    @PostMapping("/update/message")
    public OptionVo updateMessage(@RequestBody MassageDto massageDto){
        this.adminService.updateMessage(massageDto);
        return new OptionVo("修改成功",200);
    }
    //删除留言
    @PostMapping("/delete/message")
    public Map<String, Object> deleteMessage(@RequestParam Long id){
        this.adminService.deleteMessage(id);
        Map<String, Object> data = new HashMap<>();
        data.put("msg","删除成功");
        data.put("code",200);
        return data;
    }

}
