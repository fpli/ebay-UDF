package com.ebay.risk.normalize.enums;

public enum NormalizeStrategyEnum {
  DEFAULT(1,"DEFAULT");
  Integer id; String name;
  NormalizeStrategyEnum(Integer id, String name){
    this.id = id;
    this.name = name;
  }

  public static NormalizeStrategyEnum fromId(Integer id) {
    for (NormalizeStrategyEnum normalizeStrategy : values()) {
      if (normalizeStrategy.getId().equals(id)) {
        return normalizeStrategy;
      }
    }
    return null;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
