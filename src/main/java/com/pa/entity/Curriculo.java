package com.pa.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Curriculo {
	@Id
	private Long id;
	
	private String name;
	
	private int countConcludedOrientations;
	
	private int countOnGoingOrientations;
	
	@OneToMany(cascade=CascadeType.ALL)
	private Set<Publication> publications;
	
	private ArrayList<Orientation> concludedOriantations;
	
	private ArrayList<Orientation> onGoingOriantations;
	
	private ArrayList<TechinicalProduction> techinicalProduction;
	
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
	
	public ArrayList<TechinicalProduction> getTechinicalProduction() {
		return techinicalProduction;
	}

	public void setTechinicalProduction(ArrayList<TechinicalProduction> techinicalProduction) {
		this.techinicalProduction = techinicalProduction;
	}

	public ArrayList<Orientation> getConcludedOriantations() {
		return concludedOriantations;
	}

	public void setConcludedOriantations(ArrayList<Orientation> concludedOriantations) {
		this.concludedOriantations = concludedOriantations;
	}

	public ArrayList<Orientation> getOnGoingOriantations() {
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
