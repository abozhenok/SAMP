package com.abostudios.exchanges.api.binance;

import com.abostudios.exchanges.api.binance.domain.market.CandlestickInterval;
import java.util.List;
import com.abostudios.exchanges.api.binance.domain.event.AggTradeEvent;
import com.abostudios.exchanges.api.binance.domain.event.AllMarketTickersEvent;
import com.abostudios.exchanges.api.binance.domain.event.CandlestickEvent;
import com.abostudios.exchanges.api.binance.domain.event.DepthEvent;
import com.abostudios.exchanges.api.binance.domain.event.UserDataUpdateEvent;

/**
 * Binance API data streaming façade, supporting streaming of events through web sockets.
 */
public interface BinanceApiWebSocketClient {

  void onDepthEvent(String symbol, BinanceApiCallback<DepthEvent> callback);

  void onCandlestickEvent(String symbol, CandlestickInterval interval, BinanceApiCallback<CandlestickEvent> callback);

  void onAggTradeEvent(String symbol, BinanceApiCallback<AggTradeEvent> callback);

  void onUserDataUpdateEvent(String listenKey, BinanceApiCallback<UserDataUpdateEvent> callback);

  void onAllMarketTickersEvent(BinanceApiCallback<List<AllMarketTickersEvent>> callback);
}
