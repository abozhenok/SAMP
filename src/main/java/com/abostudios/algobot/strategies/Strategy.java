package com.abostudios.algobot.strategies;

import com.abostudios.algobot.AlgoBot;

public interface Strategy {
    /**
     * Executes a strategies logic to enter the market by placing a buy order
     * @return which state the bot should enter next
     */
    AlgoBot.State entry();

    /**
     * What should the strategy do whilst waiting to fill the buy/sell order
     * @return which state the bot should enter next
     */
    AlgoBot.State activeOrder();

    /**
     * The strategy is looking to close its position by placing a sell order
     * @return which state the bot should enter next
     */
    AlgoBot.State exit();
}
