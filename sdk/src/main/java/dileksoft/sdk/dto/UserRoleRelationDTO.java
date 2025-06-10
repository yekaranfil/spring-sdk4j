package dileksoft.sdk.dto;


import lombok.*;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRelationDTO {

    private String userRoleId;  // Kullanıcı rolü benzersiz kimlik numarası (TSID)
    private UserDTO user;       // Kullanıcı bilgileri
    private RoleDTO role;       // Rol bilgileri
    private OffsetDateTime createdAt;  // Kayıt oluşturulma tarihi
    private OffsetDateTime updatedAt;  // Son güncelleme tarihi
}
