package com.newness.efficient.attendance.components;

import com.newness.efficient.attendance.auth.entity.SysBackendApiTable;
import com.newness.efficient.attendance.auth.service.SysBackendApiTableService;
import com.newness.efficient.attendance.configuration.auth.MyAccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@Slf4j
public class DynamicPermission {

    @Autowired
    SysBackendApiTableService service;

    /**
     * 判断有访问API的权限
     *
     * @param request
     * @param authentication
     * @return
     * @throws MyAccessDeniedException
     */
    public boolean checkPermission(HttpServletRequest request,
                                   Authentication authentication) throws MyAccessDeniedException {

        Object principal = authentication.getPrincipal();
        log.info("DynamicPermission principal = " + principal);

        if (principal instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principal;
            //得到当前的账号
            String username = userDetails.getUsername();
            //Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

            // System.out.println("DynamicPermission  username = " + username);
            //通过账号获取资源鉴权
            List<SysBackendApiTable> apiUrls = service.getApiUrlByUserName(username);

            AntPathMatcher antPathMatcher = new AntPathMatcher();
            //当前访问路径
            String requestURI = request.getRequestURI();
            //提交类型
            String urlMethod = request.getMethod();

            // System.out.println("DynamicPermission requestURI = " + requestURI);

            //判断当前路径中是否在资源鉴权中
            boolean rs = apiUrls.stream().anyMatch(item -> {
                //判断URL是否匹配
                boolean hashAntPath = antPathMatcher.match(item.getBackendApiUrl(), requestURI);

                //判断请求方式是否和数据库中匹配（数据库存储：GET,POST,PUT,DELETE）
                String dbMethod = item.getBackendApiMethod();

                //处理null，万一数据库存值
                dbMethod = (dbMethod == null) ? "" : dbMethod;
                int hasMethod = dbMethod.indexOf(urlMethod);

                log.info("hashAntPath = " + hashAntPath);
                log.info("hasMethod = " + hasMethod);
                log.info("hashAntPath && hasMethod = " + (hashAntPath && hasMethod != -1));
                //两者都成立，返回真，否则返回假
                return hashAntPath && (hasMethod != -1);
            });
            //返回
            if (rs) {
                return true;
            } else {
                throw new MyAccessDeniedException("您没有访问该API的权限！");
            }

        } else {
            throw new MyAccessDeniedException("不是UserDetails类型！");
        }
    }
}