package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author ibenk
 */
@Entity
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderId;
    
    //Many to one bidirectional-relation to the Customer class
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Customer customer;
    
    //One to many unidirectional-relation to the OrderLine class
    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn
    private List<OrderLine> orderlines;

    public Order() {
    }

    public Order(String orderId, Customer customer, List<OrderLine> orderlines) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderlines = orderlines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "entities.Order[ id=" + id + " ]";
    }
    
}
