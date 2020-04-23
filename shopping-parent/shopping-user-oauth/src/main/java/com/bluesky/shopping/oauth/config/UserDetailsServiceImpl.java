package com.bluesky.shopping.oauth.config;

import com.bluesky.shopping.oauth.util.UserJwt;
import com.bluesky.shopping.user.feign.UserFeign;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/*****
 * 自定义授权认证类
 */
@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    private UserFeign userFeign;

    /****
     * 自定义授权认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("-----客户端信息认证开始UserDetailsServiceImpl的loadUserByUsername-----");
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //秘钥
                String clientSecret = clientDetails.getClientSecret();
                //静态方式
//                return new User(username,new BCryptPasswordEncoder().encode(clientSecret),AuthorityUtils.commaSeparatedStringToAuthorityList(""));
                //数据库查找方式
                return new User(username,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        log.info("-----客户端信息认证结束 UserDetailsServiceImpl的loadUserByUsername-----");
        log.info("-----用户信息认证开始 UserDetailsServiceImpl的loadUserByUsername-----");
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        //根据用户名查询用户信息
//        String pwd = new BCryptPasswordEncoder().encode("blueskywhitecloud");
        com.bluesky.shopping.user.pojo.User user = userFeign.findUserInfo(username);
        //创建User对象
        String permissions = "goods_list,seckill_list";
        UserJwt userDetails = new UserJwt(username,user.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
        log.info("-----用户信息认证结束 UserDetailsServiceImpl的loadUserByUsername-----");
        return userDetails;
    }
}
