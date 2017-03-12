package com.pa.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Curriculo {
	@Id
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private int countConcludedOrientations;
	
	@Column
	private int countOnGoingOrientations;
	
	@OneToMany(cascade=CascadeType.ALL)
	private Set<Publication> publications;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Orientation> concludedOriantations;
	
	@Column
	private ArrayList<Orientation> onGoingOriantations;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<TechinicalProduction> techinicalProduction;
	
	@Column
	private Date lastUpdate;
	
	public Curriculo() {}
	
	public Curriculo(String name, Date lastUpdate) {
		this.name = name;
		this.lastUpdate = lastUpdate;
		this.publications = new HashSet<Publication>();
	}
	
	public Integer getCountTechinicalProduction(){
		return this.techinicalProduction.size();
	}
	
	public Integer getCountPublications(){
		return this.publications.size();
	}
	
	public List<TechinicalProduction> getTechinicalProduction() {
		return techinicalProduction;
	}

	public void setTechinicalProduction(ArrayList<TechinicalProduction> techinicalProduction) {
		this.techinicalProduction = techinicalProduction;
	}

	public List<Orientation> getConcludedOriantations() {
		return concludedOriantations;
	}

	public void setConcludedOriantations(List<Orientation> concludedOriantations) {
		this.concludedOriantations = concludedOriantations;
	}

	public List<Orientation> getOnGoingOriantations() {
		return onGoingOriantations;
	}

	public void setOnGoingOriantations(ArrayList<Orientation> onGoingOriantations) {
		this.onGoingOriantations = onGoingOriantations;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getCountConcludedOrientations() {
		return countConcludedOrientations;
	}

	public void setCountConcludedOrientations(int countConcludedOrientations) {
		this.countConcludedOrientations = countConcludedOrientations;
	}

	public int getCountOnGoingOrientations() {
		return countOnGoingOrientations;
	}

	public void setCountOnGoingOrientations(int countOnGoingOrientations) {
		this.countOnGoingOrientations = countOnGoingOrientations;
	}

	public Set<Publication> getPublications() {
		return publications;
	}
	
	public void setPublications(Set<Publication> publications) {
		this.publications = publications;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
