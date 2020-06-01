package com.ebay.hadoop.udf.clsfd;

import fiftyone.mobile.detection.Match;
import fiftyone.mobile.detection.Provider;
import fiftyone.mobile.detection.factories.MemoryFactory;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;

public class UDTFParseUserAgent extends GenericUDTF
{
    private static final Logger logger = Logger.getLogger(UDTFParseUserAgent.class);
    Provider provider = null;
    Properties regexProp = null;
    Properties regexPropWhiteList = null;
    Properties regexPropTenant = null;
    Properties regexPropWhiteListTenant = null;
    LinkedHashMap<String, ObjectInspector> columnMap = null;
    Object[] forwardObjs = null;

    public StructObjectInspector initialize(ObjectInspector[] arg) throws UDFArgumentException
    {
        this.columnMap = new LinkedHashMap();
        this.columnMap.put("IsMobile", PrimitiveObjectInspectorFactory.writableStringObjectInspector);
        this.columnMap.put("IsSmartPhone", PrimitiveObjectInspectorFactory.writableStringObjectInspector);
        this.columnMap.put("IsTablet", PrimitiveObjectInspectorFactory.writableStringObjectInspector);
        this.columnMap.put("PlatformName", PrimitiveObjectInspectorFactory.writableStringObjectInspector);
        this.columnMap.put("PlatformVersion", PrimitiveObjectInspectorFactory.writableStringObjectInspector);
        this.columnMap.put("BrowserName", PrimitiveObjectInspectorFactory.writableStringObjectInspector);
        this.columnMap.put("BrowserVersion", PrimitiveObjectInspectorFactory.writableStringObjectInspector);
        this.columnMap.put("BrowserVendor", PrimitiveObjectInspectorFactory.writableStringObjectInspector);

        this.forwardObjs = new Object[this.columnMap.size()];
        return ObjectInspectorFactory.getStandardStructObjectInspector(new ArrayList(this.columnMap.keySet()), new ArrayList(this.columnMap.values()));
    }

    public void close() throws HiveException
    {
    }

