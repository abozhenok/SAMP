package com.abostudios.exchanges.api.binance.examples;

import com.abostudios.exchanges.api.binance.BinanceApiAsyncRestClient;
import com.abostudios.exchanges.api.binance.BinanceApiClientFactory;
import com.abostudios.exchanges.api.binance.domain.TimeInForce;
import com.abostudios.exchanges.api.binance.domain.account.request.AllOrdersRequest;
import com.abostudios.exchanges.api.binance.domain.account.request.CancelOrderRequest;
import com.abostudios.exchanges.api.binance.domain.account.request.OrderRequest;
import com.abostudios.exchanges.api.binance.domain.account.request.OrderStatusRequest;

import static com.abostudios.exchanges.api.binance.domain.account.NewOrder.limitBuy;
import static com.abostudios.exchanges.api.binance.domain.account.NewOrder.marketBuy;

/**
 * Examples on how to place orders, cancel them, and query account information.
 */
public class OrdersExampleAsync {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("YOUR_API_KEY", "YOUR_SECRET");
    BinanceApiAsyncRestClient client = factory.newAsyncRestClient();

    // Getting list of open orders
    client.getOpenOrders(new OrderRequest("LINKETH"), System.out::println);

    // Get status of a particular order
    client.getOrderStatus(new OrderStatusRequest("LINKETH", 745262L), System.out::println);

    // Getting list of all orders with a limit of 10
    client.getAllOrders(new AllOrdersRequest("LINKETH").limit(10), System.out::println);

    // Canceling an order
    client.cancelOrder(new CancelOrderRequest("LINKETH", 756703L),
        response -> System.out.println("Order has been canceled."));

    // Placing a test LIMIT order
    client.newOrderTest(limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"),
        response -> System.out.println("Test order has succeeded."));

    // Placing a test MARKET order
    client.newOrderTest(marketBuy("LINKETH", "1000"), response -> System.out.println("Test order has succeeded."));

    // Placing a real LIMIT order
    client.newOrder(limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"), System.out::println);
  }
}
