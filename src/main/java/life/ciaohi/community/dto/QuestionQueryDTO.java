package life.ciaohi.community.dto;

import lombok.Data;

/**
 * Created by ciaohi on 2019/10/13 10:12
 */

@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
