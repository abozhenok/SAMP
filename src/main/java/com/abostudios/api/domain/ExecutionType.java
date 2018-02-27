package com.abostudios.api.domain;

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