package lk.ijse.gdse67.posbackend.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto implements Serializable {
    private String name;
    private String email;
    private String address;
    private String branch;
}
