package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy") // primary보다 우선권이 높다
//@Primary
@MainDiscountPolicy // "mainDiscountPolicy" -> 컴파일 시점에 오류가 나지 않는다. annotation 으로 하면 오타로 인한 오류 발생 X
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @PostConstruct
    public void init(){
        System.out.println("RateDiscountPolicy Post Construct Test *********");
    }

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
