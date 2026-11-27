package org.example.util;

import org.example.model.Animal;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ShelterFiltr {

    public static <T> List<T> filter(Collection<T> listOfAnimals, Predicate<T>  predicate) {
        return listOfAnimals.stream()
                .filter(predicate)
                .toList();
    }

    public static <T extends Animal> double averageAge(Collection<T> listOfAnimals){
        return listOfAnimals.stream()
                .mapToDouble(Animal::getAge)
                .average().orElse(0.0);
    }

    public static <T extends Animal> Optional<T> oldestAnimal(Collection<T> listOfAnimals){
        return listOfAnimals.stream()
                .max(Comparator.comparingInt(Animal::getAge));
    }

    public static <T extends Animal> Map<String, Long> amountOfAnimalsInSpiece(Collection<T> listOfAnimals){
        return listOfAnimals.stream()
                .collect(Collectors.groupingBy(Animal::getSpecies, Collectors.counting()));
    }

    private ShelterFiltr() {}
}
