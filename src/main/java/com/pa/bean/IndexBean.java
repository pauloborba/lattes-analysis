package com.pa.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.boot.model.relational.Database;

import com.pa.database.impl.DatabaseFacade;
import com.pa.entity.Curriculo;
import com.pa.entity.Group;
import com.pa.entity.Orientation;
import com.pa.entity.Publication;
import com.pa.entity.Qualis;
import com.pa.entity.TechnicalProduction;

@ManagedBean(name="indexBean")
@ViewScoped
public class IndexBean {
	private Integer groupsSize;
	private Integer curriculosSize;
	private Integer qualisSize;
	private Integer publicationsSize;
	private Integer orientationsSize;
	private Integer technicalProductionSize;

	@PostConstruct
	public void init() {

		List<Group> groups = DatabaseFacade.getInstance().listAllGroups();
		List<Curriculo> curriculum = DatabaseFacade.getInstance().listAllCurriculos();
		List<Qualis> qualis = DatabaseFacade.getInstance().listAllQualis();
		List<TechnicalProduction> technicalProductions = DatabaseFacade.getInstance().listAllTechnicalProductions();
		List<Orientation> orientations = DatabaseFacade.getInstance().listAllOrientations();
		List<Publication> publications = DatabaseFacade.getInstance().listAllPublications();

		groupsSize = groups.size();
		curriculosSize = curriculum.size();
		qualisSize = qualis.size();
		technicalProductionSize = technicalProductions.size();
		orientationsSize = orientations.size();
		publicationsSize = publications.size();
	}
	
	
	public Integer getPublicationsSize() {
		return publicationsSize;
	}

	public Integer getOrientationsSize() {
		return orientationsSize;
	}

	public Integer getTechnicalProductionsSize() {
		return technicalProductionSize;
	}

	public Integer getGroupsSize() {
		return groupsSize;
	}

	public void setGroupsSize(Integer groupsSize) {
		this.groupsSize = groupsSize;
	}

	public Integer getCurriculosSize() {
		return curriculosSize;
	}

	public void setCurriculosSize(Integer curriculosSize) {
		this.curriculosSize = curriculosSize;
	}

	public Integer getQualisSize() {
		return qualisSize;
	}

	public void setQualisSize(Integer qualisSize) {
		this.qualisSize = qualisSize;
	}
	
	
}
