/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.ws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @ServerEndpoint("/ws")
@Slf4j
public class WebsocketsHandler {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        log.info(
            "session [{}] connection established on IP [{}].",
            session.getId(),
            session
        );
        sessions.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("session [{}] has disconnected.", session.getId());
        sessions.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {}

    @OnMessage
    public void onMessage(String message) {}

    private void broadcast(String message) {
        sessions
            .values()
            .forEach(
                s -> {
                    s
                        .getAsyncRemote()
                        .sendObject(
                            message,
                            result -> {
                                if (result.getException() != null) {
                                    System.out.println(
                                        "Unable to send message: " + result.getException()
                                    );
                                }
                            }
                        );
                }
            );
    }
}
