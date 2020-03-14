package com.uniovi.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipRepository;

@Service
public class FriendshipService {
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	public Page<Friendship> getFriends(Pageable pageable) {
		return friendshipRepository.findAll(pageable);
	}
	
	public Page<Friendship> getFriendsForUser(Pageable pageable, User user) {
		return new PageImpl<Friendship>(getFriends(pageable)
				.stream()
				.filter(f -> f.getUser1().getEmail().equals(user.getEmail()) 
						|| f.getUser2().getEmail().equals(user.getEmail()))
				.collect(Collectors.toList()));
	}

	public void addFriendship(Friendship friendship) {
		friendshipRepository.save(friendship);
	}

}