    public void process(Object[] objs) throws HiveException
    {
        String userAgent = PrimitiveObjectInspectorUtils.getString(objs[0], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        String regexPropWhiteListPath = PrimitiveObjectInspectorUtils.getString(objs[1], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        String regexPropPath = PrimitiveObjectInspectorUtils.getString(objs[2], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        String providerPath = PrimitiveObjectInspectorUtils.getString(objs[3], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        String tenant ="";
        if (objs.length ==5)
            tenant = PrimitiveObjectInspectorUtils.getString(objs[4], PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        if (this.regexPropWhiteList == null) try {
            this.regexPropWhiteList = new Properties(); this.regexPropWhiteList.load(new FileInputStream(regexPropWhiteListPath));
        } catch (IOException e) {
            //e.printStackTrace();
           logger.error("inadequate parameter to parse user agent: expecting regular expression whitelist file - useragent_whitelist.regex is not available",new Exception(e));
           throw new RuntimeException(e);

        }

        if (this.regexProp == null) try {
            this.regexProp = new Properties(); this.regexProp.load(new FileInputStream(regexPropPath));
        } catch (IOException e) {
            logger.error("inadequate parameter to parse user agent: expecting regular expression file -  useragent.regex is not available",new Exception(e));
            throw new RuntimeException(e);
        }

        if(tenant != null && tenant != "" && this.regexPropWhiteListTenant ==null) try {
            this.regexPropWhiteListTenant = new Properties(); this.regexProp.load(new FileInputStream(tenant+"_"+regexPropWhiteListPath));
        } catch (IOException e) {
            logger.warn("inadequate parameter to parse user agent: "+tenant+"_"+regexPropWhiteListPath+" not exit, so skip this",new Exception(e));
        }

        if(tenant != null && tenant != "" && this.regexPropTenant ==null) try {
            this.regexPropTenant = new Properties(); this.regexProp.load(new FileInputStream(tenant+"_"+regexPropPath));
        } catch (IOException e) {
            logger.warn("inadequate parameter to parse user agent: "+tenant+"_"+regexPropPath+" not exit, so skip this",new Exception(e));
        }

        if (this.provider == null) try {
            this.provider = new Provider(MemoryFactory.create(providerPath));
        } catch (IOException e) {
            logger.error("inadequate parameter to parse user agent: 51degree premium file is not available",new Exception(e));
            throw new RuntimeException(e);
        }

        boolean parsed = false;
        int score = 101;

        if (userAgent != null)
        {
            int i;
            //round1: match by [tenant_useragent_whitelist.regex](opt) and [useragent_whitelist.regex]
            if ((this.regexPropWhiteListTenant != null) && (userAgent != null))
                for (Iterator localIterator1 = this.regexPropWhiteListTenant.keySet().iterator(); localIterator1.hasNext(); )
                {
                    Object key = localIterator1.next();
                    if (userAgent.matches(key.toString())) {
                        String agentValue = this.regexPropWhiteListTenant.getProperty(key.toString());
                        i = 0;
                        for (String columnValue : agentValue.split(",")) {
                            this.forwardObjs[(i++)] = new Text(columnValue);
                        }
                        parsed = true;
                        break;
                    }
                }
            if ((this.regexPropWhiteList != null) && (userAgent != null))
                for (Iterator localIterator1 = this.regexPropWhiteList.keySet().iterator(); localIterator1.hasNext(); )
                {
                    Object key = localIterator1.next();
                    if (userAgent.matches(key.toString())) {
                        String agentValue = this.regexPropWhiteList.getProperty(key.toString());
                        i = 0;
                        for (String columnValue : agentValue.split(",")) {
                            this.forwardObjs[(i++)] = new Text(columnValue);
                        }
                        parsed = true;
                        break;
                    }
                }
            //round2: match by [51Degree]
            if ((this.provider != null) && (!parsed))
            {
                Match match = null;
                try {
                    match = this.provider.match(userAgent);
                    score = match.getDifference();
                } catch (IOException e) {
                    //e.printStackTrace();
                    logger.warn("parsing by 51Degree err",new IOException(e));
                }
                if (match != null) {
                    i = 0;
                    for (String column : this.columnMap.keySet()) try {
                        this.forwardObjs[(i++)] = new Text(match.getValues(column).toString());
                    } catch (IOException localIOException1) {
                        logger.warn("get value after by parsing 51Degree err",new IOException(localIOException1));
                    }
                    parsed = true;
                }
            }
            //round3: match by [tenant_useragent.regex](opt) and [useragent.regex]
            if ((score > 100) && (this.regexPropTenant != null)) {
                for (Iterator localIterator2 = this.regexPropTenant.keySet().iterator(); localIterator2.hasNext(); ) {
                    Object key = localIterator2.next();
                    if (userAgent.matches(key.toString()))
                    {
                        String agentValue = this.regexPropTenant.getProperty(key.toString());
                        i = 0;
                        for (String columnValue : agentValue.split(",")) {
                            this.forwardObjs[(i++)] = new Text(columnValue);
                        }
                        parsed = true;
                        break;
                    }
                }
            }
            if ((score > 100) && (this.regexProp != null)) {
                for (Iterator localIterator2 = this.regexProp.keySet().iterator(); localIterator2.hasNext(); ) {
                    Object key = localIterator2.next();
                    if (userAgent.matches(key.toString()))
                    {
                        String agentValue = this.regexProp.getProperty(key.toString());
                        i = 0;
                        for (String columnValue : agentValue.split(",")) {
                            this.forwardObjs[(i++)] = new Text(columnValue);
                        }
                        parsed = true;
                        break;
                    }
                }
            }

        }

        if (!parsed) {
            this.forwardObjs[0] = new Text("False");
            this.forwardObjs[1] = new Text("False");
            this.forwardObjs[2] = new Text("False");
            this.forwardObjs[3] = new Text("Unknown");
            this.forwardObjs[4] = new Text("Unknown");
            this.forwardObjs[5] = new Text("Unknown");
            this.forwardObjs[6] = new Text("Unknown");
            this.forwardObjs[7] = new Text("Unknown");
        }

        // TODO: remove this comm before release code while junit no need this.
        forward(this.forwardObjs);
    }
}
