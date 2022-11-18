package com.example.enjoyBot.service;

import com.example.enjoyBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "Java":
                    sendMessage(chatId, "Вот ссылки на некоторые ресурсы на тему Java: " + "\n" + "https://habr.com/ru/post/237043/");
                    break;
                case "C#":
                    sendMessage(chatId, "C# принял ислам(((");
                    break;
                default: sendMessage(chatId, "Sorry, command was not recognized...");
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Hi, " + name + ", nice to meet you!";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textTotSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textTotSend);

        try {
            execute(message);
        }
        catch (TelegramApiException exception) {
            throw new RuntimeException(exception);
        }
    }
}
