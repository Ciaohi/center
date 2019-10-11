package life.ciaohi.community.interceptor;

import life.ciaohi.community.mapper.UserMapper;
import life.ciaohi.community.model.User;
import life.ciaohi.community.model.UserExample;
import life.ciaohi.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample=new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users=userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                        Long unreadCount=notificationService.unreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }
                    break;
                }
            }
        return  true;
    }
}





