import java.sql.*;
import java.util.Scanner;

public class Accounts {

    private Connection connection;
    private Scanner scanner;

    public Accounts (Connection connection, Scanner sacnner){
        this.connection = connection;
        this.scanner = sacnner;
    }

    public boolean account_exist(String email){
        String query = "SELECT ACCOUNT_NUMBER  FROM ACCOUNTS WHERE EMAILS = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;

            }else{
                return  false;
            }

        }catch (SQLException e ){
            e.printStackTrace();
        }


        return false;
    }


    private long generateAccountNumber(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ACCOUNT_NUMBER FROM ACCOUNTS ORDER BY ACCOUNT_NUMBER DESC LIMIT 1");
            if(resultSet.next()){
                long last_account_number = resultSet.getLong("account_number");
                return  last_account_number +1;

            }else{
                return 10000100;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 10000100;

    }

}
