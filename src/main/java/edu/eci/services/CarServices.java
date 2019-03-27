package edu.eci.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.eci.models.Car;
import edu.eci.persistences.repositories.ICarRepository;
import edu.eci.services.contracts.ICarServices;
@Component
public class CarServices implements ICarServices{
	
	@Autowired
	@Qualifier("CarMemoryRepository")
	private ICarRepository cr;
	@Override
	public List<Car> findAll() {
		
		return cr.findAll();
	}

	@Override
	public Car find(UUID id) {
		return cr.find(id);
	}

	@Override
	public UUID save(Car entity) {
		return cr.save(entity);
	}

	@Override
	public void update(Car entity) {
		cr.save(entity);
		
	}

	@Override
	public void delete(Car o) {
		cr.delete(o);
		
	}

	@Override
	public void remove(UUID id) {
		cr.remove(id);
		
	}

}
