package guru.springframework.services;

import guru.springframework.domain.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
@Profile("jpadao")
public class CustomerServiceJpaDaoImpl implements CustomerService {

    private EntityManagerFactory emf; //EntityManagerFactory is thread safe while the EntityManager is not

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<?> listAll() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public Customer getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        return em.find(Customer.class, id);
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Customer savedCustomer = em.merge(customer); //Merge creates a new object if object doesn't exist and merge the
        //properties of that object if it does exist.

        em.getTransaction().commit();

        return savedCustomer;
    }

    @Override
    public void delete(Integer id) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.remove(em.find(Customer.class, id));

        em.getTransaction().commit();

    }
}
