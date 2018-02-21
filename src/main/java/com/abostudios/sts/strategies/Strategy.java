package com.abostudios.sts.strategies;

import com.abostudios.api.Exchange;
import com.abostudios.sts.Bot;

public interface Strategy {
    /**
     * Executes a strategies logic to enter the market by placing a buy order
     * @return which state the bot should enter next
     */
    Bot.State entry(Exchange exchange);

    /**
     * What should the strategy do whilst waiting to fill the buy/sell order
     * @return which state the bot should enter next
     */
    Bot.State activeOrder(Exchange exchange);

    /**
     * The strategy is looking to close its position by placing a sell order
     * @return which state the bot should enter next
     */
    Bot.State exit(Exchange exchange);
}
