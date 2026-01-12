package com.finalproject.finalproject.data.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usersettings")
@Audited(withModifiedFlag = true)
public class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @Audited(withModifiedFlag = false)
    private User user;

    @Column(name = "color_scheme")
    @Builder.Default
    private String colorScheme = "light"; // "light" or "dark"

    @Column(name = "font_size")
    @Builder.Default
    private String fontSize = "md"; // "sm", "md", "lg"

    @Column(name = "primary_color")
    @Builder.Default
    private String primaryColor = "blue"; // "blue", "red", "green", etc.



}
