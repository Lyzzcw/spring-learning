package lyzzcw.stupid.spring.day5.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 10:04
 * Description: No Description
 */
@Component
@Aspect
public class AspectLog {
    @Pointcut("execution(* lyzzcw.stupid.spring.day5.aop.*.*(..))")
    private void pointCut(){};

    @Before("pointCut()")
    public void before(){
        System.out.println("@Before aspectLog");
    }
    @After("pointCut()")
    public void after(){
        System.out.println("@After aspectLog");
    }
    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("@AfterReturning aspectLog");
    }
    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("@AfterThrowing aspectLog");
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Object result = null;
        try{
            System.out.println("@Around before aspect");
            Object[] args = proceedingJoinPoint.getArgs();

            result = proceedingJoinPoint.proceed(args);
            System.out.println("@Around after aspect");

        }catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("@Around error aspect");
        } finally {
            System.out.println("@Around finally aspect");
        }

        return  result;
    }

}
