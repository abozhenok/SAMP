package com.abostudios.exchanges.api.binance.examples;

import com.abostudios.exchanges.api.binance.BinanceApiClientFactory;
import com.abostudios.exchanges.api.binance.BinanceApiRestClient;
import com.abostudios.exchanges.api.binance.domain.account.Account;
import com.abostudios.exchanges.api.binance.domain.account.Trade;

import java.util.List;

/**
 * Examples on how to get account information.
 */
public class AccountEndpointsExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("YOUR_API_KEY", "YOUR_SECRET");
    BinanceApiRestClient client = factory.newRestClient();

    // Get account balances
    Account account = client.getAccount(6000000L, System.currentTimeMillis());
    System.out.println(account.getBalances());
    System.out.println(account.getAssetBalance("ETH"));

    // Get list of trades
    List<Trade> myTrades = client.getMyTrades("NEOETH");
    System.out.println(myTrades);

    // Get withdraw history
    System.out.println(client.getWithdrawHistory("ETH"));

    // Get deposit history
    System.out.println(client.getDepositHistory("ETH"));

    // Get deposit address
    System.out.println(client.getDepositAddress("ETH"));

    // Withdraw
    client.withdraw("ETH", "0x123", "0.1", null);
  }
}
