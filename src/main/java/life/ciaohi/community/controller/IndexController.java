package life.ciaohi.community.controller;

import life.ciaohi.community.dto.PageinationDTO;
import life.ciaohi.community.dto.QuestionDTO;
import life.ciaohi.community.mapper.QuesstionMapper;
import life.ciaohi.community.mapper.UserMapper;
import life.ciaohi.community.model.Question;
import life.ciaohi.community.model.User;
import life.ciaohi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */

@Controller
public class IndexController {
   @Autowired
   private UserMapper userMapper;

   @Autowired
   private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        //传递两个参数page代表页码,size代表一个分页展示的数量
                        @RequestParam(name="page",defaultValue="1") Integer page,
                        @RequestParam(name="size",defaultValue="2") Integer size){
        Cookie[] cookies = request.getCookies();

        if(cookies !=null && cookies.length!=0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }

            PageinationDTO pageination= questionService.list(page,size);

            model.addAttribute("pageination",pageination);
            return "index";

    }
}
