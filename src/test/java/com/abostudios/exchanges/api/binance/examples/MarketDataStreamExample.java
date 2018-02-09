package com.abostudios.exchanges.api.binance.examples;

import com.abostudios.exchanges.api.binance.BinanceApiClientFactory;
import com.abostudios.exchanges.api.binance.BinanceApiWebSocketClient;
import com.abostudios.exchanges.api.binance.domain.market.CandlestickInterval;

import java.io.IOException;

/**
 * Market data stream endpoints examples.
 *
 * It illustrates how to create a stream to obtain updates on market data such as executed trades.
 */
public class MarketDataStreamExample {

  public static void main(String[] args) throws InterruptedException, IOException {
    BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();

    // Listen for aggregated trade events for ETH/BTC
    client.onAggTradeEvent("ethbtc", System.out::println);

    // Listen for changes in the order book in ETH/BTC
    client.onDepthEvent("ethbtc", System.out::println);

    // Obtain 1m candlesticks in real-time for ETH/BTC
    client.onCandlestickEvent("ethbtc", CandlestickInterval.ONE_MINUTE, System.out::println);
  }
}
