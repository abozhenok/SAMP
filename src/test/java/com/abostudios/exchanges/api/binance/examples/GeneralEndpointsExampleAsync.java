package com.abostudios.exchanges.api.binance.examples;

import com.abostudios.exchanges.api.binance.BinanceApiAsyncRestClient;
import com.abostudios.exchanges.api.binance.BinanceApiClientFactory;
import com.abostudios.exchanges.api.binance.domain.general.FilterType;
import com.abostudios.exchanges.api.binance.domain.general.SymbolFilter;
import com.abostudios.exchanges.api.binance.domain.general.SymbolInfo;

/**
 * Examples on how to use the general endpoints.
 */
public class GeneralEndpointsExampleAsync {

  public static void main(String[] args) throws InterruptedException {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
    BinanceApiAsyncRestClient client = factory.newAsyncRestClient();

    // Test connectivity
    client.ping(response -> System.out.println("Ping succeeded."));

    // Check server time
    client.getServerTime(response -> System.out.println(response.getServerTime()));

    // Exchange info
    client.getExchangeInfo(exchangeInfo -> {
      System.out.println(exchangeInfo.getTimezone());
      System.out.println(exchangeInfo.getSymbols());

      // Obtain symbol information
      SymbolInfo symbolInfo = exchangeInfo.getSymbolInfo("ETHBTC");
      System.out.println(symbolInfo.getStatus());

      SymbolFilter priceFilter = symbolInfo.getSymbolFilter(FilterType.PRICE_FILTER);
      System.out.println(priceFilter.getMinPrice());
      System.out.println(priceFilter.getTickSize());
    });
  }
}
