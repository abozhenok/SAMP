package com.abostudios.api.exchanges.binance.impl;

import com.abostudios.api.domain.account.request.AllOrdersRequest;
import com.abostudios.api.domain.account.request.CancelOrderRequest;
import com.abostudios.api.domain.account.request.OrderRequest;
import com.abostudios.api.domain.account.request.OrderStatusRequest;
import com.abostudios.api.domain.general.ExchangeInfo;
import com.abostudios.api.exchanges.ExchangeApiRestClient;
import com.abostudios.api.exchanges.binance.constant.BinanceApiConstants;
import com.abostudios.api.domain.account.Account;
import com.abostudios.api.domain.account.DepositAddress;
import com.abostudios.api.domain.account.DepositHistory;
import com.abostudios.api.domain.account.NewOrder;
import com.abostudios.api.domain.account.NewOrderResponse;
import com.abostudios.api.domain.account.Order;
import com.abostudios.api.domain.account.Trade;
import com.abostudios.api.domain.account.WithdrawHistory;
import com.abostudios.api.domain.market.AggTrade;
import com.abostudios.api.domain.market.BookTicker;
import com.abostudios.api.domain.market.Candlestick;
import com.abostudios.api.domain.market.CandlestickInterval;
import com.abostudios.api.domain.market.OrderBook;
import com.abostudios.api.domain.market.TickerPrice;
import com.abostudios.api.domain.market.TickerStatistics;

import java.util.List;

/**
 * Implementation of Binance's REST API using Retrofit with synchronous/blocking method calls.
 */
public class ExchangeApiRestClientImpl implements ExchangeApiRestClient {

  private final BinanceApiService binanceApiService;

  public ExchangeApiRestClientImpl(String apiKey, String secret) {
    binanceApiService = BinanceApiServiceGenerator.createService(BinanceApiService.class, apiKey, secret);
  }

  // General endpoints

  @Override
  public void ping() {
    BinanceApiServiceGenerator.executeSync(binanceApiService.ping());
  }

  @Override
  public Long getServerTime() {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getServerTime()).getServerTime();
  }

  @Override
  public ExchangeInfo getExchangeInfo() {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getExchangeInfo());
  }

  // Market Data endpoints

  @Override
  public OrderBook getOrderBook(String symbol, Integer limit) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getOrderBook(symbol, limit));
  }

  @Override
  public List<AggTrade> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getAggTrades(symbol, fromId, limit, startTime, endTime));
  }

  @Override
  public List<AggTrade> getAggTrades(String symbol) {
    return getAggTrades(symbol, null, null, null, null);
  }

  @Override
  public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime));
  }

  @Override
  public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval) {
    return getCandlestickBars(symbol, interval, null, null, null);
  }

  @Override
  public TickerStatistics get24HrPriceStatistics(String symbol) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.get24HrPriceStatistics(symbol));
  }

  @Override
  public List<TickerStatistics> getAll24HrPriceStatistics() {
	return 	BinanceApiServiceGenerator.executeSync(binanceApiService.getAll24HrPriceStatistics());
  }

  @Override
  public TickerPrice getPrice(String symbol) {
	  return BinanceApiServiceGenerator.executeSync(binanceApiService.getLatestPrice(symbol));
  }
  
  @Override
  public List<TickerPrice> getAllPrices() {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getLatestPrices());
  }

  @Override
  public List<BookTicker> getBookTickers() {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getBookTickers());
  }

  @Override
  public NewOrderResponse newOrder(NewOrder order) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
        order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
        order.getIcebergQty(), order.getRecvWindow(), order.getTimestamp()));
  }

  @Override
  public void newOrderTest(NewOrder order) {
    BinanceApiServiceGenerator.executeSync(binanceApiService.newOrderTest(order.getSymbol(), order.getSide(), order.getType(),
        order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
        order.getIcebergQty(), order.getRecvWindow(), order.getTimestamp()));
  }

  // Account endpoints

  @Override
  public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getOrderStatus(orderStatusRequest.getSymbol(),
        orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
        orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()));
  }

  @Override
  public void cancelOrder(CancelOrderRequest cancelOrderRequest) {
    BinanceApiServiceGenerator.executeSync(binanceApiService.cancelOrder(cancelOrderRequest.getSymbol(),
        cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
        cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()));
  }

  @Override
  public List<Order> getOpenOrders(OrderRequest orderRequest) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
  }

  @Override
  public List<Order> getAllOrders(AllOrdersRequest orderRequest) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getAllOrders(orderRequest.getSymbol(),
        orderRequest.getOrderId(), orderRequest.getLimit(),
        orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
  }

  @Override
  public Account getAccount(Long recvWindow, Long timestamp) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getAccount(recvWindow, timestamp));
  }

  @Override
  public Account getAccount() {
    return getAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
  }

  @Override
  public List<Trade> getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getMyTrades(symbol, limit, fromId, recvWindow, timestamp));
  }

  @Override
  public List<Trade> getMyTrades(String symbol, Integer limit) {
    return getMyTrades(symbol, limit, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
  }

  @Override
  public List<Trade> getMyTrades(String symbol) {
    return getMyTrades(symbol, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
  }

  @Override
  public void withdraw(String asset, String address, String amount, String name) {
    BinanceApiServiceGenerator.executeSync(binanceApiService.withdraw(asset, address, amount, name, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public DepositHistory getDepositHistory(String asset) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getDepositHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public WithdrawHistory getWithdrawHistory(String asset) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getWithdrawHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public DepositAddress getDepositAddress(String asset) {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.getDepositAddress(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  // User stream endpoints

  @Override
  public String startUserDataStream() {
    return BinanceApiServiceGenerator.executeSync(binanceApiService.startUserDataStream()).toString();
  }

  @Override
  public void keepAliveUserDataStream(String listenKey) {
    BinanceApiServiceGenerator.executeSync(binanceApiService.keepAliveUserDataStream(listenKey));
  }

  @Override
  public void closeUserDataStream(String listenKey) {
    BinanceApiServiceGenerator.executeSync(binanceApiService.closeAliveUserDataStream(listenKey));
  }
}
