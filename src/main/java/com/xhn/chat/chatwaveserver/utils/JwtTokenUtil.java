package com.xhn.chat.chatwaveserver.utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
@Component
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);




    private final String secretKey = "f7A3@v9T!pZ0LwqK1rQeV5JzXk2bD4h";  // 替换为你的密钥


}
