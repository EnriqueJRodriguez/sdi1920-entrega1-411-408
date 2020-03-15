package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String email;

	private String name;
	private String lastName;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "receiver")
    private Set<Invitation> invitationRequestsTo = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sender")
    private Set<Invitation> invitationRequestsOf = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user1")
    private Set<Friendship> friendshipTo = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user2")
    private Set<Friendship> friendshipOf = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private Set<Publication> publications = new HashSet<>();
	

	private String role;

	private String password;
	@Transient
	private String passwordConfirm;

	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<Invitation> getInvitationRequestsTo() {
		return invitationRequestsTo;
	}

	public void setInvitationRequestsTo(Set<Invitation> invitationRequestsTo) {
		this.invitationRequestsTo = invitationRequestsTo;
	}

	public Set<Invitation> getInvitationRequestsOf() {
		return invitationRequestsOf;
	}

	public void setInvitationRequestsOf(Set<Invitation> invitationRequestsOf) {
		this.invitationRequestsOf = invitationRequestsOf;
	}

	public Set<Friendship> getFriendshipTo() {
		return friendshipTo;
	}

	public void setFriendshipTo(Set<Friendship> friendshipTo) {
		this.friendshipTo = friendshipTo;
	}

	public Set<Friendship> getFriendshipOf() {
		return friendshipOf;
	}

	public void setFriendshipOf(Set<Friendship> friendshipOf) {
		this.friendshipOf = friendshipOf;
	}

	public Set<Publication> getPublications() {
		return publications;
	}

	public void setPublications(Set<Publication> publications) {
		this.publications = publications;
	}

}
