package com.abostudios.api.exchanges.gdax;

import com.abostudios.api.exchanges.ExchangeApiAsyncRestClient;
import com.abostudios.api.exchanges.ExchangeApiClientFactory;
import com.abostudios.api.exchanges.ExchangeApiRestClient;
import com.abostudios.api.exchanges.ExchangeApiWebSocketClient;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiAsyncRestClientImpl;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiRestClientImpl;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiWebSocketClientImpl;

public class GDAXApiClientFactory implements ExchangeApiClientFactory {
    /**
     * API Key
     */
    private final String apiKey;

    /**
     * Secret.
     */
    private final String secret;

    /**
     * Instantiates a new binance api client factory.
     *
     * @param apiKey the API key
     * @param secret the Secret
     */
    private GDAXApiClientFactory(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    /**
     * New instance.
     *
     * @param apiKey the API key
     * @param secret the Secret
     *
     * @return gemini api client factory
     */
    public static GDAXApiClientFactory newInstance(String apiKey, String secret) {
        return new GDAXApiClientFactory(apiKey, secret);
    }

    /**
     * New instance without authentication.
     *
     * @return gemini api client factory
     */
    public static GDAXApiClientFactory newInstance() {
        return new GDAXApiClientFactory(null, null);
    }

    @Override
    public ExchangeApiRestClient newRestClient() {
        return new ExchangeApiRestClientImpl(apiKey, secret);
    }

    @Override
    public ExchangeApiAsyncRestClient newAsyncRestClient() { return new ExchangeApiAsyncRestClientImpl(apiKey, secret); }

    @Override
    public ExchangeApiWebSocketClient newWebSocketClient() {
        return new ExchangeApiWebSocketClientImpl();
    }
}
