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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.MessageHandler.Whole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reactivemarkets.platform.util.LoggerUtil;
import com.reactivemarkets.platform.ws.FeedGatewayException;
import com.reactivemarkets.platform.ws.FeedRequestParameters;

public final class FeedGatewayLiquidationSubscription {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedGatewayLiquidationSubscription.class);
    private static final String LIVE_URI = "wss://api.platform.reactivemarkets.com/feed";
    // API key is generated from the platform UI in the keys section
    private static final String API_KEY = "<insert your token here or use the REACTIVE_FEED_GATEWAY_URI environment variable>";

    private FeedGatewayLiquidationSubscription() {
    }

    public static void main(final String[] args) {

        LoggerUtil.consoleLogger();

        // load the uri and auth token from either environment variables or updated
        // constants
        final Map<String, String> env = System.getenv();
        final String uri = env.getOrDefault("REACTIVE_FEED_GATEWAY_URI", LIVE_URI);
        final String apiKey = env.getOrDefault("REACTIVE_PLATFORM_API_KEY", API_KEY);
        LOGGER.info("Attempting connection to {} with key {}", uri, apiKey);

        // create a listener that will log the first 5 messages.
        final CountDownLatch latch = new CountDownLatch(5);
        final FeedListener listener = newCountdownListener(latch);
        final Whole<ByteBuffer> handler = new FeedMessageHandler(listener);

        // auto close client resources
        try (FeedClient client = new FeedClient(uri, apiKey, handler)) {
            // blocking connect to the websocket
            client.connect();
            // create a new request with default settings for conflation, depth and grouping
            final FeedRequestParameters request = FeedRequestParameters.newLiquidationFeedRequest("XBTUSD-BMX");

            client.subscribe(request);
            // wait for the data countdown to complete or timeout after 30 seconds
            if (latch.await(30, TimeUnit.SECONDS)) {
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
            }

            @Override
            public void onTrade(final Trade trade) {
                LOGGER.info("{}", trade);
            }

            @Override
            public void onLiquidationOrder(final Liquidation liquidation) {
                LOGGER.info("{}", liquidation);
                latch.countDown();
            }
        };
    }
}
