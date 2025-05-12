import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LibraryManagementSystem {
   private static List<Patron> patronList = new ArrayList<>();
    public static void main(String[] args) {
     startLMS();
    }

    public static void initializeLMS(){
        // read the text file and split on the ’\n’ (newline) and  ‘-’ to make a 2d array
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
        System.out.println("Error: invalid entry.\n");
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

     patronList.add(new Patron(name, address, id, fee));
    }

    public static String generateId(){
     Random random = new Random();
     String uniqueId = String.valueOf(1000000 + random.nextInt(9000000));
     if (getPatronById(uniqueId) == null) return uniqueId;
     else return generateId();
    }

    public static void removePatron(){

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
