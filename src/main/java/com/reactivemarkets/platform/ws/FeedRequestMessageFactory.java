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

import java.nio.ByteBuffer;

import org.agrona.concurrent.HighResolutionClock;

import com.google.flatbuffers.FlatBufferBuilder;
import com.reactivemarkets.papi.Body;
import com.reactivemarkets.papi.FeedRequest;
import com.reactivemarkets.papi.Message;
import com.reactivemarkets.papi.SubReqType;
import com.reactivemarkets.platform.fbs.FbsFactory;

public final class FeedRequestMessageFactory {

    private FeedRequestMessageFactory() {
    }

    public static ByteBuffer newSubscription(final FeedRequestParameters request) {
        final FlatBufferBuilder builder = FbsFactory.getFbsBuilder();

        final int reqIdOffset = builder.createString(request.getRequestId());

        final String[] markets = request.getMarkets();
        final int[] marketOffsets = new int[markets.length];
        for (int i = 0; i < markets.length; i++) {
            final int marketOffset = builder.createString(markets[i]);
            marketOffsets[i] = marketOffset;
        }

        final int marketsOffset = FeedRequest.createMarketsVector(builder, marketOffsets);
        final int feedRequestOffset = FeedRequest.createFeedRequest(builder, reqIdOffset, SubReqType.Subscribe,
                request.getFeedType(), request.getGrouping(), request.getDepth(), request.getFrequency(),
                marketsOffset);

        Message.startMessage(builder);
        Message.addTts(builder, HighResolutionClock.epochNanos());
        Message.addBodyType(builder, Body.FeedRequest);
        Message.addBody(builder, feedRequestOffset);
        int message = Message.endMessage(builder);
        builder.finish(message);

        return ByteBuffer.wrap(builder.sizedByteArray());
    }
}
