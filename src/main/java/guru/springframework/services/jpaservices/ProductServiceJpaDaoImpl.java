package guru.springframework.services.jpaservices;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
@Profile("jpadao") //this is active on application.properties
public class ProductServiceJpaDaoImpl implements ProductService {

    private EntityManagerFactory emf; //EntityManagerFactory is thread safe while the EntityManager is not

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Product> listAll() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        return em.find(Product.class, id);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Product savedProduct = em.merge(product); //Merge creates a new object if object doesn't exist and merge the
                                                    //properties of that object if it does exist.
        em.getTransaction().commit();

        return savedProduct;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.remove(em.find(Product.class, id));

        em.getTransaction().commit();
    }

}
