package com.ebay.hadoop.udf.common;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ConstantObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.apache.hadoop.io.Text;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.ebay.hadoop.udf.common.brickhouse.InspectorHandle;
import com.ebay.hadoop.udf.common.brickhouse.InspectorHandle.InspectorHandleFactory;

@Description(name = "json_split",
        value = "_FUNC_(json) - Returns a array of JSON strings from a JSON Array"
)
public class JsonSplitUDF extends GenericUDF {
    private StringObjectInspector stringInspector;
    private InspectorHandle inspHandle;


    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        try {
            String jsonString = this.stringInspector.getPrimitiveJavaObject(arguments[0].get());
            if(StringUtils.isBlank(jsonString)) jsonString = "[]";
            //// Logic is the same as "from_json"
            ObjectMapper om = new ObjectMapper();
            JsonNode jsonNode = om.readTree(jsonString);
            return inspHandle.parseJson(jsonNode);


        } catch (JsonProcessingException jsonProc) {
            throw new HiveException(jsonProc);
        } catch (IOException e) {
            throw new HiveException(e);
        } catch (NullPointerException npe) {
            return null;
        }

    }

    @Override
    public String getDisplayString(String[] arg0) {
        return "json_split(" + arg0[0] + ")";
    }

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments)
            throws UDFArgumentException {
        if (arguments.length != 1 && arguments.length != 2) {
            throw new UDFArgumentException("Usage : json_split( jsonstring, optional typestring) ");
        }
        if (!arguments[0].getCategory().equals(Category.PRIMITIVE)
                || ((PrimitiveObjectInspector) arguments[0]).getPrimitiveCategory() != PrimitiveCategory.STRING) {
            throw new UDFArgumentException("Usage : json_split( jsonstring, optional typestring) ");
        }
        stringInspector = (StringObjectInspector) arguments[0];

        if (arguments.length > 1) {
            if (!arguments[1].getCategory().equals(Category.PRIMITIVE)
                    || ((PrimitiveObjectInspector) arguments[0]).getPrimitiveCategory() != PrimitiveCategory.STRING) {
                throw new UDFArgumentException("Usage : json_split( jsonstring, optional typestring) ");
            }
            if (!(arguments[1] instanceof ConstantObjectInspector)) {
                throw new UDFArgumentException("Usage : json_split( jsonstring, typestring) : typestring must be constant");
            }
            ConstantObjectInspector typeInsp = (ConstantObjectInspector) arguments[1];
            String typeString = ((Text) typeInsp.getWritableConstantValue()).toString();
            TypeInfo valType = TypeInfoUtils.getTypeInfoFromTypeString(typeString);

            ObjectInspector valInsp = TypeInfoUtils.getStandardJavaObjectInspectorFromTypeInfo(valType);
            ObjectInspector setInspector = ObjectInspectorFactory.getStandardListObjectInspector(valInsp);
            inspHandle = InspectorHandleFactory.GenerateInspectorHandle(setInspector);
            return inspHandle.getReturnType();

        } else {
            ObjectInspector valInspector = PrimitiveObjectInspectorFactory.javaStringObjectInspector;

            ObjectInspector setInspector = ObjectInspectorFactory.getStandardListObjectInspector(valInspector);
            inspHandle = InspectorHandleFactory.GenerateInspectorHandle(setInspector);
            return inspHandle.getReturnType();
        }
    }

}