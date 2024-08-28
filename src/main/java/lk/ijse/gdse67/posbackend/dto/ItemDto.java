package lk.ijse.gdse67.posbackend.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto implements Serializable {
    private String propertyId;
    private String name;
    private String description;
    private double price;
    private int qty;
}
