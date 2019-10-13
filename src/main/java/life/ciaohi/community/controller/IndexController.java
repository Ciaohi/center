package life.ciaohi.community.controller;

import life.ciaohi.community.dto.PageinationDTO;
import life.ciaohi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;


/**
 *
 */

@Controller
public class IndexController {


   @Autowired
   private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        //传递两个参数page代表页码,size代表一个分页展示的数量
                        @RequestParam(name="page",defaultValue="1") Integer page,
                        @RequestParam(name="size",defaultValue="5") Integer size,
                        @RequestParam(name="search",required = false) String search
                        ) {
            PageinationDTO pageination= questionService.list(search,page,size);
            model.addAttribute("pageination",pageination);
            model.addAttribute("search",search);
            return "index";

    }
}
