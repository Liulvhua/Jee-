package com.jidian.demo.service;

import com.jidian.demo.DTO.BidUnitDto;
import com.jidian.demo.DTO.BiddingInfoDto;
import com.jidian.demo.DTO.TenderInfoDto;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BidUnitService {
    @Autowired
    private BidingUnitRepository bidingUnitRepository;
    @Autowired
    private BiddingRepository biddingRepository;
    @Autowired
    private TenderingInfoRepository tenderingInfoRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private TenderUnitRepository tenderUnitRepository;
    @Autowired
    private JidiancRepository jidiancRepository;
    //招标单位登录
    public Map<String, Object> login(String username, String password){
        //判断用户是否存在
        BidUnit result = bidingUnitRepository.findOneByUsername(username);
        if(result != null) {
            //校验密码
            if (result.getPassword().equals(password)) {
                Map<String, Object> data = new HashMap<>();
                data.put("token", "bidUnit-token");
                data.put("userInfo", result);
                return  data;
            }else {
                throw new PasswordException(10001);
            }
        }else
            throw new NotFoundException(10002);
    }
    //招标单位注册
    public void register(BidUnitDto bidUnitDto){
        BidUnit bidUnit = bidingUnitRepository.findOneByUsernameAndPassword(bidUnitDto.getUsername(),
                bidUnitDto.getPassword());
        if(bidUnit != null){
            // 已存在 抛出异常
            throw new AllExistedException(10003);
        }else {
            BidUnit bidUnit1 = BidUnit.builder().username(bidUnitDto.getUsername())
                    .password(bidUnitDto.getPassword()).build();
            this.bidingUnitRepository.save(bidUnit1);
        }
    }
    //分页获取招标单位信息
    public Page<BidUnit> getList(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return this.bidingUnitRepository.findAll(pageable);
    }
    //添加招标信息
    public void insert(BiddingInfoDto biddingInfoDto) {
        //判断招标单位是否已对设备发布招标信息
        BiddingInfo biddingInfo = this.biddingRepository.findOneByDidAndBuid(biddingInfoDto.getDid()
                , biddingInfoDto.getBuid());
        if (biddingInfo != null) {
            //招标信息重复
            throw new AllExistedException(10003);
        } else {
            BiddingInfo biddingInfo1 = BiddingInfo.builder().did(biddingInfoDto.getDid())
                    .buid(biddingInfoDto.getBuid()).amount(biddingInfoDto.getAmount())
                    .deadline(biddingInfoDto.getDeadline())
                    .deposit(biddingInfoDto.getDeposit()).build();
            this.biddingRepository.save(biddingInfo1);
        }
    }

    //更新招标信息
    public void update(BiddingInfoDto biddingInfoDto){
        BiddingInfo biddingInfo = this.biddingRepository.findOneById(biddingInfoDto.getId());
        if(biddingInfo != null){
            BiddingInfo biddingInfo1 = BiddingInfo.builder().id(biddingInfoDto.getId())
                    .buid(biddingInfoDto.getBuid()).amount(biddingInfoDto.getAmount())
                    .deadline(biddingInfoDto.getDeadline()).deposit(biddingInfoDto.getDeposit()).build();
            this.biddingRepository.save(biddingInfo1);
        }else {
            throw new NotFoundException(10004);
        }
    }


    //发布投标信息
    public void postTenderInfo(TenderInfoDto tenderInfoDto){
        TenderingInfo tenderingInfo = this.tenderingInfoRepository.findOneByTuidAndAndCid(tenderInfoDto.getTuid()
                , tenderInfoDto.getCid());
        if(tenderingInfo != null){
            throw new AllExistedException(10003);
        }
        TenderingInfo tenderingInfo1 = TenderingInfo.builder().date(Date.valueOf(LocalDate.now())).tuid(tenderInfoDto.getTuid())
                .bgid(tenderInfoDto.getBgid()).cid(tenderInfoDto.getCid()).did(tenderInfoDto.getDid())
                .money(tenderInfoDto.getMoney()).paytime(Timestamp.valueOf(LocalDateTime.now())).build();
        this.tenderingInfoRepository.save(tenderingInfo1);
    }
    //获取投标信息表单详情
    public Map<String, Object> detailTenderInfo() {
        List<Object> detail = new ArrayList<>();
        for(TenderingInfo item:tenderingInfoRepository.findAll()){
            Map<String, Object> data = new HashMap<>();
            Tenderunit tenderunit = tenderUnitRepository.findOneById(item.getTuid());
            BiddingInfo biddingInfo = biddingRepository.findOneById(item.getBgid());
            JidianCtory jidianCtory = jidiancRepository.findOneById(item.getCid());
            Device device = deviceRepository.findOneById(item.getDid());
            data.put("date", item.getDate());
            data.put("name", tenderunit.getName());
            data.put("corporation", tenderunit.getCorporationname());
            data.put("phone", tenderunit.getPhone());
            data.put("bgId", biddingInfo.getId());
            data.put("manufacture", jidianCtory.getUsername());
            data.put("proDate", device.getProddate());
            data.put("shelfLife", device.getShelflife());
            data.put("money", item.getMoney());
            data.put("payTime", item.getPaytime());
            detail.add(data);

        }
        Map<String, Object> items = new HashMap<>();
        items.put("tenderingInfo", detail);
        return items;
    }
}
