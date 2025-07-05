package org.example.food.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ContactInformation {
    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
