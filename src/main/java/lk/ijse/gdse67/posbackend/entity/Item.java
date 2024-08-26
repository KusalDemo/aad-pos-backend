package lk.ijse.gdse67.posbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "property_id")
    private String propertyId;
    private String name;
    private String description;
    private double price;
    private int qty;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
}
