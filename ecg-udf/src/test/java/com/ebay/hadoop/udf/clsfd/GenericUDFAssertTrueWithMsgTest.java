package com.ebay.hadoop.udf.clsfd;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertTrue;

/**
 * create or replace temporary view dw_clsfd_user_dq as
 * select
 * assert_true_msg(count(1)=0,"clsfd_user_id check fail")
 * from ( select clsfd_user_id, count(1) from zeta_dev_clsfd_sander.dw_clsfd_user_dq  group by 1 having count(1) > 1);
 */
public class GenericUDFAssertTrueWithMsgTest {
    @Test
    public void testSuccessfully() throws HiveException {
        ObjectInspector[] arguments = new ObjectInspector[2];
        arguments[0] = PrimitiveObjectInspectorFactory.writableBooleanObjectInspector;
        arguments[1] = PrimitiveObjectInspectorFactory.writableStringObjectInspector;

        GenericUDF.DeferredObject[] args=new GenericUDF.DeferredObject[2];
        BooleanWritable booleanWritable=new BooleanWritable(true);
        Text strMsg= new Text("clsfd_user_id check fail");;
        args[0] = new GenericUDF.DeferredObject() {
            @Override
            public void prepare(int i) throws HiveException {

            }

            @Override
            public Object get() throws HiveException {
                return booleanWritable;
            }
        };
        args[1] = new GenericUDF.DeferredObject() {
            @Override
            public void prepare(int i) throws HiveException {

            }

            @Override
            public Object get() throws HiveException {
                return strMsg;
            }
        };
        GenericUDFAssertTrueWithMsg genericUDFAssertTrueWithMsg=new GenericUDFAssertTrueWithMsg();
        genericUDFAssertTrueWithMsg.initialize(arguments);
        genericUDFAssertTrueWithMsg.evaluate(args);
        System.out.println("happy ending ...");
    }


    @Test
    public void testFail() throws HiveException {
        ObjectInspector[] arguments = new ObjectInspector[2];
        arguments[0] = PrimitiveObjectInspectorFactory.writableBooleanObjectInspector;
        arguments[1] = PrimitiveObjectInspectorFactory.writableStringObjectInspector;

        GenericUDF.DeferredObject[] args=new GenericUDF.DeferredObject[2];
        BooleanWritable booleanWritable=new BooleanWritable(false);
        Text strMsg= new Text("clsfd_user_id check fail");;
        args[0] = new GenericUDF.DeferredObject() {
            @Override
            public void prepare(int i) throws HiveException {

            }

            @Override
            public Object get() throws HiveException {
                return booleanWritable;
            }
        };
        args[1] = new GenericUDF.DeferredObject() {
            @Override
            public void prepare(int i) throws HiveException {

            }

            @Override
            public Object get() throws HiveException {
                return strMsg;
            }
        };
        GenericUDFAssertTrueWithMsg genericUDFAssertTrueWithMsg=new GenericUDFAssertTrueWithMsg();
        genericUDFAssertTrueWithMsg.initialize(arguments);
        //genericUDFAssertTrueWithMsg.evaluate(args);
        try {
            genericUDFAssertTrueWithMsg.evaluate(args);
            assertTrue(false);
        } catch (RuntimeException e) {
            assertTrue(true);
        }
        System.out.println("happy ending ...");
    }
}
