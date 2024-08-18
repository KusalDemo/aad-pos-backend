package lk.ijse.gdse67.posbackend.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private String propertyId;
    private String name;
    private String email;
    private String address;
    private String branch;
}

