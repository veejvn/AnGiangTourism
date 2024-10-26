package javaweb.AnGiangTourism.entity;

import jakarta.persistence.*;
import javaweb.AnGiangTourism.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.EnumSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String email;

    String password;

    String name;

    @ElementCollection(targetClass =  Role.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "account_roles", joinColumns = @JoinColumn(name = "acc_id"))
    @Column(name = "role_name")
    Set<Role> roles = EnumSet.noneOf(Role.class);
}
