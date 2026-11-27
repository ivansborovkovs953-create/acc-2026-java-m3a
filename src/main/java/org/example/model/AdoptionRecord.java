package org.example.model;

import java.time.LocalDate;

public record AdoptionRecord(Animal animal , LocalDate adoptiondate, String adopterName) {
    @Override
    public String toString() {
        return "Animal: " + animal + " | Date: " + adoptiondate + " | Adopter: " + adopterName;
    }
}
