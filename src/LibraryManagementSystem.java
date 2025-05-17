import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Khaleel Zindel Clark
 * CEN 3024 - Software Development 1
 * May 18, 2025
 * LibraryManagementSystem.java
 * This application will grant Library Management System users the ability
 * to add, remove, and display patrons with robust error handling.
 */

public class LibraryManagementSystem {
    private final static List<Patron> patronList = new ArrayList<>();

    /**
     * method: startLMS
     * parameters: none
     * return: void
     * purpose: runs the functions of the LMS based off the user's input
     */
    public static void main(String[] args) {
        startLMS();
    }

    /**
     * method: startLMS
     * parameters: none
     * return: void
     * purpose: runs the functions of the LMS based off the user's input
     */
    public static void startLMS() {
        while (true) {
            System.out.println("\nWelcome to Zindel's LMS!\nPlease enter the number of the option you wish to select\n");
            System.out.println("1. Add Library Patrons from a File");
            System.out.println("2. Add a Library Patron Manually");
            System.out.println("3. Remove a Library Patron");
            System.out.println("4. Display all Library Patrons");
            System.out.println("5. Exit");

            Scanner scanner = new Scanner(System.in);

            switch (scanner.next()) {
                case "1":
                    addPatronFromFile();
                    break;
                case "2":
                    addPatronManually();
                    break;
                case "3":
                    removePatron();
                    break;
                case "4":
                    displayAllPatrons();
                    break;
                case "5": {
                    System.out.println("Thank you for using Zindel's LMS");
                    System.exit(0);
                    break;
                }
                default:
                    System.err.println("Error: invalid entry.\n");
                    break;
            }
        }
    }

