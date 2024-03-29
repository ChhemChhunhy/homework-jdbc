package view;


import model.User;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.*;


public class View {
    static Scanner scanner = new Scanner(System.in);
    static CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);

public static void showUser(List<User> userList){
    Table tableUser = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);


    tableUser.addCell("     ID     ");
    tableUser.addCell("     Name     ");
    tableUser.addCell("     Email     ");
    tableUser.addCell("     Password     ");
    tableUser.addCell("     Is Deleted     ");
    tableUser.addCell("     Is Verified     ");
    tableUser.addCell("     UUID     ");
    for (User user : userList) {
        tableUser.addCell(user.getId().toString(), cellStyle);
        tableUser.addCell(user.getName(), cellStyle);
        tableUser.addCell(user.getEmail(), cellStyle);
        tableUser.addCell(hidePassword(user.getPassword()), cellStyle);
        tableUser.addCell(user.getIsDeleted().toString(), cellStyle);
        tableUser.addCell(user.getIsVerified().toString(), cellStyle);
        tableUser.addCell(user.getUuid(), cellStyle);
    }
    System.out.println(tableUser.render());
}
    public static void viewApplication(){
        System.out.println("\uD83C\uDF3A".repeat(20)+ "   Welcome to Application   ".toUpperCase(Locale.ROOT)+"\uD83C\uDF3A".repeat(20));
    }
    public static void renderMenu(){
        Table table = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        table.setColumnWidth(0,20,30);
        table.setColumnWidth(1, 20,30);
        table.setColumnWidth(2,20,30);
        table.setColumnWidth(3,20,30);
        table.setColumnWidth(4,20,30);
        table.setColumnWidth(5,20,30);
        table.setColumnWidth(6,20,30);
        table.addCell("1)Read All User");
        table.addCell("|  2)Create User");
        table.addCell("|  3)Update User");
        table.addCell("|  4)Delete User");
        table.addCell("|  5)Search User");
        table.addCell("|  6)Sort by name");
        table.addCell("|  7)Exit");
        System.out.println(table.render());
    }
    private static String hidePassword(String password) {
        return "*".repeat(password.length());
    }
    public static int options() {
        int choice;
        System.out.print("➡️"+" Choose one option: ");
        try {
            choice = Integer.parseInt(scanner.nextLine());
            if (choice >= 1 && choice <= 7) {
                return choice;
            } else {
                System.out.println("Please choose an option between [1-6]");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return 0;
    }
    public static User createUser(){
    User userCreate = new User();
    UUID uuid = UUID.randomUUID();
    String uuidShort= uuid.toString().substring(0,6);
    System.out.print("Enter Name :");
    userCreate.setName(scanner.nextLine());
    System.out.print("Enter Email :");
    userCreate.setEmail(scanner.nextLine());
    System.out.print("Enter Password :");
    userCreate.setPassword(scanner.nextLine());
    System.out.print("Enter Verify ture or false  :");
    userCreate.setIsVerified(Boolean.parseBoolean(scanner.nextLine()));
    userCreate.setIsDeleted(false);
    userCreate.setUuid(uuidShort);
    return userCreate;
    }

    public static User userDelete(List<User> userList) {
        System.out.print("Enter user id to delete: ");
        int userId = Integer.parseInt(scanner.nextLine());

        // Find the user by userId
        Optional<User> userToRemove = userList.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();

        // Check if user with given userId exists
        if (userToRemove.isPresent()) {
            User user = userToRemove.get();

            // Ask for confirmation
            System.out.print("Are you sure you want to delete user " + user.getName() + "? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            // Process confirmation
            if (confirmation.equals("y")) {
                // Return the user if confirmed
                return user;
            } else {
                System.out.println("Deletion canceled.");
                return null; // Return null if deletion is canceled
            }
        } else {
            System.out.println("User with id " + userId + " not found.");
            return null; // Return null if user is not found
        }
    }

    public static int updateUser(List<User> userList){
        System.out.print("Enter user Id to update: ");
        int userId = Integer.parseInt(scanner.nextLine());
        for(User user : userList){
            if(user.getId().equals(userId)){
                System.out.print("Enter new name: ");
                user.setName(scanner.nextLine());
                System.out.print("Enter new email: ");
                user.setEmail(scanner.nextLine());
                System.out.print("Are you sure you want to update the user? (y/n): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("y")) {
                    System.out.print("Are you sure to change delete? (true for delete, false for no delete): ");
                    user.setIsDeleted(Boolean.parseBoolean(scanner.nextLine()));
                    System.out.print("Is user verified? (true for verify, false for no verify): ");
                    user.setIsVerified(Boolean.parseBoolean(scanner.nextLine()));
                    System.out.print("Do you want to update password? (Y/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        System.out.print("Enter new password: ");
                        user.setPassword(scanner.nextLine());
                    }
                    return userId;
                } else {
                    System.out.println("Update canceled.");
                    return -1; // Return -1 to indicate update canceled
                }
            }
        }

        return -1; // Return -1 to indicate user not found
    }
    public static void searchUser(List<User> userList){
        System.out.print("Enter user id to search :");
        int userId = Integer.parseInt(scanner.nextLine());
        Table tableUser = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
        tableUser.addCell("     ID     ");
        tableUser.addCell("     Name     ");
        tableUser.addCell("     Email     ");
        tableUser.addCell("     Password     ");
        tableUser.addCell("     Is Deleted     ");
        tableUser.addCell("     Is Verified     ");
        tableUser.addCell("     UUID     ");
        Boolean isSearchFound = false;
        for(User user : userList){
            if(user.getId().equals(userId)){
                tableUser.addCell(user.getId().toString(), cellStyle);
                tableUser.addCell(user.getName(), cellStyle);
                tableUser.addCell(user.getEmail(), cellStyle);
                tableUser.addCell(hidePassword(user.getPassword()), cellStyle);
                tableUser.addCell(user.getIsDeleted().toString(), cellStyle);
                tableUser.addCell(user.getIsVerified().toString(), cellStyle);
                tableUser.addCell(user.getUuid(), cellStyle);
                isSearchFound = true;
                break;
            }
        }
        if (isSearchFound){
            System.out.println(tableUser.render());
        }else {
            System.out.println("Search not found ");
        }
    }
    public static void showSortedUsers(List<User> userList) {

    showUser(userList);
    }
    public static int shortOptions(){
        int choice;
        System.out.print(">> Choose one option: ");
        try {
            choice = Integer.parseInt(scanner.nextLine());
            if (choice >= 1 && choice <= 3) {
                return choice;
            } else {
                System.out.println("Please choose an option between [1-3]");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return 0;
    }
    public static void sortMenu(){
        Table table = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        table.setColumnWidth(0,20,30);
        table.setColumnWidth(1, 20,30);
        table.setColumnWidth(2,20,30);
        table.addCell("1)Sort Name Order");
        table.addCell("    |   2)Sort Name Descending");
        table.addCell("    |   3)Exit");
        System.out.println(table.render());
    }


}
