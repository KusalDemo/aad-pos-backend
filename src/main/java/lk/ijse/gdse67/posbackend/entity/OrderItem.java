package lk.ijse.gdse67.posbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {
    @EmbeddedId
    private OrderItemId propertyId;
    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private PlaceOrder placeOrder;
    @MapsId("propertyId")
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    private Integer itemCount;
    private Double unitPrice;
    private Double total;

}
