package com.abostudios.api.exchanges.binance;

import com.abostudios.api.exchanges.ExchangeApiAsyncRestClient;
import com.abostudios.api.exchanges.ExchangeApiClientFactory;
import com.abostudios.api.exchanges.ExchangeApiRestClient;
import com.abostudios.api.exchanges.ExchangeApiWebSocketClient;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiAsyncRestClientImpl;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiRestClientImpl;
import com.abostudios.api.exchanges.binance.impl.ExchangeApiWebSocketClientImpl;

/**
 * A factory for creating BinanceApi client objects.
 */
public class BinanceApiClientFactory implements ExchangeApiClientFactory {

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
  private BinanceApiClientFactory(String apiKey, String secret) {
    this.apiKey = apiKey;
    this.secret = secret;
  }

  /**
   * New instance.
   *
   * @param apiKey the API key
   * @param secret the Secret
   *
   * @return binance api client factory
   */
  public static BinanceApiClientFactory newInstance(String apiKey, String secret) {
      return new BinanceApiClientFactory(apiKey, secret);
  }

  /**
   * New instance without authentication.
   *
   * @return binance api client factory
   */
  public static BinanceApiClientFactory newInstance() {
    return new BinanceApiClientFactory(null, null);
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
