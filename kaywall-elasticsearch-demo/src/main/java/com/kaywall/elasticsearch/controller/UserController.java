package com.kaywall.elasticsearch.controller;

import com.kaywall.elasticsearch.dao.UserDao;
import com.kaywall.elasticsearch.dto.UserDTO;
import com.kaywall.elasticsearch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserDao userDao;

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAge(dto.getAge());
        return String.valueOf(userDao.save(user).getId());// 返回id做验证
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(Integer id) {
        userDao.deleteById(id);
        return "Success!";
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestBody UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAge(dto.getAge());
        return String.valueOf(userDao.save(user).getId());// 返回id做验证
    }

    @GetMapping("/getUser")
    public User getUser(Integer id) {
        return userDao.findById(id).get();
    }

    @GetMapping("/getAllUsers")
    public Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

}
