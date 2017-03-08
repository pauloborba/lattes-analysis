package com.pa.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class KeyWord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String palavraChave1;
	
	@Column
	private String palavraChave2;
	
	@Column
	private String palavraChave3;
	
	@Column
	private String palavraChave4;
	
	@Column
	private String palavraChave5;
	
	@Column
	private String palavraChave6;

	public String getPalavraChave1() {
		return palavraChave1;
	}

	public void setPalavraChave1(String palavraChave1) {
		this.palavraChave1 = palavraChave1;
	}

	public String getPalavraChave2() {
		return palavraChave2;
	}

	public void setPalavraChave2(String palavraChave2) {
		this.palavraChave2 = palavraChave2;
	}

	public String getPalavraChave3() {
		return palavraChave3;
	}

	public void setPalavraChave3(String palavraChave3) {
		this.palavraChave3 = palavraChave3;
	}

	public String getPalavraChave4() {
		return palavraChave4;
	}

	public void setPalavraChave4(String palavraChave4) {
		this.palavraChave4 = palavraChave4;
	}

	public String getPalavraChave5() {
		return palavraChave5;
	}

	public void setPalavraChave5(String palavraChave5) {
		this.palavraChave5 = palavraChave5;
	}

	public String getPalavraChave6() {
		return palavraChave6;
	}

	public void setPalavraChave6(String palavraChave6) {
		this.palavraChave6 = palavraChave6;
	}

	public Long getId() {
		return id;
	}
	
}
