package com.ebay.search.qu

import com.ebay.search.util.TagNameNotFoundException
import com.ebay.tracking.Schemas
import org.apache.avro.Schema
import org.apache.avro.Schema.Parser
import org.apache.avro.SchemaNormalization.parsingFingerprint
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.io.DecoderFactory
import org.apache.commons.codec.binary.Base64
import org.apache.hadoop.hive.ql.exec.{Description, UDFArgumentException, UDFArgumentLengthException, UDFArgumentTypeException}
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF
import org.apache.hadoop.hive.serde2.avro.{AvroGenericRecordWritable, AvroSerDe, AvroSerdeUtils}
import org.apache.hadoop.hive.serde2.objectinspector.{ObjectInspector, ObjectInspectorUtils}
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector
import java.util.Properties
import java.{util => jutil}


/***
  ****** SPARK Dataframe UDF Code Replacement ********
  import org.apache.spark.sql.Column
  import org.apache.spark.sql.avro.functions._
  import org.apache.spark.sql.functions.{udf, unbase64}
  import com.ebay.tracking.Schemas

  object ComplexTagData {

    private def dropK(k: Int) = (binary: Array[Byte]) => binary.drop(k)
    private val drop10UDF = udf(dropK(10))


    /**
   * Retrieve Complex Tag Data data along with its schema [Here nested "Structs"] understandable in [Spark] SQL
   * @param writerSchema - Schema with which data is being written to a file [content from ".avsc" file in String]
   * @param readerSchema - Schema with which data to be read in [content from ".avsc" file in String]
   * @param inputColumn - Dataframe Column having Complex Tag as a String formatted data rows
   * @return Column with ComplexTag data along with its schema
     */
    def tagWithSchema(writerSchema: String = Schemas.latest("TAG_NAME"),
                       readerSchema: String = Schemas.latest("TAG_NAME"))
                      (inputColumn: Column) : Column = {
      val optionsMap = new java.util.HashMap[String, String]
      optionsMap.put("mode", "PERMISSIVE")
      optionsMap.put("avroSchema", readerSchema)

      from_avro(drop10UDF(unbase64(inputColumn)), writerSchema, optionsMap)
    }
  }
 *
 */

@Description(name = "complexTagReader", value = "_FUNC_(base64_encoded_payload, complex_tag_name) - "
  + "Parses the given Avro Base64 encoded data according to the given complex type specification"
  + "Example:\n" + "select _FUNC_('wwE7ZdSosGGJDwIAEG0', 'srpGist'")
class ComplexTagUDF extends GenericUDF {

  @transient var payloadOI: StringObjectInspector = _
  @transient var trackingSchemaLatest: Schema = _
  @transient val fingerprintToSchemaMap = new jutil.HashMap[String, Schema]()
  @transient val supportedTags = new jutil.HashSet[String]()
  @transient var tagNameString: java.lang.String = null
  @transient var avroSerde: AvroSerDe = null

  /**
   * Initialize Based on given ObjectInspectors
   *
   * @param arguments
   * Argument 1st: Complex Tag Base64 Encoded String Payload
   * Argument 2nd: Complex Tag Name
   * @return objectInspector with desired schema type
   */
  override def initialize(arguments: Array[ObjectInspector]): ObjectInspector = {
    /*** All checks must be passed to proceed further **/
    tagNameString = new String(getConstantStringValue(arguments, 1))
    validArguments(arguments)
    /** Initialize Fingerprints Map **/
    initFingerprintsToSchemaMap
    trackingSchemaLatest = new Parser().parse(Schemas.latest(tagNameString))
    /** Set up Avro SerDe Object and Initialize it **/
    avroSerde = new AvroSerDe()
    val propertiesWithSchema = new Properties()
    propertiesWithSchema.setProperty(AvroSerdeUtils.AvroTableProperties.SCHEMA_LITERAL.getPropName(), trackingSchemaLatest.toString())
    avroSerde.initialize(null, propertiesWithSchema)
    /*** Initialize up ObjectInspectors **/
    payloadOI = arguments(0).asInstanceOf[StringObjectInspector]
    avroSerde.getObjectInspector
  }



