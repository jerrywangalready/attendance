package com.newness.efficient.attendance.auth.service;


import com.newness.efficient.attendance.auth.entity.SysUserEntity;

public interface SysUserService {

    /**
     * 通过账号查询用户
     *
     * @param username
     * @return
     */
    SysUserEntity getUserByUserName(String username);

    /**
     * 个性化验证登录
     *
     * @param username    账号
     * @param rawPassword 原始密码
     * @return
     */
    boolean checkLogin(String username, String rawPassword) throws Exception;

    /**
     * 注册
     *
     * @param sysUserEntity
     * @return
     * @throws Exception
     */
    boolean register(SysUserEntity sysUserEntity) throws Exception;

}
