package bean;

import annotation.Table;
import annotation.Column;


@Table(tableName = "t_recentoj")
public class contest{
	@Column(field = "id", type = "int(8)", primaryKey = true, defaultNull = false)
	private int ID;
	
	@Column(field = "oj", type = "text")
	private String OJ;
	
	@Column(field = "url", type = "text")
	private String URL;
	
	@Column(field = "name", type = "text")
	private String NAME;
	
	@Column(field = "time", type = "text")
	private String TIME;
	
	@Column(field = "week", type = "text")
	private String WEEK;
	
	@Column(field = "acess", type = "text")
	private String ACESS;
	
	public contest() {
		ID = 0;
		OJ = "";
		URL = "";
		NAME = "";
		TIME = "";
		WEEK = "";
		ACESS = "";
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getOJ() {
		return OJ;
	}

	public void setOJ(String oJ) {
		OJ = oJ;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public String getWEEK() {
		return WEEK;
	}

	public void setWEEK(String wEEK) {
		WEEK = wEEK;
	}

	public String getACESS() {
		return ACESS;
	}

	public void setACESS(String aCESS) {
		ACESS = aCESS;
	}

}
