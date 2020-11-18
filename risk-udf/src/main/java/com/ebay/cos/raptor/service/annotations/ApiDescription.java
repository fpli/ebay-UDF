package com.ebay.cos.raptor.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to override the description of this API.
 * <p>
 * EXAMPLE : <p>
 * <code>
 @ApiDescription("Creates a cart for the user")
 <p>
 @ApiMethod(name="create", resource="cart")
 <p>
 public Response createCart(@ApiDescription("The initial items in the cart") @NotNull Cart cart) {
 ...
  * </code>
  * @see Api
 * @author pkrastogi
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface ApiDescription {
  /**
   * The description of this API
   * @return The description of this API (human-readable text)
   */
  String value();

}
