package com.jidian.demo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagingVo<T> {
    private Long total;
    private Integer size;
    private Integer page;
    private Integer total_page;
    //使用泛型
    private List<T> items;

    //构造方法
    public PagingVo(Page<T> pageT){
        //传入service层获取到的page对象 初始化参数
        this.initPageParamters(pageT);
        //items查出的分页记录
        this.items = pageT.getContent();
    }
    void initPageParamters(Page<T> pageT) {
        this.total = pageT.getTotalElements();
        this.size = pageT.getSize();
        this.page = pageT.getNumber();
        this.total_page = pageT.getTotalPages();
    }
}
