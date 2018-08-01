package com.li.blog.controller;

import com.li.blog.domain.User;
import com.li.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * @author licheng
 * @description
 * @create 2018/7/31 20:49
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 查询所有用户
     * @param model
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping
    public ModelAndView list(Model model){
        model.addAttribute("userList", userRepository.findAll());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("users/list","userModel",model);
    }
    /**
     * 根据id查询用户
     * @param id
     * @param model
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model){
        Optional<User> userOptional = userRepository.findById(id);
        model.addAttribute("user", userOptional.orElseGet(null));
        model.addAttribute("title", "查看用户");
        return new ModelAndView("users/view","userModel",model);
    }
    /**
     * 获取创建表单页面
     * @param model
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping("/form")
    public ModelAndView createForm(Model model){
        model.addAttribute("user", new User(null,null,null));
        model.addAttribute("title", "创建用户");
        return new ModelAndView("users/form","userModel",model);
    }
    /**
     * 保存或修改用户
     * @param user
     * @return org.springframework.web.servlet.ModelAndView
     */
    @PostMapping
    public ModelAndView saveOrUpdateUser(User user){
        userRepository.save(user);
        return new ModelAndView("redirect:/users");
    }
    /**
     * 删除用户
     * @param id
     * @return
     */
    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, Model model) {
        userRepository.deleteById(id);
        model.addAttribute("userList", userRepository.findAll());
        model.addAttribute("title", "删除用户");
        return new ModelAndView("users/list", "userModel", model);
    }

    /**
     * 获取要修改的用户信息 跳转到form页面
     * @param id
     * @param model
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Optional<User> userOptional = userRepository.findById(id);

        model.addAttribute("user", userOptional.orElseGet(null));
        model.addAttribute("title", "修改用户");
        return new ModelAndView("users/form", "userModel", model);
    }
}
