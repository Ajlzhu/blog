package com.li.blog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author licheng
 * @description User 实体
 * @create 2018/7/31 20:30
 */
@Entity
public class User {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增策略
    private Long id;
    private  String name;
    private String email;

    /**
     * 声明为protected 防止直接使用
     * @param
     * @return
     */
    protected User() {
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
