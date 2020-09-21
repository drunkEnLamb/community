package life.beyond.community.dto;

import life.beyond.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Integer creatorId;
    private String tag;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
