package com.abostudios.api.binance.domain;

/**
 * Order execution type.
 */
public enum ExecutionType {
  NEW,
  CANCELED,
  REPLACED,
  REJECTED,
  TRADE,
  EXPIRED
}