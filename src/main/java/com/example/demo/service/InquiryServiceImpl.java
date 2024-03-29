package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Inquiry;
import com.example.demo.repository.InquiryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquiryServiceImpl implements InquiryService {

    private final InquiryDao dao;

    @Autowired InquiryServiceImpl(InquiryDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Inquiry inquiry) {
        dao.insertInquiry(inquiry);
    }

    @Override
    public List<Inquiry> getAll() {
        return dao.getAll();
    }
    @Override
    public void update(Inquiry inquiry) {
        if(dao.updateInquiry(inquiry) == 0) {
            // 例外捕捉用に作ったクラスをインスタンス化
            throw new InquiryNotFoundException("can't find the same ID");
        }
    }

}
