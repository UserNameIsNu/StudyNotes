package com.example.ch13.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 文档映射，映射Users索引中的属性
 */
@Document(indexName = "users", createIndex = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    private String id;

    @Field(type = FieldType.Keyword, index = false)
    private String name;

    @Field(type = FieldType.Integer, index = false)
    private Integer age;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String birthplace;
}
