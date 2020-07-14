package com.zk.cloudweb.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zk.cloudweb.shiro.realm.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    // Session超时时间，单位为毫秒（默认30分钟）
    @Value("${shiro.session.expireTime}")
    private int expireTime;

    // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
    @Value("${shiro.session.validationInterval}")
    private int validationInterval;

    // 同一个用户最大会话数
    @Value("${shiro.session.maxSession}")
    private int maxSession;

    // 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
    @Value("${shiro.session.kickoutAfter}")
    private boolean kickoutAfter;

    // 验证码开关
    @Value("${shiro.user.captchaEnabled}")
    private boolean captchaEnabled;

    // 验证码类型
    @Value("${shiro.user.captchaType}")
    private String captchaType;

    // 设置Cookie的域名
    @Value("${shiro.cookie.domain}")
    private String domain;

    // 设置cookie的有效访问路径
    @Value("${shiro.cookie.path}")
    private String path;

    // 设置HttpOnly属性
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    // 设置Cookie的过期时间，秒为单位
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    // 登录地址
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    // 权限认证失败地址
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

//    @Bean(name = "shiroFilterFactoryBean")
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
//        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
//        Map<String,String> fMap=new HashMap<>();
//        //拦截页面
//        fMap.put("/album/index","authc");
//        fMap.put("/one","authc");
//        //拦截未授权
//        fMap.put("/album/index","perms[user:all]");
//        fMap.put("/one","perms[user:one]");
//        //被拦截返回登录页面
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        //授权拦截返回页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/permission");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(fMap);
//        return shiroFilterFactoryBean;
//    }


    /**
     * Shiro过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问
        filterChainDefinitionMap.put("/favicon.ico**", "anon");
//        filterChainDefinitionMap.put("/index.html**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/page/**", "anon");
        // 退出 logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/getLogin", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        // 注册相关
        filterChainDefinitionMap.put("/register", "anon");
        // 系统权限列表
        // filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

//        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
//        filters.put("onlineSession", onlineSessionFilter());
//        filters.put("syncOnlineSession", syncOnlineSessionFilter());
//        filters.put("captchaValidate", captchaValidateFilter());
//        filters.put("kickout", kickoutSessionFilter());
        // 注销成功，则跳转到指定页面
//        filters.put("logout", logoutFilter());
//        shiroFilterFactoryBean.setFilters(filters);

        // 所有请求需要认证
//        filterChainDefinitionMap.put("/**", "user,kickout,onlineSession,syncOnlineSession");
                filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


//    /**
//     * 退出过滤器
//     */
//    public LogoutFilter logoutFilter()
//    {
//        LogoutFilter logoutFilter = new LogoutFilter();
//        logoutFilter.setCacheManager(getEhCacheManager());
//        logoutFilter.setLoginUrl(loginUrl);
//        return logoutFilter;
//    }
//    /**
//     * 同一个用户多设备登录限制
//     */
//    public KickoutSessionFilter kickoutSessionFilter()
//    {
//        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
//        kickoutSessionFilter.setCacheManager(getEhCacheManager());
//        kickoutSessionFilter.setSessionManager(sessionManager());
//        // 同一个用户最大的会话数，默认-1无限制；比如2的意思是同一个用户允许最多同时两个人登录
//        kickoutSessionFilter.setMaxSession(maxSession);
//        // 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序
//        kickoutSessionFilter.setKickoutAfter(kickoutAfter);
//        // 被踢出后重定向到的地址；
//        kickoutSessionFilter.setKickoutUrl("/login?kickout=1");
//        return kickoutSessionFilter;
//    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm)
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(userRealm);
        // 记住我
//        securityManager.setRememberMeManager(rememberMeManager());
        // 注入缓存管理器;
//        securityManager.setCacheManager(getEhCacheManager());
        // session管理器
//        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    @Bean(name = "userRealm")
    public UserRealm getUserRealm(){
        return new UserRealm();
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect()
    {
        return new ShiroDialect();
    }
}
