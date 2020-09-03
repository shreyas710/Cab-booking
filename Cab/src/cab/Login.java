package cab;

import driver.DriverScreen;
import user.UserScreen;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Login {
    public HashMap<String, Integer> driver_id = new HashMap<String, Integer>();
    public HashMap<String, Integer> user_id = new HashMap<String, Integer>();
    public static ArrayList<DriverScreen> dr = new ArrayList<DriverScreen>();
    public static ArrayList<UserScreen> ur = new ArrayList<UserScreen>();

    public static void main(String[] args) {
        int driver_no = 0, user_no = 0;
        Scanner scan = new Scanner(System.in);
        int flag = 0;
        while (flag == 0) {
            int choice, choice2;
            String temp1, temp2;
            System.out.println("1. For Driver\n2. For Customer\n3. Exit");
            choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.println("1. Sign Up\n2. Sign In");
                    choice2 = scan.nextInt();
                    scan.nextLine();
                    switch (choice2) {
                        case 1 -> {
                            DriverScreen d = new DriverScreen();
                            dr.add(d);
                            d.setData(driver_no);
                            driver_no++;
                            try {
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab", "root", "0710");
                                String sql = "insert into driver(name, contact_no, gender, email, password, busy, plate) values(?, ?, ?, ?, ?, ?, ?)";
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
                                System.out.println("Unable to Connect");
                                sqlex.printStackTrace();
                            }
                        }
                        case 2 -> {
                            System.out.print("Enter Email Id: ");
                            temp1 = scan.nextLine();
                            System.out.print("Enter Password: ");
                            temp2 = scan.nextLine();
                            try {
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab", "root", "0710");
                                String sql = "select * from driver where email = ? and password = ?";
                                PreparedStatement stmt = con.prepareStatement(sql);
                                stmt.setString(1, temp1);
                                stmt.setString(2, temp2);
                                ResultSet rs = stmt.executeQuery();
                                if (!rs.next()) {
                                    System.out.println("Driver does not exist");
                                } else {
                                    System.out.println("Login successful!!");
                                    // call rider function
                                    rider(rs.getString(1));
                                }
                                con.close();
                            } catch (SQLException sqlex) {
                                System.out.println("Unable to Connect");
                                sqlex.printStackTrace();
                            }
                        }
                        default -> System.out.println("Invalid Input");
                    }
                }
                case 2 -> {
                    System.out.println("1. Sign Up\n2. Sign In");
                    choice2 = scan.nextInt();
                    scan.nextLine();
                    switch (choice2) {
                        case 1 -> {
                            UserScreen u = new UserScreen();
                            ur.add(u);
                            u.setData(user_no);
                            user_no++;
                            try {
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab", "root", "0710");
                                String sql = "insert into user(name, contact_no, gender, email, password) values(?, ?, ?, ?, ?)";
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
                                System.out.println("Unable to Connect");
                                sqlex.printStackTrace();
                            }
                        }
                        case 2 -> {
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
                                ResultSet rs = stmt.executeQuery();
                                if (!rs.next()) {
                                    System.out.println("User does not exist");
                                } else {
                                    System.out.println("Login successful!!");
                                    // call book cab function
                                    book_cab(rs.getString(1));
                                }
                                con.close();
                            } catch (SQLException sqlex) {
                                System.out.println("Unable to Connect");
                                sqlex.printStackTrace();
                            }
                        }
                        default -> System.out.println("Invalid Input!!");
                    }
                }
                case 3 -> {
                    flag = 1;
                    System.out.println("Thank you for riding!!");
                }
                default -> System.out.println("Invalid Input!!");
            }
        }
        scan.close();
    }

    static String source, destination;
    static int cost;

    public static void book_cab(String user) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your source location: ");
        source = scan.nextLine();
        System.out.println("Enter your destination location: ");
        destination = scan.nextLine();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab", "root", "0710");
            String sql = "select * from driver where busy = 0";
            String sql4 = "select * from cost where src = ? and dest = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            PreparedStatement stmt4 = con.prepareStatement(sql4);
            stmt4.setString(1, source);
            stmt4.setString(2, destination);
            ResultSet rs = stmt.executeQuery();
            ResultSet rs4 = stmt4.executeQuery();
            if (!rs.next()) {
                System.out.println("All drivers are busy");
            } else if (!rs4.next()) {
                System.out.println("No cabs available from " + source + " to " + destination);
            } else {
                System.out.println("Driver details");
                System.out.println("Driver name: " + rs.getString(1));
                System.out.println("Driver contact: " + rs.getString(2));
                System.out.println("Vehicle number: " + rs.getString(7));
                System.out.println("Cost: " + rs4.getInt(3));

                String driname = rs.getString(1);
                cost = rs4.getInt(3);

                // assign the driver to user
                String sql1 = "update user set driver = ? where name = ?";
                PreparedStatement stmt1 = con.prepareStatement(sql1);
                stmt1.setString(1, driname);
                stmt1.setString(2, user);
                stmt1.executeUpdate();

                // assign user to driver;
                String sql2 = "update driver set user = ? where name = ?";
                PreparedStatement stmt2 = con.prepareStatement(sql2);
                stmt2.setString(1, user);
                stmt2.setString(2, driname);
                stmt2.executeUpdate();

                // set busy of driver to 1
                String sql3 = "update driver set busy = ? where name = ?";
                PreparedStatement stmt3 = con.prepareStatement(sql3);
                stmt3.setInt(1, 1);
                stmt3.setString(2, driname);
                stmt3.executeUpdate();
            }
            con.close();
        } catch (SQLException sqlex) {
            System.out.println("Unable to Connect");
            sqlex.printStackTrace();
        }
    }

    public static void rider(String driver) {
        // display user details to driver and ask if ride is complete
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab", "root", "0710");
            String sql = "select * from user where driver = ?";
//            String sql4 = "select * from cost where src = ? and dest = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, driver);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No ongoing rides");
            } else {
                // display user details to driver
                Scanner scan = new Scanner(System.in);

                System.out.println("User Details");
                System.out.println("User name: " + rs.getString(1));
                System.out.println("User contact: " + rs.getString(2));
                System.out.println("Pick Up: " + source);
                System.out.println("Drop At: " + destination);
                System.out.println("Cost: " + cost);

                // ask if ride is finished;
                char c;
                System.out.println("Is the ride finished? (y/n)");
                c = scan.nextLine().charAt(0);
                if (c == 'y') {
                    // change driver table setBusy = 0, user = NULL
                    String sql2 = "update driver set busy = ? and user = ? where name = ?";
                    PreparedStatement stmt2 = con.prepareStatement(sql2);
                    stmt2.setInt(1, 0);
                    stmt2.setNull(2, Types.NULL);
                    stmt2.setString(3, driver);
                    stmt2.executeUpdate();

                    // change user table set driver = NULL
                    String sql3 = "update user set driver = ? where name = ?";
                    PreparedStatement stmt3 = con.prepareStatement(sql3);
                    stmt3.setNull(1, Types.NULL);
                    stmt3.setString(2, rs.getString(1));
                    stmt3.executeUpdate();
                }
            }
            con.close();
        } catch (SQLException sqlex) {
            System.out.println("Unable to Connect");
            sqlex.printStackTrace();
        }
    }
}
