package Bot;

import Bot.InlineKeys.InlineTableKey;
import Bot.InlineKeys.Tables.AllTables;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Bot extends TelegramLongPollingBot {
    static Integer lastMessageId = 0;
    //static Update lastUpdate =null;
    static CallbackQuery lastCallBackQueru = null;
    AllTables allTables = new AllTables();

    @Override
    public void onUpdateReceived(Update update) {

        System.out.println(update);


//        System.out.println("MessageText: " + update.getMessage().getText()); //перестаёт работать инлайн клава

//        System.out.println("hasMessage: " + update.hasMessage());
//        System.out.println("hasCallbackQuery: " + update.hasCallbackQuery());
//        System.out.println("hasInlineQuery: " + update.hasInlineQuery());



        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")){
                    DeleteMessage deleteMessage = new DeleteMessage();
                    deleteMessage.setMessageId(update.getMessage().getMessageId());
                    deleteMessage.setChatId(update.getMessage().getChatId().toString());


                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                    sendMessage.setText("Здесь можно вписать небольшенькую инструкцию по использованию");
                    setButtons(sendMessage);
                    try {
                        execute(deleteMessage);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }

                if (update.getMessage().getText().equals("nu")) {
                    try {

                        execute(sendInlineKeyBroadMessage(update.getMessage().getChatId()));

                        DeleteMessage deleteMessage = new DeleteMessage();
                        deleteMessage.setMessageId(update.getMessage().getMessageId());
                        deleteMessage.setChatId(update.getMessage().getChatId().toString());
                        execute(deleteMessage);

                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (update.hasCallbackQuery()) {
            System.out.println("updateCallBackQuery: " + update.getCallbackQuery());
//          System.out.println(update.getCallbackQuery().getMessage().getMessageId()); //id сообщения с клавиатурой
//            lastMessageId++;

//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setText(update.getCallbackQuery().getData());
//            sendMessage.setChatId((update.getCallbackQuery().getMessage().getChatId()).toString());

            switch (update.getCallbackQuery().getData()) {
                case "Закрыть меню":
                    DeleteMessage deleteMessage = new DeleteMessage();
                    deleteMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    deleteMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    try {
                        execute(deleteMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    try {
                        execute(editInlineMessageText(update.getCallbackQuery().getMessage().getMessageId(), update.getCallbackQuery().getMessage().getChatId().toString(), update.getCallbackQuery().getData()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public SendMessage sendInlineKeyBroadMessage(Long chatId) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Супер Бот приветствует тебя");

        for (InlineTableKey inlineTableKey : allTables.getAlltables()) {
            if (inlineTableKey.getTableName().equals("Запустить бота")) {
                sendMessage.setReplyMarkup(inlineTableKey.getInlineKeyboardMarkup());
            }
        }



        return sendMessage;

    }


    public EditMessageText editInlineMessageText(int MessageId, String chatId, String data) {
        EditMessageText editMessageText = new EditMessageText();

        editMessageText.setMessageId(MessageId);
        editMessageText.setChatId(chatId);
        editMessageText.setText(data);


        for (InlineTableKey inlineTableKey : allTables.getAlltables()) {
            InlineTableKey itk;
            itk = inlineTableKey;
            if (inlineTableKey.getTableName().equals(data)) {
                editMessageText.setReplyMarkup(itk.getInlineKeyboardMarkup());
            }
        }

        return editMessageText;
    }


    //------<Экранная клавиатура бота
    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("nu"));

//        // Вторая строчка клавиатуры
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        // Добавляем кнопки во вторую строчку клавиатуры
//        keyboardSecondRow.add(new KeyboardButton("Или сюда"));

//        KeyboardRow keyboardthreeRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
//        keyboardSecondRow.add(new KeyboardButton("gamno"));
        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
//        keyboard.add(keyboardthreeRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    @Override
    public String getBotUsername() {
        return "TestingApiBotTelegram_bot";
    }

    @Override
    public String getBotToken() {
        return "1995947079:AAH1KzBP1LTlVF7vrjmqA-US9kOigoSOOLw";
    }
}
