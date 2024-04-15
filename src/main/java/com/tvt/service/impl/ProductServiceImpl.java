package com.tvt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tvt.common.ValidateCommon;
import com.tvt.constant.Constant;
import com.tvt.dto.ProductDTO;
import com.tvt.entity.Category;
import com.tvt.entity.Product;
import com.tvt.repository.CategoryRepository;
import com.tvt.repository.ProductRepository;
import com.tvt.service.ProductService;

import jakarta.validation.Valid;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public int getTotalPages() {
		int totalPageNumber = 1;
		totalPageNumber = (int) productRepository.count();
		return (int) Math.ceil((double) totalPageNumber / Constant.NUM_OF_PRODUCTS_PER_PAGE);
	}

	@Override
	public Page<Product> findProductsWithPagination(int pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber - 1, Constant.NUM_OF_PRODUCTS_PER_PAGE);
		return productRepository.findAll(pageRequest);
	}

	@Override
	public int getTotalPages(String categoryId) {
		int totalPageNumber = 1;
		int catId = 0;
		if (ValidateCommon.isValidStringIntegerNumber(categoryId)) {
			catId = Integer.valueOf(categoryId);
		}
		totalPageNumber = (int) productRepository.countByCategoryId(catId);

		return (int) Math.ceil((double) totalPageNumber / Constant.NUM_OF_PRODUCTS_PER_PAGE);
	}

	@Override
	public Page<Product> findProductsByCategoryIdWithPagination(String categoryId, int pageNumber) {
		int catId = 0;

		if (ValidateCommon.isValidStringIntegerNumber(categoryId)) {
			catId = Integer.valueOf(categoryId);
		}
		PageRequest pageRequest = PageRequest.of(pageNumber - 1, Constant.NUM_OF_PRODUCTS_PER_PAGE);
		return productRepository.findProductsByCategoryIdWithPagination(catId, pageRequest);
	}

	@Override
	public Object[] calculatePageNumberList(int totalPageNumber, int pageNumber) {
		List<Integer> pageNumberList = new ArrayList<Integer>();
		Integer pageQuantity = 0;
		if (totalPageNumber <= 10) {
			for (int j = 0; j < totalPageNumber; j++) {
				pageNumberList.add(j + 1);
				pageQuantity++;
			}
		} else if (totalPageNumber > 10 && pageNumber <= 5) {
			for (int j = 0; j < 10; j++) {
				pageNumberList.add(j + 1);
				pageQuantity++;
			}
		} else if (totalPageNumber > 10 && pageNumber >= (totalPageNumber - 5)) {
			for (int j = 0; j < 10; j++) {
				pageNumberList.add(totalPageNumber - (9 - j));
				pageQuantity++;
			}
		} else if (totalPageNumber > 10 && pageNumber > 5 && pageNumber <= (totalPageNumber - 5)) {
			for (int j = 0; j < 10; j++) {
				pageNumberList.add(pageNumber - 3 + j);
				pageQuantity++;
			}
		}
		return new Object[] { pageNumberList, pageQuantity };
	}

	@Override
	public int countBySearch(String searchText) {
		int totalPageNumber = 1;
		int productId = ValidateCommon.isValidStringIntegerNumber(searchText) ? Integer.valueOf(searchText) : -1;
		double priceIn = ValidateCommon.isValidStringNumber(searchText) ? Double.valueOf(searchText) : -1;
		double priceSell = ValidateCommon.isValidStringNumber(searchText) ? Double.valueOf(searchText) : -1;

		totalPageNumber = (int) productRepository.countBySearch(productId, searchText, searchText, priceIn, priceSell,
				searchText);

		return (int) Math.ceil((double) totalPageNumber / Constant.NUM_OF_PRODUCTS_PER_PAGE);
	}

	@Override
	public List<Product> findBySearch(String searchText, int pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber - 1, Constant.NUM_OF_PRODUCTS_PER_PAGE);
		int productId = ValidateCommon.isValidStringIntegerNumber(searchText) ? Integer.valueOf(searchText) : -1;
		double priceIn = ValidateCommon.isValidStringNumber(searchText) ? Double.valueOf(searchText) : -1;
		double priceSell = ValidateCommon.isValidStringNumber(searchText) ? Double.valueOf(searchText) : -1;

		Page<Product> listProducts = productRepository.search(productId, searchText, searchText, priceIn, priceSell,
				searchText, pageRequest);

		return listProducts.getContent();
	}

	@Override
	public String save(@Valid ProductDTO p) {
		if (p.getCategoryId() == null || !ValidateCommon.isValidStringIntegerNumber(p.getCategoryId())) {
			return "Conflict foreign key";
		} else {
			Optional<Category> category = categoryRepository.findById(Integer.valueOf(p.getCategoryId()));
			if (!category.isPresent()) {
				return "Conflict foreign key";
			} else {
				Category c = category.get();
				Product product = new Product(p);
				product.setCategory(c);
				
				productRepository.save(product);
			}
		}
		
		return "No error";
	}

	@Override
	public String delete(String productId) {
		if (productId == null || !ValidateCommon.isValidStringIntegerNumber(productId)) {
			return "Product does not exist";
		} else {
			Optional<Product> p = productRepository.findById(Integer.valueOf(productId));
			if (!p.isPresent()) {
				return "Product does not exist";
			} else {
				productRepository.deleteById(Integer.valueOf(productId));
			}
		}
		
		return "No error";
	}

	@Override
	public Product findById(String productId) {
		if (productId == null || !ValidateCommon.isValidStringIntegerNumber(productId)) {
			return null;
		} else {
			Optional<Product> product = productRepository.findById(Integer.valueOf(productId));
			if (!product.isPresent()) {
				return null;
			} else {
				return product.get();
			}
		}
	}

}
