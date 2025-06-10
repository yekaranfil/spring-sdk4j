package dileksoft.sdk.persistence.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GenericGenerator(name = "seq_channels_id", strategy = "dileksoft.sdk.persistence.FastTSIDGenerator")
    @GeneratedValue(generator = "seq_channels_id")
    @Column(name = "user_id")
    String userId;

    @Column(name = "username")
    String username;

    @Column(name = "password_hash")
    String passwordHash;

    @Column(name = "created_at")
    OffsetDateTime createdAt;

    @Column(name = "updated_at")
    OffsetDateTime updatedAt;


}
