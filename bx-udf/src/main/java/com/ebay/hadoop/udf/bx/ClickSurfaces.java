package com.ebay.hadoop.udf.bx;

//Umesh - update the class to include IID tag

public class ClickSurfaces {
	
	private String region;
	private String rank;
	private String isCurated;
	private String iid;
	private Integer clicks;
	private Integer surfaces;
	private Integer views;
	private Integer hscrolls;


	public ClickSurfaces(){
		region="UNKNOWN";
		isCurated="0";
		setRank("0");	
		iid="-1";
		clicks=0;
		surfaces=0;
		views=0;
		hscrolls=0;
	}
	
	public ClickSurfaces(String rgn, String rank, String curated){
		this.region = rgn;
		this.rank = rank;
		this.isCurated = curated;
		clicks=0;
		surfaces=0;
		views=0;
		hscrolls=0;

	}

	public ClickSurfaces(String rgn, String rank, String curated, String iid){
		this.region = rgn;
		this.rank = rank;
		this.isCurated = curated;
		this.iid = iid;
		clicks=0;
		surfaces=0;
		views=0;
		hscrolls=0;

	}

	
	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}


	public Integer getSurfaces() {
		return surfaces;
	}
	public void setSurfaces(Integer surfaces) {
		this.surfaces = surfaces;
	}

	public void incrementClicks(Integer click){
		this.clicks = this.clicks + click;
	}
	
	public void incrementSurfaces(Integer surface){
		this.surfaces = this.surfaces + surface;
	}

	public Integer getViews() {
		return views;
	}

	public Integer getHscrolls(){return hscrolls;}

	public void incrementViews(Integer views){
		this.views = this.views + views;
	}

	public void incrementHscrolls(Integer hscrolls){
		this.hscrolls = this.hscrolls + hscrolls;
	}

	public void setViews(Integer views){
		this.views = views;
	}

	public void setHscrolls(Integer hscrolls){
		this.hscrolls = hscrolls;
	}


	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}
	
	public String getIsCurated() {
		return isCurated;
	}

	public void setIsCurated(String isCurated) {
		this.isCurated = isCurated;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
}

