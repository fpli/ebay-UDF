package brickhouse.udf.collect;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

@Description(name="combine", value="_FUNC_(a,b) - Returns a combined list of two lists, or a combined map of two maps ")
public class CombineUDF extends GenericUDF
{
    private static final Logger logger = Logger.getLogger(CombineUDF.class);
    private ObjectInspector.Category category;
    private StandardListObjectInspector stdListInspector;
    private StandardMapObjectInspector stdMapInspector;
    private ListObjectInspector[] listInspectorList;
    private MapObjectInspector[] mapInspectorList;

    public Object evaluate(GenericUDF.DeferredObject[] args) throws HiveException
    {
        List addList;
        if (this.category == ObjectInspector.Category.LIST)
        {
            int currSize = 0;
            Object theList = this.stdListInspector.create(currSize);
            int lastIdx = 0;
            for (int i = 0; i < args.length; i++) {
                addList = this.listInspectorList[i].getList(args[i].get());
                currSize += addList.size();
                this.stdListInspector.resize(theList, currSize);

                for (int j = 0; j < addList.size(); j++) {
                    Object uninspObj = addList.get(j);
                    Object stdObj = ObjectInspectorUtils.copyToStandardObject(uninspObj, this.listInspectorList[i].getListElementObjectInspector(), ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
                    this.stdListInspector.set(theList, lastIdx + j, stdObj);
                }
                lastIdx += addList.size();
            }
            return theList;
        }
        if (this.category == ObjectInspector.Category.MAP)
        {
            Object theMap = this.stdMapInspector.create();
            for (int i = 0; i < args.length; i++) {
                if (args[i].get() != null) {
                    Map addMap = this.mapInspectorList[i].getMap(args[i].get());
                // adjust list type like below:
//                Iterator<Map.Entry<Object, Object>> it = addMap.entrySet().iterator();
//                List addList1= new ArrayList();
//                while (it.hasNext()){ addList1.add(it.next()); }
//                while ( addList1.listIterator().hasNext() ) {
                   for (addList = (List) addMap.entrySet().iterator(); addList.listIterator().hasNext(); ) {
                        Object uninspObj = addList.listIterator().next();
                        Map.Entry uninspEntry = (Map.Entry)uninspObj;
                        Object stdKey = ObjectInspectorUtils.copyToStandardObject(uninspEntry.getKey(), this.mapInspectorList[i].getMapKeyObjectInspector(), ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
                        Object stdVal = ObjectInspectorUtils.copyToStandardObject(uninspEntry.getValue(), this.mapInspectorList[i].getMapValueObjectInspector(), ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
                        this.stdMapInspector.put(theMap, stdKey, stdVal);
                    }
                }
            }
            return theMap;
        }
        logger.error(" Only maps or lists are supported ");
        throw new HiveException(" Only maps or lists are supported ");
    }

    public String getDisplayString(String[] args)
    {
        StringBuilder sb = new StringBuilder("combine( ");
        for (int i = 0; i < args.length - 1; i++) {
            sb.append(args[i]);
            sb.append(",");
        }
        sb.append(args[(args.length - 1)]);
        sb.append(")");
        logger.error(sb.toString());
        return sb.toString();
    }

    public ObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException
    {
        //logger.setLevel(Level.INFO);
        //logger.addAppender(org.apache.log4j.ConsoleAppender);
        BasicConfigurator.configure();
        if (args.length < 2) {
            logger.error("Usage: combine takes 2 or more maps or lists, and combines the result");
            throw new UDFArgumentException("Usage: combine takes 2 or more maps or lists, and combines the result");
        }
        ObjectInspector first = args[0];
        this.category = first.getCategory();

        if (this.category == ObjectInspector.Category.LIST) {
            this.listInspectorList = new ListObjectInspector[args.length];
            this.listInspectorList[0] = ((ListObjectInspector)first);
            for (int i = 1; i < args.length; i++) {
                ObjectInspector argInsp = args[i];
                if (!ObjectInspectorUtils.compareTypes(first, argInsp)) {
                    logger.error("Combine must either be all maps or all lists of the same type");
                    throw new UDFArgumentException("Combine must either be all maps or all lists of the same type");
                }
                this.listInspectorList[i] = ((ListObjectInspector)argInsp);
            }
            this.stdListInspector = ((StandardListObjectInspector)ObjectInspectorUtils.getStandardObjectInspector(first, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA));
            return this.stdListInspector;
        }
        if (this.category == ObjectInspector.Category.MAP) {
            this.mapInspectorList = new MapObjectInspector[args.length];
            this.mapInspectorList[0] = ((MapObjectInspector)first);
            for (int i = 1; i < args.length; i++) {
                ObjectInspector argInsp = args[i];
               if (!ObjectInspectorUtils.compareTypes(first, argInsp)) {
                    logger.error("Combine must either be all maps or all lists of the same type");
                   throw new UDFArgumentException("Combine must either be all maps or all lists of the same type");
               }
                this.mapInspectorList[i] = ((MapObjectInspector)argInsp);
             }
            this.stdMapInspector = ((StandardMapObjectInspector)ObjectInspectorUtils.getStandardObjectInspector(first, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA));
            return this.stdMapInspector;
        }
        logger.error("combine only takes maps or lists.");
        throw new UDFArgumentException(" combine only takes maps or lists.");
    }
}