package Service.Impls;

import DAO.Interfaces.MessageDAOInterface;
import Model.Message;
import java.util.List;

public class MessageService {
    private final MessageDAOInterface messageDAO;

    public MessageService(MessageDAOInterface messageDAO) {
        this.messageDAO = messageDAO;
    }

    public void createMessage(Message message) {
        messageDAO.createMessage(message);
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getMessagesByAccountId(int posted_by) {
        return messageDAO.getMessagesByAccountId(posted_by);
    }

    public void updateMessage(Message message, int message_id) {
        messageDAO.updateMessage(message, message_id);
    }

    public boolean deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }
}
