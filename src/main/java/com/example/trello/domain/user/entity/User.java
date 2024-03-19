package com.example.trello.domain.user.entity;

import com.example.trello.domain.user.dto.SignupRequestDto;
import com.example.trello.domain.user.dto.UserInfoRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String introduction;

    @Column
    private String photo;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(Long userId, String email) {
        this.id = userId;
        this.email = email;
    }

    public User(SignupRequestDto requestDto) {
        email = requestDto.getEmail();
        password = requestDto.getPassword();
        nickname = requestDto.getNickname();
        introduction = requestDto.getIntroduction();
        photo = requestDto.getPhoto();
        role = UserRoleEnum.USER;
    }

    public void update(UserInfoRequestDto requestDto){
        nickname = requestDto.getNickname();
        introduction = requestDto.getIntroduction();
        photo = requestDto.getPhoto();
    }

    public void changePassword(String password){
        this.password = password;
    }
}
