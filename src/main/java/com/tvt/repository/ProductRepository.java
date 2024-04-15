package com.tvt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tvt.entity.Product;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("SELECT COUNT(p.id) FROM Product p INNER JOIN p.category c WHERE c.id = :catId")
	int countByCategoryId(@Param("catId") int catId);

	@Query("FROM Product p INNER JOIN p.category c WHERE c.id = :catId ORDER BY p.id")
	Page<Product> findProductsByCategoryIdWithPagination(@Param("catId") int catId, PageRequest pageRequest);

	@Query("""
			SELECT COUNT(p.id)
			FROM Product p
			WHERE p.id = :productId OR p.productName LIKE %:productName% OR
					p.category.categoryName LIKE %:categoryName% OR
					p.priceIn = :priceIn OR p.priceSell = :priceSell OR
					p.description LIKE %:description%
			""")
	int countBySearch(@Param("productId") int productId, @Param("productName") String productName,
			@Param("categoryName") String categoryName, @Param("priceIn") double priceIn,
			@Param("priceSell") double priceSell, @Param("description") String description);

	@Query("""
			FROM Product p
			WHERE p.id = :productId OR p.productName LIKE %:productName% OR
					p.category.categoryName LIKE %:categoryName% OR
					p.priceIn = :priceIn OR p.priceSell = :priceSell OR
					p.description LIKE %:description%
			""")

	Page<Product> search(@Param("productId") int productId, @Param("productName") String productName,
			@Param("categoryName") String categoryName, @Param("priceIn") double priceIn,
			@Param("priceSell") double priceSell, @Param("description") String description, PageRequest pageRequest);

}
