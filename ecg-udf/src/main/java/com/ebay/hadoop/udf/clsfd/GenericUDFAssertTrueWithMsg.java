/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ebay.hadoop.udf.clsfd;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.UDFType;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;

import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import org.apache.hadoop.io.BooleanWritable;

/**
 * GenericUDFAssertTrue
 * Created by Aaron chen on 2020-05-14
 */
@Description(name = "assert_true_msg", value = "_FUNC_(condition) - " + "Throw an exception if 'condition' is not true.",
        extended = "Example:\n " + "  > SELECT _FUNC_(x >= 0,'issue msg') FROM src LIMIT 1;\n" + "  NULL")
@UDFType(deterministic = false)
public class GenericUDFAssertTrueWithMsg extends GenericUDF {
    private ObjectInspectorConverters.Converter conditionConverter = null;
    private ObjectInspectorConverters.Converter error_msg=null;

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments)
            throws UDFArgumentException {
        if (arguments.length != 2) {
            throw new UDFArgumentLengthException("ASSERT_TRUE() expects two argument.");
        }
        if (arguments[0].getCategory() != Category.PRIMITIVE) {
            throw new UDFArgumentTypeException(0,
                    "Argument to ASSERT_TRUE() should be primitive.");
        }
        conditionConverter = ObjectInspectorConverters.getConverter(arguments[0],
                PrimitiveObjectInspectorFactory.writableBooleanObjectInspector);
        error_msg=ObjectInspectorConverters.getConverter(arguments[1], PrimitiveObjectInspectorFactory.writableStringObjectInspector);

        return PrimitiveObjectInspectorFactory.writableVoidObjectInspector;
    }


    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        BooleanWritable condition =(BooleanWritable) conditionConverter.convert(arguments[0].get());
        if (condition == null || !condition.get()) {
            //throw new HiveException("ASSERT_TRUE(): assertion failed, msg: " + arguments[1].get());
            throw new RuntimeException("ASSERT_TRUE(): assertion failed, msg: " + arguments[1].get());
        }
        return null;
    }

    @Override
    public String getDisplayString(String[] children) {
        return getStandardDisplayString("assert_true_msg", children);
    }
}
