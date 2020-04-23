package com.bluesky.shopping.common.util;

import com.bluesky.shopping.common.constant.AuthConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    public static void main(String[] args) {

        //获取系统的当前时间
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);

        //生成jwt令牌
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("66")//设置jwt编码
                .setSubject("黑马程序员")//设置jwt主题
                .setIssuedAt(new Date())//设置jwt签发日期
                .setSubject("JWT令牌测试")
                .setExpiration(new Date(date.getTime()+3600000))//设置jwt的过期时间
//                .claim("roles","admin")
//                .claim("company","itheima")
                .signWith(SignatureAlgorithm.HS256, AuthConstant.JWT_KEY);

        Map<String,Object> vClaims = new HashMap<>();
        vClaims.put("roles","admin");
        vClaims.put("company","itheima");
        vClaims.put("cost","3500");

        jwtBuilder.addClaims(vClaims);

        //生成jwt
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);

        //解析jwt,得到其内部的数据
        Claims claims = Jwts.parser().setSigningKey(AuthConstant.JWT_KEY).parseClaimsJws(jwtToken).getBody();
        System.out.println(claims);
    }
}
