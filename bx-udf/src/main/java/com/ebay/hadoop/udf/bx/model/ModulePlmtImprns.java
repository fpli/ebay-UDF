package com.ebay.hadoop.udf.bx.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created  05/15/2017 | 18:19
 *
 * @author : Johnnie Zhang
 * @version : 1.0.0
 * @since : 1.0.0
 */
@JsonIgnoreProperties(value = { "moduleId" })
public class ModulePlmtImprns implements Serializable{

    private static final long serialVersionUID = -7785751836589816015L;

    private String moduleId;
    private String iid;
    private String moduleData;

    public ModulePlmtImprns() {
    }

    public ModulePlmtImprns(String moduleId, String iid, String moduleData) {
        this.moduleId = moduleId;
        this.iid = iid;
        this.moduleData = moduleData;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getModuleData() {
        return moduleData;
    }

    public void setModuleData(String moduleData) {
        this.moduleData = moduleData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModulePlmtImprns that = (ModulePlmtImprns) o;

        if (moduleId != null ? !moduleId.equals(that.moduleId) : that.moduleId != null) return false;
        if (iid != null ? !iid.equals(that.iid) : that.iid != null) return false;
        return moduleData != null ? moduleData.equals(that.moduleData) : that.moduleData == null;
    }

    @Override
    public int hashCode() {
        int result = moduleId != null ? moduleId.hashCode() : 0;
        result = 31 * result + (iid != null ? iid.hashCode() : 0);
        result = 31 * result + (moduleData != null ? moduleData.hashCode() : 0);
        return result;
    }


}
