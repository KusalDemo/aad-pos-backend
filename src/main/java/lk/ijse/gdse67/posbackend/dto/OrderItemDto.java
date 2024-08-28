package lk.ijse.gdse67.posbackend.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto implements Serializable {
    private String orderId;
    private String itemId;
    private Integer itemCount;
    private Double unitPrice;
    private Double total;
}
