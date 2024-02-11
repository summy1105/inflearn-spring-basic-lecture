package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class OrderApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

//        ApplicationContext ac = new AnnotationConfigApplicationContext(TestAppConfig.class);
//        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        OrderService orderService = ac.getBean("orderService", OrderService.class);


        GenericApplicationContext ac = new GenericApplicationContext();
        ac.registerBean("memberRepository", MemberRepository.class, ()->new MemoryMemberRepository());
        ac.registerBean("memberService", MemberService.class, ()->new MemberServiceImpl(ac.getBean(MemberRepository.class)));
        ac.registerBean("discountPolicy", DiscountPolicy.class, RateDiscountPolicy::new);
        ac.registerBean("orderService", OrderService.class
                , ()->new OrderServiceImpl(ac.getBean(MemberRepository.class),ac.getBean(DiscountPolicy.class)));
        BeanDefinition discountPolicy = ac.getBeanDefinition("discountPolicy");
        discountPolicy.setInitMethodName("init");
        ac.refresh();
        MemberService memberService = ac.getBean(MemberService.class);
        OrderService orderService = ac.getBean(OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
//        System.out.println(order.calculatePrice());
    }
}
