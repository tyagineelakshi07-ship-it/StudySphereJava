
package About;

import java.util.Scanner;

public class ContactUs {

    private Scanner sc;

    public ContactUs(Scanner sc) {
        this.sc = sc;
    }

    public void show() {

        System.out.println("\n========== CONTACT US ==========");

        System.out.println("\nWe would love to hear from you!");

        System.out.println("\nProject: StudySphere");
        System.out.println("Email: studysphere@gmail.com");

        System.out.println("\nDevelopment Team:");
        System.out.println("1. Charvi");
        System.out.println("2. Neelakshi");
        System.out.println("3. Aayushi");

        System.out.println("\n--------------------------------");

        System.out.println("\nCONTACT FORM");

        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter your email: ");
        String email = sc.nextLine().trim();

        System.out.print("Enter subject: ");
        String subject = sc.nextLine().trim();

        System.out.print("Enter your message: ");
        String message = sc.nextLine().trim();

        if (name.isEmpty() || email.isEmpty()
                || subject.isEmpty() || message.isEmpty()) {

            System.out.println("\nAll fields are required!");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            System.out.println("\nPlease enter a valid email!");
            return;
        }

        System.out.println("\n========== MESSAGE SUBMITTED ==========");

        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Subject: " + subject);

        System.out.println("\nThank you for contacting StudySphere!");
        System.out.println("Your message has been received.");

        System.out.println("=======================================");
    }
}

