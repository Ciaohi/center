package life.ciaohi.community.service;

import life.ciaohi.community.dto.PageinationDTO;
import life.ciaohi.community.dto.QuestionDTO;
import life.ciaohi.community.mapper.QuesstionMapper;
import life.ciaohi.community.mapper.UserMapper;
import life.ciaohi.community.model.Question;
import life.ciaohi.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuesstionMapper quesstionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageinationDTO list(Integer page, Integer size) {
        PageinationDTO pageinationDTO=new PageinationDTO();
        Integer totalPage;

        Integer totalCount=quesstionMapper.count();

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
        List<Question> questions = quesstionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageinationDTO.setQuestions(questionDTOList);

        return pageinationDTO;
    }

    public PageinationDTO list(Integer userId, Integer page, Integer size) {
        PageinationDTO pageinationDTO=new PageinationDTO();

        Integer totalPage;

        Integer totalCount=quesstionMapper.countByUserId(userId);

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
        List<Question> questions = quesstionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageinationDTO.setQuestions(questionDTOList);

        return pageinationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=quesstionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}