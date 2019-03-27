package edu.eci.services.contracts;

import edu.eci.models.User;
import edu.eci.persistences.repositories.DAO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IUserServices extends DAO<User,UUID>{
    List<User> list();
    User create(User user);
    User get(UUID id);
    User get(String name);
	void createUser(User user);
	
}
