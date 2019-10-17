package life.ciaohi.community.schedule;

import life.ciaohi.community.cache.HotTagCache;
import life.ciaohi.community.mapper.QuestionMapper;
import life.ciaohi.community.model.Question;
import life.ciaohi.community.model.QuestionExample;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by ciaohi on 2019/10/14 23:10
 */

@Component
@Slf4j
@Data
public class HotTagTasks {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;
    Map<String,Integer> tagQuestions=new HashMap<>();
    Map<String,Integer> tagReplys=new HashMap<>();

    @Scheduled(fixedRate = 2000 )
    //@Scheduled(cron = "0 0 1 * * *")
    public void hotTagSchedule() {
        int offset=0;
        int limit=5;
        Map<String,Integer> tagQuestionsi=new HashMap<>();
        Map<String,Integer> tagReplysi=new HashMap<>();

        log.info("hotTagSchedule start{}", new Date());
        List<Question> list =new ArrayList<>();
        Map<String,Integer> priorities=new HashMap<>();
        while (offset==0 || list.size()==limit){
            list=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,limit));
            for (Question question : list) {
                String [] tags=StringUtils.split(question.getTag(),",");
                for (String tag : tags) {
                    Integer tagQuestion=tagQuestionsi.get(tag);
                    if(tagQuestion!=null){
                        tagQuestionsi.put(tag,++tagQuestion);
                    }else{
                        tagQuestionsi.put(tag,1);
                    }


                    Integer tagReply=tagReplysi.get(tag);
                    if( tagReply!=null){
                        tagReplysi.put(tag,tagReply+question.getCommentCount());
                    }else{
                        tagReplysi.put(tag,question.getCommentCount());
                    }


                    Integer priority=priorities.get(tag);
                    if(priority!=null){
                        priorities.put(tag,priority+5+question.getCommentCount());
                    }else{
                        priorities.put(tag,5+question.getCommentCount());
                    }
                }
            }
            offset+=limit;
        }

        tagQuestions=tagQuestionsi;
        tagReplys=tagReplysi;
        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule stop{}", new Date());
        System.out.println(priorities);
        System.out.println(tagQuestionsi);
        System.out.println(tagReplysi);




    }

}

