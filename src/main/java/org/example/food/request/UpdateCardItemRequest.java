package org.example.food.request;

import lombok.Data;

@Data
public class UpdateCardItemRequest {
    private Long cardItemId;
    private int quantity;
}
