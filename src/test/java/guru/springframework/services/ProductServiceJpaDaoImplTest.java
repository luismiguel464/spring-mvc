package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class ProductServiceJpaDaoImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testListMethod() {

        List<Product> products = productService.listAll();

        assert products.size() == 5;

    }

    @Test
    public void testGetById() {

        Integer id = 1;

        Product product = productService.getById(id);

        assert product.getId() == id;

    }

    @Test
    public void testSaveOrUpdate() {

        Product product =  new Product();
        product.setImageUrl("Image URL");
        product.setPrice(new BigDecimal("20.00"));
        product.setDescription("This is a description");

        Product savedProduct = productService.saveOrUpdate(product);

        assert savedProduct.getId() >= 1;

    }
}
