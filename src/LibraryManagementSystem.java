import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LibraryManagementSystem {
   private static List<Patron> patronList = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException {
     initializeLMS();
     startLMS();
    }

    public static void initializeLMS() throws FileNotFoundException {
     Scanner scanner = new Scanner(new File("C:\\Users\\khale\\Desktop\\Software Development I\\patrons.txt"));
     int line = 1;
     while (scanner.hasNextLine()) {
      String input = scanner.nextLine();
      String[] columns = input.split("-");
      if (columns.length == 4) {
       String id = columns[0];
       String name = columns[1];
       String address = columns[2];
       try {
        double fineAmount = Double.parseDouble(columns[3]);
        if (fineAmount < 0 || fineAmount > 250){
         throw new NumberFormatException("The fine must be between 0 and 250");
        }
        // Checks to see if the length of the provided id is correct
        if (id.length() != 7 || getPatronById(id) != null) {
         id = generateId();
        }
        patronList.add(new Patron(id, name, address, fineAmount));

       } catch (NumberFormatException e) {
        System.err.println("Error on line " + line + ": Invalid fine format! " + e.getMessage() + "\n    Fine amount must be a number between 0-250 to be valid\n");
       }
      }
        else {
       System.err.println("Error on line " + line + ": Invalid line format! Too many or too little columns, please correct.\n    File must have 4 columns seperated by a dash. Ex. Id - Name - Address - Fine Amount\n");
      }
        line++;
     }
    }

    public static void startLMS(){
     while(true){
      System.out.println("Please enter the number of the option you wish to select\n");
      System.out.println("1. Add a Library Patron");
      System.out.println("2. Remove a Library Patron");
      System.out.println("3. Display all Library Patrons");
      System.out.println("4. Exit");

      Scanner scanner = new Scanner(System.in);

      switch(scanner.next()){
       case "1": addPatron(); break;
       case "2": removePatron(); break;
       case "3": displayAllPatrons(); break;
       case "4": System.exit(0); break;
       default:
        System.err.println("Error: invalid entry.\n");
        break;
      }
     }
    }

    public static void addPatron(){
     Scanner scanner = new Scanner(System.in);
     System.out.println("Please enter the name of the Patron");
     String name = scanner.nextLine();

     System.out.println("Please enter the address of the Patron");
     String address = scanner.nextLine();

     System.out.println("Please enter the fee amount of the Patron");
     double fee = scanner.nextDouble();

     String id = generateId();

     patronList.add(new Patron(id, name, address, fee));
    }

    public static String generateId(){
     Random random = new Random();
     String uniqueId = String.valueOf(1000000 + random.nextInt(9000000));
     if (getPatronById(uniqueId) == null) return uniqueId;
     else return generateId();
    }

    public static void removePatron(){
     Scanner scanner = new Scanner(System.in);
     System.out.println("Please enter the Id of the Patron you'd like to remove");
     displayAllPatrons();

     String idToRemove = scanner.nextLine();
     Patron patronToRemove = getPatronById(idToRemove);

     if (patronToRemove == null) {
      System.out.println("No patron found with ID: " + idToRemove);
      return;
     }

     System.out.println("Are you sure you'd like to remove this patron?");
     System.out.println(patronToRemove.getPatronInformation());
     System.out.print("Enter 'y' to confirm, or 'n' to cancel: ");

     String confirmation = scanner.nextLine().trim().toLowerCase();
     if (confirmation.equals("y")) {
      patronList.remove(patronToRemove);
      System.out.println("Patron removed successfully.");
     } else {
      System.out.println("Removal canceled.");
     }
    }

    public static Patron getPatronById(String id){
     for ( Patron patron: patronList) {
      if(patron.getId().equals(id)){
       return patron;
      }
     } return null;
    }

    public static void displayAllPatrons(){
     for ( Patron patron: patronList) {
      System.out.println(patron.getPatronInformation());
     }
    }
}
