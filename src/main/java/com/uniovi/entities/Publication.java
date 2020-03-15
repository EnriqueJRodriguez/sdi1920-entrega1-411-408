package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Publication {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
    @JoinColumn(referencedColumnName = "id")
	private User author;

	private String date;
	private String title;
	private String content;

	public Publication(long id, User author, String date, String title, String content) {
		super();
		this.id = id;
		this.author = author;
		this.date = date;
		this.title = title;
		this.content = content;
	}

	public Publication() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
