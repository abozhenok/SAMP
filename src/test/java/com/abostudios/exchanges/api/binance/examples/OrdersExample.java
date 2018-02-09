package com.abostudios.exchanges.api.binance.examples;

import com.abostudios.exchanges.api.binance.BinanceApiClientFactory;
import com.abostudios.exchanges.api.binance.BinanceApiRestClient;
import com.abostudios.exchanges.api.binance.domain.TimeInForce;
import com.abostudios.exchanges.api.binance.domain.account.NewOrderResponse;
import com.abostudios.exchanges.api.binance.domain.account.Order;
import com.abostudios.exchanges.api.binance.domain.account.request.AllOrdersRequest;
import com.abostudios.exchanges.api.binance.domain.account.request.CancelOrderRequest;
import com.abostudios.exchanges.api.binance.domain.account.request.OrderRequest;
import com.abostudios.exchanges.api.binance.domain.account.request.OrderStatusRequest;
import com.abostudios.exchanges.api.binance.exception.BinanceApiException;

import java.util.List;

import static com.abostudios.exchanges.api.binance.domain.account.NewOrder.limitBuy;
import static com.abostudios.exchanges.api.binance.domain.account.NewOrder.marketBuy;

/**
 * Examples on how to place orders, cancel them, and query account information.
 */
public class OrdersExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("YOUR_API_KEY", "YOUR_SECRET");
    BinanceApiRestClient client = factory.newRestClient();

    // Getting list of open orders
    List<Order> openOrders = client.getOpenOrders(new OrderRequest("LINKETH"));
    System.out.println(openOrders);

    // Getting list of all orders with a limit of 10
    List<Order> allOrders = client.getAllOrders(new AllOrdersRequest("LINKETH").limit(10));
    System.out.println(allOrders);

    // Get status of a particular order
    Order order = client.getOrderStatus(new OrderStatusRequest("LINKETH", 751698L));
    System.out.println(order);

    // Canceling an order
    try {
      client.cancelOrder(new CancelOrderRequest("LINKETH", 756762L));
    } catch (BinanceApiException e) {
      System.out.println(e.getError().getMsg());
    }

    // Placing a test LIMIT order
    client.newOrderTest(limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"));

    // Placing a test MARKET order
    client.newOrderTest(marketBuy("LINKETH", "1000"));

    // Placing a real LIMIT order
    NewOrderResponse newOrderResponse = client.newOrder(limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"));
    System.out.println(newOrderResponse);
  }

}
