package ar.edu.itba.paw.webapp.models;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParamLongList {
    private List<Long> values;
    public ParamLongList(String url) {
        if(url == null) {
            throw new IllegalArgumentException();
        }
        String[] split = url.split(",");
        values = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }

    public List<Long> getValues() {
        return values;
    }
}
