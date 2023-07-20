package Service.Impls;

import Service.Interfaces.MessageServiceInterface;
import DAO.Impls.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService implements MessageServiceInterface {
    MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text().isEmpty()) {
            return null;
        }
        if (message.getMessage_text().length() > 254) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getMessagesByAccountId(int posted_by) {
        return messageDAO.getMessagesByAccountId(posted_by);
    }

    public Message updateMessage(int message_id, Message message) {
        if (message.getMessage_text().isEmpty() || message.getMessage_text().length() > 254) {
            return null;
        }

        Message exists = messageDAO.getMessageById(message_id);

        if (exists != null) {
            exists.setMessage_text(message.getMessage_text());
            messageDAO.updateMessage(message_id, exists);
            return exists;
        }else{
            return null;
        }
    }

    public boolean deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

}
