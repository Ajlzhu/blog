package com.li.blog.repository;

import com.li.blog.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author licheng
 * @description User Respository接口
 * @create 2018/7/31 20:34
 */
public interface UserRepository extends CrudRepository<User,Long> {

}
