package com.abostudios.api.exchanges.gdax.constant;

import com.abostudios.api.exchanges.ExchangeApiAsyncRestClient;
import com.abostudios.api.exchanges.ExchangeApiClientFactory;
import com.abostudios.api.exchanges.ExchangeApiRestClient;
import com.abostudios.api.exchanges.ExchangeApiWebSocketClient;

public class GDAXApiConstants implements ExchangeApiClientFactory {

    @Override
    public ExchangeApiRestClient newRestClient() {
        return null;
    }

    @Override
    public ExchangeApiAsyncRestClient newAsyncRestClient() {
        return null;
    }

    @Override
    public ExchangeApiWebSocketClient newWebSocketClient() {
        return null;
    }
}
