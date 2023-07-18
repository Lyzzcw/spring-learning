package lyzzcw.stupid.spring.day5.aop;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/7/18 10:28
 * Description: No Description
 */
public @interface LZY_Aspect {
    /**
     * {@link org.springframework.context.annotation.EnableAspectJAutoProxy}
     * 表示开启spring对基于注解aop的支持
     *  proxyTargetClass: 表示是否基于cglib代理 fales->基于JDK代理，默认false
     *  exportProxy: 表示是否暴露代理对象 false->不暴露，默认false
     */

    /**
     * {@link org.aspectj.lang.annotation.Aspect}
     * 将当前类声明为切面类
     *  value: 指定预处理的切入表达式
     */

    /**
     * {@link org.aspectj.lang.annotation.Pointcut}
     * 指定切入点表达式
     *  value: 表示指定切入点表达式
         * execution(public * *(..)) 定义任意公共方法的执行
         * execution(* set*(..)) 定义任何一个以"set"开始的方法的执行
         * execution(* com.xyz.service.AccountService.*(..)) 定义AccountService 接口的任意方法的执行
         * execution(* com.xyz.service.*.*(..)) 定义在service包里的任意方法的执行
         * execution(* com.xyz.service ..*.*(..)) 定义在service包和所有子包里的任意类的任意方法的执行
         * execution(* com.test.spring.aop.pointcutexp…JoinPointObjP2.*(…)) 定义在pointcutexp包和所有子包里的JoinPointObjP2类的任意方法的执行
         * within(com.test.spring.aop.pointcutexp.*) pointcutexp包里的任意类.
         * within(com.test.spring.aop.pointcutexp…*) pointcutexp包和所有子包里的任意类.
         * @within(org.springframework.transaction.annotation.Transactional) 带有@Transactional标注的所有类的任意方法.
        * @target(org.springframework.transaction.annotation.Transactional) 带有@Transactional标注的所有类的任意方法.
        * @annotation(org.springframework.transaction.annotation.Transactional) 带有@Transactional标注的任意方法.
     *  argNames: 表示指定切入点表达式的参数
     */

    /**
     * {@link org.aspectj.lang.annotation.Before}
     * 指定方法为前置通知
     *  value: 表示指定切入点表达式
     *  argNames: 表示指定切入点表达式的参数
     */

    /**
     * {@link org.aspectj.lang.annotation.After}
     * 指定方法为最终通知
     *  value: 表示指定切入点表达式
     *  argNames: 表示指定切入点表达式的参数
     */

    /**
     * {@link org.aspectj.lang.annotation.AfterReturning}
     * 指定方法为后置增强切入点
     *  value: 表示指定切入点表达式
     *  pointcut: 同value
     *  returning: 表示指定切入点方法返回值的变量名称
     *  argNames: 表示指定切入点表达式的参数
     */

    /**
     * {@link org.aspectj.lang.annotation.AfterThrowing}
     * 指定方法为异常通知
     *  value: 表示指定切入点表达式
     *  pointcut: 同value
     *  returning: 表示指定切入点方法返回值的变量名称
     *  argNames: 表示指定切入点表达式的参数
     */

    /**
     * {@link org.aspectj.lang.annotation.Around}
     * 指定方法为环绕通知
     *  value: 表示指定切入点表达式
     *  argNames: 表示指定切入点表达式的参数
     */


}
