package com.ebay.kernel;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

/**
 * This is a base class for Enum/Flag constants in the system. It declares
 * basic attributes common to all the typed constants (id and name).
 * It provides accessors as well as a set of convenience methods.
 * The key attributes of the class don't have mutators, which makes the
 * Enum immutable in terms of its base state. This class is serializable
 * and readResolve() method in the base class replaces de-serialized object
 * reference to an existing typed object in that respective VM object space.
 * <p/>
 * The object type and its id value make the constant unique across VM.
 */
public abstract class BaseEnum implements Cloneable, Serializable {
  public static final long serialVersionUID = -3864257445388624423L;

  //unique id for given type
  private final int m_id;

  //all transient attributes
  private transient final String m_name;

  transient BaseEnum m_nextEnum;

  transient BaseEnum m_previousEnum;

  /**
   * Base constructor to set the id and name of the constant.
   * It should be called by other constructors from the derived classes.
   * It internally adds this object into the EnumManager, which in turn
   * sets the relationship between this constant with others, such as
   * previous and next constants of the same type.
   */
  protected BaseEnum(int id, String name) {
    this(id, name, true);
  }

  /**
   * Supports the ability to create an Enum instance but NOT
   * have it added to its EnumManager.  This basically invalidates
   * all the general capabilities and uniqueness assertion.  This
   * is useful to support dynamic creation of Enum types on the
   * fly who manager their own identity as well as allow for testing.
   * <p/>
   * Note that the registration would simply keep growing for these
   * on-the-fly instances and finally blow our memory...
   *
   * @param id
   * @param name
   * @param register
   */
  protected BaseEnum(int id, String name, boolean register) {
    m_id = id;
    m_name = name;
    if (register) {
      EnumManager.add(this);
    }
  }

  /**
   * Return a List for a given class type. The order of the constants
   * this List returned is based on the definition order of the constants
   * for that type.  The List may be modified and will not affect the
   * contents of the Enum.
   *
   * @param type a Class type.
   * @return List - List over all the constants defined by the class type.
   */
  public static List getList(Class type) {
    return EnumManager.getList(type);
  }

  /**
   * Return a ListIterator for a given class type. The order of the constants
   * this iterator returned is based on the definition order of the constants
   * for that type.
   *
   * @param type a Class type.
   * @return ListIterator - iterator over all the constants defined by the
   * class.
   */
  public static ListIterator getIterator(Class type) {
    return EnumManager.getIterator(type);
  }

  /**
   * Return the first existing constant for a given class type and name.  If the
   * type is not an Enum type and exception is thrown.  If the id does not
   * exist in the Enum, null is returned.  Since an Enum is not gauranteed
   * to have a unique name, the first match (if any) is returned.
   *
   * @param type a Class type.
   * @param id   an String value for name.
   * @return BaseEnum - an existing constant, null if not exists.
   */
  public static final BaseEnum getEnum(Class type, String name) {
    return EnumManager.getEnum(type, name);
  }

  public static final BaseEnum getEnumIgnoreCase(Class type, String name) {
    return EnumManager.getEnumIgnoreCase(type, name);
  }

  public static final BaseEnum getEnum(Class type, Integer id) {
    return EnumManager.getEnum(type, id);
  }

  /**
   * Return a existing constant for a given class type and id.
   * If not exists, return the passed-in default constant.
   *
   * @param type        a Class type.
   * @param id          an int value for id.
   * @param defaultEnum an alternative constant if can find existing one.
   * @return BaseEnum - an existing constant, defaultEnum if not exists.
   */
  public static final BaseEnum getElseReturnEnum(Class type, int id, BaseEnum defaultEnum) {
    return EnumManager.getElseEnum(type, id, defaultEnum);
  }

  /**
   * Create an array of constants of given class type including all the
   * constants from its enclosing derived classes.
   * This operation is only applicable to the Enum types where the
   * root class can't be extended outside the class (only derivable
   * through inner class) while all derived inner class types are final.
   *
   * @param clz           a root Class type.
   * @param enclosingClzz an array of enclosing classes.
   * @return Object - an array of Enum constants.
   */
  protected static Object createEnclosedEnumArray(Class clz, Class[] enclosingClzz) {
    return EnumManager.createEnclosedEnumArray(clz, enclosingClzz);
  }

  /**
   * Create an array of constants of given parent class type including all the
   * constants from this class and inherited ones up to the top class.
   *
   * @param clz    a Class type.
   * @param topClz a top parent Class type.
   * @return Object - an array of Enum constants.
   */
  protected static Object createInheritedEnumArray(Class clz, Class topClz) {
    return EnumManager.createInheritedEnumArray(clz, topClz);
  }

  /**
   * Return the name defined in this constant.
   *
   * @return String - name of the constant.
   */
  public final String getName() {
    return m_name;
  }

  /**
   * Return the int id value defined in this constant.
   * It is temp method to keep compatable interface from the previous impl.
   *
   * @return int - id of the constant.
   */
  public final int getValue() {
    return getId();
  }

  /**
   * Return the id value defined in this constant.
   *
   * @return int - id of the constant.
   */
  public final int getId() {
    return m_id;
  }

  /**
   * Return the id value wrapped as an Integer. The returned value is
   * cached and reused by subsequent calls to this method.
   *
   * @return Integer - wrapper object for id of the constant.
   */
  public final Integer getInteger() {
    return getId();
  }

  /**
   * Generate a hash value for this constant. Conforms with equals.
   *
   * @return int - hash code of the constant.
   */
  @Override
  public int hashCode() {
    return m_id;
  }

  /**
   * Returns true if the object is the same type as this one
   * and the ids are equal.
   *
   * @param other an Object reference for comparison
   * @return boolean - true if they are equal, false otherwise.
   */
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (this == other) {
      return true;
    }
    if (this.getClass() == other.getClass() && m_id == ((BaseEnum) other).m_id) {
      return true;
    }

    return false;
  }

  /**
   * The general contract for clones mean original.clone() != original.
   * However, based on the identity nature of this type, we deviate from such
   * a general contract and return this.
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return this;
  }

  /**
   * Return a String representation of this constant.
   *
   * @return String - verbose for this constant.
   */
  @Override
  public String toString() {
    return m_name + "=" + m_id;
  }

  /**
   * Return next constant of same type defined just after this constant.
   * Answer null if no next exists.
   *
   * @return BaseEnum - next constant defined after this.
   */
  public final BaseEnum next() {
    return m_nextEnum;
  }

  /**
   * Return previous constant of same type defined just before this constant.
   * Answer null if no next exists.
   *
   * @return BaseEnum - previous constant defined before this.
   */
  public final BaseEnum previous() {
    return m_previousEnum;
  }

  /**
   * This method is invoked by de-serialization for all the objects of the
   * derived type, if there is no further override inside the derived classes.
   * It designates an Object replacement with the existing refernece when an
   * instance of it is read from the stream.
   *
   * @return Object - a replacement object reference.
   */
  protected Object readResolve() throws ObjectStreamException {
    return getEnum(this.getClass(), m_id);
  }

  /**
   * Return a existing constant for a given class type and id.  If the type
   * is not an Enum type and exception is thrown.  If the id does not exist
   * in the Enum, null is returned.
   *
   * @param type a Class type.
   * @param id   an int value for id.
   * @return BaseEnum - an existing constant, null if not exists.
   */
  public static final BaseEnum getEnum(Class type, int id) {
    return EnumManager.getEnum(type, id);
  }

}
