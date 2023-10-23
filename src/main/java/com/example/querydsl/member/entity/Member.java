package com.example.querydsl.member.entity;

import com.example.querydsl.team.entity.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"memberId", "username", "age"})
public class Member {

    @Id @GeneratedValue
    private Long memberId;

    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username){
        this(username, 0);
    }

    public Member(String username, int age){
        this(username, age, null);
    }
    public Member(String username, int age, Team team){
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    private void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

}
