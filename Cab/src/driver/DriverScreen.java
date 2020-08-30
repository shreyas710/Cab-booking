package driver;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import cab.Login;

public class DriverScreen {
    String name;
    String contact_no;
    String gender;
    String email;
    String password;
    int busy;
    String namePlate;

    public DriverScreen() {
        name = "";
        contact_no = "";
        gender = "";
        email = "";
        password = "";
        namePlate = "";
        busy = 0;
    }

    Login l = new Login();

    public void setData(int drive) {
        String temp;
        HashMap<String, Integer> driver_id_1 = l.driver_id;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name:");
        temp = scan.nextLine();
        setName(temp);
        System.out.println("Enter Contact no.:");
        temp = scan.nextLine();
        setContact_no(temp);
        System.out.println("Enter Gender:");
        temp = scan.nextLine();
        setGender(temp);
        System.out.println("Enter Email ID:");
        temp = scan.nextLine();
        driver_id_1.put(temp, drive);
        setEmail(temp);
        System.out.println("Set Your Password:");
        temp = scan.nextLine();
        setPassword(temp);
        System.out.println("Enter vehicle number:");
        temp = scan.nextLine();
        setPlate(temp);
    }

    public void setName(String name) {this.name = name;}

    public void setPlate(String namePlate) {this.namePlate = namePlate;}

    public void setContact_no(String contact_no) {this.contact_no = contact_no;}

    public void setGender(String gender) {this.gender = gender;}

    public void setEmail(String email) {this.email = email;}

    public void setPassword(String password) {this.password = password;}

    public void setBusy(int busy) {this.busy = busy;}

    public String getPassword() {return this.password;}

    public String getPlate() {return this.namePlate;}

    public int getBusy() {return this.busy;}

    public String getEmail() {return this.email;}

    public String getGender() {return this.gender;}

    public String getName() {return this.name;}

    public String getContact_no() {return this.contact_no;}
}