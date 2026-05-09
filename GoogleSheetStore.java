import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class GoogleSheetStore {
    static String WEB_APP_URL =
    "https://script.google.com/macros/s/AKfycbwrjZBMXT582QdruSdh1Befql-zDQy-96T4eA5Uo3MFn3ado0TjOk6L4xPpGanEII9eCQ/exec";
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("      STUDENT CRUD MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. ADD STUDENT");
            System.out.println("2. VIEW STUDENTS");
            System.out.println("3. UPDATE STUDENT");
            System.out.println("4. DELETE STUDENT");
            System.out.println("5. SEARCH STUDENT");
            System.out.println("6. EXIT");
            System.out.print("\nEnter Choice : ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    updateStudent();
                    break;

                case 4:
                    deleteStudent();
                    break;

                case 5:
                    searchStudent();
                    break;

                case 6:
                    System.out.println("\nProgram Exited!");
                    System.exit(0);

                default:
                    System.out.println("\nInvalid Choice!");
            }
        }
    }
    public static void sendRequest(String jsonInput) {

        try {

            URI uri = new URI(WEB_APP_URL);

            URL url = uri.toURL();

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            os.write(jsonInput.getBytes());

            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String response;

            StringBuilder sb = new StringBuilder();

            while ((response = br.readLine()) != null) {

                sb.append(response);
            }

            br.close();

            String result = sb.toString();

            System.out.println("\n========================================");
            if (result.startsWith("[[")) {

                result = result
                        .replace("[[", "")
                        .replace("]]", "")
                        .replace("\"", "");

                String[] rows = result.split("\\],\\[");

                System.out.println("          STUDENT RECORDS");
                System.out.println("========================================");

                for (int i = 1; i < rows.length; i++) {

                    String[] cols = rows[i].split(",");

                    if (cols.length < 9) {
                        continue;
                    }

                    if (cols[1].trim().isEmpty()) {
                        continue;
                    }

                    System.out.println("\nSLNO      : " + cols[0]);
                    System.out.println("NAME      : " + cols[1]);
                    System.out.println("ADDRESS   : " + cols[2]);
                    System.out.println("PHONE     : " + cols[3]);
                    System.out.println("EMAIL     : " + cols[4]);
                    System.out.println("PASSWORD  : " + cols[5]);
                    System.out.println("COURSE    : " + cols[6]);
                    System.out.println("REGNO     : " + cols[7]);
                    System.out.println("COLLEGE   : " + cols[8]);

                    System.out.println("----------------------------------------");
                }
            }
            else if (result.startsWith("[")) {

                result = result
                        .replace("[", "")
                        .replace("]", "")
                        .replace("\"", "");

                String[] cols = result.split(",");

                if (cols.length >= 9) {

                    System.out.println("          STUDENT DETAILS");
                    System.out.println("========================================");

                    System.out.println("\nSLNO      : " + cols[0]);
                    System.out.println("NAME      : " + cols[1]);
                    System.out.println("ADDRESS   : " + cols[2]);
                    System.out.println("PHONE     : " + cols[3]);
                    System.out.println("EMAIL     : " + cols[4]);
                    System.out.println("PASSWORD  : " + cols[5]);
                    System.out.println("COURSE    : " + cols[6]);
                    System.out.println("REGNO     : " + cols[7]);
                    System.out.println("COLLEGE   : " + cols[8]);
                }
            }
            else {

                result = result
                        .replace("{", "")
                        .replace("}", "")
                        .replace("\"", "");

                String[] parts = result.split(",");

                System.out.println("          SERVER RESPONSE");
                System.out.println("========================================");

                for (String part : parts) {

                    System.out.println(part);
                }
            }

            System.out.println("========================================");

        } catch (Exception e) {

            System.out.println("\n========================================");
            System.out.println("ERROR : " + e.getMessage());
            System.out.println("========================================");
        }
    }
    public static void addStudent() {

        try {

            System.out.println("\n===== ADD STUDENT =====");

            System.out.print("Enter Name : ");
            String name = sc.nextLine();

            System.out.print("Enter Address : ");
            String address = sc.nextLine();

            System.out.print("Enter Phone Number : ");
            String phno = sc.nextLine();

            System.out.print("Enter Email : ");
            String email = sc.nextLine();

            System.out.print("Enter Password : ");
            String password = sc.nextLine();

            System.out.print("Enter Course : ");
            String course = sc.nextLine();

            System.out.print("Enter Register Number : ");
            String regno = sc.nextLine();

            System.out.print("Enter College Name : ");
            String college = sc.nextLine();

            String jsonInput = "{"
                    + "\"action\":\"ADD\","
                    + "\"name\":\"" + name + "\","
                    + "\"address\":\"" + address + "\","
                    + "\"phno\":\"" + phno + "\","
                    + "\"email\":\"" + email + "\","
                    + "\"password\":\"" + password + "\","
                    + "\"course\":\"" + course + "\","
                    + "\"regno\":\"" + regno + "\","
                    + "\"college\":\"" + college + "\""
                    + "}";

            sendRequest(jsonInput);

        } catch (Exception e) {

            System.out.println("ERROR : " + e.getMessage());
        }
    }
    public static void viewStudents() {

        String jsonInput = "{"
                + "\"action\":\"VIEW\""
                + "}";
        sendRequest(jsonInput);
    }
    public static void updateStudent() {

        try {

            System.out.println("\n===== UPDATE STUDENT =====");

            System.out.print("Enter Register Number : ");
            String regno = sc.nextLine();

            System.out.print("Enter New Name : ");
            String name = sc.nextLine();

            System.out.print("Enter New Address : ");
            String address = sc.nextLine();

            System.out.print("Enter New Phone Number : ");
            String phno = sc.nextLine();

            System.out.print("Enter New Email : ");
            String email = sc.nextLine();

            System.out.print("Enter New Password : ");
            String password = sc.nextLine();

            System.out.print("Enter New Course : ");
            String course = sc.nextLine();

            System.out.print("Enter New College Name : ");
            String college = sc.nextLine();

            String jsonInput = "{"
                    + "\"action\":\"UPDATE\","
                    + "\"regno\":\"" + regno + "\","
                    + "\"name\":\"" + name + "\","
                    + "\"address\":\"" + address + "\","
                    + "\"phno\":\"" + phno + "\","
                    + "\"email\":\"" + email + "\","
                    + "\"password\":\"" + password + "\","
                    + "\"course\":\"" + course + "\","
                    + "\"college\":\"" + college + "\""
                    + "}";

            sendRequest(jsonInput);

        } catch (Exception e) {

            System.out.println("ERROR : " + e.getMessage());
        }
    }
    public static void deleteStudent() {
        System.out.println("\n===== DELETE STUDENT =====");
        System.out.print("Enter Register Number : ");
        String regno = sc.nextLine();
        String jsonInput = "{"
                + "\"action\":\"DELETE\","
                + "\"regno\":\"" + regno + "\""
                + "}";
        sendRequest(jsonInput);
    }
    public static void searchStudent() {
        System.out.println("\n===== SEARCH STUDENT =====");
        System.out.print("Enter Register Number : ");
        String regno = sc.nextLine();
        String jsonInput = "{"
                + "\"action\":\"SEARCH\","
                + "\"regno\":\"" + regno + "\""
                + "}";
        sendRequest(jsonInput);
    }
}