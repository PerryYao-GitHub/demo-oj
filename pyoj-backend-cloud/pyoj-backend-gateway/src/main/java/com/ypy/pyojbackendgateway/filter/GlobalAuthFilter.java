package com.ypy.pyojbackendgateway.filter;

import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.dto.UserAuthDTO;
import com.ypy.pyojbackendcommon.model.enums.UserRoleEnum;
import com.ypy.pyojbackendcommon.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        DataBuffer dataBuffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8");
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String path = serverHttpRequest.getURI().getPath();

        if (antPathMatcher.match("/**/inner/**", path)) {
            return buildErrorResponse(exchange, HttpStatus.FORBIDDEN, "No Auth");
        }

        if (antPathMatcher.match("/**/admin/**", path)) {
            String authHeader = serverHttpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return buildErrorResponse(exchange, HttpStatus.FORBIDDEN, "No Auth");
            }

            String token = authHeader.substring(7);
            try {
                UserAuthDTO dto = JwtUtils.parseToken(token);
                if (dto.getRole() != UserRoleEnum.ADMIN.getValue()) {
                    return buildErrorResponse(exchange, HttpStatus.FORBIDDEN, "No Auth");
                }
            } catch (AppException e) {
                return buildErrorResponse(exchange, HttpStatus.FORBIDDEN, e.getMessage());
            }
        }

        return chain.filter(exchange);
    }

    /**
     * 优先级提到最高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
