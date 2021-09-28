package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.exception.PaginationArgumentException;

public class CampusPageSort {
    private final String direction;
    private final String property;

    public CampusPageSort(String direction, String property) {
        if(!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc"))
            throw new PaginationArgumentException();
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
                throw new PaginationArgumentException();
        }

    }

    public String getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
    }
}
