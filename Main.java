
import Database.DBConnection;

import Home.Home;
import Pomodoro.Pomodoro;
import YouTubeManager.YouTubeManager;
import Notes.Notes;
import Performance.Performance;
import ToDoList.ToDoList;
import QuizGenerator.QuizGenerator;
import Settings.Settings;
import About.About;
import Settings.Login;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database Connection Failed!");
                sc.close();
                return;
            }

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
            sc.close();
            return;
        }
         

         Login login = new Login(sc);

        int userId = login.login();

       if (userId == -1) {
       System.out.println("Login Failed! Exiting...");
      sc.close();
      return;
      }
       

    
        Home home = new Home(sc, userId);
        Pomodoro pomodoro = new Pomodoro(sc, userId);
        YouTubeManager youtube = new YouTubeManager(sc, userId);

        Notes notes = new Notes(sc, userId);
        Performance performance = new Performance(sc, userId);

        ToDoList todo = new ToDoList(sc, userId);
        QuizGenerator quiz = new QuizGenerator(sc, userId);

        Settings settings = new Settings(sc, userId);
        About about = new About(sc);

        int choice = -1;

        do {

            System.out.println("\n========== STUDYSPHERE ==========");
            System.out.println("1. Home");
            System.out.println("2. Pomodoro Timer");
            System.out.println("3. YouTube Manager");
            System.out.println("4. Notes Management");
            System.out.println("5. Performance Management");
            System.out.println("6. To-Do List");
            System.out.println("7. Quiz Generator");
            System.out.println("8. Settings");
            System.out.println("9. About");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");

            try {

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        home.showMenu();
                        break;

                    case 2:
                        pomodoro.showMenu();
                        break;

                    case 3:
                        youtube.showMenu();
                        break;

                    case 4:
                        notes.showMenu();
                        break;

                    case 5:
                        performance.showMenu();
                        break;

                    case 6:
                        todo.showMenu();
                        break;

                    case 7:
                        quiz.showMenu();
                        break;

                    case 8:
                        settings.showMenu();
                        break;

                    case 9:
                        about.showMenu();
                        break;

                    case 0:
                        System.out.println(
                            "\nThank you for using StudySphere!"
                        );
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number!");
            }

        } while (choice != 0);

        sc.close();
    }
}
