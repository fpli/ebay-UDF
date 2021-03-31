package com.ebay.kernel.util;

import java.lang.RuntimeException;

/**
 * JdkUtil holds common Jdk-Specific utility methods.
 */
public final class JdkUtil {
  /**
   * evaluating a class literal (for example, Foo.class) caused the class to
   * be initialized; as of JDK 5.0, it does not. Code that depends on the previous
   * behavior should be rewritten to invoke the 'forceInit' method here to
   * 'force initialization' of the class by invoking 'Class.forName' on the
   * class. The 'forceInit' method caches classes already loaded to
   * resolve possible performance issues with loading the class many times
   * (in a loop, for example).
   * @param klass the class to 'force initialize'
   * @return klass the class, after it has been 'force initialized'
   * @deprecated This method is deprecated.  Do not add <b>new calls</b> to
   * this method.  It was introduced to migrate existing code to JDK 1.5 while
   * preserving the old behavior.  Therefore, it is not necessary to use this
   * for new code, and in fact new calls are strongly discouraged.  You might
   * also want to remove existing calls if you are certain that the class will
   * have been loaded by the time the call to this method is made.
   */
  public static Class forceInit(Class klass) {
    // KERNEL-2175 : Adding back the class loading logic
    try {
      final ClassLoader loader = klass.getClassLoader();
      if (loader != null) {
        Class.forName(klass.getName(), true, loader);
      }
    } catch (ClassNotFoundException e) {
      //this could happen (for example, if you passed in int.class, etc.)
      throw new RuntimeException(e.getMessage(), e);
    }
    return klass;
  }
}
