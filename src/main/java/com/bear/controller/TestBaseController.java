package com.bear.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bear.entity.User;
import com.bear.entity.UserExample;
import com.bear.entity.UserExample.Criteria;
import com.bear.mapper.UserMapper;

/**
 * @Description: TODO
 * @author bear
 * @date 2017年5月14日上午12:53:21
 *
 */
@RestController
public class TestBaseController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/testbase")
    public @ResponseBody String testBase(@RequestBody String username) {

        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        // criteria.andUsernameEqualTo(username);
        criteria.andUseridEqualTo(1);

        List<User> userList = userMapper.selectByExample(example);
        if (!ObjectUtils.isEmpty(userList)) {
            return userList.get(0).getUserpassword();
        } else {
            return null;
        }
    }
}
