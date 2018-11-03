/**
 * Note:
 * Make sure to "jdbc:h2:mem:testdb" for the connection of the H2 db
 */
package guru.springframework.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product implements DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //AUTO will work for DB with row incremental feature
    private Integer id;

    @Version
    Integer version; //Optimistic Locking to avoid losing record updates

    private String description;
    private BigDecimal price;
    private String imageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
