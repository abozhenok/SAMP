package com.abostudios.api.exchanges.binance.examples;

import com.abostudios.api.exchanges.binance.BinanceApiClientFactory;
import com.abostudios.api.exchanges.ExchangeApiRestClient;
import com.abostudios.api.domain.general.ExchangeInfo;
import com.abostudios.api.domain.general.FilterType;
import com.abostudios.api.domain.general.SymbolFilter;
import com.abostudios.api.domain.general.SymbolInfo;

/**
 * Examples on how to use the general endpoints.
 */
public class GeneralEndpointsExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
    ExchangeApiRestClient client = factory.newRestClient();

    // Test connectivity
    client.ping();

    // Check server time
    long serverTime = client.getServerTime();
    System.out.println(serverTime);

    // ExchangeApiClientFactory info
    ExchangeInfo exchangeInfo = client.getExchangeInfo();
    System.out.println(exchangeInfo.getTimezone());
    System.out.println(exchangeInfo.getSymbols());

    // Obtain symbol information
    SymbolInfo symbolInfo = exchangeInfo.getSymbolInfo("ETHBTC");
    System.out.println(symbolInfo.getStatus());

    SymbolFilter priceFilter = symbolInfo.getSymbolFilter(FilterType.PRICE_FILTER);
    System.out.println(priceFilter.getMinPrice());
    System.out.println(priceFilter.getTickSize());
  }
}
