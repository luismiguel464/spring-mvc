package guru.springframework.domain;

import javax.persistence.*;

@Entity
public class Customer implements DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    Integer version;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    // This is needed for embedded entities when the have duplicated keys
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "billing_addressLine1")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "billing_addressLine2")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billing_zipCode"))
    })
    @Embedded
    private Address billingAddress;

    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "shipping_addressLine1")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "shipping_addressLine2")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zipCode"))
    })
    @Embedded
    private Address shippingAddress;

    @OneToOne //Removed Cascade because if customer is removed, I want to keep the related User information
    private User user;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
