package org.monstor.entitymapping.spark.udf;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.undercouch.bson4jackson.BsonFactory;
import de.undercouch.bson4jackson.BsonGenerator;
import lombok.val;
import org.monstor.repackaged.com.google.protobuf.Any;
import org.monstor.repackaged.com.google.protobuf.UnsafeByteOperations;
import org.monstor.service.v1.proto_common.*;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * DocumentGroup is like
 *            person1            person2
 *              /  \                /  \
 *             /    \              /    \
 *         pub1    pub2         pub3   pub4
 */
public class DocumentGroupFragmentGenerator {
    public static List<Fragment> createDocumentGroupFragements() {
        String keyspace = "MyKeyspace";
        IDp personId1 = IDp.newBuilder().setCollection("Person").setLong(123L).build();
        IDp personId2 = IDp.newBuilder().setCollection("Person").setLong(124L).build();
        IDp pubId1 = IDp.newBuilder().setCollection("Publication").setLong(1L).build();
        IDp pubId2 = IDp.newBuilder().setCollection("Publication").setLong(2L).build();
        IDp pubId3 = IDp.newBuilder().setCollection("Publication").setLong(3L).build();
        IDp pubId4 = IDp.newBuilder().setCollection("Publication").setLong(4L).build();

        // function to create document
        Function<String, DocumentP> docCreator = (json) -> DocumentP.newBuilder()
                .setCreationDate(DateP.newBuilder().setMilliseconds(123456))
                .setModificationDate(DateP.newBuilder().setMilliseconds(123456))
                .setStructEncoded(toBson(json))
                .build();

        // documents
        KeyWithDocumentP doc1 = KeyWithDocumentP.newBuilder().setKey(KeyP.newBuilder().addId(personId1)).setDocument(docCreator.apply(createPerson1())).build();
        KeyWithDocumentP doc2 = KeyWithDocumentP.newBuilder().setKey(KeyP.newBuilder().addId(personId1).addId(pubId1)).setDocument(docCreator.apply(createPerson1Publication1())).build();
        KeyWithDocumentP doc3 = KeyWithDocumentP.newBuilder().setKey(KeyP.newBuilder().addId(personId1).addId(pubId2)).setDocument(docCreator.apply(createPerson1Publication2())).build();
        KeyWithDocumentP doc4 = KeyWithDocumentP.newBuilder().setKey(KeyP.newBuilder().addId(personId2)).setDocument(docCreator.apply(createPerson2())).build();
        KeyWithDocumentP doc5 = KeyWithDocumentP.newBuilder().setKey(KeyP.newBuilder().addId(personId2).addId(pubId3)).setDocument(docCreator.apply(createPerson2Publication1())).build();
        KeyWithDocumentP doc6 = KeyWithDocumentP.newBuilder().setKey(KeyP.newBuilder().addId(personId1).addId(pubId4)).setDocument(docCreator.apply(createPerson2Publication2())).build();

        /*
         * document group 1 = fragment 1 + fragment 2
         * fragment 1 = doc1(root) + doc2
         * fragment 2 = doc3
         */
        DocumentGroupFragmentP f1 = DocumentGroupFragmentP.newBuilder().setSeq(0).setLast(false)
                .setDocumentGroup(
                        DocumentGroupP.newBuilder().setGroupKey(KeyP.newBuilder().addId(personId1).build())
                                .setRoot(doc1)
                                .putChildren("Publication", DocumentList.newBuilder().addList(doc2).build())
                ).build();

        DocumentGroupFragmentP f2 = DocumentGroupFragmentP.newBuilder().setSeq(1).setLast(true)
                .setDocumentGroup(
                        DocumentGroupP.newBuilder().setGroupKey(KeyP.newBuilder().addId(personId1).build())
                                .putChildren("Publication", DocumentList.newBuilder().addList(doc3).build())
                ).build();

        /*
         * document group 2 = fragment 3 + fragment 4
         * fragment 3 = doc4(root)
         * fragment 4 = doc5 + doc6
         */
        DocumentGroupFragmentP f3 = DocumentGroupFragmentP.newBuilder().setSeq(0).setLast(false)
                .setDocumentGroup(
                        DocumentGroupP.newBuilder().setGroupKey(KeyP.newBuilder().addId(personId2).build())
                                .setRoot(doc4)
                ).build();

        DocumentGroupFragmentP f4 = DocumentGroupFragmentP.newBuilder().setSeq(1).setLast(true)
                .setDocumentGroup(
                        DocumentGroupP.newBuilder().setGroupKey(KeyP.newBuilder().addId(personId2).build())
                                .putChildren("Publication", DocumentList.newBuilder().addList(doc5).addList(doc6).build())
                ).build();

        // create DocumentGroupFragmentEncoded
        KeyP groupKey1 = KeyP.newBuilder().addId(personId1).build();
        Fragment fragment1 = new Fragment();
        fragment1.setKeyspace(keyspace);
        fragment1.setGroupKey(groupKey1.toByteArray());
        fragment1.setValue(f1.toByteArray());

        Fragment fragment2 = new Fragment();
        fragment2.setKeyspace(keyspace);
        fragment2.setGroupKey(groupKey1.toByteArray());
        fragment2.setValue(f2.toByteArray());

        KeyP groupKey2 = KeyP.newBuilder().addId(personId2).build();
        Fragment fragment3 = new Fragment();
        fragment3.setKeyspace(keyspace);
        fragment3.setGroupKey(groupKey2.toByteArray());
        fragment3.setValue(f3.toByteArray());

        Fragment fragment4 = new Fragment();
        fragment4.setKeyspace(keyspace);
        fragment4.setGroupKey(groupKey2.toByteArray());
        fragment4.setValue(f4.toByteArray());

        return Arrays.asList(fragment1, fragment2, fragment3, fragment4);
    }

