package cinema;

import java.util.Scanner;

public class Cinema {
    public static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = sc.nextInt();
        Room room = new Room(rows, seats);
        do {
            System.out.println(
                "1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit"
            );
            int choice = sc.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println(room);
                    break;
                }
                case 2: {
                    while (!room.bookSeat());
                    break;
                }
                case 3: {
                    room.printStats();
                    break;
                }
                case 0: return;
            }
        } while (true);
    }

    static class Room {
        final int seats;
        final int rows;
        final int totalSeats;
        final char[][] room;
        final int totalIncome;
        int purchaseCount, currentIncome;

        public Room(int rows, int seats) {
            this.seats = seats;
            this.rows = rows;
            totalSeats = rows * seats;
            room = new char[rows][seats];
            if (totalSeats <= 60) {
                totalIncome = totalSeats * 10;
            } else {
                int frontHalf = rows / 2;
                int backHalf = rows - frontHalf;
                totalIncome = frontHalf * seats * 10 + backHalf * seats * 8;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Cinema:").append('\n').append(' ');
            for (int i = 1; i <= seats; i++) {
                sb.append(' ').append(i);
            }
            sb.append('\n');
            int rowIndex = 1;
            for (char[] row : room) {
                sb.append(rowIndex++);
                for (char seat : row) {
                    sb.append(String.format(" %c", seat == 0? 'S': seat));
                }
                sb.append('\n');
            }
            return sb.toString();
        }
        void printStats() {
            System.out.printf("Number of purchased tickets: %d\n", purchaseCount);
            System.out.printf("Percentage: %.2f%%\n", ((float) purchaseCount/ (rows * seats)) * 100);
            System.out.printf("Current income: $%d\n", currentIncome);
            System.out.printf("Total income: $%d\n", totalIncome);
        }
        public boolean bookSeat() {
            System.out.println("Enter a row number:");
            int rowNumber = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatNumber = sc.nextInt();
            if (rowNumber > rows || seatNumber > seats) {
                System.out.println("Wrong input!");
                return false;
            }
            else if (room[rowNumber - 1][seatNumber - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
                return false;
            }
            room[rowNumber - 1][seatNumber - 1] = 'B';
            purchaseCount++;
            int profit = getTotalProfit(rowNumber);
            currentIncome += profit;
            System.out.printf("Ticket price: $%d\n", profit);
            return true;
        }

        private int getTotalProfit(int rowNumber) {
            if (totalSeats <= 60) {
                return 10;
            }
            int frontHalf = rows / 2;
            return (rowNumber <= frontHalf)? 10: 8;
        }
    }
}