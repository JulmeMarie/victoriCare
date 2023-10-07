package com.victoricare.api.security.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.victoricare.api.entities.Connection;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.ERight;
import com.victoricare.api.enums.ERole;
import com.victoricare.api.exceptions.AuthException;
import com.victoricare.api.security.services.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
@Component
public class JWTUtils {
	private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	@Value("${fmc.api.jwtSecret}")
	private String jwtSecret;

	@Value("${fmc.api.jwtExpirationMs}")
	private Long jwtExpirationMs;

	@Value("${fmc.api.apiKey}")
	private String apiKey;

	@Value("${fmc.api.visitor.name}")
	private String visitorName;

	@Value("${fmc.api.visitor.id}")
	private Integer visitorId;

	@Value("${fmc.api.visitor.password}")
	private String visitorPassword;
	
	@Value("${s3.bucket.name}")
    private String s3BucketName;

    @Value("${s3.bucket.url}")
    private String s3BucketUrl;

	private final static List<String> headerIpCandidates = List.of(
			"X-Forwarded-For",
			"Proxy-Client-IP",
			"WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR",
			"HTTP_X_FORWARDED",
			"HTTP_X_CLUSTER_CLIENT_IP",
			"HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR",
			"HTTP_FORWARDED",
			"HTTP_VIA,REMOTE_ADDR"
	);

	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

	public Map<String, Object> generateJwtToken(Connection connection) {
		Instant now= Instant.now();

		Map<String, Object> response = new HashMap<>();

		response.put("createAt", Date.from(now));
		response.put("expireAt", Date.from(now.plusMillis(jwtExpirationMs)));

		String jwt = Jwts
			.builder()
			.setClaims(
				Map.of("userIp", connection.getIpConnection(),
						"userBrowser", connection.getBrowserConnection()
				)
			)
			.setSubject(connection.getLoginConnection())
			.setIssuedAt((Date)response.get("createAt"))
			.setExpiration((Date)response.get("expireAt"))
			.signWith(key(), SignatureAlgorithm.HS256).compact();

		response.put("jwt", jwt);
		return response;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret));
	}

	public String getIp(HttpServletRequest request) {
		return headerIpCandidates
				.stream()
				.filter(c->{
					String ipList = request.getHeader(c);
					return (ipList != null && ipList.length() > 0 && !"unknown".equalsIgnoreCase(ipList));
				}).
				map(c->request.getHeader(c).split(",")[0])
				.findFirst().orElse(request.getRemoteAddr());
	}

	public void checkApiKey(HttpServletRequest request) {
		String apiKeyFromRequest = request.getHeader("Fmc-Apikey");
		if (apiKeyFromRequest == null || !apiKeyFromRequest.equals(apiKey)) {
			logger.error("The Fmc-Apikey is not valid : {}", apiKeyFromRequest);
			throw new AuthException();
		}
	}

	public String getBrowser(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	public  User getVisitor() {
		User visitor = new User();
		visitor.setIdUser(visitorId);
		visitor.setUsernameUser(visitorName);
		visitor.setLastnameUser(visitorName);
		visitor.setFirstnameUser(visitorName);
		visitor.setPasswordUser(visitorPassword);
		visitor.setRightUser(ERight.ANONYMOUS.name());
		visitor.setRoleUser(ERole.NONE.name());
		return visitor;
	}

	public User checkConnection(UserDetailsServiceImpl userDetailsService, HttpServletRequest request) {
		String username = this.getClaims(request).getSubject();

		if(this.visitorName.equals(username)) { //Visitors don't need connection
			return this.getVisitor();
		}

		Connection connection = userDetailsService.getConnectionByToken(this.parseJwt(request));

		if (connection == null) {
			logger.error("Connection not found for user : {}" + username);
			throw new AuthException();
		}

		if (connection.getDeleteAtConnection() != null || new Date().after(connection.getExpireAtConnection())) {
			logger.error("Connection is expired from DB : {}", connection);
			throw new AuthException();
		}
		if (!connection.getIpConnection().equals(this.getIp(request))) {
			logger.error("Ip from DB missmatch for connection: {}", connection);
			throw new AuthException();
		}
		if (!connection.getBrowserConnection().equals(this.getBrowser(request))) {
			logger.error("Browser from DB missmatch for connection : {}", connection);
			throw new AuthException();
		}
		if(connection.getCreateByConnection() == null || connection.getCreateByConnection().getDeleteAtUser() != null) {
			logger.error("User does not exist anymore : {}", connection);
			throw new AuthException();
		}
		return connection.getCreateByConnection();
	}

	public Claims getClaims(HttpServletRequest request) {
		String token = this.parseJwt(request);
		if(token != null) {
			return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
		}
		return null;
	}


	public String validateJwtToken(HttpServletRequest request) {
		try {
			logger.info("Checking jwt token validation...");

			String token = this.parseJwt(request);
			Claims claims = this.getClaims(request);
			Optional<String> username = Optional.of(claims.getSubject());

			if (username.isEmpty()) {
				logger.error("User not found in token : {}", token);
				throw new AuthException();
			}
			if (claims.get("userIp") == null || !claims.get("userIp").equals(this.getIp(request))) {
				logger.error("Ip missmatch {} => {}", claims.get("userIp"), this.getIp(request));
				throw new AuthException();
			}
			if (claims.get("userBrowser") == null || !claims.get("userBrowser").equals(this.getBrowser(request))) {
				logger.error("Nav missmatch{} => {}", claims.get("userBrowser"), this.getBrowser(request));
				throw new AuthException();
			}
			logger.info("JWT token is valid : {}", token);
			return username.get();
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e);
			throw new AuthException();
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e);
			throw new AuthException();
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e);
			throw new AuthException();
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e);
			throw new AuthException();
		} catch (Exception e) {
			logger.error("unkown exception : ", e);
			throw new AuthException();
		}
	}
}