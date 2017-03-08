package com.pa.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CurriculumController {
	private static final long serialVersionUID = 1L;
	
	private String name = "teste";

	public String getName() {
		return name;
	}
	
	public CurriculumController() {
		System.out.println("constructing...");
	}
	
	public void loadCurriculum() {
		System.out.println("loading...");
	}
	
}
