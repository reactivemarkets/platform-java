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

import javax.websocket.MessageHandler;

import com.reactivemarkets.papi.Body;
import com.reactivemarkets.papi.FeedRequestAccept;
import com.reactivemarkets.papi.FeedRequestReject;
import com.reactivemarkets.papi.MDLevel2;
import com.reactivemarkets.papi.MDSnapshotL2;
import com.reactivemarkets.papi.Message;

public class FeedMessageHandler implements MessageHandler.Whole<ByteBuffer> {

    private final FeedListener callbackHandler;
    private static final ThreadLocal<Message> LOCAL_MESSAGE = ThreadLocal.withInitial(Message::new);
    private static final ThreadLocal<MDSnapshotL2> LOCAL_L2_SNAPSHOT = ThreadLocal.withInitial(MDSnapshotL2::new);
    private static final ThreadLocal<FeedRequestAccept> LOCAL_FEED_REQUEST_ACK = ThreadLocal
            .withInitial(FeedRequestAccept::new);
    private static final ThreadLocal<MDLevel2> LOCAL_MDLEVEL = ThreadLocal.withInitial(MDLevel2::new);
    private static final ThreadLocal<FeedRequestReject> LOCAL_REQUEST_REJECT = ThreadLocal
            .withInitial(FeedRequestReject::new);

    public FeedMessageHandler(final FeedListener mdListener) {
        this.callbackHandler = mdListener;
    }

    public void onMessage(final ByteBuffer buf) {
        final Message msg = LOCAL_MESSAGE.get();
        Message.getRootAsMessage(buf, msg);
        final long timestamp = msg.tts();

        switch (msg.bodyType()) {
        case Body.FeedRequestAccept:
            final FeedRequestAccept ack = LOCAL_FEED_REQUEST_ACK.get();
            msg.body(ack);
            onFeedRequestAck(timestamp, ack);
            break;
        case Body.MDSnapshotL2:
            final MDSnapshotL2 ss = LOCAL_L2_SNAPSHOT.get();
            msg.body(ss);
            onLevel2Snapshot(timestamp, ss);
            break;
        case Body.FeedRequestReject:
            final FeedRequestReject reqRej = LOCAL_REQUEST_REJECT.get();
            msg.body(reqRej);
            onRejectedRequest(timestamp, reqRej);
            break;
        default:
            throw new IllegalStateException("Unexpected message type.");
        }
    }

    private void onFeedRequestAck(final long timestamp, final FeedRequestAccept reqAck) {
        RequestAck ack = new RequestAck(timestamp, reqAck.reqId(), reqAck.feedId());
        callbackHandler.onFeedRequestAck(ack);
    }

    private void onLevel2Snapshot(final long timestamp, final MDSnapshotL2 ss) {
        final int bidCount = ss.bidSideLength();
        final int offerCount = ss.offerSideLength();
        final MarketDepth depth = new MarketDepth(bidCount, offerCount);
        depth.setId(ss.id());
        depth.setFeedId(ss.feedId());
        depth.setDepth(ss.depth());
        depth.setSource(ss.source());
        depth.setFlags(ss.flags());
        depth.setBidCount(ss.bidSideLength());
        depth.setOfferCount(ss.offerSideLength());
        depth.setTimestamp(timestamp);
        depth.setSourceTimestamp(ss.sourceTs());
        depth.setSymbol(ss.market());

        final MDLevel2 l2 = LOCAL_MDLEVEL.get();
        for (int i = 0; i < ss.bidSideLength(); i++) {
            ss.bidSide(l2, i);
            depth.setBidQty(l2.qty(), i);
            depth.setBidPrice(l2.price(), i);
        }

        for (int i = 0; i < ss.offerSideLength(); i++) {
            ss.offerSide(l2, i);
            depth.setOfferQty(l2.qty(), i);
            depth.setOfferPrice(l2.price(), i);
        }

        callbackHandler.onMarketDepth(depth);
    }

    private void onRejectedRequest(final long timestamp, final FeedRequestReject reqRej) {
        RequestReject reject = new RequestReject();
        reject.setTimestamp(timestamp);
        reject.setRequestId(reqRej.reqId());
        reject.setErrorCode(reqRej.errorCode());
        reject.setErrorMessage(reqRej.errorMessage());
        callbackHandler.onFeedRequestReject(reject);
    }
}
