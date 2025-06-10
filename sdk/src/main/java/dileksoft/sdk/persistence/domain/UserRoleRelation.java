package dileksoft.sdk.persistence.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_role_relations")
public class UserRoleRelation {

    @Id
    @GenericGenerator(name = "seq_user_roles_id", strategy = "dileksoft.piticaret.persistence.FastTSIDGenerator")
    @GeneratedValue(generator = "seq_user_roles_id")
    @Column(name = "user_role_id")
    String userRoleId;  // Kullanıcı rolü benzersiz kimlik numarası (TSID)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "created_at")
    OffsetDateTime createdAt = OffsetDateTime.now();  // Kayıt oluşturulma tarihi
    @Column(name = "updated_at", nullable = false)
    OffsetDateTime updatedAt = OffsetDateTime.now();  // Son güncelleme tarihi


}
