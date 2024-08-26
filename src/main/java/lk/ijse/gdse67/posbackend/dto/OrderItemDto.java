package lk.ijse.gdse67.posbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private String orderId;
    private String itemId;
    private Integer itemCount;
    private Double unitPrice;
    private Double total;
}
