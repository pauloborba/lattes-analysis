package com.pa.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ActivitySectors {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String setorDeAtividade1;
	
	@Column
	private String setorDeAtividade2;
	
	@Column
	private String setorDeAtividade3;

	public String getSetorDeAtividade1() {
		return setorDeAtividade1;
	}

	public void setSetorDeAtividade1(String setorDeAtividade1) {
		this.setorDeAtividade1 = setorDeAtividade1;
	}

	public String getSetorDeAtividade2() {
		return setorDeAtividade2;
	}

	public void setSetorDeAtividade2(String setorDeAtividade2) {
		this.setorDeAtividade2 = setorDeAtividade2;
	}

	public String getSetorDeAtividade3() {
		return setorDeAtividade3;
	}

	public void setSetorDeAtividade3(String setorDeAtividade3) {
		this.setorDeAtividade3 = setorDeAtividade3;
	}

	public Long getId() {
		return id;
	}
	
	

}
