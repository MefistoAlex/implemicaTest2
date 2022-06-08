package test2;

import java.io.Serializable;
import java.util.Objects;

public class City implements Serializable, Comparable<City> {

    private int mark;// mark of the top of the graph
    private int id;
    private String name;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
        this.mark = Integer.MAX_VALUE;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "City{id = " + id + name + '}';
    }


    @Override
    public int compareTo(City o) {
        //kill-sort to start at the first vertex
        return mark - o.mark;
    }
}
