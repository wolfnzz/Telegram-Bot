package ru.notif.bot.platforms.telegrambot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TgBot extends TelegramLongPollingBot{

    final private String token;
    final private String name;

    public TgBot()
    {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/bot.properties");
            property.load(fis);

            token = property.getProperty("BOT_TOKEN");
            name = property.getProperty("BOT_NAME");

            System.out.println("token: " + token + ", name: " + name);


        }
        catch (IOException e) {
            System.err.println("ОШИБКА: токен и имя отсуствуют!");
            throw new IllegalStateException(e);
        }
    }

    private void sendWithoutURl(Message message) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText("нажать");
        button.setCallbackData("start0");

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<InlineKeyboardButton>();
        keyboardButtonsRow.add(button);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        keyboard.setKeyboard(rowList);

        try{
            execute(
                    SendMessage.builder()
                            .chatId(String.valueOf(message)) // не внедрили переменную в ф-цию
                            .parseMode("Markdown")
                            .text("hello world")
                            .replyMarkup(keyboard)
                            .build());
        }

        catch(TelegramApiException e){
            e.printStackTrace();
        }


    }
    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Message command = update.getMessage();
            long chatID = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    startAnswer(command);
                    break;
                default:
                    break;
            }

        }
        else if (update.hasCallbackQuery()){
            if (update.getCallbackQuery().getData().equals("start0")){
                try{
                    execute(
                            SendMessage.builder()
                                    .chatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()))
                                    .parseMode("Markdown")
                                    .text("hello world")
                                    .build());
                }

                catch(TelegramApiException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public void startAnswer(Message command){
        sendWithoutURl(command);

    }

}
