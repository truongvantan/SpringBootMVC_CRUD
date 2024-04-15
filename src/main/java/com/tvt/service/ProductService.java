package com.tvt.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tvt.dto.ProductDTO;
import com.tvt.entity.Product;

import jakarta.validation.Valid;

public interface ProductService {

	List<Product> findAll();

	Page<Product> findProductsWithPagination(int pageNumber);

	int getTotalPages();

	int getTotalPages(String categoryId);

	Page<Product> findProductsByCategoryIdWithPagination(String categoryId, int pageNumber);

	Object[] calculatePageNumberList(int totalPageNumber, int pageNumber);

	int countBySearch(String searchText);

	List<Product> findBySearch(String searchText, int pageNumber);

	String save(@Valid ProductDTO p);

	String delete(String productId);

	Product findById(String productId);

}
