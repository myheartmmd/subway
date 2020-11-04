package work;

import java.util.ArrayList;

public class station {
	private String name;
	private ArrayList<String> linename=new ArrayList<String>();
	public int cnt=-1;
	public station lastation;
    public String getname() {
        return name;
    }
    public void setname(String name) {
    	this.name=name;
    }
    public void addLine(String line) {
    	linename.add(line);
    }
    public ArrayList<String> getLine() {
        return linename;
    }
    
	
	

}
