import java.util.Scanner;
import java.util.InputMismatchException;

public class PlaneManagement{
    // Array to store row names
    public static String[] rows ={"A","B","C","D"};
    // Array to store tickets
    public static Ticket[] tickets=new Ticket[52];
    // Array to store seat booking status
    public static int[][] seats =
            {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            };

    public static void buySeat() // Method to book a seat
    {
        Scanner read = new Scanner(System.in);

        // Display available rows
        System.out.println("Row-A- 14 seats\nRow-B- 12 seats\nRow-C- 12 seats\nRow-D- 14 seats\n");
        System.out.print("Please enter the row you prefer:- ");
        String row = read.nextLine().toUpperCase();
        boolean found = false;
        int rownum=0;
        for(int i=0;i<rows.length;i++) // Check if the entered row is valid
        {
            if(rows[i].equals(row)){
                rownum=i;
                found=true;
                break;
            }
        }
        if(!found){
            System.out.println("Please select a valid row");
            buySeat();
        }
        int seatindex =0;
        int ticketprice=0;

        do{
            try {
                System.out.print("Please enter the seat number you prefer:- ");
                int seatnum = read.nextInt();
                read.nextLine(); // Consume the newline character left by nextInt()

                seatindex = seatnum - 1;

                if (seatnum > 0 && seatnum <= seats[rownum].length) {
                    if (seats[rownum][seatindex] == 0) {
                        seats[rownum][seatindex] = 1;

                        // Set ticket price based on seat number
                        if (seatnum<=5){
                            ticketprice = 200;
                        } else if (seatnum<=9) {
                            ticketprice = 150;
                        }else {
                            ticketprice = 180;
                        }

                        System.out.print("Please enter your Name :-");  // Input passenger details
                        String name = read.nextLine();
                        System.out.print("Please enter your Surname :-");
                        String surname = read.nextLine();
                        System.out.print("Please enter your email :-");
                        String email = read.nextLine();

                        // Create Person object
                        Person person = new Person(name,surname,email);

                        // Create Ticket object
                        Ticket ticket =new Ticket(row,seatnum,ticketprice,person);
                        for (int i=0;i<tickets.length;i++){ // Add ticket to tickets array
                            if (tickets[i]==null){
                                tickets[i]=ticket;
                                break;
                            }
                        }
                        System.out.println("The seat booked successfully!!!");
                        ticket.save(); // Save ticket information to file

                        break;
                    } else {
                        System.out.println("The seat you choose is already booked");
                    }
                } else {
                    System.out.println("Entered seat number is invalid");
                }
            }catch (InputMismatchException e){
                System.out.println("Entered seat number is invalid");
                read.nextLine();
            }
        }while(seatindex <=0 || seatindex>=seats[rownum].length); // Validate seat index
        menu(); // Display menu
    }

    // Method to cancel a seat booking
    public static void cancelSeat()
    {
        Scanner read = new Scanner(System.in);

        System.out.print("Please enter the row you want to cancel the seat from :- ");
        String row = read.nextLine().toUpperCase();
        boolean found = false;
        int rownum=0;
        for(int i=0;i<rows.length;i++)   // Check if the entered row is valid
        {
            if(rows[i].equals(row)){ // Validating the row input
                rownum=i;
                found=true;
                break;
            }
        }
        if(!found){
            System.out.println("Please select a valid row");
            cancelSeat();
        }
        int seatindex =0;
        do{
            try {
                System.out.print("Please enter the seat number you want to cancel :- "); //Validating the seat number input
                int seatnum = read.nextInt();
                seatindex = seatnum - 1;

                if (seatnum > 0 && seatnum <= seats[rownum].length) {
                    if (seats[rownum][seatindex] == 1) {
                        seats[rownum][seatindex] = 0;
                        for (int i=0;i<=tickets.length;i++){ //Removing the ticket from the array
                            if (tickets[i] != null && tickets[i].getRow().equals(row) && tickets[i].getSeatnum() == seatnum) {
                                tickets[i] = null;
                                break;
                            }
                        }
                        System.out.println("The seat canceled successfully !!!");

                        break;
                    } else {
                        System.out.println("The seat you choose is not already booked.");
                    }
                } else {
                    System.out.println("Entered seat number is invalid.");
                }
            }catch (InputMismatchException e){
                System.out.println("Entered seat number is invalid.");
                read.nextLine();
            }
        }while(seatindex <=0 || seatindex>=seats[rownum].length); //Validating the seat index

        menu();
    }
    public static void findFirstAvailableSeat()//Method for finding the first available seat
    {
        boolean found=false;
        for(int i=0; i<4; i++){ // Looping until finding the seat
            for(int j=0; j<seats[i].length;j++){
                if(seats[i][j]==0){
                    System.out.println("The first available seat is\n"+"Row number - "+rows[i] +"\nSeat number - "+(j+1)); //Printing the first available seat
                    found=true;
                    break;
                }
            }
            if(found){
                menu();
            }
        }
        if(!found){
            System.out.println("There's no available seats.");
        }
    }
    public static void showSeatPlan() // Method for showing the seat plan (available)
    {
        for(int i=0; i<4; i++){ //Looping through the seats array to print the layout
            for(int j=0; j<seats[i].length;j++){
                System.out.print(seats[i][j]+"");
            }
            System.out.println();
        }
        menu();
    }
    public static void printTicketInfo() { // The method for printing the already bought tickets and the total sales
        int totalAmount = 0;
        int ticketCount = 0;

        for (Ticket ticket : tickets) { // Getting the seat count (purchased)
            if (ticket != null) {
                ticketCount++;
            }
        }

        for (int i = 0; i < ticketCount; i++) {   // Print details of each booked ticket
            Ticket currentTicket = tickets[i];
            if (currentTicket != null) {
                String row = currentTicket.getRow();
                int seatnum = currentTicket.getSeatnum();
                double ticketPrice = currentTicket.getPrice();
                System.out.println(row + seatnum + ":- £" + ticketPrice); // Print ticket details
                totalAmount += (int) ticketPrice; // Update total amount of sales
            }
        }
        // Print total amount of ticket sales
        System.out.println("Total amount: £" + totalAmount);
        menu();
    }

