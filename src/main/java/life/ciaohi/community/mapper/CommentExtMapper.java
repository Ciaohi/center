package life.ciaohi.community.mapper;

import life.ciaohi.community.model.Comment;
import life.ciaohi.community.model.CommentExample;
import life.ciaohi.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}