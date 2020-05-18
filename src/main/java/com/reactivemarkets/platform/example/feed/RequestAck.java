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

public class RequestAck {

    private long timestamp;
    private String requestId;
    private int feedId;

    public RequestAck(final long timestamp, final String requestId, final int feedId) {
        super();
        this.timestamp = timestamp;
        this.requestId = requestId;
        this.feedId = feedId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(final String requestId) {
        this.requestId = requestId;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(final int feedId) {
        this.feedId = feedId;
    }

    @Override
    public String toString() {
        return "RequestAck [timestamp=" + timestamp + ", requestId=" + requestId + ", feedId=" + feedId + "]";
    }
}
