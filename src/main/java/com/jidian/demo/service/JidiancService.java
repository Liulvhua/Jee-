package com.jidian.demo.service;

import com.jidian.demo.DTO.JidiancDto;
import com.jidian.demo.DTO.MassageDto;
import com.jidian.demo.exception.http.AllExistedException;
import com.jidian.demo.exception.http.NotFoundException;
import com.jidian.demo.exception.http.PasswordException;
import com.jidian.demo.model.*;
import com.jidian.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JidiancService {
    @Autowired
    private JidiancRepository jidiancRepository;
    @Autowired
    private BiddingRepository biddingRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TenderingInfoRepository tenderingInfoRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    //厂家登录
    public Map<String, Object> login(String name,String password){
        //判断用户是否存在
        JidianCtory result = jidiancRepository.findOneByUsername(name);
        if(result != null) {
            //校验密码
            if (result.getPassword().equals(password)) {
                Map<String, Object> data = new HashMap<>();
//                data.put("token", "ctory-token");
                data.put("userInfo", result);
                return  data;

            }else {
                throw new PasswordException(10001);
            }
        }else
            throw new NotFoundException(10002);
    }

    //厂家注册
    public void register(JidiancDto jidiancDto){
        // 查询数据库是否已经拥有该用户
        JidianCtory jidianCtory = this.jidiancRepository.findOneByUsername(jidiancDto.getUsername());
        if(jidianCtory != null){
            // 已存在 抛出异常
            throw new AllExistedException(10003);
        }else {
            JidianCtory jidianCtory1 = JidianCtory.builder().username(jidiancDto.getUsername())
                    .password(jidiancDto.getPassword()).address(jidiancDto.getAddress())
                    .borndate(jidiancDto.getBorndate()).build();
            this.jidiancRepository.save(jidianCtory1);
        }
    }
    //通过id获取厂家信息
    public JidianCtory getCtoryInfo(Long id){
        return jidiancRepository.findOneById(id);
    }
    //分页获取厂家信息列表
    public Page<JidianCtory> getListc(Integer page, Integer size){

        Pageable pageable = PageRequest.of(page, size);
        return this.jidiancRepository.findAll(pageable);
    }

    //通过招标信息表单id获取招标信息
//    public BiddingInfo getBiddingInfo(Long id){
//        BiddingInfo biddingInfo =  this.biddingRepository.findOneById(id);
//        return biddingInfo;
//    }
    //获取招标信息表单详情
    public Map<String, Object> detailBidingInfo() {
        List<Object> detail = new ArrayList<>();
        for(BiddingInfo item:biddingRepository.findAll()){
            Map<String, Object> data = new HashMap<>();
            Device device = deviceRepository.findOneById(item.getDid());
            data.put("id", item.getId());
            data.put("name", device.getName());
            data.put("type", device.getType());
            data.put("packrequire", device.getPackreqire());
            data.put("amount", item.getAmount());
            data.put("deadline", item.getDeadline());
            data.put("deposit", item.getDeposit());
            detail.add(data);
        }
        Map<String, Object> items = new HashMap<>();
        items.put("biddingInfo", detail);
        items.put("total", detail.size());
        return items;
    }

    //分页查询招标信息
    public Page<BiddingInfo> getBidding(Integer page, Integer size){
        //Jpa创建分页器
        Pageable pageable = PageRequest.of(page, size);
        //传入pageable分页器，相当于 select * from ..
        return this.biddingRepository.findAll(pageable);
    }

    //分页查看设备信息
    public Page<Device> getDevices(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return this.deviceRepository.findAll(pageable);
    }

    //添加留言
    public void addMassage(MassageDto massageDto){
        // 定义留言时间 使用系统所在时区，由当前的 Unix 时间构建
        LocalDate localDate = LocalDate.now();
        Message message = Message.builder().cid(massageDto.getCid()).content(massageDto.getContent())
                .bgid(massageDto.getBgid()).date(Date.valueOf(localDate)).build();
        this.messageRepository.save(message);
    }
    //传入招标信息id，获取招标信息下的留言信息
//    public Page<Message>getMessage(Integer page, Integer size,Long bgid){
//        Pageable pageable = PageRequest.of(page, size);
//        return this.messageRepository.findAllByBgid(bgid,pageable);
//    }
    //通过招标id获取留言信息
    public Map<String, Object> getMessageInfo(Long bgid) {
            BiddingInfo biddingInfo = biddingRepository.findOneById(bgid);
            if(biddingInfo == null){
                throw new NotFoundException(10002);
            }else{
                List<Object> messageList = new ArrayList<>();
                for(Message item:messageRepository.findAll()){
                    Map<String, Object> data = new HashMap<>();
                while (item.getId().equals(bgid)){
                    JidianCtory jidianCtory = jidiancRepository.findOneById(item.getCid());
                    data.put("id", item.getId());
                    data.put("bgId", bgid);
                    data.put("name", jidianCtory.getUsername());
                    data.put("content", item.getContent());
                    data.put("date", item.getDate());
                }
                messageList.add(data);
            }
                Map<String, Object> items = new HashMap<>();
                items.put("messageList", messageList);
                items.put("total", messageList.size());
                return items;
        }

    }
    //分页查看投标信息列表
    public Page<TenderingInfo> getTenderingInfo(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return this.tenderingInfoRepository.findAll(pageable);
    }

}
