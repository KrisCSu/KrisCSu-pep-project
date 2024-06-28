package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);

        return app;
    }

    private void registerHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account registeredAccount = accountService.register(account);

        if (registeredAccount != null) {
            context.json(registeredAccount).status(200);
        } else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account authAccount = accountService.login(account.getUsername(), account.getPassword());

        if (authAccount != null) {
            context.json(authAccount).status(200);
        } else {
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message);

        if (createdMessage != null) {
            context.json(createdMessage).status(200);
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages()).status(200);
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            context.json(message).status(200);
        } else {
            context.status(200);
        }
    }

    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessage(messageId);

        if (message != null) {
            context.json(message).status(200);
        } else {
            context.status(200);
        }
    }

    private void updateMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        String newMessageText = context.bodyAsClass(Message.class).getMessage_text();
        Message updatedMessage = messageService.updateMessage(messageId, newMessageText);

        if (updatedMessage != null) {
            context.json(updatedMessage).status(200);
        } else {
            context.status(400);
        }
    }

    private void getMessagesByAccountIdHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getMessagesByAccountId(accountId)).status(200);
    }

}