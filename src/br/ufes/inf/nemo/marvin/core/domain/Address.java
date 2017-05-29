package br.ufes.inf.nemo.marvin.core.domain;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable @Access(AccessType.FIELD)
public @Data class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1087140827037854322L;

	

	@Basic
	private String street;
	
	@Basic
	private int number;
	
	@Basic
	private String district;
	
	@Basic
	private String city;
	
	@Basic
	private String state;

	@Basic
	private String cep;	
}