    public static void searchTicket() //The method for searching for available seats
    {
        Scanner read = new Scanner(System.in);

        int ticketCount=0;
        for (Ticket ticket : tickets) {     // Count the number of booked tickets
            if (ticket != null) {
                ticketCount+=1;
            }
        }
        // Prompt user to enter row to search
        System.out.print("Please enter the row you want to search :- ");
        String row = read.nextLine().toUpperCase();
        boolean found = false;
        int rownum=0;

        for(int i=0;i<rows.length;i++) // Check if the entered row is valid
        {
            if(rows[i].equals(row)){
                rownum=i;
                found=true;
                break;
            }
        }
        if(!found){  // If entered row is not valid, prompt user to enter again
            System.out.println("Please select a valid row");
            searchTicket();
        }
        int seatindex=0;
        // Prompt user to enter seat number to search
        do{
            try {
                System.out.print("Please enter the seat number you want to search:- ");
                int seatnum = read.nextInt();
                seatindex = seatnum - 1;

                // Check if entered seat number is within valid range
                if (seatnum >= 0 && seatnum <= seats[rownum].length) {
                    if (seats[rownum][seatindex] == 1) {
                        for (int i=0;i<=ticketCount;i++) {  // If seat is booked, find and print ticket information
                            if (tickets[i].getRow().equals(row)&& tickets[i].getSeatnum()==seatnum) {
                                tickets[i].printTicketInfo();
                                menu();
                            }
                        }
                    }else { //Showing the searched seat is available
                        System.out.println("The seat you searched is available.");
                        break;
                    }
                } else {
                    System.out.println("Entered seat number is invalid.");
                }
            }catch (InputMismatchException e){
                System.out.println("Entered seat number is invalid.");
                read.nextLine(); // Clear input buffer
            }
        }while(seatindex <=0 || seatindex>=seats[rownum].length);
        menu();
    }

    public static void sc(){ // Method to select options
        Scanner read = new Scanner(System.in);

        boolean correct = false;
        // Loop until correct option is selected
        while (!correct){
            try{  // Prompt user to select an option
                System.out.print("Please select an option : ");
                int option= read.nextInt();
                switch (option) {
                    case 1:
                        buySeat();
                        break;
                    case 2:
                        cancelSeat();
                        break;
                    case 3:
                        findFirstAvailableSeat();
                        break;
                    case 4:
                        showSeatPlan();
                        break;
                    case 5:
                        printTicketInfo();
                        break;
                    case 6:
                        searchTicket();
                        break;
                    case 0:
                        System.out.println("Exited the the Plane Management application. ");
                        break;
                    default:
                        System.out.println("Enter a valid input(An integer between 0-6))");
                        sc(); // Recursive call to allow user to enter valid input
                }
                correct=true;  // Set flag to exit loop
            }catch (InputMismatchException e){
                System.out.println("only numbers are accepted");
                sc(); // Recursive call to allow user to enter valid input
                break; // Exit loop
            }
        }
    }
    public static void menu(){ // Method to print the menue

        for (int r1 = 1; r1 <= 50; r1++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("*                  MENU OPTIONS                  *");

        for (int r1 = 1; r1 <= 50; r1++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("     1) Buy a seat");
        System.out.println("     2) Cancle seat");
        System.out.println("     3) Find first Available seat");
        System.out.println("     4) Show seating plan");
        System.out.println("     5) Print tickets information and total sales");
        System.out.println("     6) Search ticket");
        System.out.println("     0) Quit");

        for (int r1 = 1; r1 <= 50; r1++) {
            System.out.print("*");
        }
        System.out.println();
        sc();
    }
    public static void main(String[] args) { // main method
        System.out.println("Welcome to the Plane Management application");
        menu();
    }
}
