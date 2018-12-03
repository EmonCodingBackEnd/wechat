package com.coding.component.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {

    // 静态变量，用来记录当前在线用户信息
    private static ConcurrentHashMap<String, WebSocketServer> userSession =
            new ConcurrentHashMap<>();

    // 静态变量，用来记录当前在线连接数
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    // 用户标志
    private String userId;

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /** 连接建立成功调用的方法 */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        this.userId = userId;
        this.session = session;
        userSession.put(userId, this);
        addOnlineCount();

        log.info(
                "【WebSocket】连接成功，onlineCount={},userId={},sessionId={}",
                getOnlineCount(),
                userId,
                session.getId());
        try {
            sendMessage("WebSocket连接成功");
        } catch (IOException e) {
            log.error(String.format("【WebSocket】连接成功时发送数据异常, userId=%s", userId), e);
        }
    }

    /** 连接关闭调用的方法 */
    @OnClose
    public void onClose() {
        userSession.remove(userId);
        subOnlineCount(); // 在线数减1
        log.info(
                "【WebSocket】连接关闭，onlineCount={},userId={},sessionId={}",
                getOnlineCount(),
                userId,
                session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【WebSocket】来自客户端的消息, userId={}, message={}", userId, message);

        // 群发消息
        for (Map.Entry<String, WebSocketServer> entry : userSession.entrySet()) {
            if (!entry.getKey().equals(userId)) {
                try {
                    entry.getValue().sendMessage(String.format("本消息来自[%s]:%s", userId, message));
                } catch (IOException e) {
                    log.error(
                            String.format(
                                    "【WebSocket】发送消息错误,userId=%s, message=%s",
                                    userId, session.getId()),
                            e);
                }
            }
        }
    }

    /**
     * @param session -
     * @param error -
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(
                String.format(
                        "【WebSocket】通讯发生异常, onlineCount=%s,userId=%s,sessionId=%s",
                        getOnlineCount(), userId, session.getId()),
                error);
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 是否存在关于某个用户的连接.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180718 15:21</font><br>
     * [请在此输入功能详述]
     *
     * @param userId - 用户ID
     * @return boolean true:存在;false:不存在
     * @author Rushing0711
     * @since 1.0.0
     */
    public static boolean existConnextion(String userId) {
        return userSession.containsKey(userId);
    }

    /**
     * 单发自定义消息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180718 15:24</font><br>
     * [请在此输入功能详述]
     *
     * @param userId - 用户ID
     * @param message - 要发送的信息
     * @author Rushing0711
     * @since 1.0.0
     */
    public static void sendWebSocketMessage(String userId, String message) throws IOException {
        log.info("【WebSocket】单发送自定义消息， userId={}, message={}", userId, message);
        userSession.get(userId).sendMessage(message);
    }

    /**
     * 群发自定义消息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180718 15:24</font><br>
     * 群发信息
     *
     * @param message - 群发的信息
     * @author Rushing0711
     * @since 1.0.0
     */
    public static void sendWebSocketMessage(String message) throws IOException {
        log.info("【WebSocket】群发送自定义消息 message={}", message);
        // 群发消息
        for (Map.Entry<String, WebSocketServer> entry : userSession.entrySet()) {
            try {
                entry.getValue().sendMessage(message);
            } catch (IOException e) {
                log.error(
                        String.format(
                                "【WebSocket】发送消息错误,userId=%s, message=%s",
                                entry.getKey(), entry.getValue().session.getId()),
                        e);
            }
        }
    }

    /**
     * 群发自定义消息，发送给指定的人.
     *
     * <p>创建时间: <font style="color:#00FFFF">20181203 12:32</font><br>
     * [请在此输入功能详述]
     *
     * @param userIdList
     * @param message
     * @return void
     * @author Rushing0711
     * @since 1.0.0
     */
    public static void sendWebSocketMessage(List<String> userIdList, String message)
            throws IOException {
        log.info("【WebSocket】群发送自定义消息 message={}", message);
        // 群发消息
        for (Map.Entry<String, WebSocketServer> entry : userSession.entrySet()) {
            try {
                if (userIdList.contains(entry.getKey())) {
                    entry.getValue().sendMessage(message);
                }
            } catch (IOException e) {
                log.error(
                        String.format(
                                "【WebSocket】发送消息错误,userId=%s, message=%s",
                                entry.getKey(), entry.getValue().session.getId()),
                        e);
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    private static synchronized void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    private static synchronized void subOnlineCount() {
        onlineCount.decrementAndGet();
    }
}
