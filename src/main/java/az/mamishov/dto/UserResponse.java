package az.mamishov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String username;
    private Boolean isActive;
    private String roles;
}
