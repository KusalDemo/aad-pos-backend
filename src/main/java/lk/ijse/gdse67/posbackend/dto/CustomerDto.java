package lk.ijse.gdse67.posbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private String name;
    private String email;
    private String address;
    private String branch;
}
