package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;
import org.example.util.ShelterFiltr;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);


    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start() {
        while (true) {
            printMenu();
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            switch (command) {
                case "1" -> askAboutAnimal();
                case "2" -> showAllAnimals();
                case "3" -> askAboutSpecies();
                case "4" -> showAviableAnimals();
                case "5" -> askAboutId();
                case "6" -> showSortedAnimalsByName();
                case "7" -> showSortedAnimalsByAge();
                case "8" -> startOfStatistic();
                case "9" -> showAdoptionHistory();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid command");
            }
        }
    }

    private void startOfStatistic() {
        printStatisticMenu();
        System.out.print("Enter command: ");
        String answer = scanner.nextLine();
        switch (answer) {
            case "1" -> askAboutPersonalChoise();
            case "2" -> System.out.println(ShelterFiltr.averageAge(shelter.getAllAnimals()));
            case "3" -> ShelterFiltr.oldestAnimal(shelter.getAllAnimals()).ifPresentOrElse(System.out::println, () -> System.out.println("No animals in shelter"));
            case "4" -> System.out.println(ShelterFiltr.amountOfAnimalsInSpiece(shelter.getAllAnimals()));
            case "5" -> {
                return;
            }
            default -> System.out.println("Invalid answer");
        }
    }

    private void printMenu() {
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                6.List all animals sorted by name
                7.List all animals sorted by age
                8.Get statistic
                9.Adoption history
                0. Exit
                """);
    }

    private void printStatisticMenu() {
        System.out.println("""
                1.Personal choise of statistic
                2.Average age in shelter
                3.Oldest Animal
                4.Amount of animals in spiece
                5.back""");
    }

    private void askAboutSpecies() {
        String species = requireNonBlank("Enter species: ");
        for (Animal animal : shelter.findBySpecies(species)) {
            System.out.println("Species: " + animal);
        }
    }

    private void askAboutId() {
        String adopterName = requireNonBlank("Enter adopter name: ");
        String animalId = requireNonBlank("Enter id: ");
        if (!shelter.markAsAdopted(animalId , adopterName)) {
            System.out.println("Invalid animal id");
        } else {
            System.out.println("Animal " + animalId + " marked as adopted");
        }
    }

    private void askAboutAnimal() {
        String name = requireNonBlank("Enter animal name: ");
        int age = requireValidAge("Enter animal age: ");
        String species = requireSpiece("Enter animal species: ");
        createAnimal(name, age, species);
    }

    private void askAboutPersonalChoise(){
        int minAge=requireValidAge("Enter animal min age: ");
        List<Animal> sortedByMinAge = ShelterFiltr.filter(shelter.getAllAnimals(), animal -> animal.getAge() >= minAge);
        for (Animal animal : sortedByMinAge){
            System.out.println("Animal " + animal);
        }
    }

    private String requireNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input == null || input.isBlank()) {
                System.out.println("Field cannot be blank");
            } else {
                return input;
            }
        }
    }

    private String requireSpiece(String prompt) {
        while (true) {
            System.out.print(prompt);
            String rawInput = scanner.nextLine().trim();
            String input = rawInput.toLowerCase().trim();
            if (!(input.equals("dog") || input.equals("cat") || input.equals("bird") || input.equals("rabbit"))) {
                System.out.println("Not a valid species");
            } else {
                return input;
            }
        }
    }

    private int requireValidAge(String prompt) {
        while (true) {
            System.out.print(prompt);
            String rawInput = scanner.nextLine().trim();
            try {
                int input = Integer.parseInt(rawInput);
                if (input < 0 || input > 100) {
                    System.out.println("Invalid animal age");
                } else {
                    return input;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid animal age");
            }
        }
    }

    private void createAnimal(String name, Integer age, String species) {
        switch (species.toLowerCase().trim()) {
            case "dog" -> shelter.addAnimal(new Dog(new AnimalId(), name, age));
            case "cat" -> shelter.addAnimal(new Cat(new AnimalId(), name, age));
            case "bird" -> shelter.addAnimal(new Bird(new AnimalId(), name, age));
            case "rabbit" -> shelter.addAnimal(new Rabbit(new AnimalId(), name, age));
            default -> System.out.println("Invalid animal species");
        }
    }

    private void showAllAnimals() {
        for (Animal animal : shelter.getAllAnimals()) {
            System.out.println(animal);
        }
    }

    private void showAviableAnimals() {
        for (Animal animal : shelter.findAvailableAnimals()) {
            System.out.println(animal);
        }
    }

    private void showSortedAnimalsByAge() {
        for (Animal animal : shelter.sortAnimalsByAge()) {
            System.out.println(animal);
        }
    }

    private void showSortedAnimalsByName() {
        for (Animal animal : shelter.sortAnimalsByName()) {
            System.out.println(animal);
        }
    }

    private void showAdoptionHistory() {
        for (AdoptionRecord adoptionRecord : shelter.getAdoptionHistory()) {
            System.out.println(adoptionRecord);
        }
    }
}