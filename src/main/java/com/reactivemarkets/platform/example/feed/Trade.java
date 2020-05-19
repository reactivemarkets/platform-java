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

public class Trade {

    private long timestamp;
    private long sourceTimestamp;
    private String source;
    private String market;
    private int feedId;
    private String tradeId;
    private int flags;
    private String side;
    private double qty;
    private double price;
    private String executionVenue;

    public long getSourceTimestamp() {
        return sourceTimestamp;
    }

    public void setSourceTimestamp(final long sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(final String market) {
        this.market = market;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(final int feedId) {
        this.feedId = feedId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(final String tradeId) {
        this.tradeId = tradeId;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(final int flags) {
        this.flags = flags;
    }

    public String getSide() {
        return side;
    }

    public void setSide(final String side) {
        this.side = side;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(final double qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public String getExecutionVenue() {
        return executionVenue;
    }

    public void setExecutionVenue(final String executionVenue) {
        this.executionVenue = executionVenue;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Trade [timestamp=" + timestamp + ", sourceTimestamp=" + sourceTimestamp + ", source=" + source
                + ", market=" + market + ", feedId=" + feedId + ", tradeId=" + tradeId + ", flags=" + flags + ", side="
                + side + ", qty=" + qty + ", price=" + price + ", executionVenue=" + executionVenue + "]";
    }
}
