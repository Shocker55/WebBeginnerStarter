package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.entity.Inquiry;
import org.springframework.stereotype.Repository;

@Repository
public class InquiryDaoImpl implements InquiryDao {

    // 変数の初期化
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InquiryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertInquiry(Inquiry inquiry) {
        jdbcTemplate.update("INSERT INTO inquiry(name, email, contents, created) VALUES(?, ?, ?, ?)",
                inquiry.getName(), inquiry.getEmail(), inquiry.getContents(), inquiry.getCreated());
    }

    // DAO はデータベースから値を取得するだけではなく
    // Entityに値を詰めるという役割を担っている
    @Override
    public List<Inquiry> getAll() {
        String sql = "SELECT id, name, email, contents, created FROM inquiry";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        List<Inquiry> list = new ArrayList<Inquiry>();
        // Mapの中身をlistに入れる
        for (Map<String, Object> result : resultList) {
            // inquiryエンティティをインスタンス化
            // resultはObject型が格納されているので、キャストで方変換(int)等
            Inquiry inquiry = new Inquiry();
            inquiry.setId((int)result.get("id"));
            inquiry.setName((String)result.get("name"));
            inquiry.setEmail((String)result.get("Email"));
            inquiry.setContents((String)result.get("Contents"));
            inquiry.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
            list.add(inquiry);
        }
        return list;
    }

}
