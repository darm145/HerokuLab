package edu.eci.services.contracts;

import java.util.UUID;

import edu.eci.models.Car;
import edu.eci.persistences.repositories.DAO;

public interface ICarServices extends DAO<Car ,UUID>{
	
}