    /**
     * method: addPatronFromFile
     * parameters: none
     * return: void
     * purpose: this method prompts the user for the path of the file they wish to use to load the LMS,
     * and then it adds the Patrons to the Patron list
     */
    public static void addPatronFromFile() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("""
                Enter the absolute, or relative path of the file you wish to use to load the LMS, without quotations.
                Absolute Ex. C:\\LibrarySystem\\Data\\Patrons\\patrons_list.txt
                Relative Ex. .\\Patrons\\patrons_list.txt""");
        String filePath = userInput.nextLine();
        Scanner scanner;
        File file = new File(filePath);

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file: " + e.getMessage());
            return;
        }

        int line = 1;
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] columns = input.split("-");
            if (columns.length == 4) {
                String id = columns[0];
                String name = columns[1];
                if (!name.matches("^[a-zA-Z.'\\s-]+$"
                )) {
                    System.err.println("Error on line " + line + ": Invalid character input in name: " + name + " Please correct.");
                    break;
                }
                String address = columns[2];
                try {
                    double fineAmount = Double.parseDouble(columns[3]);
                    if (fineAmount < 0 || fineAmount > 250) {
                        throw new NumberFormatException("The fine must be between 0 and 250");
                    }
                    // Checks to see if the length of the provided id is correct
                    if (id.length() != 7 || getPatronById(id) != null) {
                        id = generateId();
                    }
                    patronList.add(new Patron(id, name, address, fineAmount));

                } catch (NumberFormatException e) {
                    System.err.println("Error on line " + line + ": Invalid fine format! " + e.getMessage() +
                            "\n    Fine amount must be a number between 0-250 to be valid\n");
                }
            } else {
                System.err.println("Error on line " + line + ": Invalid line format! Too many or too little columns, please correct.\n" +
                        "    File must have 4 columns seperated by a dash. Ex. Id - Name - Address - Fine Amount\n");
            }
            line++;
        }
        System.out.println("\nFile: " + filePath + " Successfully added!\nCurrent Patrons:");
        displayAllPatrons();
    }

    /**
     * method: addPatronManually
     * parameters: none
     * return: void
     * purpose: this method allows the user to manually add a Patron to the patron list by inputting a patrons attributes
     */
    public static void addPatronManually() {
        Scanner scanner = new Scanner(System.in);
        String name;
        String address;

        while (true) {
            System.out.println("Please enter the name of the Patron:");
            name = scanner.nextLine();
            if (name.matches("^[a-zA-Z.'\\s-]+$"
            )) {
                break;
            } else {
                System.err.println("Error: invalid character input in name. Please try again.");
            }
        }

        System.out.println("Please enter the address of the Patron");
        address = scanner.nextLine();

        System.out.println("Please enter the fine amount of the Patron");

        String id = generateId();

        double fineAmount;

        while (true) {
            String fineAmountScan = scanner.nextLine();

            try {
                fineAmount = Double.parseDouble(fineAmountScan);
                if (fineAmount < 0 || fineAmount > 250) {
                    throw new NumberFormatException("The fine must be between 0 and 250");
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: The fine amount must be a number between 0 and 250");
            }
        }

        while (true) {
            System.out.println("Are you sure you'd like to add this patron?");
            System.out.println("Patron Id: " + id + "\n Name: " + name + "\n Address: " + address + "\n Fine Amount: " + fineAmount);
            System.out.print("\nEnter 'y' to confirm, or 'c' to cancel: \n");

            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equals("y")) {
                patronList.add(new Patron(id, name, address, fineAmount));
                System.out.println("Patron added successfully.");
                displayAllPatrons();
                break;
            } else if (confirmation.equals("c")) {
                System.out.println("Add cancelled. Patron has not been added.");
                break;
            } else {
                System.err.println("Error: invalid entry. Please enter 'y' or 'c'.");
            }
        }

    }

    /**
     * method: generateId
     * parameters: none
     * return: String uniqueId
     * purpose: This method creates a random seven-digit number and checks if it already exists for a patron.
     * If it does, the method recursively generates a new id until a unique one is found.
     */
    public static String generateId() {
        Random random = new Random();
        String uniqueId = String.valueOf(1000000 + random.nextInt(9000000));
        if (getPatronById(uniqueId) == null) return uniqueId;
        else return generateId();
    }

    /**
     * method: removePatron
     * parameters: none
     * return: void
     * purpose: this method displays all patrons then prompts the user to input the id of the patron they would like to remove
     * then it prompts a confirmation message and removes a patron from the patron list or cancels the operation.
     */
    public static void removePatron() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the Id of the Patron you'd like to remove");
        displayAllPatrons();

        String idToRemove = scanner.nextLine();
        Patron patronToRemove = getPatronById(idToRemove);

        if (patronToRemove == null) {
            System.out.println("No patron found with ID: " + idToRemove);
            return;
        }

        while (true) {
            System.out.println("Are you sure you'd like to remove this patron?");
            System.out.println(patronToRemove.getPatronInformation());
            System.out.print("Enter 'y' to confirm, or 'c' to cancel: \n");

            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equals("y")) {
                patronList.remove(patronToRemove);
                System.out.println("Patron removed successfully.");
                break;
            } else if (confirmation.equals("c")) {
                System.out.println("Patron removal canceled.");
                break;
            } else {
                System.err.println("Error: invalid entry. Please enter 'y' or 'c'.");
            }
        }
    }

    /**
     * method: getPatronById
     * parameters: String id
     * return: Patron
     * purpose: this method returns the patron with the id that matches the id parameter
     */
    public static Patron getPatronById(String id) {
        for (Patron patron : patronList) {
            if (patron.getId().equals(id)) {
                return patron;
            }
        }
        return null;
    }

    /**
     * method: displayAllPatrons
     * parameters: none
     * return: void
     * purpose: this method prints out the patron list to the screen
     */
    public static void displayAllPatrons() {
        if (!patronList.isEmpty()) {
            System.out.println("Patrons: ");
            for (Patron patron : patronList) {
                System.out.println(patron.getPatronInformation());
            }
        } else {
            System.out.println("No patrons to display.");
        }
    }
}
