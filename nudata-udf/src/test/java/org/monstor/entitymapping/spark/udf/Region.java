package org.monstor.entitymapping.spark.udf;

public enum Region {
    WEST,
    MIDDLE,
    EAST;

    public static Region fromZipcode(int zipCode){
        // 95125
        int firstDigit = zipCode / 10000;
        if(firstDigit < 5){
            return EAST;
        }else if(firstDigit < 7){
            return MIDDLE;
        }else{
            return WEST;
        }
    }
}
