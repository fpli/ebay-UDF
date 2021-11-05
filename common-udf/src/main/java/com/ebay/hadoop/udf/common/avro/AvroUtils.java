//
// Source code recreated by IntelliJ IDEA
// From viewfs://apollo-rno/user/hive/warehouse/adpo-crmd-udf-0.0.1-20191031.175623-3.jar
// (powered by FernFlower decompiler)
//

package com.ebay.hadoop.udf.common.avro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.io.FileUtils;

public class AvroUtils {
  public AvroUtils() {
  }

  public static <C> Schema generateAvroSchema(Class<C> c) {
    Schema schema = ReflectData.get().getSchema(c);
    return schema;
  }

  public static <C> void writeAvroSchemaFile(Class<C> c) throws IOException {
    Schema schema = generateAvroSchema(c);
    FileUtils.writeStringToFile(new File(c.getSimpleName() + ".avsc"), schema.toString());
  }

  public static Schema getSchema(String file) {
    try {
      InputStream in = AvroUtils.class.getClassLoader().getResourceAsStream(file);
      return (new Parser()).parse(in);
    } catch (IOException var2) {
      throw new IllegalStateException(var2);
    }
  }

  public static String serJson(GenericRecord gr) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Encoder jsonEncoder = EncoderFactory.get().jsonEncoder(gr.getSchema(), out);
    SpecificDatumWriter<GenericRecord> writer = new SpecificDatumWriter(gr.getSchema());
    writer.write(gr, jsonEncoder);
    jsonEncoder.flush();
    return out.toString();
  }

  public static byte[] ser(GenericRecord gr) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, (BinaryEncoder)null);
      DatumWriter<GenericRecord> writer = new SpecificDatumWriter(gr.getSchema());
      writer.write(gr, encoder);
      encoder.flush();
      out.close();
      byte[] serializedBytes = out.toByteArray();
      return serializedBytes;
    } catch (IOException var5) {
      throw new IllegalStateException(var5);
    }
  }

  public static GenericRecord deser(byte[] serializedBytes, String schemaStr) {
    try {
      Schema schema = (new Parser()).parse(schemaStr);
      SpecificDatumReader<GenericRecord> reader = new SpecificDatumReader(schema);
      Decoder decoder = DecoderFactory.get().binaryDecoder(serializedBytes, (BinaryDecoder)null);
      GenericRecord gr = (GenericRecord)reader.read(null, decoder);
      return gr;
    } catch (IOException var6) {
      throw new IllegalStateException(var6);
    }
  }

  public static GenericRecord deser(byte[] serializedBytes, Schema schema) {
    try {
      SpecificDatumReader<GenericRecord> reader = new SpecificDatumReader(schema);
      Decoder decoder = DecoderFactory.get().binaryDecoder(serializedBytes, (BinaryDecoder)null);
      GenericRecord gr = (GenericRecord)reader.read(null, decoder);
      return gr;
    } catch (IOException var5) {
      throw new IllegalStateException(var5);
    }
  }
}
