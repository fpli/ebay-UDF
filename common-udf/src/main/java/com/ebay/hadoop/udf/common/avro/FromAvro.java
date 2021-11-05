//
// Source code recreated by IntelliJ IDEA
// From viewfs://apollo-rno/user/hive/warehouse/adpo-crmd-udf-0.0.1-20191031.175623-3.jar
// (powered by FernFlower decompiler)
//

package com.ebay.hadoop.udf.common.avro;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.ebay.hadoop.udf.tags.ETLUdf;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BinaryObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;

@ETLUdf(name = "from_avro")
public class FromAvro extends GenericUDF {
  private Schema schema = null;
  private BinaryObjectInspector binaryInspector;
  private StringObjectInspector stringInspector;
  List<PrimitiveObjectInspector> inputOI;

  public FromAvro() {
  }

  public Object evaluate(DeferredObject[] args) throws HiveException {
    try {
      Object blobObj = args[0].get();
      byte[] data = this.binaryInspector.getPrimitiveJavaObject(blobObj);
      Object str = args[1].get();
      String path = this.stringInspector.getPrimitiveJavaObject(str);
      this.loadAvroSchema(path);
      GenericRecord gr = AvroUtils.deser(data, this.schema);
      return gr.toString();
    } catch (Exception var7) {
      throw new HiveException(var7);
    }
  }

  public String getDisplayString(String[] args) {
    return "from avro binary to readable text";
  }

  public ObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
    if (args.length < 2) {
      throw new UDFArgumentException("please specify COLUMN and Avro Schema HDFS path");
    } else {
      PrimitiveObjectInspector primInsp = (PrimitiveObjectInspector)args[0];
      if (primInsp.getPrimitiveCategory() != PrimitiveCategory.BINARY) {
        throw new UDFArgumentException("Column type must be BINARY");
      } else {
        PrimitiveObjectInspector sPath = (PrimitiveObjectInspector)args[1];
        if (sPath.getPrimitiveCategory() != PrimitiveCategory.STRING) {
          throw new UDFArgumentException("Avro Schema path must be String");
        } else {
          this.binaryInspector = (BinaryObjectInspector)primInsp;
          this.stringInspector = (StringObjectInspector)sPath;
          return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        }
      }
    }
  }

  public synchronized void loadAvroSchema(String path) throws HiveException, IOException {
    if (this.schema == null) {
      try {
        FileSystem fs = FileSystem.get(new Configuration());
        Path hdfs = new Path(path);
        FSDataInputStream in = fs.open(hdfs);
        String contents = IOUtils.toString(in, "UTF-8");
        this.schema = (new Parser()).parse(contents);
      } catch (EOFException var6) {
        throw new HiveException("File: " + path + " cannot be read");
      } catch (FileNotFoundException var7) {
        throw new HiveException((new File(path)).getAbsolutePath() + " doesn't exist");
      }
    }

  }
}
