package com.jidian.demo.controller;

import com.jidian.demo.DTO.JidiancDto;
import com.jidian.demo.DTO.MassageDto;
import com.jidian.demo.DTO.UserInfoDto;
import com.jidian.demo.exception.http.NotFoundException;
import com.jidian.demo.model.*;
import com.jidian.demo.service.JidiancService;
import com.jidian.demo.vo.OptionVo;
import com.jidian.demo.vo.PagingVo;
import com.jidian.demo.vo.ResponseVo;
import com.jidian.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/c")
public class JidiancController {
    @Autowired
    private JidiancService jidiancService;

    //厂家注册
    @PostMapping("/register")
    public OptionVo register(@RequestBody JidiancDto jidiancDto){
        this.jidiancService.register(jidiancDto);
        return new OptionVo("注册成功",200);
    }
    //厂家登录
    @PostMapping("/login")
    public ResponseVo login(@RequestBody UserInfoDto userInfoDto){
        return new ResponseVo("登录成功",jidiancService.login(userInfoDto.getUsername(),
                userInfoDto.getPassword()),200);
    }
//    @RequestMapping("/login")
//    public Result login(@RequestBody UserInfoDto userInfoDto) {
//        Result result=new Result();
//        result.setData(jidiancService.login(userInfoDto.getUsername(),userInfoDto.getPassword()));
//        result.setMessage("登录成功");
//        result.setSuccess(true);
//        Map<String,String> map=new HashMap<>();
//        map.put("content", result.toString());
//        return Result.ok(map);
//    }
    //厂家对招标信息进行留言
    @PostMapping("/add/massage")
    public OptionVo addMassage(@RequestBody MassageDto massageDto){
        this.jidiancService.addMassage(massageDto);
        return new OptionVo("留言成功",200);
    }
    //获取招标信息
    @GetMapping("/biddingInfo")
    public ResponseVo getBiddingInfo(){
        return new ResponseVo("获取成功",jidiancService.detailBidingInfo(),200);
    }
    //查看设备信息
    @GetMapping("/getList/devices")
    public ResponseVo getDeveices(@RequestParam(name = "page", defaultValue = "0")Integer page,
                                  @RequestParam(name = "size", defaultValue = "10")Integer size){
        Page page1 = jidiancService.getDevices(page, size);
        PagingVo<Device> pagingVo = new PagingVo<>(page1);
        return new ResponseVo("获取成功", pagingVo, 200);
    }
    //分页获取招标信息列表
    @GetMapping("/getList/BiddingInfo")
    public ResponseVo getBiddingInfo(@RequestParam(name = "page", defaultValue = "0")Integer page,
                              @RequestParam(name = "size", defaultValue = "10")Integer size) {
        //获取刚刚在service获取的分页对象
        Page page1 = jidiancService.getBidding(page, size);
        //统一分页格式
        PagingVo<BiddingInfo> pagingVo = new PagingVo<>(page1);
        //返回ResponseVo 类格式的对象，传入msg,data(pagingVo),code
        return new ResponseVo("返回成功",pagingVo,200);
    }
    //传入招标信息表单id，查看招标信息下的留言列表
    @GetMapping("/getMessage")
    public ResponseVo getMessageList(@RequestParam(name = "bgid", defaultValue = "1")Long bgid){
        return new ResponseVo("返回成功", jidiancService.getMessageInfo(bgid),200);
    }

    //通过留言表id获取留言信息
    @GetMapping("/getMessageInfo")
    public ResponseVo getMessageInfo(@RequestParam Long id){
        return new ResponseVo("返回成功",this.jidiancService.getMessageInfo(id),200);
    }
    //分页查看投标信息列表
    @GetMapping("/getlist/TenderingInfo")
    public ResponseVo getTenderInfo(@RequestParam(name = "page", defaultValue = "0")Integer page,
                                    @RequestParam(name = "size", defaultValue = "10")Integer size){
        Page page3 = jidiancService.getTenderingInfo(page, size);
        PagingVo<TenderingInfo> pagingVo = new PagingVo<>(page3);
        return new ResponseVo("返回成功", pagingVo,200);
    }
}
