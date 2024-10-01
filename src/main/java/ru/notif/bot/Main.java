package ru.notif.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.notif.bot.logic.echo_RequestHanger;
import ru.notif.bot.platforms.console.Console_Bot;
import ru.notif.bot.platforms.console.Console_InputReader;
import ru.notif.bot.platforms.console.Console_OutputWriter;
import ru.notif.bot.platforms.telegrambot.TgBot;
import ru.notif.bot.platforms.telegrambot.TgBot;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        Console_InputReader inputReader = new Console_InputReader();
        Console_OutputWriter outputWriter = new Console_OutputWriter();
        echo_RequestHanger requestHandler = new echo_RequestHanger();

        // Создание и запуск консольного бота
        Console_Bot bot = new Console_Bot(inputReader, requestHandler, outputWriter);
        bot.startBot();

        TgBot bot1 = new TgBot();
        TelegramBotsApi telegrambot = new TelegramBotsApi(DefaultBotSession.class);
        telegrambot.registerBot(bot1);
    }
}