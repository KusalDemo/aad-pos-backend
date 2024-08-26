package lk.ijse.gdse67.posbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "place_order")
public class PlaceOrder {
    @Id
    private String orderId;
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;
    private Date orderDate;
    private Double paid;
    private int discount;
    private Double balance;

    @OneToMany(mappedBy = "placeOrder",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;
}
