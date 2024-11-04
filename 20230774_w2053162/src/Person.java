public class Person { //Person class

    //convirting the class details to a string
    public String toString() {
        return " Name :- " + name + "\n Surname :- " + surname + "\n Email :- " + email+"\n";
    }

    private String name;
    private String surname;
    private String email;

    // The constructor.
    public Person(String name, String surname, String email){
        this.name=name;
        this.surname=surname;
        this.email=email;
    }
    // Getters and setters
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getEmail(){
        return email;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    public void setEmail(String email){
        this.email=email;
    }

    //Method to print the person details.
    public void printDetails(){
        System.out.println("Name : "+name);
        System.out.println("Surname : "+surname);
        System.out.println("Email : "+email);
    }

}
