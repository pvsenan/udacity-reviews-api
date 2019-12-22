package com.udacity.course3.reviews.jpatests;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ProductRepositoryTests {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testProductFindById() {
        Product product = new Product("Arla Ko", "Best Swedish milk");
        entityManager.persist(product);
        productRepository.save(product);
        Assert.assertNotNull(product);

        Optional<Product> product2 = productRepository.findById(1);
        Assert.assertTrue(product2.isPresent());
        Assert.assertEquals(product2.get().getDescription(), product.getDescription());
    }

    @Test
    public void testFindAllProducts(){
        Product product1 = new Product("Arla Ko", "Best Swedish milk");
        entityManager.persist(product1);
        productRepository.save(product1);

        Product product2 = new Product("Snickers", "You are not you when you are hungry");
        entityManager.persist(product2);
        productRepository.save(product2);

        Optional<List<Product>> result = Optional.ofNullable(productRepository.findAll());
        Assert.assertEquals(2,result.get().size());
    }

    @Test
    public void testDeleteProduct(){
        Product product1 = new Product("Arla Ko", "Best Swedish milk");
        entityManager.persist(product1);
        productRepository.save(product1);

        Optional<Product> product2 = productRepository.findById(1);
        Assert.assertTrue(product2.isPresent());

        Integer id = product2.get().getId();
        productRepository.deleteById(id);
        Optional<Product> result = productRepository.findById(id);
        Assert.assertFalse(result.isPresent());
    }
}
