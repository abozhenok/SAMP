package com.abostudios.exchanges.api.binance.examples;

import com.abostudios.exchanges.api.binance.BinanceApiClientFactory;
import com.abostudios.exchanges.api.binance.BinanceApiWebSocketClient;

/**
 * All market tickers channel examples.
 *
 * It illustrates how to create a stream to obtain all market tickers.
 */
public class AllMarketTickersExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
    BinanceApiWebSocketClient client = factory.newWebSocketClient();

    client.onAllMarketTickersEvent(System.out::println);
  }
}
