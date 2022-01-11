package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.entity.dto.MemberDto;
import study.datajpa.repository.MemberRepository;
import study.datajpa.repository.TeamRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).orElseGet(()->new Member("emptyMember"));
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member){
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort="username") Pageable pageable){
        return memberRepository.findAll(pageable).map(MemberDto::new);/*map으로 DTO로 전환*/
    }

//    @PostConstruct
//    public void (){
//        Team team = new Team("ManCity");
//        teamRepository.save(team);
//        for(int i=0; i<100; i++) memberRepository.save(new Member("user"+i, i, team));
//    }
}
