package com.ebay.risk.n11n.api.entity.input;

import com.ebay.risk.normalize.enums.DataTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 * The input interface for any entity to normalize
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
    property = "dataType"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AddressInput.class, name = "ADDRESS"),
    @JsonSubTypes.Type(value = EmailInput.class, name = "EMAIL"),
    @JsonSubTypes.Type(value = PhoneInput.class, name = "PHONE")
})
public abstract class Input {

  protected DataTypeEnum dataType;

  protected Optional<String> subscriptionKey = Optional.empty();

  @JsonIgnore
  public DataTypeEnum getDataType() {
    return dataType;
  }

  public void setDataType(DataTypeEnum dataType) {
    this.dataType = dataType;
  }

  @JsonIgnore
  public Optional<String> getSubscriptionKey() {
    return subscriptionKey;
  }

  public void setSubscriptionKey(String subscriptionKey) {
    if(!StringUtils.isEmpty(subscriptionKey)){
      this.subscriptionKey = Optional.of(subscriptionKey);
    }
  }

  /**
   * Get a key from the input, which could identify the entity and ignore other transient fields.
   *
   * @return the key
   */
  @JsonIgnore
  public abstract String getInputKey();

  /**
   * Convert the input object to uniformed format (unified case and trimmed).
   * This method won't change the object itself, but return a new object.
   */
  @JsonIgnore
  public abstract Input convertToUniformedInput();
}
