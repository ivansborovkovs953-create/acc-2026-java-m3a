package org.example.shelter;

import org.example.model.AdoptionRecord;
import org.example.model.AdoptionStatus;
import org.example.model.Animal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shelter <T extends Animal>{
    private final List<T> animals = new ArrayList<>();
    private final List<AdoptionRecord> adoptionHistory = new ArrayList<>();
    public void addAnimal(T animal){
        animals.add(animal);
    }

    public List<T> getAllAnimals(){
        return animals;
    }

    public List<T> findBySpecies(String species){
        List<T> animalsBySpecies = new ArrayList<>();
        for (T animal : animals){
            if (animal.getSpecies().equalsIgnoreCase(species)){
                animalsBySpecies.add(animal);
            }
        }
        return animalsBySpecies;
    }

    public List<T> findAvailableAnimals(){
        List<T> availableAnimals = new ArrayList<>();
        for (T animal : animals){
            if (animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE){
                availableAnimals.add(animal);
            }
        }
        return availableAnimals;
    }

    public boolean markAsAdopted(String id, String adopterName){
        for (T animal : animals){
            if (animal.getId().toString().equals(id) && animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE){
                animal.markAsAdopted();
                adoptionHistory.add(new AdoptionRecord(animal, LocalDate.now(), adopterName));

                return true;
            }
        }
        return false;
    }

    public List<T> sortAnimalsByAge(){
        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::getAge))
                .toList();
    }
    public List<T> sortAnimalsByName(){
        return animals.stream()
                .sorted(Comparator.comparing(Animal::getName,String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<AdoptionRecord> getAdoptionHistory(){
        return List.copyOf(adoptionHistory);
    }
}
