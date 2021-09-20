package ar.edu.itba.paw.models;

public enum SearchingCriterias {
    NONE(0,"NONE"),
    NAME(1,"NAME"),
    DATE(2,"DATE");
    private final int identifier;
    private final String value;
    SearchingCriterias(int identifier, String value){
        this.identifier = identifier;
        this.value = value;
    }
    public int getIdentifier(){
        return identifier;
    }
    public String getValue(){ return value; };
}