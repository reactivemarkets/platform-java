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

package com.reactivemarkets.platform.example.feed;

import static com.reactivemarkets.platform.ws.tyrus.TyrusWebSocketClientFactory.newWebSocketClient;

import java.nio.ByteBuffer;

import javax.websocket.MessageHandler;
import javax.websocket.MessageHandler.Whole;

import com.reactivemarkets.platform.ws.FeedGatewayException;
import com.reactivemarkets.platform.ws.FeedRequestMessageFactory;
import com.reactivemarkets.platform.ws.FeedRequestParameters;
import com.reactivemarkets.platform.ws.tyrus.TyrusWebSocketClient;

public class FeedClient implements AutoCloseable {

    private final String uri;
    private final String apiKey;
    private final MessageHandler.Whole<ByteBuffer> handler;
    private TyrusWebSocketClient wsClient;

    public FeedClient(final String uri, final String apiKey, final Whole<ByteBuffer> handler) {
        super();
        this.uri = uri;
        this.apiKey = apiKey;
        this.handler = handler;
    }

    /**
     * Connect to the specified feed gateway URL with the supplied authentication
     * token. This call is blocking so on return the FeedClient is ready to use.
     * Note that connect should only be called once. The FeedClient is not reusable.
     *
     * @throws FeedGatewayException if there was a failure to connect.
     */
    public void connect() throws FeedGatewayException {
        wsClient = newWebSocketClient(uri, apiKey, handler);
        wsClient.connect();
    }

    /**
     * Subscribe to the feed as define in the FeedRequestParameters. This call
     * returns once the message has been sent but any response is asynchronous.
     *
     * @param request - the parameters for the subscription
     * @throws FeedGatewayException if the subscription failed to be sent
     */
    public void subscribe(final FeedRequestParameters request) throws FeedGatewayException {
        final ByteBuffer buffer = FeedRequestMessageFactory.newSubscription(request);
        wsClient.send(buffer);
    }

    @Override
    public void close() {
        wsClient.close();
    }
}
