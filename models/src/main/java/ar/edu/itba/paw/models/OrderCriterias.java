package ar.edu.itba.paw.models;

public enum OrderCriterias {
        ASC(0,"ASC"),
        DESC(1,"DESC");
        private final int identifier;
        private final String value;
         OrderCriterias(int identifier,String value){
            this.identifier = identifier;
            this.value = value;
        }
        public String getValue(){return value;};
        public int getIdentifier(){
            return identifier;
        }

}
