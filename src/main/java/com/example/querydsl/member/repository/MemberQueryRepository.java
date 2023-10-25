package com.example.querydsl.member.repository;

import com.example.querydsl.member.dto.response.MemberSearchCond;
import com.example.querydsl.member.dto.response.MemberTeamDto;
import com.example.querydsl.member.dto.response.QMemberTeamDto;
import com.example.querydsl.member.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.querydsl.member.entity.QMember.member;
import static com.example.querydsl.team.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class MemberQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryfactory;

    public MemberQueryRepository(EntityManager em) {
        this.em = em;
        this.queryfactory = new JPAQueryFactory(em);
    }

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAll_Querydsl(){
        return queryfactory
                .select(member)
                .from(member)
                .fetch();
    }

    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username){
        return queryfactory
                .select(member)
                .from(member)
                .where(member.username.eq(username))
                .fetch();
    }

    public List<MemberTeamDto> searchByBuilder(MemberSearchCond memberSearchCond){

        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(memberSearchCond.getUsername())) {
            builder.and(member.username.eq(memberSearchCond.getUsername()));
        }
        if (hasText(memberSearchCond.getTeamName())) {
            builder.and(team.name.eq(memberSearchCond.getTeamName()));
        }
        if(memberSearchCond.getAgeGoe() != null){
            builder.and(member.age.goe(memberSearchCond.getAgeGoe()));
        }
        if (memberSearchCond.getAgeLoe() != null) {
            builder.and(member.age.loe(memberSearchCond.getAgeLoe()));
        }

        return queryfactory
                .select(new QMemberTeamDto(
                        member.memberId,
                        member.username,
                        member.age,
                        team.teamId,
                        team.name.as("teamName")
                ))
                .from(member)
                .where(builder)
                .leftJoin(member.team, team)
                .fetch();

    }

    public List<MemberTeamDto> searchByWhere(MemberSearchCond cond){
        return queryfactory
                .select(new QMemberTeamDto(
                        member.memberId,
                        member.username,
                        member.age,
                        team.teamId,
                        team.name.as("teamName")
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(cond.getUsername()),
                        teamNameEq(cond.getTeamName()),
                        ageGoe(cond.getAgeGoe()),
                        ageLoe(cond.getAgeLoe())
                )
                .fetch();
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }

}
