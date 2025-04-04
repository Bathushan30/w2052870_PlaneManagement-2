
import java.util.InputMismatchException;
import java.util.Scanner;

public class w2052870_PlaneManagement {

    public static int rows = 4;
    public static int[] seatcount = {14, 12, 12, 14};
    public static int[][] seats = new int[4][];
    public static int[] prices = {200, 150, 180};

    public static Ticket[] tickets =  new Ticket[52];
    public static int  count =0;

    public static void countofseats() {
        for (int i = 0; i < seatcount.length; i++) {
            seats[i] = new int[seatcount[i]];
        }
    }

    public  static int totalSales;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Plane Management application");

        countofseats();

        while (true) {
            System.out.println("""
                    **************************************************
                    *\t\t\t\t Menu Option  \t\t\t\t\t *
                    **************************************************
                    \t 1) Buy a seat
                    \t 2) Cancel a seat
                    \t 3) Find first available seat
                    \t 4) Show seating plan
                    \t 5) Print tickets information and total sales
                    \t 6) Search ticket
                    \t 0) Quit
                    **************************************************
                    """);


            System.out.print("Please select the option : ");
            int option = scanner.nextInt();

            switch (option) {
                case 0:
                    System.out.println("Thank you for using Plane Management application. Goodbye!");
                    System.exit(0);
                    break;
                case 1:
                    buy_seat(scanner);
                    break;
                case 2:
                    cancel_seat(scanner);
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    // Method to buy a seat
    public static void buy_seat(Scanner scanner) {
        try {
            System.out.print("Enter row letter (A-D): ");
            char rowLetter = Character.toUpperCase(scanner.next().charAt(0));
            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row.Please enter a row letter between A and D.");
                return; //Exit the method if the row is invalid
            }
            int row_num = rowLetter - 'A'; // Convert row letter to index

            System.out.print("Enter seat number: ");
            int seat = scanner.nextInt();

            if (seat < 1 || seat > seatcount[row_num]) {
                System.out.println("Invalid row or seat number.");
                return;
            }

            int price_per_seat;
            if (seat < 6) {
                price_per_seat = prices[0];
            } else if (seat > 5 && seat < 11) {
                price_per_seat = prices[1];
            } else {
                price_per_seat = prices[2];
            }

            if (seats[row_num][seat - 1] == 1) {
                System.out.println("Seat is already taken.");
            } else {
                scanner.nextLine(); // Consume newline left-over
                System.out.print("Enter passenger name: ");
                String name = scanner.nextLine();
                System.out.print("Enter passenger surname: ");
                String surname = scanner.nextLine();
                System.out.print("Enter passenger email: ");
                String email = scanner.nextLine();

                Person passenger = new Person(name, surname, email);
//                passengers[row_num][seat - 1] = passenger;

                seats[row_num][seat - 1] = 1;
                Person person = new Person(name,surname,email);
                Ticket ticket = new Ticket(rowLetter,seat,price_per_seat,person);
                tickets[count] = ticket;
                count++;

                totalSales = totalSales + price_per_seat;
                ticket.save();

                System.out.println("Seat successfully purchased.");
            }

        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine(); // Clear the input buffer
        }
    }

    // Method to cancel a seat
    public static void cancel_seat(Scanner scanner) {
        try {
            System.out.print("Enter row letter (A-D): ");
            char rowLetter = Character.toUpperCase(scanner.next().charAt(0));
            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row.Please enter a row letter between A and D.");
                return; //Exit the method if the row is invalid
            }
            int row_num = rowLetter - 'A'; // Convert row letter to index

            System.out.print("Enter seat number: ");
            int seat = scanner.nextInt();

            if (seat < 1 || seat > seatcount[row_num]) {
                System.out.println("Invalid row or seat number.");
                return;
            }

            int price_per_seat;
            if (seat < 6) {
                price_per_seat = prices[0];
            } else if (seat > 5 && seat < 11) {
                price_per_seat = prices[1];
            } else {
                price_per_seat = prices[2];
            }

            if (seats[row_num][seat - 1] == 0) {
                System.out.println("Seat is already available.");
            } else {
                seats[row_num][seat - 1] = 0;

                Ticket ticket = tickets[count];
                count--;
                totalSales = totalSales - price_per_seat;

                System.out.println("Seat successfully canceled.");
            }
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine(); // Clear the input buffer
        }
    }

    // Method to find the first available seat
    public static void find_first_available() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    char rowLetter = (char) ('A' + i);
                    System.out.println("First available seat: Row " + rowLetter + ", Seat " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    // Method to show seating plan
    public static void show_seating_plan() {
        for (int i = 0; i < seats.length; i++) {
            System.out.print("Row " + (char) ('A' + i) + ": ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] == 1 ? "X " : "O ");
            }
            System.out.println();
        }
    }

    // Method to print tickets information and total sales
    public static void print_tickets_info() {
        for (int i = 0; i < count; i++) {
            tickets[i].printTicketInfo();
        }
        System.out.println("Total sales: $" + totalSales);
    }


    // Method to search for a ticket
    public static void search_ticket(Scanner scanner) {
        try {
            System.out.print("Enter row letter (A-D): ");
            char rowLetter = Character.toUpperCase(scanner.next().charAt(0));
            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row.Please enter a row letter between A and D.");
                return; //Exit the method if the row is invalid
            }
            int row_num = rowLetter - 'A'; // Convert row letter to index

            System.out.print("Enter seat number: ");
            int seat = scanner.nextInt();

            if (seat < 1 || seat > seatcount[row_num]) {
                System.out.println("Invalid row or seat number.");
                return;
            }

            for(Ticket ticket: tickets)
            {
                if (ticket == null) {
                    break;
                }
                if (ticket.getRow() == rowLetter && ticket.getSeat() == seat)
                {
                    ticket.printTicketInfo();
                    return;
                }
            }
            System.out.println("This seat is available row: " + rowLetter + ", Seat: " + seat);

        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine(); // Clear the input buffer
        }
    }
}