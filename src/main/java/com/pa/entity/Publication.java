package com.pa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.pa.util.EnumQualisClassification;

@Entity
public class Publication implements Comparable<Publication>{
	
	@Id @GeneratedValue
	private Long id;
	
	@Column
	private String title;
	
	@Transient
	private EnumQualisClassification qualis;
	
	@Column
	private Integer year;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private PublicationType publicationType;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Author> authors;
	
	public Publication() {}
	
	public Publication(String title, int year, PublicationType typePublication, List<Author> authors) {
		this.title = title;
		this.year = year;
		this.publicationType = typePublication;
		this.authors = authors;
	}
	
	
	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public EnumQualisClassification getQualis() {
		return qualis;
	}
	
	public void setQualis(EnumQualisClassification qualis) {
		this.qualis = qualis;
	}
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public PublicationType getPublicationType() {
		return publicationType;
	}
	
	public void setPublicationType(PublicationType publicationType) {
		this.publicationType = publicationType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(Publication o) {
		if (this.year < o.getYear()) {
			return -1;
		}
		else if (this.year > o.getYear()) {
			return 1;
		}
		return 0;
	}

}
