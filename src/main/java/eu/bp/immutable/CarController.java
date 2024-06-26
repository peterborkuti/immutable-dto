package eu.bp.immutable;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService service;
    @GetMapping(name = "cars")
    public List<CarDto> cars(){
        return service.cars();
    }
}
