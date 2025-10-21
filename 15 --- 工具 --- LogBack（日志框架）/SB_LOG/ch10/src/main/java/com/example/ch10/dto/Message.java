package com.example.ch10.dto;

import java.util.Date;

// 消息对象
public class Message {
    private String content;
    private Date sendDate;

    public Message() {
    }

    public Message(String content, Date sendDate) {
        this.content = content;
        this.sendDate = sendDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
