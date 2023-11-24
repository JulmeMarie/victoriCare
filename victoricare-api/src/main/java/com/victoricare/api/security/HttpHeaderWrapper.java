package com.victoricare.api.security;

import java.util.List;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class HttpHeaderWrapper {
    public final static List<String> IP_CANDIDATES = List.of(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA,REMOTE_ADDR");

    private String apiKey;
    private String ip;
    private String browser;
    private String jwt;

    public void init(HttpServletRequest request) {
        this.apiKey = request.getHeader("x-api-key");

        this.ip = IP_CANDIDATES.stream()
                .filter(candidate -> this.isIPKnown(request, candidate))
                .map(candidate -> request.getHeader(candidate).split(",")[0])
                .findFirst()
                .orElse(request.getRemoteAddr());

        this.browser = request.getHeader("User-Agent");

        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            this.jwt = headerAuth.substring(7, headerAuth.length());
        }
    }

    public boolean isHeaderPresent(HttpServletRequest request, String header) {
        return request.getHeader(header) != null;
    }

    private boolean isIPKnown(HttpServletRequest request, String candidate) {
        String ip = request.getHeader(candidate);
        return !(ip == null || ip.isBlank() || ip.equalsIgnoreCase("unknown"));
    }
}