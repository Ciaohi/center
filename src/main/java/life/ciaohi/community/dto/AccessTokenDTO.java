package life.ciaohi.community.dto;


import lombok.Data;

@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String edirect_uri;
    private String state;

}
