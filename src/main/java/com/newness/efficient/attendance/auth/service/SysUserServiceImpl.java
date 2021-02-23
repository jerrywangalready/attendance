package com.newness.efficient.attendance.auth.service;

import com.newness.efficient.attendance.auth.entity.SysUserEntity;
import com.newness.efficient.attendance.auth.mapper.SysUserMapper;
import com.newness.efficient.attendance.components.BCryptPasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Autowired
    SysUserMapper sysUserMapper;

    //用户激活状态
    private static final int USER_STATE = 1;

    /**
     * 通过账号查询用户
     *
     * @param username
     * @return
     */
    @Override
    public SysUserEntity getUserByUserName(String username) {
        return sysUserMapper.getUserByUsernameNState(username, USER_STATE);
    }

    /**
     * 个性化验证登录
     *
     * @param username    账号
     * @param rawPassword 原始密码
     * @return
     */
    @Override
    public boolean checkLogin(String username, String rawPassword) throws Exception {
        SysUserEntity userEntity = this.getUserByUserName(username);
        if (userEntity == null) {
            //设置友好提示
            throw new Exception("账号不存在，请重新尝试！");
        } else {
            //加密的密码
            String encodedPassword = userEntity.getPassWord();
            //和加密后的密码进行比配
            if (!bCryptPasswordEncoderUtil.matches(rawPassword, encodedPassword)) {
                //设置友好提示
                throw new Exception("密码不正确！");
            } else {
                return true;
            }
        }
    }

    /**
     * 注册
     *
     * @param sysUserVo
     * @return
     * @throws Exception
     */
    @Override
    public boolean register(SysUserEntity sysUserEntity) throws Exception {
        if (sysUserEntity != null) {
            SysUserEntity f = this.getUserByUserName(sysUserEntity.getUserName());
            if (f != null) {
                throw new Exception("这个用户已经存在，不能重复。");
            }
            //保存到数据库
            sysUserEntity.setPassWord(bCryptPasswordEncoderUtil.encode(sysUserEntity.getPassWord()));
            sysUserEntity.setState(USER_STATE);
            return sysUserMapper.saveUser(sysUserEntity);
        } else {
            throw new Exception("错误消息：用户对象为空！");
        }
    }

}
