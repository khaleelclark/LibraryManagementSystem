/**
 * Khaleel Zindel Clark
 * CEN 3024 - Software Development 1
 * May 18, 2025
 * Patron.java
 * This class creates a patron object with the attributes
 * id, name, address, and fine amount
 */

public class Patron {
    private final String id;
    private final String name;
    private final String address;
    private final double fineAmount;

    public Patron(String id, String name, String address, double fineAmount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fineAmount = fineAmount;
    }

    /**
     * method: getId
     * parameters: none
     * return: void
     * purpose: this method is a getter to retrieve the id of a patron
     */
    public String getId() {
        return id;
    }

    /**
     * method: getPatronInformation
     * parameters: none
     * return: String
     * purpose: this method returns patron attribute information in a formatted string
     */
    public String getPatronInformation() {
        return "Id: " + id + " Name: " + name + " Address: " + address + " Fine Amount: " + fineAmount;
    }
}
