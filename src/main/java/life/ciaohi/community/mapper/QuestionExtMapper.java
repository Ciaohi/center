package life.ciaohi.community.mapper;

import life.ciaohi.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView( Question record);
    int incCommentCount( Question record);


}