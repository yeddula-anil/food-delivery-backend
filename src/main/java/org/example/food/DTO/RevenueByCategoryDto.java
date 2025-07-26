package org.example.food.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueByCategoryDto {
    private String categoryName;
    private Long dailyRevenue;
    private Long monthlyRevenue;
    private Long yearlyRevenue;
}
