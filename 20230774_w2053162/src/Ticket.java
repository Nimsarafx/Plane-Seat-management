import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Ticket { // Ticket class
    private String row;
    private int seatnum;
    private double price;
    private Person person;

    //Convirting to a string
    public String toString() {
        return "Ticket details\n Row :- " + row + "\n Seat :- " + seatnum + "\n Price :- £" + price + "\n" + person.toString();
        }

        //The constructor
    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seatnum = seat;
        this.price = price;
        this.person = person;
    }
    //Getters and setters
    public String getRow() {
        return row;
    }
    public void setRow(String row) {
        this.row = row;
    }
    public int getSeatnum() {
        return seatnum;
    }
    public void setSeatnum(int seatnum) {
        this.seatnum = seatnum;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    //Method to print ticket details.
    public void printTicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seatnum);
        System.out.println("Price: £" + price);
        System.out.println("Person Information:");
        person.printDetails();
    }
    // Method to save the text file
    public void save() {
        try {
            File file = new File(row + seatnum + ".txt");
            FileWriter writer = new FileWriter(file);
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seatnum + "\n");
            writer.write("Price: £ " + price + "\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving ticket information: " + e.getMessage());
        }
    }

}
