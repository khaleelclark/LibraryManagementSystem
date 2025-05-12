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
    public String getId() {
        return id;
    }
    public String getPatronInformation(){
        return name + " " + address + " " + fineAmount + " " + id + "\n";
    }
}
