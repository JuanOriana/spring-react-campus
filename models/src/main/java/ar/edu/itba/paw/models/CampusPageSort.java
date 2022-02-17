package ar.edu.itba.paw.models;


import ar.edu.itba.paw.models.exception.OrderParamArgumentException;

public class CampusPageSort {
    private final String direction;
    private final String property;

    public CampusPageSort(String direction, String property) {
        if(!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc"))
            throw new OrderParamArgumentException();
        this.direction = direction;
        switch (property.toLowerCase()) {
            case "date":
                this.property = "fileDate";
                break;
            case "downloads":
                this.property = "downloads";
                break;
            case "name":
                this.property = "fileName";
                break;
            default:
                throw new OrderParamArgumentException();
        }

    }

    public String getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
    }
}
