import java.sql.*;
import java.util.Scanner;

public class BankApp {

    static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    static final String USER = "root";
    static final String PASS = "";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== BANK MENU =====");
            System.out.println("1. Create Account");
            System.out.println("2. Login & Manage Account");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // 🔐 LOGIN SYSTEM
    static void login() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            System.out.print("Enter Account ID: ");
            int id = sc.nextInt();

            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            String query = "SELECT * FROM accounts WHERE id=? AND pin=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Login Successful!");
                accountMenu(id);
            } else {
                System.out.println("❌ Invalid ID or PIN");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 📋 ACCOUNT MENU
    static void accountMenu(int id) {
        while (true) {
            System.out.println("\n--- ACCOUNT MENU ---");
            System.out.println("1. View Details");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Transaction History");
            System.out.println("6. Delete Account");
            System.out.println("7. Logout");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewAccount(id);
                case 2 -> deposit(id);
                case 3 -> withdraw(id);
                case 4 -> checkBalance(id);
                case 5 -> transactionHistory(id);
                case 6 -> deleteAccount(id);
                case 7 -> { return; }
                default -> System.out.println("Invalid!");
            }
        }
    }

    // ✅ CREATE ACCOUNT
    static void createAccount() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            System.out.print("Enter Name: ");
            sc.nextLine();
            String name = sc.nextLine();

            System.out.print("Enter Initial Balance: ");
            double balance = sc.nextDouble();

            System.out.print("Set 4-digit PIN: ");
            int pin = sc.nextInt();

            if (balance < 0) {
                System.out.println("❌ Invalid balance");
                return;
            }

            String query = "INSERT INTO accounts(name, balance, pin) VALUES(?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setDouble(2, balance);
            ps.setInt(3, pin);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("✅ Account Created!");
                System.out.println("👉 Your Account ID: " + rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 👁 VIEW ACCOUNT
    static void viewAccount(int id) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            String query = "SELECT * FROM accounts WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Balance: " + rs.getDouble("balance"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 💰 DEPOSIT
    static void deposit(int id) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            if (amount <= 0) {
                System.out.println("❌ Invalid amount");
                return;
            }

            String query = "UPDATE accounts SET balance = balance + ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDouble(1, amount);
            ps.setInt(2, id);
            ps.executeUpdate();

            addTransaction(id, "DEPOSIT", amount);
            System.out.println("✅ Deposit Successful");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 💸 WITHDRAW
    static void withdraw(int id) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            String check = "SELECT balance FROM accounts WHERE id=?";
            PreparedStatement cps = con.prepareStatement(check);
            cps.setInt(1, id);

            ResultSet rs = cps.executeQuery();

            if (rs.next()) {
                double bal = rs.getDouble("balance");

                if (bal >= amount) {
                    String q = "UPDATE accounts SET balance = balance - ? WHERE id=?";
                    PreparedStatement ps = con.prepareStatement(q);
                    ps.setDouble(1, amount);
                    ps.setInt(2, id);
                    ps.executeUpdate();

                    addTransaction(id, "WITHDRAW", amount);
                    System.out.println("✅ Withdraw Successful");
                } else {
                    System.out.println("❌ Insufficient Balance");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 💵 CHECK BALANCE
    static void checkBalance(int id) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            String query = "SELECT balance FROM accounts WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("💰 Balance: " + rs.getDouble("balance"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 📜 TRANSACTION HISTORY
    static void transactionHistory(int id) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            String query = "SELECT * FROM transactions WHERE acc_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getString("type") + " | ₹" +
                        rs.getDouble("amount") + " | " +
                        rs.getTimestamp("date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ➕ ADD TRANSACTION
    static void addTransaction(int id, String type, double amount) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            String query = "INSERT INTO transactions(acc_id, type, amount) VALUES(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, type);
            ps.setDouble(3, amount);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ❌ DELETE ACCOUNT
    static void deleteAccount(int id) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            String query = "DELETE FROM accounts WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("✅ Account Deleted");
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}