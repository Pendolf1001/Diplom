package com.example.diplom;

import com.example.diplom.model.Menu;
import com.example.diplom.repositories.MenuRepository;
import com.example.diplom.repositories.OrderRepository;
import com.example.diplom.repositories.ProductRepository;
import com.example.diplom.service.CustomUserDetailsService;
import com.example.diplom.service.MenuService;
import com.example.diplom.service.OrderService;
import com.example.diplom.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import com.example.diplom.model.*;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




	@SpringBootTest
	@AutoConfigureMockMvc
	class DiplomApplicationTests {



		@Autowired
		private OrderRepository orderRepository;

		@Autowired
		private OrderService orderService;

		@Autowired
		private MockMvc mockMvc;

		@Autowired
		private MenuService menuService;

		@Autowired
		private ProductService productService;

		@Autowired
		private MenuRepository menuRepository;

		@Autowired
		private ProductRepository productRepository;

		@Autowired
		private CustomUserDetailsService customUserDetailsService;

		@Test
		void contextLoads() {
			// Проверка загрузки контекста
		}


		@Test
		void testMenuService() {
			Menu menu = new Menu();
			menu.setName("Test Menu");
			Menu savedMenu = menuService.createMenu(menu);

			assertNotNull(savedMenu.getId());
			assertEquals("Test Menu", savedMenu.getName());
		}



		@Test
		@WithMockUser(roles = "ADMIN")
		void testAdminAccess() throws Exception {
			mockMvc.perform(get("/orders"))
					.andExpect(status().isOk());
		}

		@Test
		@WithMockUser(roles = "STAFF")
		void testStaffAccess() throws Exception {
			mockMvc.perform(get("/cart/all"))
					.andExpect(status().isOk());
		}

		@Test
		void viewAllOrders_Unauthenticated_ShouldRedirectToLogin() throws Exception {
			mockMvc.perform(get("/cart/all"))
					.andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("http://localhost/login"));
		}


		@Test
		void loadUserByUsername_ValidUser_ReturnsUserDetails() {
			UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin");
			assertTrue(userDetails.getAuthorities().stream()
					.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
		}

		@Test
		void testProductService() {
			Pizza product = new Pizza();
			product.setName("Test Pizza");
			product.setPrice(15.99);
			product.setDiameter(30);  // Уникальное поле для Pizza

			Product savedProduct = productService.createProduct(product);

			assertNotNull(savedProduct.getId());
			assertTrue(productService.existsById(savedProduct.getId()));
		}

		@Test
		void testRepositoryLayer() {
			Menu menu = new Menu();
			menu.setName("Test Menu");
			Menu savedMenu = menuRepository.save(menu);
			assertNotNull(menuRepository.findById(menu.getId()));

			Product product = productRepository.save(new Pizza("Test", "Desc", 10.0, 25));
			assertTrue(productRepository.existsById(product.getId()));
		}


		@Test
		void addPizzaToMenu_ValidData_ReturnsPizzaMenuItem() {
			PizzaMenuItem pizza = new PizzaMenuItem();
			pizza.setName("Test");
			pizza.setDescription("Test");
			pizza.setDiameter(30);
			MenuItem result = menuService.addItemToMenu(1L, pizza);
			assertInstanceOf(PizzaMenuItem.class, result);
		}


		@Test
		void menuPage_ContainsAddToCartButton() throws Exception {
			mockMvc.perform(get("/menu?mid=1"))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("В корзину")));
		}
}


