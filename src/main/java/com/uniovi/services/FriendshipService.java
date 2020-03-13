package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipRepository;

@Service
public class FriendshipService {
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	public List<Friendship> getFriends() {
		List<Friendship> friends = new ArrayList<Friendship>();
		friendshipRepository.findAll().forEach(friends::add);
		return friends;
	}
	
	public List<Friendship> getFriendsForUser(User user) {
		return getFriends()
				.stream()
				.filter(f -> f.getUser1().getEmail().equals(user.getEmail()) 
						|| f.getUser2().getEmail().equals(user.getEmail()))
				.collect(Collectors.toList());
	}

	public void addFriendship(Friendship friendship) {
		friendshipRepository.save(friendship);
	}

}
