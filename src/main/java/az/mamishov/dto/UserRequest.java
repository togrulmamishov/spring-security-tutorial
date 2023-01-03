package az.mamishov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {

    private String username;
    private String password;
    private String roles;
    private Boolean isActive;
}
