package com.newness.efficient.attendance.system.security.filter;

import com.alibaba.fastjson.JSON;
import com.newness.efficient.attendance.components.R;
import com.newness.efficient.attendance.components.ResponseUtil;
import com.newness.efficient.attendance.system.security.model.SecurityUser;
import com.newness.efficient.attendance.system.apimanage.entity.SysApiEntity;
import com.newness.efficient.attendance.system.security.token.TokenManager;
import com.newness.efficient.attendance.system.user.model.BaseUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TokenAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("=================" + request.getRequestURI());
        //从header获取token
        String token = request.getHeader("Authentication");
        // 1. token是否合法
        if (!checkToken(token)) {
            ResponseUtil.out(response, R.error(), 401);
        }
        // 2. token是否临期, 临期提示前端重新申请token
        if (tokenExpiredSoon(token)) {
            response.setStatus(201);
        }
        // 3. 根据用户名获取用户对象
        SecurityUser securityUser = null;
        try {
            securityUser = getAuthentication(request);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.out(response, R.error());
        }
        // 4. 请求鉴权
        if (!checkPermission(request, securityUser)) {
            ResponseUtil.out(response, R.error(), 403);
        }

        // 5. 加入上下文
        if (securityUser != null) {
            //判断如果有权限信息，放到权限上下文中
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, token, securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseUtil.out(response, R.error());
        }
        chain.doFilter(request, response);
    }

    private boolean tokenExpiredSoon(String token) {
        Date expireTime = tokenManager.getExpireTime(token);
        Date now = new Date();
        now.setTime(now.getTime() + 10 * 60 * 1000);
        return now.after(expireTime);
    }

    private boolean checkToken(String token) {
        if (token == null) {
            return false;
        }
        // 查看token黑名单
        String blacklistKey = (String) redisTemplate.opsForValue().get("prohibit_" + token);
        if (StringUtils.hasText(blacklistKey)) {
            return false;
        }
        // 校验token是否合法
        return tokenManager.validateToken(token);
    }

    private SecurityUser getAuthentication(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("Authentication");
        if (token != null && !"".equals(token.trim())) {
            String username = tokenManager.getUsernameFromToken(token);

            if (StringUtils.hasText(username)) {
                BaseUser baseUser = JSON.parseObject(Objects.requireNonNull(redisTemplate.opsForValue().get(username)).toString(), BaseUser.class);
                baseUser.setPassword("*");
                return baseUser.toSecurityUser();
            }
            return null;
        }
        return null;
    }

    private boolean checkPermission(HttpServletRequest request, SecurityUser securityUser) {
        //得到当前的账号
        String username = securityUser.getUsername();
        //获取资源鉴权
        List<SysApiEntity> permissions = securityUser.getPermissions();

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        //当前访问路径
        String requestURI = request.getRequestURI();
        //提交类型
        String urlMethod = request.getMethod();

        //判断当前路径中是否在资源鉴权中
        boolean rs = permissions.stream().anyMatch(item -> {
            //判断URL是否匹配
            boolean hashAntPath = antPathMatcher.match(item.getApiPath(), requestURI);

            //判断请求方式是否和数据库中匹配（数据库存储：GET,POST,PUT,DELETE）
            String dbMethod = item.getApiMethod();

            //处理null，万一数据库存值
            dbMethod = (dbMethod == null) ? "" : dbMethod;
            int hasMethod = dbMethod.indexOf(urlMethod);

//            System.out.println(hashAntPath && (hasMethod != -1));
            //两者都成立，返回真，否则返回假
//            return hashAntPath && (hasMethod != -1);
            return hashAntPath;
        });

        return rs;
    }
}
