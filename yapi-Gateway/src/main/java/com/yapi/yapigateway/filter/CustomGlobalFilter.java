package com.yapi.yapigateway.filter;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.yapi.yapi0clientsdk.utils.SignUtils;
import com.yapi.yapicommon.model.entity.InterfaceInfo;
import com.yapi.yapicommon.model.entity.User;
import com.yapi.yapicommon.service.InnerInterfaceInfoService;
import com.yapi.yapicommon.service.InnerUserInterfaceInfoService;
import com.yapi.yapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Yuuue
 * creat by 2023-09-02
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserInterfaceInfoService userInterfaceInfoService;
    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    @DubboReference
    private InnerUserService userService;

    private static final List<String> IP_WITHE_LIST = Arrays.asList("127.0.0.1","localhost");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //用户发送请求到API网关
        ServerHttpRequest request = exchange.getRequest();
        String path = "http://localhost:8123"+request.getPath().value();
        URI uri = request.getURI();
        String host = uri.getHost();
        String method = request.getMethod().toString();
//        InetSocketAddress remoteAddress = request.getRemoteAddress();
        String localAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("address"+host);
        log.info("path"+path);
        log.info("url"+uri);
        log.info("custom global filter");
        ServerHttpResponse response = exchange.getResponse();

        //白名单
        if (!IP_WITHE_LIST.contains(host)){
            return getVoidMono(response);
        }
        String accessKey = request.getHeaders().getFirst("accessKey");
        String body = request.getHeaders().getFirst("body");
        String nonce = request.getHeaders().getFirst("nonce");
        String timestamp = request.getHeaders().getFirst("timestamp");
        String sign = request.getHeaders().getFirst("sign");

//        if (!Objects.equals(accessKey, "yupi")){
//            throw new RuntimeException("无权限");
//        }
        User loginUser = null;
     try {
         //getLoginUser里泡过异常 这里最好try
         loginUser = userService.getLoginUser(accessKey);
     }catch (Exception e){
         log.info("error"+e);
     }
     if (loginUser==null){
         return getNoVoidMono(response);
     }

        if (!Objects.equals(sign, SignUtils.getSign1(body, loginUser.getSecretKey()))){
            return getNoVoidMono(response);
        }
        //TODO 从数据库中查询接口是否存在
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = interfaceInfoService.getInterface(path, method);
        }catch (Exception e){
            log.info("error"+e);
        }

        if (interfaceInfo==null){
            return getNoVoidMono(response);
        }
        //todo 校验接口是否还有调用次数
        if (!userInterfaceInfoService.canInvoke(interfaceInfo.getId(),loginUser.getId())){
            log.info("invoke error :接口调用次数不足" );
            return getNoVoidMono(response);
        }
        return filter1(exchange,chain,loginUser.getId(),interfaceInfo.getId());
    }

    public Mono<Void> filter1(ServerWebExchange exchange, GatewayFilterChain chain,long loginUserId,long interfaceInfoId) {
        try {
            //获取原来的响应
            ServerHttpResponse originalResponse = exchange.getResponse();
            //缓存数据工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if(statusCode == HttpStatus.OK){
                //装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    //等调用完妆发接口才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //往返回值里写数据
                            //拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        //TODO 调用成功后，次数加一

                                        try {
                                            userInterfaceInfoService.invokeCount(interfaceInfoId,loginUserId);
                                        } catch (Exception e) {
                                            log.info("error"+e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        sb2.append("<--- {} {} \n");
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
//                                rspArgs.add(requestUrl);
                                        String data = new String(content, StandardCharsets.UTF_8);//data
                                        sb2.append(data);
                                        log.info(sb2.toString(), rspArgs.toArray());//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }


    /**
     * forbid
     * @param response
     * @return
     */
    private static Mono<Void> getVoidMono(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    /**
     * 服务器内部异常
     * @param response
     * @return
     */
    private static Mono<Void> getNoVoidMono(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
