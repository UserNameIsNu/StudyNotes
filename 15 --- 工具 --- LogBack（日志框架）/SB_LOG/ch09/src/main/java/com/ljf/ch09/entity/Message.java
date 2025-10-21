package com.ljf.ch09.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Message {
    private String uName;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private Integer onlineNum = null;

    public Message(String uName, String content, Date date) {
        this.uName = uName;
        this.content = content;
        this.date = date;
    }

    public Message(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }
}
