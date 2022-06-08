package test2;

import java.io.Serializable;

public class CityPair implements Serializable {
    private City cityFromId;
    private City cityToId;

    public CityPair() {
    }

    public CityPair(City cityFromId, City cityToId) {
        this.cityFromId = cityFromId;
        this.cityToId = cityToId;
    }

    public City getCityFromId() {
        return cityFromId;
    }

    public void setCityFromId(City cityFromId) {
        this.cityFromId = cityFromId;
    }

    public City getCityToId() {
        return cityToId;
    }

    public void setCityToId(City cityToId) {
        this.cityToId = cityToId;
    }

    @Override
    public String toString() {
        return "{From " + cityFromId.getName() + " to " + cityToId.getName() + '}';
    }
}
