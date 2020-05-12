package net.kuper.tz.config;

import net.kuper.tz.core.constant.Time;
import net.kuper.tz.core.properties.TransportProperties;
import net.kuper.tz.system.auth.OAuth2Filter;
import net.kuper.tz.system.auth.OAuth2Realm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean("sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setGlobalSessionTimeout(15 * 60 * 1000);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    //	记住我的配置
    @Bean
    public RememberMeManager rememberMeManager() {
        Cookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);//通过js脚本将无法读取到cookie信息
        cookie.setMaxAge(7 * Time.DAY);//cookie保存七天
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(cookie);
        return manager;
    }

    //  缓存配置
    @Bean
    public CacheManager cacheManager() {
        MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();//使用内存缓存
        return cacheManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager, TransportProperties properties) {
        Map<String, Filter> filters = new HashMap<>(16);
        // 自动登录，当session失效，token有效情况下
//        filters.put("autoLogin", new AutoLoginFilter());
        OAuth2Filter oAuth2Filter = new OAuth2Filter();
        oAuth2Filter.setTransportProperties(properties);
        filters.put("oauth2",oAuth2Filter);

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/", "anon");
        filterMap.put("/system/auth/login", "anon");
        filterMap.put("/system/auth/login/captcha", "anon");
        filterMap.put("/static/**", "anon");
        filterMap.put("/storage/file/**", "anon");
        filterMap.put("/**", "oauth2");

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setFilters(filters);
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        shiroFilter.setLoginUrl("/error/login/invalid");
        shiroFilter.setUnauthorizedUrl("/error/unauth");
        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
