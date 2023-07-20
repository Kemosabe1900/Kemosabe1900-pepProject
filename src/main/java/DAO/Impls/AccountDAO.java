package DAO.Impls;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

import DAO.Interfaces.AccountDAOInterface;

public class AccountDAO implements AccountDAOInterface {


    public Account createAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username, password) VALUES(?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet pkResultSet = ps.getGeneratedKeys();
            if (pkResultSet.next()) {
                int accId = pkResultSet.getInt(1);
                return new Account(accId, account.getUsername(), account.getPassword());
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // handles exceptions related to database
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE account SET username = ?, password = ? WHERE accound_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());// handles exceptions related to database
        }
    }

    public Account deleteAccount(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM account where account_id=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            int affectedRows = ps.executeUpdate(sql);
            if (affectedRows > 0)
                return getAccountById(account_id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}