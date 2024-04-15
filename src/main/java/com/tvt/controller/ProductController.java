package com.tvt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tvt.common.ValidateCommon;
import com.tvt.dto.ProductDTO;
import com.tvt.entity.Category;
import com.tvt.entity.Product;
import com.tvt.service.CategoryService;
import com.tvt.service.ProductService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@RequestMapping("/show")
	public String show(Model model, HttpSession session,
			@RequestParam(value = "categoryId", required = false) String categoryId,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page) {
		session.removeAttribute("searchText");
		if (session.getAttribute("sessionAccount") == null) {
			return "redirect:/login?error=1";
		} else {
			// lấy danh sách category
			List<Category> listCategories = categoryService.findAll();

			int pageNumber = 1; // Mặc định là trang 1
			int totalPageNumber = 1;

			if (page != null && !"".equals(page) && ValidateCommon.isValidStringIntegerNumber(page)) {
				pageNumber = Integer.valueOf(page);
			}

			Page<Product> pageProduct = null;
			List<Product> listProducts = null;
			if ("0".equals(categoryId) || categoryId == null || "".equals(categoryId)) {
				categoryId = "0";
				// lấy tổng số trang
				totalPageNumber = productService.getTotalPages();
				if (pageNumber > totalPageNumber && totalPageNumber > 0) {
					pageNumber = totalPageNumber;
				}
				// lấy danh sách Product
				pageProduct = productService.findProductsWithPagination(pageNumber);
				listProducts = pageProduct.getContent();
			} else {
				// lấy tổng số trang theo category_id
				totalPageNumber = productService.getTotalPages(categoryId);
				if (pageNumber > totalPageNumber && totalPageNumber > 0) {
					pageNumber = totalPageNumber;
				}
				// lấy danh sách Product theo category_id
				pageProduct = productService.findProductsByCategoryIdWithPagination(categoryId, pageNumber);
				listProducts = pageProduct.getContent();
			}

			List<Integer> pageNumberList = (List<Integer>) productService.calculatePageNumberList(totalPageNumber,
					pageNumber)[0];
			int pageQuantity = (int) productService.calculatePageNumberList(totalPageNumber, pageNumber)[1];

			model.addAttribute("pageNumberList", pageNumberList);
			model.addAttribute("pageQuantity", pageQuantity);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("currentPageNumber", pageNumber);
			model.addAttribute("totalPageNumber", totalPageNumber);
			model.addAttribute("categoryId", categoryId);

			return "views/productList";
		}
	}

	@RequestMapping("/search")
	public String searchProduct(HttpSession session, Model model,
			@RequestParam(value = "searchText", required = false) String searchText,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "categoryId", required = false) String categoryId) {
		if (session.getAttribute("sessionAccount") == null) {
			return "redirect:/login?error=1";
		} else {
			if (searchText == null) {
				searchText = (String) session.getAttribute("searchText");
			}

			// phân trang
			int pageNumber = 1; // Mặc định là trang 1
			if (page != null && !"".equals(page) && ValidateCommon.isValidStringIntegerNumber(page)) {
				pageNumber = Integer.valueOf(page);
			}

			List<Category> listCategories = categoryService.findAll();
			int totalPageNumber = productService.countBySearch(searchText);

			if (pageNumber > totalPageNumber && totalPageNumber > 0) {
				pageNumber = totalPageNumber;
			}
			List<Product> listProducts = productService.findBySearch(searchText, pageNumber);
			List<Integer> pageNumberList = (List<Integer>) productService.calculatePageNumberList(totalPageNumber,
					pageNumber)[0];
			int pageQuantity = (int) productService.calculatePageNumberList(totalPageNumber, pageNumber)[1];

			model.addAttribute("pageNumberList", pageNumberList);
			model.addAttribute("pageQuantity", pageQuantity);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("currentPageNumber", pageNumber);
			model.addAttribute("totalPageNumber", totalPageNumber);
			model.addAttribute("categoryId", categoryId);
			session.setAttribute("searchText", searchText);

			return "views/productList";
		}
	}

	@RequestMapping("/showAdd")
	public String showAddProduct(Model model, HttpSession session) {
		if (session.getAttribute("sessionAccount") == null) {
			return "redirect:/login?error=1";
		} else {
			List<Category> listCategories = categoryService.findAll();
			ProductDTO p = new ProductDTO();

			model.addAttribute("listCategories", listCategories);
			model.addAttribute("product", p);

			return "views/createProduct";
		}
	}

	@PostMapping("/add")
	public String addProduct(Model model, HttpSession session, @Valid @ModelAttribute("product") ProductDTO p,
			BindingResult bindingResult) {

		if (session.getAttribute("sessionAccount") == null) {
			return "redirect:/login?error=1";
		} else {
			if (bindingResult.hasErrors()) {
				List<Category> listCategories = categoryService.findAll();
				model.addAttribute("listCategories", listCategories);
				return "views/createProduct";
			} else {
				String message = productService.save(p);
				if ("No error".equals(message)) {
					model.addAttribute("successMessage", "Thêm mới sản phẩm thành công");
					return "forward:/product/show";
				} else if ("Conflict foreign key".equals(message)) {
					model.addAttribute("errorMessage", "Mã thương hiệu không tồn tại");
				}
				return "forward:/product/showAdd";
			}
		}

	}

	@GetMapping("/delete/{productId}")
	public String deleteProduct(Model model, HttpSession session,
			@PathVariable(name = "productId", required = false) String productId) {
		if (session.getAttribute("sessionAccount") == null) {
			return "redirect:/login?error=1";
		} else {
			String message = productService.delete(productId);
			if ("No error".equals(message)) {
				model.addAttribute("successMessage", "Xóa sản phẩm thành công");
			} else if ("Product does not exist".equals(message)) {
				model.addAttribute("errorMessage", "Không thể xóa sản phẩm. Mã sản phẩm không tồn tại");
			}
			return "forward:/product/show";
		}

	}

	@RequestMapping("/showEdit/{productId}")
	public String showEditProduct(Model model, HttpSession session,
			@PathVariable(name = "productId", required = false) String productId) {
		if (session.getAttribute("sessionAccount") == null) {
			return "redirect:/login?error=1";
		} else {
			System.err.println("showEdit productId="+productId);
			Product p = productService.findById(productId);
			if (p == null) {
				model.addAttribute("errorMessage", "Không thể chỉnh sửa sản phẩm. Sản phẩm không tồn tại");
				return "forward:/product/show";
			} else {
				ProductDTO pDTO = new ProductDTO(p);
				pDTO.setCategoryId(String.valueOf(p.getCategory().getId()));
				List<Category> listCategories = categoryService.findAll();

				model.addAttribute("listCategories", listCategories);
				model.addAttribute("product", pDTO);

				return "views/editProduct";
			}
		}
	}

	@PostMapping("/edit")
	public String editProduct(Model model, HttpSession session, @Valid @ModelAttribute("product") ProductDTO p,
			BindingResult bindingResult) {
		if (session.getAttribute("sessionAccount") == null) {
			return "redirect:/login?error=1";
		} else {
			if (bindingResult.hasErrors()) {
				List<Category> listCategories = categoryService.findAll();
				model.addAttribute("listCategories", listCategories);
				return "views/editProduct";
			} else {
				System.err.println("Edit productId="+p.getId());
				String message = productService.save(p);
				if ("No error".equals(message)) {
					model.addAttribute("successMessage", "Chỉnh sửa sản phẩm thành công");
					return "forward:/product/show";
				} else if ("Conflict foreign key".equals(message)) {
					model.addAttribute("errorMessage", "Mã thương hiệu không tồn tại");
				}
				return "forward:/product/showEdit/"+p.getId();
			}
		}
	}
}
