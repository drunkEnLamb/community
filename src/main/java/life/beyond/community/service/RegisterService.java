package life.beyond.community.service;

import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.User;
import life.beyond.community.provider.OSSProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class RegisterService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    OSSProvider ossProvider;
    public void register(String username, String password, MultipartFile file) {

        String avatarUrl = "http://beyond-1.oss-cn-beijing.aliyuncs.com/87f90673-7dca-47aa-bd87-9063fcc46183.png?Expires=1916739694&OSSAccessKeyId=LTAI4GFgqtjeTs713zGAWert&Signature=FXByoyJg9He9WXKHKOYCd4MRKL0%3D";
        try {
            avatarUrl = ossProvider.upload(file.getInputStream(),file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setAvatarUrl(avatarUrl);
        userMapper.insert(user);
    }
}
