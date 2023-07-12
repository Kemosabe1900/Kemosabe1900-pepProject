package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO{

    public void createAccount(Account account) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO Account(username, password) VALUES(?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // handles exceptions related to database
        }
    }

    public Account getAccountByUsername(String username) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Account WHERE  username=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int retrievedAccountId = rs.getInt("account_id");
                String password = rs.getString("password");
                return new Account(retrievedAccountId, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // handles exceptions related to database
        }
        return null;
    }

    // find account by acount_id method
    public Account getAccountById(int account_id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Account WHERE  account_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int retrievedAccountId = rs.getInt("account_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                return new Account(retrievedAccountId, username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // handles exceptions related to database
        }
        return null;
    }

    // method to inset account into database
    public Account insertAccount(Account account) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO Account(username,password) VALUES(?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int getAccountId = pkeyResultSet.getInt(1);
                return new Account(getAccountId, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // handles exceptions related to database
        }
        return null;
    }

    public void updateAccount(Account account) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE Account SET username = ?, password = ? WHERE accound_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());// handles exceptions related to database
        }
    }

    public Account deleteAccount(int account_id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM Account where account_id=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            int affectedRows = ps.executeUpdate(sql);
            if (affectedRows > 0)
                return getAccountById(account_id);// returns the deleted object
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // handles exceptions related to database
        }
        return null;
    }

}