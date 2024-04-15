package com.tvt.dto;

import com.tvt.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryDTO {
	private int id;

	@NotBlank
	@NotNull
	private String categoryName;

	public CategoryDTO() {
		super();
	}

	public CategoryDTO(int id, @NotBlank @NotNull String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}
	
	public CategoryDTO(Category c) {
		this(c.getId(), c.getCategoryName());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
