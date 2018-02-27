package com.abostudios.sts.strategies;

import com.abostudios.api.exchanges.ExchangeApiClientFactory;
import com.abostudios.sts.Bot;

public interface Strategy {
    /**
     * Executes a strategies logic to enter the market by placing a buy order
     * @return which state the bot should enter next
     */
    Bot.State entry(ExchangeApiClientFactory exchangeApiClientFactory);

    /**
     * What should the strategy do whilst waiting to fill the buy/sell order
     * @return which state the bot should enter next
     */
    Bot.State activeOrder(ExchangeApiClientFactory exchangeApiClientFactory);

    /**
     * The strategy is looking to close its position by placing a sell order
     * @return which state the bot should enter next
     */
    Bot.State exit(ExchangeApiClientFactory exchangeApiClientFactory);
}
