package user;

import java.util.HashMap;
import java.util.Scanner;

import driver.DriverScreen;
import cab.Login;

public class UserScreen {
    String name;
    String contact_no;
    String gender;
    String email;
    String password;
    Login l = new Login();

    public void setData(int user_no) {
        HashMap<String, Integer> user_id_1 = l.user_id;
        String temp;
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
        user_id_1.put(temp, user_no);
        setEmail(temp);
        System.out.println("Set Your Password:");
        temp = scan.nextLine();
        setPassword(temp);
    }

    public void book_cab() {
        Scanner scan = new Scanner(System.in);
        String source, destination;
        System.out.println("Enter your source location: ");
        source = scan.nextLine();
        System.out.println("Enter your destination location: ");
        destination = scan.nextLine();
        int ok = 0, i = 0;
        for (DriverScreen d : l.dr) {
            if (d.getBusy() == 0) {
                ok = 1;
                System.out.println("Cab Booked!!");
                System.out.println("Your Driver is: " + d.getName());
                System.out.println("Driver contact no.: " + d.getContact_no());
                System.out.println("Vehicle: " + d.getPlate());
                d.setBusy(1);
                d.add_rider(getName());
                break;
            }
            i++;
        }
        if (ok == 0) {
            System.out.println("Sorry, no Driver available...");
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getGender() {
        return this.gender;
    }

    public String getName() {
        return this.name;
    }

    public String getContact_no() {
        return this.contact_no;
    }
}