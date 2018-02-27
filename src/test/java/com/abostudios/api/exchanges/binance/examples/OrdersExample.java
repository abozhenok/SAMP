package com.abostudios.api.exchanges.binance.examples;

import com.abostudios.api.exchanges.binance.BinanceApiClientFactory;
import com.abostudios.api.exchanges.ExchangeApiRestClient;
import com.abostudios.api.domain.TimeInForce;
import com.abostudios.api.domain.account.NewOrder;
import com.abostudios.api.domain.account.NewOrderResponse;
import com.abostudios.api.domain.account.Order;
import com.abostudios.api.domain.account.request.AllOrdersRequest;
import com.abostudios.api.domain.account.request.CancelOrderRequest;
import com.abostudios.api.domain.account.request.OrderRequest;
import com.abostudios.api.domain.account.request.OrderStatusRequest;
import com.abostudios.api.exchanges.ExchangeApiException;

import java.util.List;

/**
 * Examples on how to place orders, cancel them, and query account information.
 */
public class OrdersExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("YOUR_API_KEY", "YOUR_SECRET");
    ExchangeApiRestClient client = factory.newRestClient();

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
    } catch (ExchangeApiException e) {
      System.out.println(e.getError().getMsg());
    }

    // Placing a test LIMIT order
    client.newOrderTest(NewOrder.limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"));

    // Placing a test MARKET order
    client.newOrderTest(NewOrder.marketBuy("LINKETH", "1000"));

    // Placing a real LIMIT order
    NewOrderResponse newOrderResponse = client.newOrder(NewOrder.limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"));
    System.out.println(newOrderResponse);
  }

}
