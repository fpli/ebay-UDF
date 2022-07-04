package org.monstor.entitymapping.spark.udf;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.monstor.entitymapping.annotations.DocumentGroupToLogicalTransformer;
import org.monstor.entitymapping.model.DocumentGroup;
import org.monstor.entitymapping.model.DocumentWithMetadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestTransformer {
    @DocumentGroupToLogicalTransformer(name="mapPersonValue")
    public PersonVehicleValues mapPersonValue(DocumentGroup dg){
        ObjectNode input = dg.getRoot().parseAsJson();
        PersonVehicleValues stats = new PersonVehicleValues();
        stats.setName(input.get("name").asText());
        // add prices for all vehicles
        ArrayNode vehicles = (ArrayNode)input.get("vehicles");
        int totalValue = 0;
        Iterator<JsonNode> it = vehicles.elements();
        while (it.hasNext()){
            ObjectNode vehicle = (ObjectNode)it.next();
            totalValue += vehicle.get("price").asInt();
        }
        stats.setValue(totalValue);
        stats.setRegion(Region.fromZipcode(input.get("address").get("zipcode").asInt()));
        return stats;
    }

    @DocumentGroupToLogicalTransformer(name="mapPersonPublicationFans")
    public PersonPublicationFans mapPersonPublicationFans(DocumentGroup dg){
        PersonPublicationFans pfv = new PersonPublicationFans();

        ObjectNode input = dg.getRoot().parseAsJson();
        pfv.setName(input.get("name").asText());
        pfv.setRegion(Region.fromZipcode(input.get("address").get("zipcode").asInt()));

        List<DocumentWithMetadata> publications = dg.getChildren().get("Publication");
        int totalFans = 0;
        if(publications != null) {
            for (DocumentWithMetadata publication : publications) {
                ObjectNode pub = publication.parseAsJson();
                totalFans += pub.get("fans").asInt();
            }
        }
        pfv.setFans(totalFans);
        return pfv;
    }

    @DocumentGroupToLogicalTransformer(name="mapPublicationFanCount")
    public List<PublicationFanCount> mapPublicationFanCount(DocumentGroup dg){
        List<PublicationFanCount> pfs = new ArrayList<>();
        // get all publications in this document group
        List<DocumentWithMetadata> publications = dg.getChildren().get("Publication");

        for(DocumentWithMetadata publication : publications){
            PublicationFanCount pf = new PublicationFanCount();
            ObjectNode pub = publication.parseAsJson();
            pf.setTitle(pub.get("title").asText());
            pf.setFans(pub.get("fans").asInt());
            pfs.add(pf);
        }
        return pfs;
    }
}
