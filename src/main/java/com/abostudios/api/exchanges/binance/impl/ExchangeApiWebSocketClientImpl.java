package com.abostudios.api.exchanges.binance.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.abostudios.api.domain.market.CandlestickInterval;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import com.abostudios.api.exchanges.ExchangeApiCallback;
import com.abostudios.api.exchanges.ExchangeApiWebSocketClient;
import com.abostudios.api.exchanges.binance.constant.BinanceApiConstants;
import com.abostudios.api.domain.event.AggTradeEvent;
import com.abostudios.api.domain.event.AllMarketTickersEvent;
import com.abostudios.api.domain.event.CandlestickEvent;
import com.abostudios.api.domain.event.DepthEvent;
import com.abostudios.api.domain.event.UserDataUpdateEvent;

/**
 * Binance API WebSocket client implementation using OkHttp.
 */
public class ExchangeApiWebSocketClientImpl implements ExchangeApiWebSocketClient, Closeable {

  private final OkHttpClient client;

  public ExchangeApiWebSocketClientImpl() {
    Dispatcher d = new Dispatcher();
    d.setMaxRequestsPerHost(100);
    this.client = new OkHttpClient.Builder().dispatcher(d).build();
  }

  public void onDepthEvent(String symbol, ExchangeApiCallback<DepthEvent> callback) {
    final String channel = String.format("%s@depth", symbol);
    createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, DepthEvent.class));
  }

  @Override
  public void onCandlestickEvent(String symbol, CandlestickInterval interval, ExchangeApiCallback<CandlestickEvent> callback) {
    final String channel = String.format("%s@kline_%s", symbol, interval.getIntervalId());
    createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, CandlestickEvent.class));
  }

  public void onAggTradeEvent(String symbol, ExchangeApiCallback<AggTradeEvent> callback) {
    final String channel = String.format("%s@aggTrade", symbol);
    createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, AggTradeEvent.class));
  }

  public void onUserDataUpdateEvent(String listenKey, ExchangeApiCallback<UserDataUpdateEvent> callback) {
    createNewWebSocket(listenKey, new BinanceApiWebSocketListener<>(callback, UserDataUpdateEvent.class));
  }

  public void onAllMarketTickersEvent(ExchangeApiCallback<List<AllMarketTickersEvent>> callback) {
    final String channel = "!ticker@arr";
    createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback));
  }

  private void createNewWebSocket(String channel, BinanceApiWebSocketListener<?> listener) {
    String streamingUrl = String.format("%s/%s", BinanceApiConstants.WS_API_BASE_URL, channel);
    Request request = new Request.Builder().url(streamingUrl).build();
    client.newWebSocket(request, listener);
  }

  @Override
  public void close() throws IOException {
    client.dispatcher().executorService().shutdown();
  }

}
