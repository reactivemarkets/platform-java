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

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.MessageHandler.Whole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reactivemarkets.platform.util.LoggerUtil;
import com.reactivemarkets.platform.ws.FeedGatewayException;
import com.reactivemarkets.platform.ws.FeedRequestParameters;

public final class FeedGatewayL2Subscription {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedGatewayL2Subscription.class);
    private static final String LIVE_URI = "wss://api.platform.reactivemarkets.com/feed";
    // API key is generated from the platform UI in the keys section
    private static final String API_KEY = "<insert your token here or use the REACTIVE_FEED_GATEWAY_URI environment variable>";

    private FeedGatewayL2Subscription() {
    }

    public static void main(final String[] args) {

        LoggerUtil.consoleLogger();

        // load the uri and auth token from either environment variables or updated
        // constants
        final Map<String, String> env = System.getenv();
        final String uri = env.getOrDefault("REACTIVE_FEED_GATEWAY_URI", LIVE_URI);
        final String apiKey = env.getOrDefault("REACTIVE_PLATFORM_API_KEY", API_KEY);
        LOGGER.info("Attempting connection to {} with key {}", uri, apiKey);

        // create a listener that will log the first 10 messages.
        final CountDownLatch latch = new CountDownLatch(10);
        final FeedListener listener = newCountdownListener(latch);
        final Whole<ByteBuffer> handler = new FeedMessageHandler(listener);

        try (FeedClient client = new FeedClient(uri, apiKey, handler)) {
            // blocking connect to the websocket
            client.connect();
            // create a unique request id for this request - this will be returned on the
            // subscription Accept/Reject
            final String requestId = UUID.randomUUID().toString();
            // create a new request with default settings for conflation, depth and grouping
            final FeedRequestParameters request = new FeedRequestParameters(requestId, "BTCUSD-CNB", "BCHUSD-CNB");
            // currently supports fixed sizes at 1, 5, 10, 20
            request.setDepth((short) 5);
            // currently supports fixed sizes at 1 (i.e. raw) and 50 for Coinbase
            request.setGrouping(50);
            // set the update frequency to 1000ms
            request.setFrequency((short) 1000);

            client.subscribe(request);
            // wait for the data countdown to complete or timeout after 15 seconds
            if (latch.await(15, TimeUnit.SECONDS)) {
                LOGGER.info("Example completed successfully");
            }
        } catch (FeedGatewayException ex) {
            LOGGER.error("Error connecting to feed websocket.", ex);
        } catch (InterruptedException e) {
            LOGGER.error("Error whilst waiting for data.", e);
            Thread.currentThread().interrupt();
        }
    }

    private static FeedListener newCountdownListener(final CountDownLatch latch) {
        return new FeedListener() {

            @Override
            public void onFeedRequestAck(final RequestAck ack) {
                LOGGER.info("{}", ack);
            }

            @Override
            public void onFeedRequestReject(final RequestReject reject) {
                LOGGER.warn("{}", reject);
            }

            @Override
            public void onMarketDepth(final MarketDepth depth) {
                LOGGER.info("{}", depth);
                latch.countDown();
            }
        };
    }
}
