package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO = new MessageDAO();
    private AccountDAO accountDAO = new AccountDAO();

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessage();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty() ||
                message.getMessage_text().length() > 255 || !accountExists(message.getPosted_by())) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public Message updateMessage(int id, String newMessageText) {
        if (newMessageText == null || newMessageText.isEmpty() || newMessageText.length() > 255) {
            return null;
        }
        return messageDAO.updateMessage(id, newMessageText);
    }

    public Message deleteMessage(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public List<Message> getMessagesByAccountId(int accountid) {
        return messageDAO.getMessagesByAccountId(accountid);
    }

    private boolean accountExists(int accountId) {
        return accountDAO.getAccountById(accountId) != null;
    }
}
