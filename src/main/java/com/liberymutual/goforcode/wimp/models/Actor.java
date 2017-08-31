package com.liberymutual.goforcode.wimp.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property="id")
@Entity
public class Actor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length=75, nullable=false)
	private String firstName;
	
	@Column(length=75, nullable=true)
	private String lastName;
	
	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@Column(nullable=true)
	private Long activeSinceLastYear;
	
//	@JsonIgnore
	@ManyToMany(mappedBy="actors")
	private List<Movie> movies;
	
	private Date birthDate;
	
	
	//getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getActiveSinceLastYear() {
		return activeSinceLastYear;
	}

	public void setActiveSinceLastYear(Long activeSinceLastYear) {
		this.activeSinceLastYear = activeSinceLastYear;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	

}
