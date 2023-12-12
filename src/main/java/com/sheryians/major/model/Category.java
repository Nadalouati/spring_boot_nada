package com.sheryians.major.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@jakarta.persistence.Entity
@Data

public class Category {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
	private int id;
	private String name;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
