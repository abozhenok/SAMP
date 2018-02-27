package com.abostudios.api.exchanges.binance.impl;

import com.abostudios.api.domain.account.request.AllOrdersRequest;
import com.abostudios.api.domain.account.request.CancelOrderRequest;
import com.abostudios.api.domain.account.request.OrderRequest;
import com.abostudios.api.domain.account.request.OrderStatusRequest;
import com.abostudios.api.domain.event.ListenKey;
import com.abostudios.api.domain.general.ExchangeInfo;
import com.abostudios.api.exchanges.ExchangeApiAsyncRestClient;
import com.abostudios.api.exchanges.ExchangeApiCallback;
import com.abostudios.api.exchanges.binance.constant.BinanceApiConstants;
import com.abostudios.api.domain.account.Account;
import com.abostudios.api.domain.account.DepositAddress;
import com.abostudios.api.domain.account.DepositHistory;
import com.abostudios.api.domain.account.NewOrder;
import com.abostudios.api.domain.account.NewOrderResponse;
import com.abostudios.api.domain.account.Order;
import com.abostudios.api.domain.account.Trade;
import com.abostudios.api.domain.account.WithdrawHistory;
import com.abostudios.api.domain.general.ServerTime;
import com.abostudios.api.domain.market.AggTrade;
import com.abostudios.api.domain.market.BookTicker;
import com.abostudios.api.domain.market.Candlestick;
import com.abostudios.api.domain.market.CandlestickInterval;
import com.abostudios.api.domain.market.OrderBook;
import com.abostudios.api.domain.market.TickerPrice;
import com.abostudios.api.domain.market.TickerStatistics;

import java.util.List;

import static com.abostudios.api.exchanges.binance.impl.BinanceApiServiceGenerator.createService;

/**
 * Implementation of Binance's REST API using Retrofit with asynchronous/non-blocking method calls.
 */
public class ExchangeApiAsyncRestClientImpl implements ExchangeApiAsyncRestClient {

  private final BinanceApiService binanceApiService;

  public ExchangeApiAsyncRestClientImpl(String apiKey, String secret) {
    binanceApiService = BinanceApiServiceGenerator.createService(BinanceApiService.class, apiKey, secret);
  }

  // General endpoints

  @Override
  public void ping(ExchangeApiCallback<Void> callback) {
    binanceApiService.ping().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getServerTime(ExchangeApiCallback<ServerTime> callback) {
    binanceApiService.getServerTime().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getExchangeInfo(ExchangeApiCallback<ExchangeInfo> callback) {
    binanceApiService.getExchangeInfo().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  // Market Data endpoints

  @Override
  public void getOrderBook(String symbol, Integer limit, ExchangeApiCallback<OrderBook> callback) {
    binanceApiService.getOrderBook(symbol, limit).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime, ExchangeApiCallback<List<AggTrade>> callback) {
    binanceApiService.getAggTrades(symbol, fromId, limit, startTime, endTime).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAggTrades(String symbol, ExchangeApiCallback<List<AggTrade>> callback) {
    getAggTrades(symbol, null, null, null, null, callback);
  }

  @Override
  public void getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime, ExchangeApiCallback<List<Candlestick>> callback) {
    binanceApiService.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getCandlestickBars(String symbol, CandlestickInterval interval, ExchangeApiCallback<List<Candlestick>> callback) {
    getCandlestickBars(symbol, interval, null, null, null, callback);
  }

  @Override
  public void get24HrPriceStatistics(String symbol, ExchangeApiCallback<TickerStatistics> callback) {
    binanceApiService.get24HrPriceStatistics(symbol).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }
  
  @Override
  public void getAll24HrPriceStatistics(ExchangeApiCallback<List<TickerStatistics>> callback) {
    binanceApiService.getAll24HrPriceStatistics().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAllPrices(ExchangeApiCallback<List<TickerPrice>> callback) {
    binanceApiService.getLatestPrices().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }
  
  @Override
  public void getPrice(String symbol , ExchangeApiCallback<TickerPrice> callback) {
    binanceApiService.getLatestPrice(symbol).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getBookTickers(ExchangeApiCallback<List<BookTicker>> callback) {
    binanceApiService.getBookTickers().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void newOrder(NewOrder order, ExchangeApiCallback<NewOrderResponse> callback) {
    binanceApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
        order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
        order.getIcebergQty(), order.getRecvWindow(), order.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void newOrderTest(NewOrder order, ExchangeApiCallback<Void> callback) {
    binanceApiService.newOrderTest(order.getSymbol(), order.getSide(), order.getType(),
        order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
        order.getIcebergQty(), order.getRecvWindow(), order.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  // Account endpoints

  @Override
  public void getOrderStatus(OrderStatusRequest orderStatusRequest, ExchangeApiCallback<Order> callback) {
    binanceApiService.getOrderStatus(orderStatusRequest.getSymbol(),
        orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
        orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void cancelOrder(CancelOrderRequest cancelOrderRequest, ExchangeApiCallback<Void> callback) {
    binanceApiService.cancelOrder(cancelOrderRequest.getSymbol(),
        cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
        cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getOpenOrders(OrderRequest orderRequest, ExchangeApiCallback<List<Order>> callback) {
    binanceApiService.getOpenOrders(orderRequest.getSymbol(),
        orderRequest.getRecvWindow(), orderRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAllOrders(AllOrdersRequest orderRequest, ExchangeApiCallback<List<Order>> callback) {
    binanceApiService.getAllOrders(orderRequest.getSymbol(),
        orderRequest.getOrderId(), orderRequest.getLimit(),
        orderRequest.getRecvWindow(), orderRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAccount(Long recvWindow, Long timestamp, ExchangeApiCallback<Account> callback) {
    binanceApiService.getAccount(recvWindow, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAccount(ExchangeApiCallback<Account> callback) {
    long timestamp = System.currentTimeMillis();
    binanceApiService.getAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, ExchangeApiCallback<List<Trade>> callback) {
    binanceApiService.getMyTrades(symbol, limit, fromId, recvWindow, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getMyTrades(String symbol, Integer limit, ExchangeApiCallback<List<Trade>> callback) {
    getMyTrades(symbol, limit, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), callback);
  }

  @Override
  public void getMyTrades(String symbol, ExchangeApiCallback<List<Trade>> callback) {
    getMyTrades(symbol, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), callback);
  }

  @Override
  public void withdraw(String asset, String address, String amount, String name, ExchangeApiCallback<Void> callback) {
    binanceApiService.withdraw(asset, address, amount, name, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getDepositHistory(String asset, ExchangeApiCallback<DepositHistory> callback) {
    binanceApiService.getDepositHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getWithdrawHistory(String asset, ExchangeApiCallback<WithdrawHistory> callback) {
    binanceApiService.getWithdrawHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getDepositAddress(String asset, ExchangeApiCallback<DepositAddress> callback) {
    binanceApiService.getDepositAddress(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  // User stream endpoints

  @Override
  public void startUserDataStream(ExchangeApiCallback<ListenKey> callback) {
    binanceApiService.startUserDataStream().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void keepAliveUserDataStream(String listenKey, ExchangeApiCallback<Void> callback) {
    binanceApiService.keepAliveUserDataStream(listenKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void closeUserDataStream(String listenKey, ExchangeApiCallback<Void> callback) {
    binanceApiService.closeAliveUserDataStream(listenKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }
}