  /**
   * Verify all arguments are as per expectations of UDF
   * @param arguments
   */
  def validArguments(arguments: Array[ObjectInspector]): Unit = {
    /// Add way in tracking schema to fetch this nicely
    Seq("srpGist", "ep", "cassini").foreach(tag => supportedTags.add(tag))
    // checkArgsSize(arguments, 2, 2)
    checkArgPrimitive(arguments, 0)
    checkArgPrimitive(arguments, 1)
    if(arguments.length != 2)
      throw new UDFArgumentLengthException("ComplexTagUDF only takes 2 arguments: "
        + "1st: Payload [Base64 Encoded], 2nd: Complex Tag Name")
    else if (!ObjectInspectorUtils.isConstantObjectInspector(arguments(1)))
      throw new UDFArgumentTypeException(1, getFuncName + " argument 2 may only be a constant (tag name)")
    else if(!arguments(0).isInstanceOf[StringObjectInspector])
      throw new UDFArgumentException("Argument Complex Tag must be a String")
    else if(!supportedTags.contains(tagNameString))
      throw new TagNameNotFoundException(s"Tag Name must be one of the : ${supportedTags.toString}")
  }

  /**
   * Evaluates each data row based on schema passed and Base64 Encoded String
   * @param arguments
   * @return Complex Tag With Parsed Struct Hive Schema
   */
  override def evaluate(arguments: Array[GenericUDF.DeferredObject]): AnyRef = {
    val payloadString: java.lang.String = payloadOI.getPrimitiveJavaObject(arguments(0).get())
    val binaryInput = Base64.decodeBase64(payloadString)
    val (fingerprint, binaryData) = (fingerprintStringForTag(binaryInput.slice(2, 10)), binaryInput.drop(10))
    val writerSchema: Schema = schemaForFingerprint(fingerprint)
    val decoder = DecoderFactory.get().binaryDecoder(binaryData, 0, binaryInput.length, null)
    val reader = new GenericDatumReader[Any](writerSchema, trackingSchemaLatest)
    val result = reader.read(null, decoder).asInstanceOf[org.apache.avro.generic.GenericRecord]
    val avroWritable = new AvroGenericRecordWritable(result)
    avroWritable.setFileSchema(trackingSchemaLatest) // Set OutputSchema
    avroSerde.deserialize(avroWritable)
  }


  /**
   *
   * @param children
   * @return
   */
  override def getDisplayString(children: Array[String]): String =
    getStandardDisplayString("complex_tag_read", children)

  /***
   * Retrieve matching writer schema for the current Record
   * @param currentFingerprint
   * @return
   */
  private def schemaForFingerprint(currentFingerprint: String): Schema = fingerprintToSchemaMap
    .getOrDefault(currentFingerprint, trackingSchemaLatest)

  /**
   * Using Java Map to avoid invocation error on nodes
   */
  private def initFingerprintsToSchemaMap = {
    Schemas.all(tagNameString)
      .map(schemaStr => new Parser().parse(schemaStr))
      .foreach{ schema =>
        fingerprintToSchemaMap.put(fingerprintStringForTag(parsingFingerprint("CRC-64-AVRO", schema)), schema)
      }
  }

  /**
   * Evaluate only when needed
   */
  @transient private lazy val keyPrefix = tagNameString + "_"

  /***
   * Easy way to store fingerprints as a String to fetch schema back from Map,
   * This is to avoid overriding, custom hashCode and equality on byteArray
   */
  @transient private val fingerprintStringForTag = (binaryFingerprint: Array[Byte]) =>
    keyPrefix + Base64.encodeBase64String(binaryFingerprint)

}
