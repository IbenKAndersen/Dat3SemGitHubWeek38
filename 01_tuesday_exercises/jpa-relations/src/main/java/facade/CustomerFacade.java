package facade;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author ibenk
 */
public class CustomerFacade {

    private static EntityManagerFactory emf;
    private static CustomerFacade instance;

    private CustomerFacade() {
    }

    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) { //emf tells which database to use 
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    public Customer getCustomer(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return customer;
        } finally {
            em.close();
        }
    }

    public List<Customer> getCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery(
                    "Select c from Customer c", Customer.class);
            return query.getResultList();
        } finally {
            em.close();

        }
    }

    public Customer addCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
    }

    public Customer deleteCustomer(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = 
                    em.createQuery("DELETE FROM Customer c "
                            + "WHERE c.id = :id", Customer.class);
            query.executeUpdate();
            return getCustomer(id); 
        } finally {
            em.close();
        }
    }

    public Customer editCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery(
                    "Update Customer c SET c.firstname = ? "
                            + "WHERE c.firstname = :firstname", Customer.class);
            query.setParameter(1, "Lars");
            query.executeUpdate();
            return cust;
        } finally {
            em.close();
        }
    }

}
