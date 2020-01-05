package com.website.weily.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * @Description
 * @Author
 * @Date 2020/1/4 16:26
 * @Version
 */
@Configuration
public class ShiroConfiguration {

    /**
     * 加密方式
     */
    @Value("${shiro.hashedCredentialsMatcher.hashAlgorithmName}")
    private String hashAlgorithmName;

    /**
     * 加密迭代次数
     */
    @Value("${shiro.hashedCredentialsMatcher.hashIterations}")
    private int hashIterations;

    /**
     * 登陆url
     */
    @Value("${shiro.shiroFilter.loginUrl}")
    private String loginUrl;

    /**
     * 认证失败的url
     */
    @Value("${shiro.shiroFilter.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * 放行image验证码
     */
    @Value("${shiro.shiroFilter.filterChainDefinitionMap.anon.image}")
    private String image;


    /**
     * 放行登陆方法
     */
    @Value("${shiro.shiroFilter.filterChainDefinitionMap.anon.login}")
    private String login;

    /**
     * 放行静态资源
     */
    @Value("${shiro.shiroFilter.filterChainDefinitionMap.anon.static}")
    private String staticStr;

    /**
     * 需要认证的资源
     */
    @Value("${shiro.shiroFilter.filterChainDefinitionMap.authc.other}")
    private String other;

    /**
     * 放行swagger
     */
    @Value("${shiro.shiroFilter.filterChainDefinitionMap.anon.html}")
    private String html;

    @Value("${shiro.shiroFilter.filterChainDefinitionMap.anon.resources}")
    private String resources;

    @Value("${shiro.shiroFilter.filterChainDefinitionMap.anon.docs}")
    private String docs;

    @Value("${shiro.shiroFilter.filterChainDefinitionMap.anon.ext}")
    private String ext;

    @Value("${shiro.shiroFilter.filterChainDefinitionMap.anon.webjars}")
    private String webjars;


    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        //加密次数
        credentialsMatcher.setHashIterations(hashIterations);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    /**
     * @param matcher
     * @return
     */
    @Bean("authRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public AuthRealm authRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        authRealm.setCachingEnabled(true);
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        authRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        authRealm.setAuthorizationCacheName("authorizationCache");
        return authRealm;
    }


    /**
     * 定义安全管理器securityManager,注入自定义的realm
     * @param authRealm
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //配置 ehcache缓存管理器
        manager.setCacheManager(ehCacheManager());
        manager.setRealm(authRealm);
        return manager;
    }

    /**
     * 定义shiroFilter过滤器并注入securityManager
     * @param manager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置securityManager
        bean.setSecurityManager(manager);
        //设置登录页面
        bean.setLoginUrl(loginUrl);
        //设置未授权跳转的页面
        bean.setUnauthorizedUrl(unauthorizedUrl);


        //定义过滤器
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put(image, "anon");
        filterChainDefinitionMap.put(login, "anon");
        filterChainDefinitionMap.put(staticStr, "anon");
        //放行测试数据
        filterChainDefinitionMap.put("/test/**", "anon");

        // 放行swagger
        filterChainDefinitionMap.put(html, "anon");
        filterChainDefinitionMap.put(resources, "anon");
        filterChainDefinitionMap.put(docs, "anon");
        filterChainDefinitionMap.put(ext, "anon");
        filterChainDefinitionMap.put(webjars, "anon");

        //需要登录访问的资源 , 一般将/**放在最下边
        filterChainDefinitionMap.put(other, "authc");

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     * Spring的一个bean , 由Advisor决定对哪些类的方法进行AOP代理 .
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 配置shiro跟spring的关联
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * lifecycleBeanPostProcessor是负责生命周期的 , 初始化和销毁的类
     * (可选)
     */
    @Bean("lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }
}
