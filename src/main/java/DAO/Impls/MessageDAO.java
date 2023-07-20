package DAO.Impls;

import DAO.Interfaces.MessageDAOInterface;

import Model.Message;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MessageDAO implements MessageDAOInterface {

    public Message createMessage(Message message) {

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet pkResultSet = ps.getGeneratedKeys();
            if (pkResultSet.next()) {
                int messageId = pkResultSet.getInt(1);
                message.setMessage_id(messageId);
                // return new Message(messageId, message.getPosted_by(),
                // message.getMessage_text(),
                // message.getTime_posted_epoch());
                // message.setMessage_id(messageId);
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int message_id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int posted_by) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, posted_by);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
    


    public Message insertMessage(Message message) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            // insert into table "messages" with values of the object passed as parameter
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            // get the generated key
            ResultSet getGeneratedKeys = ps.getGeneratedKeys();
            if (getGeneratedKeys.next()) {
                int messageId = getGeneratedKeys.getInt(1);
                message.setMessage_id(messageId);
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateMessage(int message_id, Message message) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE message SET message_text = ?, time_posted_epoch = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, message.getMessage_text());
            ps.setLong(2, message.getTime_posted_epoch());
            ps.setInt(3, message_id);

            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       
    }

    public boolean deleteMessage(int message_id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
