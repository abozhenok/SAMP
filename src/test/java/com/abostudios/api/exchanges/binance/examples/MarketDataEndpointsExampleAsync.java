package com.abostudios.api.exchanges.binance.examples;

import com.abostudios.api.exchanges.ExchangeApiAsyncRestClient;
import com.abostudios.api.exchanges.binance.BinanceApiClientFactory;
import com.abostudios.api.domain.market.CandlestickInterval;
import com.abostudios.api.domain.market.OrderBook;
import com.abostudios.api.exchanges.ExchangeApiException;

/**
 * Examples on how to get market data information such as the latest price of a symbol, etc., in an async way.
 */
public class MarketDataEndpointsExampleAsync {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
    ExchangeApiAsyncRestClient client = factory.newAsyncRestClient();

    // Getting depth of a symbol (async)
    client.getOrderBook("NEOETH", 10, (OrderBook response) -> System.out.println(response.getBids()));

    // Getting latest price of a symbol (async)
    client.get24HrPriceStatistics("NEOETH", System.out::println);

    // Getting all latest prices (async)
    client.getAllPrices(System.out::println);

    // Getting agg trades (async)
    client.getAggTrades("NEOETH", System.out::println);

    // Weekly candlestick bars for a symbol
    client.getCandlestickBars("NEOETH", CandlestickInterval.WEEKLY, System.out::println);

    // Book tickers (async)
    client.getBookTickers(System.out::println);

    // Exception handling
    try {
      client.getOrderBook("UNKNOWN", 10, System.out::println);
    } catch (ExchangeApiException e) {
      System.out.println(e.getError().getCode()); // -1121
      System.out.println(e.getError().getMsg());  // Invalid symbol
    }
  }
}
