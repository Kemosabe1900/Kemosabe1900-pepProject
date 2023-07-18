package DAO.Interfaces;

import Model.Message;

import java.util.List;

public interface MessageDAOInterface {

    void createMessage(Message message);

    List<Message> getAllMessages();

    Message getMessageById(int message_id);

    List<Message> getMessagesByAccountId(int posted_by);

    void updateMessage(int message_id, Message message);

    boolean deleteMessage(int message_id);
}