import java.util.*;
import java.net.http.*;
import java.net.URI;

public class StudentClient {
    static Scanner sc = new Scanner(System.in);
    static HttpClient client = HttpClient.newHttpClient();

    public static void addStudent() throws Exception {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();

        String json = String.format("{\"roll\":%d,\"name\":\"%s\",\"age\":%d}", roll, name, age);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://127.0.0.1:5000/add"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            System.out.println("✅ Student Added!");
        } else {
            System.out.println("❌ Failed to add student: " + response.body());
        }
    }

    public static void viewStudents() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://127.0.0.1:5000/view"))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            System.out.println("All Students: " + response.body());
        } else {
            System.out.println("❌ Failed to fetch students");
        }
    }

    public static void searchStudent() throws Exception {
        System.out.print("Enter Roll No to search: ");
        int roll = sc.nextInt();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://127.0.0.1:5000/search/" + roll))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            System.out.println("Search Result: " + response.body());
        } else {
            System.out.println("❌ Student not found");
        }
    }

    public static void main(String[] args) throws Exception {
        int choice;
        do {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }
}
