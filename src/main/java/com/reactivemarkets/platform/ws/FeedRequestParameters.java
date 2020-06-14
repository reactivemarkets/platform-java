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

package com.reactivemarkets.platform.ws;

import java.util.UUID;

import com.reactivemarkets.papi.FeedType;

public class FeedRequestParameters {

    /**
     * Unique identifier of this request to be referenced on Feed Gateway responses.
     */
    private final String requestId;
    /**
     * List of markets to subscribe to in the form <instrument>-<venue>. E.g.
     * BTCUSD-CNB
     */
    private final String[] markets;
    /**
     * Type of feed for the subscription.
     */
    private final short feedType;
    /**
     * Depth of the order book. Top of Book = 1.
     */
    private short depth = 10;
    /**
     * Requested frequency of updates in milliseconds. Note, this value will be
     * rounded up to the nearest data conflation cycle supported by the publisher.
     * For example, if the conflation cycle is 100ms and you request 150ms then you
     * will receive updates every 200ms.
     */
    private short frequency = 1000;
    /**
     * Grouping of order book updates in price ticks. E.g. for BTCUSD at 6501.37, 1
     * would be 6501.37, 10 would be 6501.4, 50 would be 6503.5
     */
    private int grouping = 1;

    public FeedRequestParameters(final short feedType, final String requestId, final String... markets) {
        super();
        this.feedType = feedType;
        this.requestId = requestId;
        this.markets = markets;
    }

    public FeedRequestParameters(final String requestId, final String... markets) {
        this(FeedType.Default, requestId, markets);
    }

    public FeedRequestParameters setDepth(final Short depth) {
        this.depth = depth;
        return this;
    }

    public FeedRequestParameters setFrequency(final short frequency) {
        this.frequency = frequency;
        return this;
    }

    public FeedRequestParameters setGrouping(final int grouping) {
        this.grouping = grouping;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public String[] getMarkets() {
        return markets;
    }

    public short getFeedType() {
        return feedType;
    }

    public short getDepth() {
        return depth;
    }

    public short getFrequency() {
        return frequency;
    }

    public int getGrouping() {
        return grouping;
    }

    public static FeedRequestParameters newTradeFeedRequest(final String... markets) {
        return new FeedRequestParameters(FeedType.Trade, UUID.randomUUID().toString(), markets);
    }

    public static FeedRequestParameters newMarketDataFeedRequest(final short depth, final short frequency,
            final int grouping, final String... markets) {
        return new FeedRequestParameters(FeedType.Default, UUID.randomUUID().toString(), markets).setDepth(depth)
                .setFrequency(frequency).setGrouping(grouping);
    }
}
