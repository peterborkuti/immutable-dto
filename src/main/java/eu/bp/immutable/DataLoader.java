package eu.bp.immutable;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class DataLoader implements ApplicationRunner {
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        List<Car> cars = RandomEntity.cars(20);
        carRepository.saveAll(cars);
        List<Owner> owners = RandomEntity.owners(cars);
        ownerRepository.saveAll(owners);
    }
}
