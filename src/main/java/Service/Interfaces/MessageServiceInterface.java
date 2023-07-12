package Service.Interfaces;

import Model.Message;

import java.util.List;

public interface MessageServiceInterface {
    void createMessage(Message message);

    Message getMessageById(int message_id);

    List<Message> getMessagesByAccountId(int posted_by);

    void updateMessage(Message message, int message_id);

    boolean deleteMessage(int message_id);
}