package com.victoricare.api.security;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAuthorizedException;
import com.victoricare.api.utils.Tools;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class JWTToken {

    @Value("${fmc.api.jwtExpirationMs}")
    private Long jwtExpirationMs;

    @Value("${fmc.api.jwtSecret}")
    private String jwtSecret;

    @Value("${fmc.api.apiKey}")
    private String apiKey;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpHeaderWrapper httpHeader;
    private String subject;
    private String ip;
    private String browser;

    public void init() {
        this.httpHeader.init(request);

        if (this.httpHeader.getJwt() != null) {
            Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(this.httpHeader.getJwt())
                    .getBody();
            this.subject = claims.getSubject();
            this.ip = (String) claims.get("userIp");
            this.browser = (String) claims.get("userBrowser");
        }
    }

    public void checkApiKey() {
        if (!Tools.isEqual(this.apiKey, this.httpHeader.getApiKey())) {
            log.error("The Fmc-Apikey is not valid : {}", this.httpHeader.getApiKey());
            throw new ActionNotAuthorizedException(EMessage.API_KEY_NOT_MATCH);
        }
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret));
    }

    public Date getExpirationDate(Map<String, Object> token) {
        return (Date) token.get("expireAt");
    }

    public String getJWT(Map<String, Object> token) {
        return (String) token.get("jwt");
    }

    public Map<String, Object> generate(String username) {
        Instant now = Instant.now();

        Map<String, Object> response = new HashMap<>();
        response.put("createAt", Date.from(now));
        response.put("expireAt", Date.from(now.plusMillis(jwtExpirationMs)));

        String jwt = Jwts
                .builder()
                .setClaims(
                        Map.of("userIp", this.httpHeader.getIp(),
                                "userBrowser", this.httpHeader.getBrowser()))
                .setSubject(username)
                .setIssuedAt((Date) response.get("createAt"))
                .setExpiration((Date) response.get("expireAt"))
                .signWith(this.key(), SignatureAlgorithm.HS256).compact();

        response.put("jwt", jwt);
        return response;
    }

    public void doCheck() {
        try {
            log.info("Checking jwt token validation...");
            if (Tools.isNullOrEmpty(this.subject)) {
                log.error("User not found in token : {}", this.httpHeader.getJwt());
                throw new Exception(EMessage.USER_NOT_FOUND.name());
            }

            if (!Tools.isEqual(this.ip, this.httpHeader.getIp())) {
                log.error("Ip missmatch {} => {}", this.ip, this.httpHeader.getIp());
                throw new Exception(EMessage.IP_NOT_MATCH.name());
            }
            if (!Tools.isEqual(this.browser, this.httpHeader.getBrowser())) {
                log.error("Nav missmatch{} => {}", this.browser, this.httpHeader.getBrowser());
                throw new Exception(EMessage.BROWSER_NOT_MATCH.name());
            }
        } catch (Exception e) {
            log.error("Invalid jwt token: {}", e);
            throw new ActionNotAuthorizedException(EMessage.INVALID_TOKEN);
        }
    }
}
