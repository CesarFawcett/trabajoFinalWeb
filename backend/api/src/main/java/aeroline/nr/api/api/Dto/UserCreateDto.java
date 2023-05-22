package aeroline.nr.api.api.Dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private int id;
    private String fullname;
    private String username;
    private String password;
}
