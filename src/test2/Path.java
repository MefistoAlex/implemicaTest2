package test2;

import java.io.Serializable;

public class Path implements Serializable {
    private Integer idFrom;
    private Integer idTo;
    private Integer cost;

    public Path() {
    }

    public Path(Integer idFrom, Integer idTo, Integer cost) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.cost = cost;
    }

    public Integer getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Integer idFrom) {
        this.idFrom = idFrom;
    }

    public Integer getIdTo() {
        return idTo;
    }

    public void setIdTo(Integer idTo) {
        this.idTo = idTo;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Path{" + "idFrom=" + idFrom + ", idTo=" + idTo + ", cost=" + cost + '}';
    }
}
