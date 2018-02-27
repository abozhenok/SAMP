package com.abostudios.api.exchanges;

import com.abostudios.api.domain.market.CandlestickInterval;

import java.util.List;
import com.abostudios.api.domain.event.AggTradeEvent;
import com.abostudios.api.domain.event.AllMarketTickersEvent;
import com.abostudios.api.domain.event.CandlestickEvent;
import com.abostudios.api.domain.event.DepthEvent;
import com.abostudios.api.domain.event.UserDataUpdateEvent;

/**
 * Binance API data streaming façade, supporting streaming of events through web sockets.
 */
public interface ExchangeApiWebSocketClient {

  void onDepthEvent(String symbol, ExchangeApiCallback<DepthEvent> callback);

  void onCandlestickEvent(String symbol, CandlestickInterval interval, ExchangeApiCallback<CandlestickEvent> callback);

  void onAggTradeEvent(String symbol, ExchangeApiCallback<AggTradeEvent> callback);

  void onUserDataUpdateEvent(String listenKey, ExchangeApiCallback<UserDataUpdateEvent> callback);

  void onAllMarketTickersEvent(ExchangeApiCallback<List<AllMarketTickersEvent>> callback);
}
