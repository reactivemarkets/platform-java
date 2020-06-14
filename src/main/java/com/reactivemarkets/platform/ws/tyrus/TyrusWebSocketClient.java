/*
 * Copyright (C) 2020 Reactive Markets Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reactivemarkets.platform.ws.tyrus;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ClientEndpointConfig.Builder;
import javax.websocket.ClientEndpointConfig.Configurator;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reactivemarkets.platform.ws.FeedGatewayException;

/**
 * TyrusWebSocketClient is a websocket client implementation based on the Tyrus
 * javax.websocket implementation. This implementation has drawn from examples
 * in
 * https://abhirockzz.gitbooks.io/java-websocket-api-handbook/content/Receiving%20Messages.html
 * <p>
 * TyrusWebSocketClient are not intended for use by multiple threads and the
 * connect, close, send and isConnected methods are not thread-safe.
 */
public class TyrusWebSocketClient extends Endpoint implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TyrusWebSocketClient.class);
    private final URI uri;
    private final String apiKey;
    private final MessageHandler handler;
    private Session session;

    public TyrusWebSocketClient(final String uri, final String apiKey, final MessageHandler.Whole<ByteBuffer> handler) {
        super();
        this.uri = URI.create(uri);
        this.apiKey = apiKey;
        this.handler = handler;
    }

    public void connect() throws FeedGatewayException {

        final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        final Builder configBuilder = ClientEndpointConfig.Builder.create();
        configBuilder.configurator(new Configurator() {
            public void beforeRequest(final Map<String, List<String>> headers) {
                headers.put("Authorization", Arrays.asList("Bearer " + apiKey));
            }
        });
        final ClientEndpointConfig config = configBuilder.build();
        try {
            session = container.connectToServer(this, config, uri);
        } catch (DeploymentException | IOException e) {
            throw new FeedGatewayException("Failed to connect to endpoint: " + uri, e);
        }
    }

    @Override
    public void close() {
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void onOpen(final Session session, final EndpointConfig config) {
        session.addMessageHandler(handler);
    }

    @Override
    public void onClose(final Session session, final CloseReason closeReason) {
        LOGGER.warn("WebSocket session closed: {}", closeReason.getReasonPhrase());
    }

    @Override
    public void onError(final Session session, final Throwable thr) {
        LOGGER.error("WebSocket session error.", thr);
    }

    /*
     * Blocking send of the message
     */
    public void send(final ByteBuffer buf) throws FeedGatewayException {
        try {
            session.getBasicRemote().sendBinary(buf);
        } catch (IOException e) {
            throw new FeedGatewayException(e);
        }
    }

    public boolean isConnected() {
        return session != null && session.isOpen();
    }
}
