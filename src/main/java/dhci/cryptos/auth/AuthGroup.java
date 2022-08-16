package dhci.cryptos.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "AUTH_USER_GROUP")
public class AuthGroup {
    @Id
    @Column(name = "AUTH_USER_GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "AUTH_GROUP")
    private String authgroup;

    public AuthGroup(String username, String authgroup) {
        this.username = username;
        this.authgroup = authgroup;
    }
}
