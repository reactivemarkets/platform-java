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

import java.util.Arrays;

public class MarketDepth {

    private long id;
    private int feedId;
    private int depth;
    private long flags;
    private long timestamp;
    private long sourceTimestamp;
    private String symbol;
    private String source;
    private int bidCount;
    private int offerCount;
    private double[] bidPrice;
    private double[] bidQty;
    private double[] offerPrice;
    private double[] offerQty;

    public MarketDepth(final int bidCount, final int offerCount) {
        this.bidCount = bidCount;
        this.offerCount = offerCount;
        this.bidPrice = new double[bidCount];
        this.bidQty = new double[bidCount];
        this.offerPrice = new double[offerCount];
        this.offerQty = new double[offerCount];
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getFlags() {
        return flags;
    }

    public void setFlags(final long flags) {
        this.flags = flags;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(final int bidCount) {
        this.bidCount = bidCount;
    }

    public int getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(final int offerCount) {
        this.offerCount = offerCount;
    }

    public double[] getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(final double[] bidPrice) {
        this.bidPrice = bidPrice;
    }

    public double[] getBidQty() {
        return bidQty;
    }

    public void setBidQty(final double[] bidQty) {
        this.bidQty = bidQty;
    }

    public double[] getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(final double[] offerPrice) {
        this.offerPrice = offerPrice;
    }

    public double[] getOfferQty() {
        return offerQty;
    }

    public void setOfferQty(final double[] offerQty) {
        this.offerQty = offerQty;
    }

    public void setOfferQty(final double offerQty, final int i) {
        this.offerQty[i] = offerQty;
    }

    public void setOfferPrice(final double offerPrice, final int i) {
        this.offerPrice[i] = offerPrice;
    }

    public void setBidQty(final double bidQty, final int i) {
        this.bidQty[i] = bidQty;
    }

    public void setBidPrice(final double bidPrice, final int i) {
        this.bidPrice[i] = bidPrice;
    }

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

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(final int feedId) {
        this.feedId = feedId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(final int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "MarketDepth [id=" + id + ", feedId=" + feedId + ", depth=" + depth + ", flags=" + flags + ", timestamp="
                + timestamp + ", sourceTimestamp=" + sourceTimestamp + ", symbol=" + symbol + ", source=" + source
                + ", bidCount=" + bidCount + ", offerCount=" + offerCount + ", bidPrice=" + Arrays.toString(bidPrice)
                + ", bidQty=" + Arrays.toString(bidQty) + ", offerPrice=" + Arrays.toString(offerPrice) + ", offerQty="
                + Arrays.toString(offerQty) + "]";
    }
}
