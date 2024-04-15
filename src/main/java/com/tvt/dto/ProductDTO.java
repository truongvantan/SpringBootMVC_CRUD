package com.tvt.dto;

import com.tvt.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {
	private int id;

	@NotBlank(message = "Vui lòng nhập tên sản phẩm")
	private String productName;

	@NotNull(message = "Vui lòng nhập đơn giá nhập")
	private double priceIn;

	@NotNull(message = "Vui lòng nhập đơn giá bán")
	private double priceSell;

	private String linkImage;
	private String description;

	@NotNull(message = "Vui lòng chọn thương hiệu")
	private String categoryId;

	public ProductDTO() {
		super();
	}

	public ProductDTO(int id, @NotBlank(message = "Vui lòng nhập tên sản phẩm") String productName,
			@NotNull(message = "Vui lòng nhập đơn giá nhập") double priceIn,
			@NotNull(message = "Vui lòng nhập đơn giá bán") double priceSell, String linkImage, String description,
			@NotNull(message = "Vui lòng chọn thương hiệu") String categoryId) {
		super();
		this.id = id;
		this.productName = productName;
		this.priceIn = priceIn;
		this.priceSell = priceSell;
		this.linkImage = linkImage;
		this.description = description;
		this.categoryId = categoryId;
	}

	public ProductDTO(Product p) {
		this.id = p.getId();
		this.productName = p.getProductName();
		this.priceIn = p.getPriceIn();
		this.priceSell = p.getPriceSell();
		this.linkImage = p.getLinkImage();
		this.description = p.getDescription();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPriceIn() {
		return priceIn;
	}

	public void setPriceIn(double priceIn) {
		this.priceIn = priceIn;
	}

	public double getPriceSell() {
		return priceSell;
	}

	public void setPriceSell(double priceSell) {
		this.priceSell = priceSell;
	}

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
