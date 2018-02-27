package com.abostudios.api.exchanges.gemini;

import com.abostudios.api.exchanges.ExchangeApiAsyncRestClient;
import com.abostudios.api.exchanges.ExchangeApiClientFactory;
import com.abostudios.api.exchanges.ExchangeApiRestClient;
import com.abostudios.api.exchanges.ExchangeApiWebSocketClient;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiAsyncRestClientImpl;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiRestClientImpl;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiWebSocketClientImpl;

public class GeminiApiClientFactory implements ExchangeApiClientFactory {
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
    private GeminiApiClientFactory(String apiKey, String secret) {
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
    public static GeminiApiClientFactory newInstance(String apiKey, String secret) {
        return new GeminiApiClientFactory(apiKey, secret);
    }

    /**
     * New instance without authentication.
     *
     * @return gemini api client factory
     */
    public static GeminiApiClientFactory newInstance() {
        return new GeminiApiClientFactory(null, null);
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
