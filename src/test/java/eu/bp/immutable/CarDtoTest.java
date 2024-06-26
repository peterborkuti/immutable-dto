package eu.bp.immutable;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CarDtoTest {
    @Test
    void immutable() {
        Set<Long> set = new HashSet<>();
        CarDto dto = new CarDto(1L, "X", set);
        assertThrows(UnsupportedOperationException.class, () -> dto.getOwnerIds().add(1L));

        set.add(1L);
        assertEquals(0, dto.getOwnerIds().size());
    }

}