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

public class FeedGatewayException extends Exception {

    private static final long serialVersionUID = -2158080208144828335L;

    public FeedGatewayException(final String msg, final Throwable t) {
        super(msg, t);
    }

    public FeedGatewayException(final Throwable t) {
        super(t);
    }
}
