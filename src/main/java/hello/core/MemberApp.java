package hello.core;

import hello.core.member.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
//        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        GenericApplicationContext ac = new GenericApplicationContext();
        ac.registerBean("memberRepository", MemberRepository.class, ()->new MemoryMemberRepository());
        ac.registerBean("memberService", MemberService.class, ()->new MemberServiceImpl(ac.getBean(MemberRepository.class)));
        ac.refresh();

        MemberService memberService = ac.getBean(MemberService.class);

        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(1L);
        System.out.println(memberA.getName());
        System.out.println(findMember.getName());
    }
}
