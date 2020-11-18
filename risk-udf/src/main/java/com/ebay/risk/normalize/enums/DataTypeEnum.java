package com.ebay.risk.normalize.enums;

public enum DataTypeEnum {
    PHONE(1,"PHONE"),
  EMAIL(2,"EMAIL"),
  ADDRESS(3,"ADDRESS");
  Integer id; String name;
     DataTypeEnum(Integer id, String name){
      this.id = id;
      this.name = name;
    }

  public static DataTypeEnum fromId(Integer id) {
    for (DataTypeEnum dataType : values()) {
      if (dataType.getId().equals(id)) {
        return dataType;
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
