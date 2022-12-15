package org.pizzeria.crud.serv;

import java.util.List;
import java.util.Optional;

import org.pizzeria.crud.pojo.User;
import org.pizzeria.crud.repo.UserRepo;
import org.pizzeria.crud.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	
//  Save
	public void save(User user) {
		userRepo.save(user);
	}

//  Delete	
	public void delete(User user) {
		userRepo.delete(user);
	}

//  Delete	by ID
	public void deleteById(int id) {
		userRepo.deleteById(id);
	}
	
//  Find all
	public List<User> findAll() {
		return userRepo.findAll();
	}
	
//  Find by ID
	public Optional<User> findById(int id) {
		return userRepo.findById(id);
	}

//  Find by Username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userOpt = userRepo.findByUsername(username);
	
		if (userOpt.isEmpty()) throw new UsernameNotFoundException("User not found");
		
		return new DatabaseUserDetails(userOpt.get());	
	}
}
