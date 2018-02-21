package com.abostudios.api.binance.examples;

import com.abostudios.api.binance.BinanceApiClientFactory;
import com.abostudios.api.binance.BinanceApiWebSocketClient;
import com.abostudios.api.binance.BinanceApiClientFactory;
import com.abostudios.api.binance.BinanceApiWebSocketClient;

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
