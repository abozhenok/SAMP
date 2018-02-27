package com.abostudios.sts.strategies.mm;

import com.abostudios.api.exchanges.ExchangeApiClientFactory;
import com.abostudios.sts.Bot;
import com.abostudios.sts.strategies.Strategy;

/**
 * <h1>Basic Market Maker Strategy</h1>
 * Runs a basic market maker strategy, by placing placing better buy/sell orders with
 * some basic logic to minimize market risk.
 *
 * @author ABOStudios
 * @version 1.0
 * @since 24/02/2018
 *
 */
public class BasicMarketMaker implements Strategy {
    @Override
    public Bot.State entry(ExchangeApiClientFactory exchangeApiClientFactory) {
        /**
         * Algo:
         *
         *
         */
        return null;
    }

    @Override
    public Bot.State activeOrder(ExchangeApiClientFactory exchangeApiClientFactory) {
        return null;
    }

    @Override
    public Bot.State exit(ExchangeApiClientFactory exchangeApiClientFactory) {
        return null;
    }
}
