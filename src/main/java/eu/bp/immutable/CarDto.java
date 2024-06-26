package eu.bp.immutable;

import com.google.common.collect.ImmutableSet;
import lombok.*;

import java.util.*;

@Getter
public class CarDto {
    private final long id;
    private final String color;
    private final Set<Long> ownerIds;

    public CarDto(Long id, String color, Set<Long> ownerIds) {
        // validate(...)
        this.id = id;
        this.color = color;
        this.ownerIds = ImmutableSet.copyOf(ownerIds);
        validate();
    }

    private void validate() {
        //if ( ... ) throw some Exception
    }
}
