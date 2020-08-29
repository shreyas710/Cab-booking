package cab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import driver.DriverScreen;
import user.UserScreen;

public class Login {
    public HashMap<String, Integer> driver_id = new HashMap<String, Integer>();
    public HashMap<String, Integer> user_id = new HashMap<String, Integer>();
    public static ArrayList<DriverScreen> dr = new ArrayList<DriverScreen>();
    public static ArrayList<UserScreen> ur = new ArrayList<UserScreen>();

    public static void main(String[] args) {
        int driver_no = 0, user_no = 0;
        Scanner scan = new Scanner(System.in);
        int flag = 0, id = -1;
        while (flag == 0) {
            int choice, choice2, ok;
            String temp1, temp2;
            System.out.println("1. For Driver\n2. For Customer\n3. Exit");
            choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("1. Sign Up\n2. Sign In");
                    choice2 = scan.nextInt();
                    scan.nextLine();
                    switch (choice2) {
                        case 1:
                            DriverScreen d = new DriverScreen();
                            dr.add(d);
                            d.setData(driver_no);
                            driver_no++;
                            try {
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab", "root", "0710");
                                String sql = "insert into driver values(?, ?, ?, ?, ?, ?, ?)";
                                PreparedStatement stmt = con.prepareStatement(sql);
                                stmt.setString(1, d.getName());
                                stmt.setString(2, d.getContact_no());
                                stmt.setString(3, d.getGender());
                                stmt.setString(4, d.getEmail());
                                stmt.setString(5, d.getPassword());
                                stmt.setInt(6, d.getBusy());
                                stmt.setString(7, d.getPlate());
                                stmt.executeUpdate();
                                con.close();
                                System.out.println("Driver registered successfully!!");
                            } catch (SQLException sqlex) {
                                System.out.println("Unable to insert");
                                sqlex.printStackTrace();
                            }
                            break;
                        case 2:
                            System.out.print("Enter Email Id: ");
                            temp1 = scan.nextLine();
                            System.out.print("Enter Password: ");
                            temp2 = scan.nextLine();
                            ok = 0;
                            for (int i = 0; i < dr.size(); i++) {
                                if (temp1.equals(dr.get(i).getEmail())) {

                                    ok = 1;
                                    if (temp2.equals(dr.get(i).getPassword())) {
                                        id = i;
                                        ok = 2;
                                        break;
                                    }
                                }
                            }
                            if (ok == 1) {
                                System.out.println("Incorrect Password");
                                break;
                            } else if (ok == 0) {
                                System.out.println("Invalid Email Id");
                                break;
                            } else {
                                System.out.println("Login Successfull!!");
                                dr.get(id).setBusy(0);
                                dr.get(id).rider_history();
                            }
                            break;
                        default:
                            System.out.println("Invalid Input");
                    }
                    break;
                case 2:
                    System.out.println("1. Sign Up\n2. Sign In");
                    choice2 = scan.nextInt();
                    scan.nextLine();
                    switch (choice2) {
                        case 1:
                            UserScreen u = new UserScreen();
                            ur.add(u);
                            u.setData(user_no);
                            user_no++;
                            try {
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab", "root", "0710");
                                String sql = "insert into user values(?, ?, ?, ?, ?)";
                                PreparedStatement stmt = con.prepareStatement(sql);
                                stmt.setString(1, u.getName());
                                stmt.setString(2, u.getContact_no());
                                stmt.setString(3, u.getGender());
                                stmt.setString(4, u.getEmail());
                                stmt.setString(5, u.getPassword());
                                stmt.executeUpdate();
                                con.close();
                                System.out.println("User registered successfully!!");
                            } catch (SQLException sqlex) {
                                System.out.println("Unable to insert");
                                sqlex.printStackTrace();
                            }
                            break;
                        case 2:
                            System.out.print("Enter Email Id: ");
                            temp1 = scan.nextLine();
                            System.out.print("Enter Password: ");
                            temp2 = scan.nextLine();
                            try {
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab", "root", "0710");
                                String sql = "select * from user where email = ? and password = ?";
                                PreparedStatement stmt = con.prepareStatement(sql);
                                stmt.setString(1, temp1);
                                stmt.setString(2, temp2);
                                final ResultSet rs = stmt.executeQuery();
                                if (rs.next() == false) {
                                    System.out.println("User does not exist");
                                } else {
                                    System.out.println("Login successful!!");
                                    // call book cab function
                                }
                                con.close();
                            } catch (SQLException sqlex) {
                                System.out.println("Unable to insert");
                                sqlex.printStackTrace();
                            }
                            break;
                        default:
                            System.out.println("Invalid Input!!");
                    }
                    break;
                case 3:
                    flag = 1;
                    System.out.println("Thank you for riding!!");
                    break;
                default:
                    System.out.println("Invalid Input!!");
            }
        }
        scan.close();
    }
}
