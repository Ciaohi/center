package life.ciaohi.community.mapper;

import life.ciaohi.community.dto.QuestionQueryDTO;
import life.ciaohi.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView( Question record);
    int incCommentCount( Question record);
    List<Question> selectRelated(Question question);


    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}