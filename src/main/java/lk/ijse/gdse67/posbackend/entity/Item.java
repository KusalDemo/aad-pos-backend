package lk.ijse.gdse67.posbackend.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private String propertyId;
    private String name;
    private String description;
    private double price;
    private int qty;
}
