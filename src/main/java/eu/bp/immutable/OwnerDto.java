package eu.bp.immutable;

import lombok.*;

import java.util.*;

@Getter
public class OwnerDto {
    private final Long id;
    private final String name;
    private final Set<Long> carIds;

    public OwnerDto(Long id, String name, Set<Long> carIds) {
        this.id = id;
        this.name = name;
        this.carIds = Collections.unmodifiableSet(carIds);
    }
}
