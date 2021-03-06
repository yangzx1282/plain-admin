/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: AdminController
 * Author:   Young
 * Date:     2020/6/13 20:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.plainadmin.controller;

import cn.hutool.extra.servlet.ServletUtil;
import cn.xiaosm.plainadmin.config.security.service.TokenService;
import cn.xiaosm.plainadmin.config.security.service.UserDetailsServiceImpl;
import cn.xiaosm.plainadmin.entity.LoginUser;
import cn.xiaosm.plainadmin.entity.ResponseBody;
import cn.xiaosm.plainadmin.entity.User;
import cn.xiaosm.plainadmin.entity.vo.LoginUserVO;
import cn.xiaosm.plainadmin.entity.vo.UserVO;
import cn.xiaosm.plainadmin.service.UserService;
import cn.xiaosm.plainadmin.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AuthenticationManagerBuilder managerBuilder;
    @Autowired
    TokenService tokenService;

    @RequestMapping("/login")
    public void login(@RequestBody LoginUserVO user, HttpServletRequest request, HttpServletResponse response) {
        /* START
          该方法会去调用 UserDetailService 的方法
          由于我创建了此类的实现类，所以会去调用我自定义的登录逻辑，从后台获取 User 的信息
          密码的校验 security 会帮我们处理，如果密码错误，会抛出异常
         */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = managerBuilder
                .getObject().authenticate(authenticationToken);

        /* END */

        // 如果密码没有输入错误，将能够正确执行以下代码
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 设置登录用户信息（用户的权限和菜单列表）
        userDetailsService.loadUserInfo(loginUser);
        // 记录登录足迹
        userService.addLoginTrack(loginUser.getId(), ServletUtil.getClientIP(request));
        // 根据认证创建 Token
        String token = tokenService.createToken(loginUser);
        // 返回 token
        ResponseUtils.sendToken(response, token);
    }


}