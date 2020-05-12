package net.kuper.tz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.kuper.tz.service.LogInterceptorServiceImpl;
import net.kuper.tz.core.cache.Cache;
import net.kuper.tz.core.cache.RedisCache;
import net.kuper.tz.core.cache.adapter.JsonAdapter;
import net.kuper.tz.core.controller.auth.AuthorizationInterceptor;
import net.kuper.tz.core.controller.auth.IAuthInterceptorService;
import net.kuper.tz.core.controller.auth.UserArgumentResolver;
import net.kuper.tz.core.controller.auth.UserIdArgumentResolver;
import net.kuper.tz.core.controller.form.RepeatFormInterceptor;
import net.kuper.tz.core.controller.limit.DefaultLimitRuler;
import net.kuper.tz.core.controller.limit.ILimitMeasure;
import net.kuper.tz.core.controller.limit.LimitInterceptor;
import net.kuper.tz.core.controller.log.LogInterceptor;
import net.kuper.tz.core.properties.TransportProperties;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Configuration
public class TzConfig {

    /**
     * 缓存配置
     *
     * @param redisTemplate
     * @param objectMapper
     * @return
     */
    @Bean
    public Cache cache(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        JsonAdapter jsonAdapter = new JsonAdapter();
        jsonAdapter.setObjectMapper(objectMapper);
        RedisCache cache = new RedisCache(redisTemplate, jsonAdapter);
        return cache;
    }

    /**
     * 系统日志
     *
     * @param objectMapper
     * @param logInterceptorServiceImpl
     * @return
     */
    @Bean
    public Advisor logAop(ObjectMapper objectMapper, LogInterceptorServiceImpl logInterceptorServiceImpl, TransportProperties properties) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(LogInterceptor.EXPRESSION);
        LogInterceptor logInterceptor = new LogInterceptor(objectMapper, logInterceptorServiceImpl, properties);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(logInterceptor);
        advisor.setOrder(10);
        return advisor;
    }

    /**
     * 重复表单提交过滤
     *
     * @param cache
     * @return
     */
    @Bean
    public Advisor repeatFormAop(Cache cache) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(RepeatFormInterceptor.EXPRESSION);
        RepeatFormInterceptor repeatFormInterceptor = new RepeatFormInterceptor(cache);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(repeatFormInterceptor);
        advisor.setOrder(11);
        return advisor;
    }

    /**
     * 访问次数限制
     *
     * @param limitMeasure
     * @return
     */
    @Bean
    public Advisor requestLimitAop(ILimitMeasure limitMeasure, TransportProperties properties) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(LimitInterceptor.EXPRESSION);
        LimitInterceptor repeatFormInterceptor = new LimitInterceptor(new DefaultLimitRuler(limitMeasure), properties);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(repeatFormInterceptor);
        advisor.setOrder(12);
        return advisor;
    }

    /**
     * 登录拦截
     *
     * @param auth
     * @return
     */
    @Bean
    @Order(0)
    public AuthorizationInterceptor authInterceptor(IAuthInterceptorService auth, TransportProperties properties) {
        AuthorizationInterceptor interceptor = new AuthorizationInterceptor();
        interceptor.setAuth(auth);
        interceptor.setProperties(properties);
        return interceptor;
    }

    /**
     * User参数注入
     *
     * @param auth
     * @return
     */
    @Bean
    @Order(1)
    public UserArgumentResolver userArgumentResolver(IAuthInterceptorService auth) {
        UserArgumentResolver argumentResolver = new UserArgumentResolver();
        argumentResolver.setAuth(auth);
        return argumentResolver;
    }

    /**
     * User参数注入
     *
     * @param auth
     * @return
     */
    @Bean
    @Order(2)
    public UserIdArgumentResolver userIdArgumentResolver(IAuthInterceptorService auth) {
        UserIdArgumentResolver argumentResolver = new UserIdArgumentResolver();
        argumentResolver.setAuth(auth);
        return argumentResolver;
    }


}
