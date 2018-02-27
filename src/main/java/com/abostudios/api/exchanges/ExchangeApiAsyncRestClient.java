package com.abostudios.api.exchanges;

import com.abostudios.api.domain.account.request.AllOrdersRequest;
import com.abostudios.api.domain.account.request.CancelOrderRequest;
import com.abostudios.api.domain.account.request.OrderRequest;
import com.abostudios.api.domain.account.request.OrderStatusRequest;
import com.abostudios.api.domain.event.ListenKey;
import com.abostudios.api.domain.general.ExchangeInfo;
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

/**
 * Binance API façade, supporting asynchronous/non-blocking access Binance's REST API.
 */
public interface ExchangeApiAsyncRestClient {

  // General endpoints

  /**
   * Test connectivity to the Rest API.
   */
  void ping(ExchangeApiCallback<Void> callback);

  /**
   * Check server time.
   */
  void getServerTime(ExchangeApiCallback<ServerTime> callback);

  /**
   * Current exchange trading rules and symbol information
   */
  void getExchangeInfo(ExchangeApiCallback<ExchangeInfo> callback);

  // Market Data endpoints

  /**
   * Get order book of a symbol (asynchronous)
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param limit depth of the order book (max 100)
   * @param callback the callback that handles the response
   */
  void getOrderBook(String symbol, Integer limit, ExchangeApiCallback<OrderBook> callback);

