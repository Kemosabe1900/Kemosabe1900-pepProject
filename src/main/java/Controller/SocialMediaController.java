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
        app.post("/message", this::createMessageHandler);
        // app.get("/messages", this::getAllMessagesHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    // context.json("sample text");
    // }

    private void createAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        Account existingAccount = accountService.getAccountByUsername(account.getUsername());
        if (existingAccount != null) { 
            ctx.status(400);
        }else if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
                ctx.status(400); // Invalid username or password
        } else {
            Account createdAccount = accountService.createAccount(account);
            if (createdAccount != null) {
                ctx.json(createdAccount);
            } else {
                ctx.status(400); // Error creating account
            }
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
        if (message.getMessage_text().isEmpty() && message.getMessage_text().length() > 255) {
            ctx.status(400);
            return;
        }  
        Account poster = accountService.getAccountById(message.getPosted_by());
        if(poster == null){
            ctx.status(400);
            return;
        }
    }

    // private void getAllMessagesHandler(Context ctx) {
    //     List<Message> messages = messageService.getAllMessages();
    // }
}