package ar.edu.itba.paw.models;

public enum SearchingCriterias {
    NONE(0),
    NAME(1),
    DATE(2);
    private final int identifier;
    SearchingCriterias(int identifier){
        this.identifier = identifier;
    }
    public int getIdentifier(){
        return identifier;
    }
}