package com.abostudios.api.exchanges.binance.examples;

import com.abostudios.api.exchanges.ExchangeApiAsyncRestClient;
import com.abostudios.api.exchanges.binance.BinanceApiClientFactory;
import com.abostudios.api.domain.account.Account;

/**
 * Examples on how to get account information.
 */
public class AccountEndpointsExampleAsync {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("YOUR_API_KEY", "YOUR_SECRET");
    ExchangeApiAsyncRestClient client = factory.newAsyncRestClient();

    // Get account balances (async)
    client.getAccount((Account response) -> System.out.println(response.getAssetBalance("ETH")));

    // Get list of trades (async)
    client.getMyTrades("NEOETH", System.out::println);

    // Get withdraw history (async)
    client.getWithdrawHistory("ETH", System.out::println);

    // Get deposit history (async)
    client.getDepositHistory("ETH", System.out::println);

    // Withdraw (async)
    client.withdraw("ETH", "0x123", "0.1", null, response -> {});
  }
}
