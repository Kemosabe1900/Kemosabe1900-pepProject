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
            e.printStackTrace(); // handles exceptions related to database
        }
    }

    public Account getAccountByUsername(String username) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM `Account` WHERE  username=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int retrievedAccountId = rs.getInt("account_id");
                String password = rs.getString("password");
                return new Account(retrievedAccountId, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // handles exceptions related to database
        }
        return null;
    }

    public Account getAccountById(int account_id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM `Account` WHERE  account_id=?";
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
            e.printStackTrace(); // handles exceptions related to database
        }
        return null;
    }

}