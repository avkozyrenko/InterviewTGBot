package ru.kozyrenko.interview.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kozyrenko.interview.bot.config.BotConfig;

@Service
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final QuestionService questionService;
    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    public void onUpdateReceived(Update update) {
        String currency = "";
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    currency = "Кукуddcdc";
//                    try {
//                        currency = "Куку";
//
//                    } catch (IOException e) {
//                        sendMessage(chatId, "We have not found such a currency." + "\n" +
//                                "Enter the currency whose official exchange rate" + "\n" +
//                                "you want to know in relation to BYN." + "\n" +
//                                "For example: USD");
//                    } catch (ParseException e) {
//                        throw new RuntimeException("Unable to parse date");
//                    }
                    sendMessage(chatId, currency);
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Hi, " + name + ", nice to meet you!" + "\n" +
                "Enter the currency whose official exchange rate" + "\n" +
                "you want to know in relation to BYN." + "\n" +
                "For example: USD";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }

}