  /**
   * Get compressed, aggregate trades. Trades that fill at the time, from the same order, with
   * the same price will have the quantity aggregated.
   *
   * If both <code>startTime</code> and <code>endTime</code> are sent, <code>limit</code>should not
   * be sent AND the distance between <code>startTime</code> and <code>endTime</code> must be less than 24 hours.
   *
   * @param symbol symbol to aggregate (mandatory)
   * @param fromId ID to get aggregate trades from INCLUSIVE (optional)
   * @param limit Default 500; max 500 (optional)
   * @param startTime Timestamp in ms to get aggregate trades from INCLUSIVE (optional).
   * @param endTime Timestamp in ms to get aggregate trades until INCLUSIVE (optional).
   * @param callback the callback that handles the response
   */
  void getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime, ExchangeApiCallback<List<AggTrade>> callback);

  /**
   * Return the most recent aggregate trades for <code>symbol</code>
   *
   * @see #getAggTrades(String, String, Integer, Long, Long, ExchangeApiCallback)
   */
  void getAggTrades(String symbol, ExchangeApiCallback<List<AggTrade>> callback);

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
   *
   * @param symbol symbol to aggregate (mandatory)
   * @param interval candlestick interval (mandatory)
   * @param limit Default 500; max 500 (optional)
   * @param startTime Timestamp in ms to get candlestick bars from INCLUSIVE (optional).
   * @param endTime Timestamp in ms to get candlestick bars until INCLUSIVE (optional).
   * @param callback the callback that handles the response containing a candlestick bar for the given symbol and interval
   */
  void getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime, ExchangeApiCallback<List<Candlestick>> callback);

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
   *
   * @see #getCandlestickBars(String, CandlestickInterval, ExchangeApiCallback)
   */
  void getCandlestickBars(String symbol, CandlestickInterval interval, ExchangeApiCallback<List<Candlestick>> callback);

  /**
   * Get 24 hour price change statistics (asynchronous).
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param callback the callback that handles the response
   */
  void get24HrPriceStatistics(String symbol, ExchangeApiCallback<TickerStatistics> callback);
  
  /**
   * Get 24 hour price change statistics for all symbols (asynchronous).
   * 
   * @param callback the callback that handles the response
   */
   void getAll24HrPriceStatistics(ExchangeApiCallback<List<TickerStatistics>> callback);

  /**
   * Get Latest price for all symbols (asynchronous).
   *
   * @param callback the callback that handles the response
   */
  void getAllPrices(ExchangeApiCallback<List<TickerPrice>> callback);
  
  /**
   * Get latest price for <code>symbol</code> (asynchronous).
   * 
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param callback the callback that handles the response
   */
   void getPrice(String symbol, ExchangeApiCallback<TickerPrice> callback);

  /**
   * Get best price/qty on the order book for all symbols (asynchronous).
   *
   * @param callback the callback that handles the response
   */
  void getBookTickers(ExchangeApiCallback<List<BookTicker>> callback);

  // Account endpoints

  /**
   * Send in a new order (asynchronous)
   *
   * @param order the new order to submit.
   * @param callback the callback that handles the response
   */
  void newOrder(NewOrder order, ExchangeApiCallback<NewOrderResponse> callback);

  /**
   * Test new order creation and signature/recvWindow long. Creates and validates a new order but does not send it into the matching engine.
   *
   * @param order the new TEST order to submit.
   * @param callback the callback that handles the response
   */
  void newOrderTest(NewOrder order, ExchangeApiCallback<Void> callback);

  /**
   * Check an order's status (asynchronous).
   *
   * @param orderStatusRequest order status request parameters
   * @param callback the callback that handles the response
   */
  void getOrderStatus(OrderStatusRequest orderStatusRequest, ExchangeApiCallback<Order> callback);

  /**
   * Cancel an active order (asynchronous).
   *
   * @param cancelOrderRequest order status request parameters
   * @param callback the callback that handles the response
   */
  void cancelOrder(CancelOrderRequest cancelOrderRequest, ExchangeApiCallback<Void> callback);

  /**
   * Get all open orders on a symbol (asynchronous).
   *
   * @param orderRequest order request parameters
   * @param callback the callback that handles the response
   */
  void getOpenOrders(OrderRequest orderRequest, ExchangeApiCallback<List<Order>> callback);

  /**
   * Get all account orders; active, canceled, or filled.
   *
   * @param orderRequest order request parameters
   * @param callback the callback that handles the response
   */
  void getAllOrders(AllOrdersRequest orderRequest, ExchangeApiCallback<List<Order>> callback);

  /**
   * Get current account information (async).
   */
  void getAccount(Long recvWindow, Long timestamp, ExchangeApiCallback<Account> callback);

  /**
   * Get current account information using default parameters (async).
   */
  void getAccount(ExchangeApiCallback<Account> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @param limit default 500; max 500
   * @param fromId TradeId to fetch from. Default gets most recent trades.
   * @param callback the callback that handles the response with a list of trades
   */
  void getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, ExchangeApiCallback<List<Trade>> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @param limit default 500; max 500
   * @param callback the callback that handles the response with a list of trades
   */
  void getMyTrades(String symbol, Integer limit, ExchangeApiCallback<List<Trade>> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @param callback the callback that handles the response with a list of trades
   */
  void getMyTrades(String symbol, ExchangeApiCallback<List<Trade>> callback);

  /**
   * Submit a withdraw request.
   *
   * Enable Withdrawals option has to be active in the API settings.
   *
   * @param asset asset symbol to withdraw
   * @param address address to withdraw to
   * @param amount amount to withdraw
   * @param name description/alias of the address
   */
  void withdraw(String asset, String address, String amount, String name, ExchangeApiCallback<Void> callback);

  /**
   * Fetch account deposit history.
   *
   * @param callback the callback that handles the response and returns the deposit history
   */
  void getDepositHistory(String asset, ExchangeApiCallback<DepositHistory> callback);

  /**
   * Fetch account withdraw history.
   *
   * @param callback the callback that handles the response and returns the withdraw history
   */
  void getWithdrawHistory(String asset, ExchangeApiCallback<WithdrawHistory> callback);

  /**
   * Fetch deposit address.
   *
   * @param callback the callback that handles the response and returns the deposit address
   */
   void getDepositAddress(String asset, ExchangeApiCallback<DepositAddress> callback);

  // User stream endpoints

  /**
   * Start a new user data stream.
   *
   * @param callback the callback that handles the response which contains a listenKey
   */
  void startUserDataStream(ExchangeApiCallback<ListenKey> callback);

  /**
   * PING a user data stream to prevent a time out.
   *
   * @param listenKey listen key that identifies a data stream
   * @param callback the callback that handles the response which contains a listenKey
   */
  void keepAliveUserDataStream(String listenKey, ExchangeApiCallback<Void> callback);

  /**
   * Close out a new user data stream.
   *
   * @param listenKey listen key that identifies a data stream
   * @param callback the callback that handles the response which contains a listenKey
   */
  void closeUserDataStream(String listenKey, ExchangeApiCallback<Void> callback);
}