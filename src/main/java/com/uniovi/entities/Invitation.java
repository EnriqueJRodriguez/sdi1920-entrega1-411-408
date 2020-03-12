package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Invitation {
	
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
    @JoinColumn(referencedColumnName = "id")
	private User sender;
	@ManyToOne
    @JoinColumn(referencedColumnName = "id")
	private User receiver;

	public Invitation(long id, User sender, User receiver) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
	}

	public Invitation() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

}
