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
        userMapper.insert(user);
    }
}
