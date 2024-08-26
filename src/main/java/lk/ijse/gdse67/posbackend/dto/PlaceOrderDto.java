package lk.ijse.gdse67.posbackend.dto;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderDto {
    private String orderId;
    private String customerId;  // Reference to customer ID
    private Date orderDate;
    private Double totalAmount;
    private Double paid;
    private Double discount;
    private Double balance;
    private List<OrderItemDto> orderItems;
}
