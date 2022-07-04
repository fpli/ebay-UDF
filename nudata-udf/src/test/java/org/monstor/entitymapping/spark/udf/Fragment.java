package org.monstor.entitymapping.spark.udf;

public class Fragment {
    private String keyspace;
    private byte[] groupKey;
    private byte[] value;

    public String getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }

    public byte[] getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(byte[] groupKey) {
        this.groupKey = groupKey;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}