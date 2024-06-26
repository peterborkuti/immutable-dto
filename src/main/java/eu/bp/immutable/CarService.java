package eu.bp.immutable;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository repository;
    public List<CarDto> cars() {
        return repository.findAll().stream().map(Car::toDto).collect(Collectors.toList());
    }
}
