package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;
import Service.Impls.AccountService;
import Service.Impls.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::createAccountHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMessageIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByMessageIdHandler);

        return app;
    }

    private void createAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.createAccount(account);

        if (createdAccount != null) {
            ctx.json(mapper.writeValueAsString(createdAccount));

        } else {
            ctx.status(400); // Bad Request
        }

    }

    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedIn = accountService.loginAccount(account.getUsername(), account.getPassword());
        if (loggedIn != null) {
            ctx.json(mapper.writeValueAsString(loggedIn));
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        // if (message.getMessage_text().isEmpty()) {
        // ctx.status(400);
        // return;
        // }
        // if (message.getMessage_text().length() > 254) {
        // ctx.status(400);
        // return;
        // }

        Account poster = accountService.getAccountById(message.getPosted_by());
        if (poster == null) {
            ctx.status(400);
            return;
        }
        Message newMes = messageService.createMessage(message);
        if (newMes == null) {
            ctx.status(400);
            return;
        } else {
            ctx.json(newMes).status(200);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByMessageIdHandler(Context ctx) {

        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    public void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        int messageId = Integer.parseInt(ctx.pathParam("message_id"));

        // if (message.getMessage_text().isEmpty() || message.getMessage_text().length()
        // > 254) {
        // ctx.status(400);
        // return;
        // }

        // Message exists = messageService.getMessageById(messageId);
        // if (exists == null) {
        // ctx.status(400);
        // return;
        // }
        // exists.setMessage_text(message.getMessage_text());

        Message updatedMessage = messageService.updateMessage(messageId, message);

        if (updatedMessage != null) {
            ctx.json(updatedMessage).status(200); // Success
        } else {
            ctx.status(400);
        }

    }

    private void getMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);

        if (messages != null) {
            ctx.json(messages);
        } else {
            ctx.status(200);
        }
    }

    private void deleteMessageByMessageIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message toDelete = messageService.getMessageById(messageId);

        if (toDelete != null) {
            Boolean isDeleted = messageService.deleteMessage(messageId);

            if (isDeleted) {
                ctx.json(toDelete);
            } else {
                ctx.status(200);
            }
        } else {
            ctx.status(200);
        }
    }
}
