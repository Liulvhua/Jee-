package com.jidian.demo.repository;

import com.jidian.demo.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {

//    Page<Message> findAllByBgid(Long bgid, Pageable pageable);
//    Message findAllByBgid(Long bgid);
    //通过id获取留言
    Message findOneById(Long id);
    //通过id删除留言信息
    void deleteOneById(Long id);
    //通过厂家id获取留言
    Message findAllByCid(Long cid);
}
