package life.ciaohi.community.controller;


import life.ciaohi.community.dto.NotificationDTO;
import life.ciaohi.community.dto.PageinationDTO;
import life.ciaohi.community.model.User;
import life.ciaohi.community.service.NotificationService;
import life.ciaohi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,@PathVariable(name="action")String action,
                          Model model,
                          @RequestParam(name="page",defaultValue="1") Integer page,
                          @RequestParam(name="size",defaultValue="5") Integer size){
        User user=(User)request.getSession().getAttribute("user");

        if (user==null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageinationDTO pageination= questionService.list(user.getId(),page,size);
            model.addAttribute("pageination",pageination);

        }else if("replies".equals(action)){
            PageinationDTO pageinationDTO=notificationService.list(user.getId(),page,size);
            Long unreadCount=notificationService.unreadCount(user.getId());
            model.addAttribute("section","replies");
            model.addAttribute("pageination",pageinationDTO);
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("sectionName","最新回复");
        }


        return "profile";
    }
}
