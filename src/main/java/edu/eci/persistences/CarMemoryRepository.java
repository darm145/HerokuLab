package edu.eci.persistences;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.eci.models.Car;
import edu.eci.models.User;
import edu.eci.persistences.repositories.ICarRepository;
import static java.util.stream.Collectors.toList;
@Component
@Qualifier("CarMemoryRepository")
public class CarMemoryRepository implements ICarRepository{
	public static List<Car> carsContainer;
    public static List<Car> getContainer(){
        if(CarMemoryRepository.carsContainer == null)
            CarMemoryRepository.carsContainer = new ArrayList<>();
        return CarMemoryRepository.carsContainer;
    }

	@Override
	public List<Car> findAll() {
		
		return CarMemoryRepository.getContainer();
	}

	@Override
	public Car find(UUID id) {
		Optional<Car> car=CarMemoryRepository.getContainer()
						.stream()
						.filter(c -> id.equals(c.getId()))
						.findAny();
		return car.isPresent() ? car.get() : null;
	}

	@Override
	public UUID save(Car entity) {
		CarMemoryRepository.getContainer().add(entity);
		return entity.getId();
	}

	@Override
	public void update(Car entity) {
		CarMemoryRepository.carsContainer=CarMemoryRepository.getContainer()
										  .stream()
										  .map(c -> c.getBrand().equals(entity.getBrand())? entity : c)
										  .collect(toList());
	}

	@Override
	public void delete(Car o) {
		CarMemoryRepository.carsContainer = CarMemoryRepository.getContainer()
                .stream()
                .filter(c -> !c.getBrand().equals(o.getBrand()))
                .collect(toList());
		
	}

	@Override
	public void remove(UUID id) {
		CarMemoryRepository.carsContainer = CarMemoryRepository.getContainer()
                .stream()
                .filter(c -> !c.getId().equals(id))
                .collect(toList());
		
	}

}
