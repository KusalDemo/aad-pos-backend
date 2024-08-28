package lk.ijse.gdse67.posbackend.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderDto implements Serializable {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private Double totalAmount;
    private Double paid;
    private int discount;
    private Double balance;
    private List<OrderItemDto> orderItems;
}
