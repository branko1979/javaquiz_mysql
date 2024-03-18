package javaQuiz.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javaQuiz.dao.UserRepository;
import javaQuiz.dto.UserWithAnswerCount;
import javaQuiz.dto.UserWithAnswerCountProjection;
import javaQuiz.model.User;

@Service
@Transactional
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;

	public UserService(UserRepository participantRepository) {
		this.userRepository = participantRepository;
	}

	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		System.out.println("iz Ucesnik service");
		for (User user : userRepository.findAll()) {
			users.add(user);
			System.out.println("Dodao ucesnika u listu!");
		}
		return users;
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public void changeDate(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			// Ažuriraj datum zadnjeg logovanja
			user.setDatum(new Date());
			userRepository.save(user);
		} else {
			// Baci izuzetak ili vrati poruku o grešci
		}
	}
	
	public List<User> findAllParticipants() {
		List<User> participants = new ArrayList<>();
		for (User user : userRepository.findAll()) {
			if (user.getRole().equals("USER")) {
				participants.add(user);
			}
		}
		return participants;
	}


	public List<User> findUsersWithCorrectAnswers(){
		return userRepository.findUsersWithCorrectAnswers();
	}
	
	public List<User> findUsersWithAllAnswers(){
		return userRepository.findUsersWithAllAnswers();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	public List<UserWithAnswerCount> getBestUsers() {
	    List<Object[]> rows = userRepository.findBestUsers();
	    List<UserWithAnswerCount> usersWithAnswerCount = new ArrayList<>();
	    for (Object[] row : rows) {
	    	User user = new User();
	    	user.setId((int)row[0]);
	    	user.setDatum((Date) row[1]);
	    	user.setIme((String)row[2]);
	    	user.setPassword((String)row[3]);
	    	user.setPrezime((String)row[4]);
	    	user.setUsername((String)row[5]);
	    	user.setRole((String)row[6]);
	    	user.setEmail((String)row[7]);
	    	
	    	System.out.println(user);
	    	
	        //User user = new User((int)row[0],(String) row[1],(String) row[2],(String) row[3],(String) row[4],(String) row[5],(Date) row[6],(String) row[7]);
	        int answerCount = ((Long) row[8]).intValue(); // U novijoj verziji je Long umesto bigInteger

	        UserWithAnswerCount userWithAnswerCount = new UserWithAnswerCount();
	        userWithAnswerCount.setUser(user);
	        userWithAnswerCount.setAnswerCount( answerCount);

	        usersWithAnswerCount.add(userWithAnswerCount);
	    }
	    return usersWithAnswerCount;
	}
	
	public List<UserWithAnswerCount> get5BestUsers() {
	    List<Object[]> rows = userRepository.find5BestUsers();
	    List<UserWithAnswerCount> usersWithAnswerCount = new ArrayList<>();
	    for (Object[] row : rows) {
	    	User user = new User();
	    	user.setId((int)row[0]);
	    	user.setDatum((Date) row[1]);
	    	user.setIme((String)row[2]);
	    	user.setPassword((String)row[3]);
	    	user.setPrezime((String)row[4]);
	    	user.setUsername((String)row[5]);
	    	user.setRole((String)row[6]);
	    	user.setEmail((String)row[7]);
	    	
	    	System.out.println(user);
	    	
	        //User user = new User((int)row[0],(String) row[1],(String) row[2],(String) row[3],(String) row[4],(String) row[5],(Date) row[6],(String) row[7]);
	        int answerCount = ((Long) row[8]).intValue(); // Assuming answerCount is at index 1

	        UserWithAnswerCount userWithAnswerCount = new UserWithAnswerCount();
	        userWithAnswerCount.setUser(user);
	        userWithAnswerCount.setAnswerCount( answerCount);

	        usersWithAnswerCount.add(userWithAnswerCount);
	    }
	    return usersWithAnswerCount;
	}

	public void save(User user) {
		userRepository.save(user);
		
	}
}
