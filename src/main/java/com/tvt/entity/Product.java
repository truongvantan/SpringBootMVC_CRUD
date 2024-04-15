package com.tvt.entity;

import com.tvt.dto.ProductDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "product_name", columnDefinition = "NVARCHAR(50)", nullable = false)
	private String productName;

	@Column(name = "price_in", nullable = false)
	private double priceIn;

	@Column(name = "price_sell", nullable = false)
	private double priceSell;

	@Column(name = "link_image", columnDefinition = "VARCHAR(MAX)")
	private String linkImage;

	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String description;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "category_id")
	private Category category;

	public Product() {
		super();
	}
	
	public Product(ProductDTO p) {
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
