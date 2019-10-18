package life.ciaohi.community.dto;

import lombok.Data;

/**
 * Created by ciaohi on 2019/10/16 15:48
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority()-((HotTagDTO) o).getPriority();
    }
}