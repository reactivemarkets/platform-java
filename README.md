# Platform Java

The Reactive Platform API for Java.

## Building

### Linux/Mac

```bash
git clone https://github.com/reactivemarkets/platform-java
cd platform-java
./gradlew build
```

### Windows

```bash
git clone https://github.com/reactivemarkets/platform-java
cd platform-java
./gradlew.bat build
```

## Using the API

### Feed Gateway WebSocket API

The feed gateway provides an API to a binary feed over websockets using the [Google Flatbuffers](https://google.github.io/flatbuffers/) serialisation library. The API provides a flexible subscription model to stream of Level 2 market data snapshots and public trades. Subscriptions are made using a `FeedRequest` message resulting in a stream of `MDSnapshotL2` or `PublicTrade` messages. Invalid or failed subscriptions will return a `FeedRequestReject`.

A full example of subscribing to and consuming from the level 2 market data feed can be seen in the `com.reactivemarkets.platform.example.feed.FeedGatewayL2Subscription` example. You will need an authorisation token to access the API which is generated against your authenticated login in the [Reactive Markets platform](https://platform.reactivemarkets.com/); instructions are inthe next section. The generated token must then be inserted into the websocket headers in the form "Bearer your\_token". Examples of this using the Tyrus or java_websocket library can be seen below.

Flatbuffers provides an efficient serialization/deserializaton mechanism in terms of both processing and space requirements. The `com.reactivemarkets.platform.example.feed.FeedMessageHandler` provides an example of how to consume the binary feed into your application. Note, the generated java classes for our Flatbuffers schema are located in the `com.reactivemarkets.encoding.feed` package.

#### Generating the authorisation token

Creating an authorisation token is a simple three step process:

1. Log in to the Reactive Markets platform [here](https://platform.reactivemarkets.com/)
2. Select the API keys option from the pop out menu in the top left of the UI.
3. Select 'Create new API key'

#### Tyrus Token Example
```java
final Builder configBuilder = ClientEndpointConfig.Builder.create();
configBuilder.configurator(new Configurator() {
    public void beforeRequest(final Map<String, List<String>> headers) {
        headers.put("Authorization", Arrays.asList("Bearer " + authToken));
    }
});
```

#### java_websocket Token Example (TooTallNate)
```java
Map<String, String> httpHeaders = new HashMap<>();
httpHeaders.put("Authorization", "Bearer " + authToken);
return new JWSWebSocketClient(uri, httpHeaders, handler);
```

### Feed Gateway Reference Data

Reference data for use with the API is available via the REST API [here](https://api.platform.reactivemarkets.com/reference/markets). Reference data is specified on a per market basis where a market is an instrument on a venue e.g. BTCUSD-CNB or BTCUSD on Coinbase. For the market data feed the following fields are relevant:

* symbol: the symbol that should be used for subscribing to the market e.g. "BCHUSD-CNB"
* tick\_numer and tick\_denom: describe the numerator and denominator for market data tick sizes. For example, tick\_numer=1 and tick\_denom=100 would indicate the data should be formatted to 1/100 or 0.01.
* display, description, instr & venue should all be self explanatory

## Help and Support
If you require any help/support, or wish to raise a new feature request, then please contact us via the support desk [here](https://reactivemarkets.atlassian.net/servicedesk/customer/portals).

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process
for submitting pull requests.

## Versioning

We use [SemVer](https://semver.org/) for versioning. For the versions available, see the [releases
page](https://github.com/reactivemarkets/platform-java/releases).

## License

This project is licensed under the [Apache 2.0
License](https://www.apache.org/licenses/LICENSE-2.0). A copy of the license is available in the
[LICENSE.md](LICENSE.md) file in the root directory of the source tree.
