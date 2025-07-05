package org.example.food.request;

import lombok.Data;

@Data
public class UpdateCardItemRequest {
    private Long CarditemId;
    private int quantity;
}
