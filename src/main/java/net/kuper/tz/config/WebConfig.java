package net.kuper.tz.config;

import net.kuper.tz.core.controller.auth.AuthorizationInterceptor;
import net.kuper.tz.core.controller.auth.UserArgumentResolver;
import net.kuper.tz.core.controller.auth.UserIdArgumentResolver;
import net.kuper.tz.core.properties.TzProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private UserArgumentResolver userArgumentResolver;
    @Autowired
    private UserIdArgumentResolver userIdArgumentResolver;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private TzProperties tzProperties;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/static/index.html");
    }

    /**
     * 登录验证拦截
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }

    /**
     * 添加用户参数解析
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
        argumentResolvers.add(userIdArgumentResolver);
    }
}