    private static Any toBson(String json) throws RuntimeException{
        try {
            ObjectMapper om = new ObjectMapper();
            BsonFactory bf = new BsonFactory(om);
            val outputStream = new ByteArrayOutputStream();
            BsonGenerator gen = bf.createGenerator(outputStream);
            om.writeTree(gen, toJsonNode(json));
            gen.close();
            byte[] documentInBytes = outputStream.toByteArray();
            return Any.newBuilder().setTypeUrl("BSON").setValue(UnsafeByteOperations.unsafeWrap(documentInBytes)).build();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private static JsonNode toJsonNode(String json) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(json);
    }

    private static String createPerson1(){
        return "{\"name\":\"Tom\",\"address\":{\"street\":\"Hamilton Avenue\",\"zipcode\":95125},\"vehicles\":[{\"brand\":\"Tesla\",\"model\":\"model3\",\"price\":25000},{\"brand\":\"Toyota\",\"model\":\"camry\",\"price\":20000}]}";
    }

    private static String createPerson1Publication1() {
        return "{\"title\":\"Spring Data 101\",\"status\":1,\"fans\":1200}";
    }

    private static String createPerson1Publication2(){
        return "{\"title\":\"Deep Learning 101\",\"status\":2,\"fans\":1000}";
    }

    private static String createPerson2(){
        return "{\"name\":\"Jerry\",\"address\":{\"street\":\"Fremont Avenue\",\"zipcode\":35128},\"vehicles\":[{\"brand\":\"Honda\",\"model\":\"Civic\",\"price\":20000},{\"brand\":\"Mazda\",\"model\":\"Mazda6\",\"price\":25000}]}";
    }

    private static String createPerson2Publication1() {
        return "{\"title\":\"Artificial Intelligence\",\"status\":1,\"fans\":2200}";
    }

    private static String createPerson2Publication2(){
        return "{\"title\":\"C++ Primer\",\"status\":2,\"fans\":5000}";
    }
}
