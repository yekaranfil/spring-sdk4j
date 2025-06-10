package dileksoft.sdk.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  {

    private String userId;

    private String username;

    private String passwordHash;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}