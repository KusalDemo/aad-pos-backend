package lk.ijse.gdse67.posbackend.util;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardResponse {
    private int status;
    private String message;
    private Object data;
}
