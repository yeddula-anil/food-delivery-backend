package org.example.food.request;

import lombok.Data;
import org.example.food.model.Adress;
@Data
public class OrderRequest {
    private Long restaurantId;
    private Adress deliveryAdress;
}
