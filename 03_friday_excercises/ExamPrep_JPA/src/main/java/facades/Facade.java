package facades;

import entities.Customer;
import entities.ItemType;
import entities.Order;
import entities.OrderLine;
import exceptions.CustomerNotFoundException;
import exceptions.ItemNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ibenk
 */
public class Facade {

    private static Facade instance;
    private static EntityManagerFactory emf;

    private Facade() {
    }

    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Customer createCustomer(String name, String email, List orders) {
        if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                Customer customer = new Customer(name, email, orders);
                em.persist(customer);
                em.getTransaction().commit();
                return customer;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Something went wrong when persisting Customer: " + e.getMessage());
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("Wrong input. Try again.");
        }
    }

    public Customer findCustomer(long id) throws CustomerNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Customer customer = em.find(Customer.class, id);
            em.getTransaction().commit();
            if (customer != null) {
                return customer;
            } else {
                throw new CustomerNotFoundException("No customer with provided id found");
            }
        } finally {
            em.close();
        }
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            List<Customer> customer = em.createNamedQuery("Customer.getAll").getResultList();
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }
    }

    public ItemType createItemType(String name, String description, int price, List orderlines) {
        if (name != null && !name.isEmpty() && description != null && !description.isEmpty() && price > 0) {
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                ItemType item = new ItemType(name, description, price, orderlines);
                em.persist(item);
                em.getTransaction().commit();
                return item;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Something went wrong when persisting ItemType: " + e.getMessage());
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("Wrong input. Try again.");
        }
    }

    public ItemType findItemLine(long id) throws ItemNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ItemType item = em.find(ItemType.class, id);
            em.getTransaction().commit();
            if (item != null) {
                return item;
            } else {
                throw new ItemNotFoundException("No item with provided id found");
            }
        } finally {
            em.close();
        }
    }

    public Order createOrder(String orderId, Customer customer, List orderlines) {
        if (orderId != null && !orderId.isEmpty() && customer != null && !orderlines.isEmpty()) {
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                Order order = new Order(orderId, customer, orderlines);
                em.persist(order);
                em.getTransaction().commit();
                return order;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Something went wrong when persisting Order: " + e.getMessage());
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("Wrong input. Try again.");
        }
    }

    public OrderLine createOrderLine(int qty, ItemType item) {
        if (qty > 0 && item != null) {
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                OrderLine orderLine = new OrderLine(qty, item);
                em.persist(orderLine);
                em.getTransaction().commit();
                return orderLine;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Something went wrong when persisting OrderLine: " + e.getMessage());
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("Wrong input. Try again.");
        }
    }

//    public List<Order> findOrdersByCustomer(long id) {
//        return orders;
//    }

//    public int findTotalPrice() {
//        return 0;
//    }

}
