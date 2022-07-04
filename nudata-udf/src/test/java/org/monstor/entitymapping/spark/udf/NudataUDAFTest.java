package org.monstor.entitymapping.spark.udf;

import org.apache.spark.sql.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NudataUDAFTest {
    private transient SparkSession spark;

    @Before
    public void setUp() {
        spark = SparkSession.builder()
                .master("local[*]")
                .appName("testing")
                .getOrCreate();
    }

    @After
    public void tearDown() {
        spark.stop();
        spark = null;
    }

    @Test
    public void udafTest_transform_single_docset(){
        List<Fragment> f = DocumentGroupFragmentGenerator.createDocumentGroupFragements();
        Encoder<Fragment> encoder = Encoders.bean(Fragment.class);
        spark.createDataset(f, encoder).createOrReplaceTempView("df");
        spark.sql("select * from df").show();
        spark.udf().registerJavaUDAF("dgMergeAndTransform", DocumentGroupFragmentUDAF.class.getName());
        Dataset<Row> ds= spark.sql("SELECT dgMergeAndTransform(value, 'org.monstor.entitymapping.spark.udf.TestTransformer', 'mapPersonValue') from df group by groupKey");
        List<Row> transformed = ds.collectAsList();
        System.out.println("transformed: " + transformed);
        Assert.assertEquals(2, transformed.size()); // # of rows
        Assert.assertEquals(1, transformed.get(0).size()); // # of columns
        Assert.assertEquals("{\"name\":\"Jerry\",\"region\":\"EAST\",\"value\":45000}", transformed.get(0).getString(0));
        Assert.assertEquals(1, transformed.get(1).size()); // # of columns
        Assert.assertEquals("{\"name\":\"Tom\",\"region\":\"WEST\",\"value\":45000}", transformed.get(1).getString(0));
    }

    @Test
    public void udafTest_transform_multiple_docsets(){
        List<Fragment> f = DocumentGroupFragmentGenerator.createDocumentGroupFragements();
        Encoder<Fragment> encoder = Encoders.bean(Fragment.class);
        spark.createDataset(f, encoder).createOrReplaceTempView("df");
        spark.sql("select * from df").show();
        spark.udf().registerJavaUDAF("dgMergeAndTransform", DocumentGroupFragmentUDAF.class.getName());
        Dataset<Row> ds= spark.sql("SELECT dgMergeAndTransform(value, 'org.monstor.entitymapping.spark.udf.TestTransformer', 'mapPersonPublicationFans') from df group by groupKey");
        List<Row> transformed = ds.collectAsList();
        System.out.println("transformed: " + transformed);
        Assert.assertEquals(2, transformed.size()); // # of rows
        Assert.assertEquals(1, transformed.get(0).size()); // # of columns
        Assert.assertEquals("{\"name\":\"Jerry\",\"region\":\"EAST\",\"fans\":7200}", transformed.get(0).getString(0));
        Assert.assertEquals(1, transformed.get(1).size()); // # of columns
        Assert.assertEquals("{\"name\":\"Tom\",\"region\":\"WEST\",\"fans\":2200}", transformed.get(1).getString(0));
    }

    @Test
    public void udafTest_returning_array(){
        List<Fragment> f = DocumentGroupFragmentGenerator.createDocumentGroupFragements();
        Encoder<Fragment> encoder = Encoders.bean(Fragment.class);
        spark.createDataset(f, encoder).createOrReplaceTempView("df");
        spark.sql("select * from df").show();
        spark.udf().registerJavaUDAF("dgMergeAndTransform", DocumentGroupFragmentUDAF.class.getName());
        Dataset<Row> ds = spark.sql("SELECT dgMergeAndTransform(value, 'org.monstor.entitymapping.spark.udf.TestTransformer', 'mapPublicationFanCount') from df group by groupKey");
        List<Row> transformed = ds.collectAsList();
        System.out.println("transformed_array: " + transformed);
        Assert.assertEquals(2, transformed.size()); // # of rows
        Assert.assertEquals(1, transformed.get(0).size()); // # of columns
        Assert.assertEquals("[{\"title\":\"Artificial Intelligence\",\"fans\":2200},{\"title\":\"C++ Primer\",\"fans\":5000}]", transformed.get(0).getString(0));
        Assert.assertEquals(1, transformed.get(1).size()); // # of columns
        Assert.assertEquals("[{\"title\":\"Spring Data 101\",\"fans\":1200},{\"title\":\"Deep Learning 101\",\"fans\":1000}]", transformed.get(1).getString(0));

        spark.sql(
                "create temporary view array_view " +
                        "as select  " +
                        "from_json(dgMergeAndTransform(value, 'org.monstor.entitymapping.spark.udf.TestTransformer', 'mapPublicationFanCount'), 'ARRAY<STRUCT<title:string,fans:int>>') " +
                        "as publicationFans from df group by groupKey");

        System.out.println("after merge and transformation ...");
        spark.sql("create temporary view exploded_view as " +
                "select explode(publicationFans) as pf from array_view");
        Dataset<Row> ds_exploded = spark.sql("select pf.title, pf.fans from exploded_view order by pf.fans desc limit 3");

        List<Row> transformed_exploded = ds_exploded.collectAsList();
        System.out.println("transformed_exploded: " + transformed);
        Assert.assertEquals(3, transformed_exploded.size()); // # of rows
        Assert.assertEquals(2, transformed_exploded.get(0).size()); // # of columns
        Assert.assertEquals("C++ Primer", transformed_exploded.get(0).getString(0));
        Assert.assertEquals(5000, transformed_exploded.get(0).getInt(1));
        Assert.assertEquals(2, transformed_exploded.get(1).size()); // # of columns
        Assert.assertEquals("Artificial Intelligence", transformed_exploded.get(1).getString(0));
        Assert.assertEquals(2200, transformed_exploded.get(1).getInt(1));
        Assert.assertEquals(2, transformed_exploded.get(2).size()); // # of columns
        Assert.assertEquals("Spring Data 101", transformed_exploded.get(2).getString(0));
        Assert.assertEquals(1200, transformed_exploded.get(2).getInt(1));
    }

}