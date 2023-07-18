package Service.Interfaces;

import Model.Message;

import java.util.List;

public interface MessageServiceInterface {
    void createMessage(Message message);

    Message getMessageById(int message_id);

    List<Message> getMessagesByAccountId(int posted_by);

    Message updateMessage( int message_id, Message message);

    boolean deleteMessage(int message_id);
}