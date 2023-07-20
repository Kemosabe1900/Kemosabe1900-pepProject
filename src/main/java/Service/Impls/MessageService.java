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
        Message exists = messageDAO.getMessageById(message_id);

        if (exists != null) {
            messageDAO.updateMessage(message_id, message);
            Message updated = messageDAO.getMessageById(message_id);
            return updated;
        }else{
            return null;
        }
    }

    public boolean deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

   
}
