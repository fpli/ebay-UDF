/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.ebay.hadoop.udf.ep.avro.core;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class MetricIdValue extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"MetricIdValue\",\"namespace\":\"com.ebay.hadoop.udf.ep.avro.core\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"value\",\"type\":\"double\"},{\"name\":\"context\",\"type\":[\"null\",\"bytes\"],\"default\":null}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int id;
  @Deprecated public double value;
  @Deprecated public java.nio.ByteBuffer context;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public MetricIdValue() {}

  /**
   * All-args constructor.
   */
  public MetricIdValue(java.lang.Integer id, java.lang.Double value, java.nio.ByteBuffer context) {
    this.id = id;
    this.value = value;
    this.context = context;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return value;
    case 2: return context;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: value = (java.lang.Double)value$; break;
    case 2: context = (java.nio.ByteBuffer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.Integer value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'value' field.
   */
  public java.lang.Double getValue() {
    return value;
  }

  /**
   * Sets the value of the 'value' field.
   * @param value the value to set.
   */
  public void setValue(java.lang.Double value) {
    this.value = value;
  }

  /**
   * Gets the value of the 'context' field.
   */
  public java.nio.ByteBuffer getContext() {
    return context;
  }

  /**
   * Sets the value of the 'context' field.
   * @param value the value to set.
   */
  public void setContext(java.nio.ByteBuffer value) {
    this.context = value;
  }

  /** Creates a new MetricIdValue RecordBuilder */
  public static com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder newBuilder() {
    return new com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder();
  }
  
  /** Creates a new MetricIdValue RecordBuilder by copying an existing Builder */
  public static com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder newBuilder(com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder other) {
    return new com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder(other);
  }
  
  /** Creates a new MetricIdValue RecordBuilder by copying an existing MetricIdValue instance */
  public static com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder newBuilder(com.ebay.hadoop.udf.ep.avro.core.MetricIdValue other) {
    return new com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder(other);
  }
  
  /**
   * RecordBuilder for MetricIdValue instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<MetricIdValue>
    implements org.apache.avro.data.RecordBuilder<MetricIdValue> {

    private int id;
    private double value;
    private java.nio.ByteBuffer context;

    /** Creates a new Builder */
    private Builder() {
      super(com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.value)) {
        this.value = data().deepCopy(fields()[1].schema(), other.value);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.context)) {
        this.context = data().deepCopy(fields()[2].schema(), other.context);
        fieldSetFlags()[2] = true;
      }
    }
    
    /** Creates a Builder by copying an existing MetricIdValue instance */
    private Builder(com.ebay.hadoop.udf.ep.avro.core.MetricIdValue other) {
            super(com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.value)) {
        this.value = data().deepCopy(fields()[1].schema(), other.value);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.context)) {
        this.context = data().deepCopy(fields()[2].schema(), other.context);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.Integer getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder setId(int value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'id' field */
    public com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'value' field */
    public java.lang.Double getValue() {
      return value;
    }
    
    /** Sets the value of the 'value' field */
    public com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder setValue(double value) {
      validate(fields()[1], value);
      this.value = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'value' field has been set */
    public boolean hasValue() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'value' field */
    public com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder clearValue() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'context' field */
    public java.nio.ByteBuffer getContext() {
      return context;
    }
    
    /** Sets the value of the 'context' field */
    public com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder setContext(java.nio.ByteBuffer value) {
      validate(fields()[2], value);
      this.context = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'context' field has been set */
    public boolean hasContext() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'context' field */
    public com.ebay.hadoop.udf.ep.avro.core.MetricIdValue.Builder clearContext() {
      context = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public MetricIdValue build() {
      try {
        MetricIdValue record = new MetricIdValue();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.value = fieldSetFlags()[1] ? this.value : (java.lang.Double) defaultValue(fields()[1]);
        record.context = fieldSetFlags()[2] ? this.context : (java.nio.ByteBuffer) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
