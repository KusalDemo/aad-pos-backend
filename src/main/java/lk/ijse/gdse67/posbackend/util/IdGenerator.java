package lk.ijse.gdse67.posbackend.util;

import java.util.UUID;

public class IdGenerator {
    public static String generateId(){
        return UUID.randomUUID().toString();
    }
}
