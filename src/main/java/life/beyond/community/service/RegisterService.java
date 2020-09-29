package life.beyond.community.service;

import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterService {

    @Autowired
    UserMapper userMapper;

    public void register(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setAvatarUrl("http://beyond-1.oss-cn-beijing.aliyuncs.com/44160d5e-5a53-4952-bee4-91cd22d545a6.png?Expires=1916734743&OSSAccessKeyId=LTAI4GG9kyUsqCWFGxyVcTT1&Signature=DhHbmY%2BtWFDc9TndD4%2BE92WqfzM%3D");
        userMapper.insert(user);
    }
}
