package eu.bp.immutable;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
public class Car {
    @Id
    @GeneratedValue
    private long id;

    private String color;

    @ManyToMany(mappedBy = "cars")
    private Set<Owner> owners;

    public CarDto toDto() {
        return new CarDto(id, color, owners.stream().map(Owner::getId).collect(Collectors.toSet()));
    }

 }
