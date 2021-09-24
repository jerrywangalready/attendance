package com.newness.efficient.attendance.system.security.config;

import com.newness.efficient.attendance.system.security.exception.MyAccessDeniedHandler;
import com.newness.efficient.attendance.system.security.exception.MyAuthenticationEntryPoint;
import com.newness.efficient.attendance.system.security.filter.TokenAuthFilter;
import com.newness.efficient.attendance.system.security.filter.TokenLoginFilter;
import com.newness.efficient.attendance.system.security.token.TokenManager;
import com.newness.efficient.attendance.system.security.utils.DefaultPasswordEncoder;
import com.newness.efficient.attendance.system.security.utils.TokenLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private TokenManager tokenManager;

    private DefaultPasswordEncoder passwordEncoder;

    private RedisTemplate redisTemplate;

    @Autowired
    public TokenWebSecurityConfigurerAdapter(UserDetailsService userDetailsService, DefaultPasswordEncoder passwordEncoder, TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.tokenManager = tokenManager;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //第1步：解决跨域问题。cors 预检请求放行,让Spring security 放行所有preflight request（cors 预检请求）
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        // 解决跨域问题 让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers().cacheControl();
        // token验证
        http.addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate));
        // 认证拦截
        http.addFilter(new TokenAuthFilter(authenticationManager(), tokenManager, redisTemplate));
        // 请求权限配置
//        http.authorizeRequests().anyRequest().access("@dynamicPermission.checkPermission(request, authentication)");
        // 异常处理
        http.exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint()).accessDeniedHandler(new MyAccessDeniedHandler());
        // 退出
        http.logout().logoutUrl("/auth/logout").addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不进行认证的路径
        web.ignoring().antMatchers("/signup/**");
    }
}
