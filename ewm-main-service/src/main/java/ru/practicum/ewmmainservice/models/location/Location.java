package ru.practicum.ewmmainservice.models.location;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "locations", uniqueConstraints = {@UniqueConstraint(columnNames = {"lat", "lon"})})
@AllArgsConstructor
@NoArgsConstructor
public class Location implements Coordinates{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private double lat;
    @NotNull
    private double lon;

    private int radius = 10;
    @ManyToOne
    private Location parent;
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Location> childs;
    private Boolean approved;

    public Location(String name, double lat, double lon, int radius, Location parent, Boolean approved) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
        this.parent = parent;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Location getParent() {
        return parent;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    public Collection<Location> getChilds() {
        return childs;
    }

    public void setChilds(Collection<Location> childs) {
        this.childs = childs;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.lat, lat) == 0 && Double.compare(location.lon, lon) == 0 && radius == location.radius && Objects.equals(id, location.id) && Objects.equals(name, location.name) && Objects.equals(parent, location.parent) && Objects.equals(childs, location.childs) && Objects.equals(approved, location.approved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lat, lon, radius, parent, childs, approved);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", radius=" + radius +
                ", parent=" + parent +
                ", approved=" + approved +
                '}';
    }
}
