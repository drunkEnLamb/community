package life.beyond.community.service;

import life.beyond.community.dto.NotificationDTO;
import life.beyond.community.dto.PaginationDTO;
import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.enums.NotificationStatusEnum;
import life.beyond.community.enums.NotificationTypeEnum;
import life.beyond.community.exception.CustomizeErrorCode;
import life.beyond.community.exception.CustomizeException;
import life.beyond.community.mapper.CommentMapper;
import life.beyond.community.mapper.NotificationMapper;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    public PaginationDTO list(Long id, Integer page, Integer size) {
        if(page<1)  page = 1;
        Integer offset = size * (page - 1);

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));
       if(notificationList.size()==0){
           return paginationDTO;
       }

        List<NotificationDTO> notificationDTOList = new ArrayList<>();

        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOList);
        int totalCount = (int)notificationMapper.countByExample(notificationExample);
        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }if(!notification.getReceiver().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return  notificationDTO;
    }

    public Long getQuestionId(Long outerId) {
        return commentMapper.selectByPrimaryKey(outerId).getParentId();
    }
}
