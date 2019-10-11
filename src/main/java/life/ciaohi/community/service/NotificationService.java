package life.ciaohi.community.service;

import life.ciaohi.community.dto.NotificationDTO;
import life.ciaohi.community.dto.PageinationDTO;
import life.ciaohi.community.enums.NotificationStatusEnum;
import life.ciaohi.community.enums.NotificationTypeEnum;
import life.ciaohi.community.exceptin.CustomizeErrorCode;
import life.ciaohi.community.exceptin.CustomizeException;
import life.ciaohi.community.mapper.NotificationMapper;
import life.ciaohi.community.mapper.UserMapper;
import life.ciaohi.community.model.Notification;
import life.ciaohi.community.model.NotificationExample;
import life.ciaohi.community.model.User;
import life.ciaohi.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;



    public PageinationDTO list(Long userId, Integer page, Integer size) {

        PageinationDTO <NotificationDTO> pageinationDTO=new PageinationDTO<>() ;

        Integer totalPage;

        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount=(int) notificationMapper.countByExample(new NotificationExample());

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }


        pageinationDTO.setPagination(totalPage,page);


        //size*(page-1)算limit偏移量
        Integer offset=size*(page-1);
        NotificationExample example=new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications =notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        if(notifications.size()==0){
            return pageinationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);

        }
        pageinationDTO.setData(notificationDTOS);
        return pageinationDTO;
    }

    public Long unreadCount(Long userID) {
        
        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userID)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification=notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }if(!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }


        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO=new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
