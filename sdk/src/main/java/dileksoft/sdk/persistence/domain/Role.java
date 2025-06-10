package dileksoft.sdk.persistence.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {


    @Id
    @GenericGenerator(name = "seq_roles_id", strategy = "dileksoft.sdk.persistence.FastTSIDGenerator")
    @GeneratedValue(generator = "seq_roles_id")
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private OffsetDateTime updatedAt;
}
