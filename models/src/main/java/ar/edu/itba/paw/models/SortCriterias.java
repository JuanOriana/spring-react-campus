package ar.edu.itba.paw.models;

public enum SortCriterias {
    NONE(0,"none"),
    NAME(1,"name", "fileName"),
    DATE(2,"date", "fileDate"),
    DOWNLOADS(3,"downloads");
    private final int identifier;
    private final String value;
    private final String translation;
    SortCriterias(int identifier, String value){
        this.identifier = identifier;
        this.value = value;
        this.translation = this.value;
    }
    SortCriterias(int identifier, String value, String translation){
        this.identifier = identifier;
        this.value = value;
        this.translation = translation;
    }

    public int getIdentifier(){
        return identifier;
    }
    public String getValue(){ return value; }
    public String getTranslation() { return translation; }
}