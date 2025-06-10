package dileksoft.sdk.dto;


import lombok.*;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private String roleId;
    private String roleName;
    private String description;
    private OffsetDateTime createdAt;
//    private OffsetDateTime updatedAt;
}
