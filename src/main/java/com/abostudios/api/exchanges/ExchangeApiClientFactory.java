package com.abostudios.api.exchanges;

public interface ExchangeApiClientFactory {

    /**
     * Creates a new synchronous/blocking REST client.
     */
    ExchangeApiRestClient newRestClient();

    /**
     * Creates a new asynchronous/non-blocking REST client.
     */
    ExchangeApiAsyncRestClient newAsyncRestClient();

    /**
     * Creates a new web socket client used for handling data streams.
     */
    ExchangeApiWebSocketClient newWebSocketClient();

}
