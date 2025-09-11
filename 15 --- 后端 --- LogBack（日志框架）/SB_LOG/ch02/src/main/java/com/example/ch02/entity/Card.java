package com.example.ch02.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
//@Component
public class Card {
//    @Value("${student.card.cardNum}")
    private Integer cardNum;
}
