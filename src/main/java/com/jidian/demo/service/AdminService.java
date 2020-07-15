package com.jidian.demo.service;

import com.jidian.demo.DTO.BidUnitDto;
import com.jidian.demo.DTO.JidiancDto;
import com.jidian.demo.DTO.MassageDto;
import com.jidian.demo.exception.http.NotFoundException;
import com.jidian.demo.exception.http.PasswordException;
import com.jidian.demo.model.*;
import com.jidian.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JidiancRepository jidiancRepository;
    @Autowired
    private BidingUnitRepository bidingUnitRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private BiddingRepository biddingRepository;

    // 统一角色登录
    public Map<String, Object> login(String username, String password){
        Long result = adminRepository.countByUsernameAndPassword(username, password);
        Long result1 = jidiancRepository.countByUsernameAndPassword(username, password);
        Long result2 = bidingUnitRepository.countByUsernameAndPassword(username, password);
        if(result == 1){
            Admin admin = adminRepository.findOneByUsername(username);
            Map<String, Object> data = new HashMap<>();
            // 暂时不使用jwt token
            data.put("token","admin-token");
            data.put("userInfo", admin);
            return data;
        }
        else if(result1 == 1){
            JidianCtory jidianCtory = jidiancRepository.findOneByUsername(username);
            Map<String, Object> data = new HashMap<>();
            data.put("token","ctory-token");
            data.put("userInfo", jidianCtory);
            return data;
        }else if(result2 == 1){
            BidUnit bidUnit = bidingUnitRepository.findOneByUsername(username);
            Map<String, Object> data = new HashMap<>();
            data.put("token","bidUnit-token");
            data.put("userInfo", bidUnit);
            return data;
        }else{
            throw new PasswordException(10001);
        }
    }

    // 通过token获取角色信息 uid-唯一标识 生产厂家id、招标单位id、管理员id

    public Map<String, Object> getUserInfo(String token,Long uid ){
        Map<String, Object> data = new HashMap<>();
        if("ctory-token".equals(token)){
            // 设备生产厂家
            JidianCtory result = jidiancRepository.findOneById(uid);
            if(result == null){
                throw new NotFoundException(10002);
            }else{
                List<String>role = new ArrayList<>();
                role.add("ctory");
                data.put("roles",role);
                data.put("userInfo",result);
            }

        }else if ("bidUnit-token".equals(token)){
            // 招标单位
            BidUnit result2 = bidingUnitRepository.findOneById(uid);
            if(result2 == null){
                throw  new NotFoundException(10002);
            }else{
                List<String>role = new ArrayList<>();
                role.add("bidUnit");
                data.put("roles",role);
                data.put("userInfo",result2);
            }

        }else if("admin-token".equals(token)){
            //管理员
            Admin admin = adminRepository.findOneById(uid);
            if(admin == null){
                throw new NotFoundException(10002);
            }else{
                List<String>role = new ArrayList<>();
                role.add("admin");
                data.put("roles",role);
                data.put("userInfo",admin);
            }
        }
        return data;
    }


    // 获取首页数据

    public List<Long> getHomePage(){
        // 3个值分别代表 系统厂家用户总数 系统招标单位总数 系统超级管理员总数
        List<Long> items = new ArrayList<>();
        Long CtoryCount = jidiancRepository.count();
        Long bidUnitCount = bidingUnitRepository.count();
        Long adminCount = adminRepository.count();
        items.add(CtoryCount);
        items.add(bidUnitCount);
        items.add(adminCount);
        return items;

    }


    //通过id修改厂家信息
    public void updateC(JidiancDto jidiancDto){
        // 查询数据库是否已经拥有该用户
        JidianCtory jidianCtory = this.jidiancRepository.findOneById(jidiancDto.getId());
        if(jidianCtory == null){
            // 不存在 抛出异常
            throw new NotFoundException(10004);
        }else {
            JidianCtory jidianCtory1 = JidianCtory.builder().id(jidiancDto.getId()).username(jidiancDto.getUsername())
                    .password(jidiancDto.getPassword()).address(jidiancDto.getAddress())
                    .borndate(jidianCtory.getBorndate()).build();
            this.jidiancRepository.save(jidianCtory1);
        }
    }

    //通过id删除厂家信息
    @Transactional
    public void deleteC(Long id){
        JidianCtory jidianCtory2 = this.jidiancRepository.findOneById(id);
        Message message = this.messageRepository.findAllByCid(id);
        if(jidianCtory2 != null){
            jidiancRepository.deleteOneById(id);
            //同时删除厂家留言
            messageRepository.deleteOneById(message.getId());
        }else
            throw new NotFoundException(10004);
    }
    //通过id修改单个招标单位信息
    public void updateBidUnit(BidUnitDto bidUnitDto){
        BidUnit bidUnit = this.bidingUnitRepository.findOneById(bidUnitDto.getId());
        if(bidUnit == null){
            throw new NotFoundException(10004);
        }else {
            BidUnit bidUnit1 = BidUnit.builder().id(bidUnitDto.getId()).username(bidUnitDto.getUsername())
                              .password(bidUnitDto.getPassword()).build();
            this.bidingUnitRepository.save(bidUnit1);
        }
    }

    //通过id删除招标单位信息
    @Transactional
    public void deleteBidUnit(Long id){
        BidUnit bidUnit = this.bidingUnitRepository.findOneById(id);
        BiddingInfo biddingInfo = this.biddingRepository.findAllByBuid(id);
        if(bidUnit == null){
            throw new NotFoundException(10004);
        }else {
            this.bidingUnitRepository.deleteOneById(id);
            this.biddingRepository.deleteOneById(biddingInfo.getId());
        }
    }

    //分页查看留言表留言信息
    public Page<Message> getMessage(Integer page, Integer size){
        Pageable pageable1 = PageRequest.of(page, size);
        return this.messageRepository.findAll(pageable1);
    }
    //通过id修改单条留言记录
    public void updateMessage(MassageDto massageDto){
        Message message1 = this.messageRepository.findOneById(massageDto.getId());
        if(message1 != null){
            // 定义留言时间 使用系统所在时区，由当前的 Unix 时间构建
            LocalDate localDate = LocalDate.now();
            Message message = Message.builder().id(massageDto.getId()).cid(massageDto.getCid())
                    .content(massageDto.getContent()).bgid(massageDto.getBgid()).date(Date.valueOf(localDate)).build();
            this.messageRepository.save(message);
        }else {
            throw new NotFoundException(10004);
        }
    }

    //通过id删除留言

    @Transactional
    public void deleteMessage(Long id){
        Message message = this.messageRepository.findOneById(id);
        if(message == null){
            throw new NotFoundException(10004);
        }
        this.messageRepository.deleteOneById(message.getId());
    }
}
