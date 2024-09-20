import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


    public void register() {
        scanner.nextLine();
        System.out.println("Full Name: ");
        String full_name = scanner.nextLine();
        System.out.println("email: ");
        String email = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();

        if (userexist(email)) {
            System.out.println("already exixt");
            return;
        }

        String register_querry = "INSERT INTO USER (FULL_NAME, EMAIL,PASSWORD) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(register_querry);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("succecful register");
            } else {
                System.out.println("registerd failed");

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public String login(){
        scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();





        String login_query = "SELECT * FROM user WHERE email = ?  And password = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else {
                return null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }


        return null ;
    }

    public boolean userexist(String email) {
        String querry = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;

    }

}


