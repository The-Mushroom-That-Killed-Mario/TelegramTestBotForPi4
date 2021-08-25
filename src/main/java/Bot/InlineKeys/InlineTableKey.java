package Bot.InlineKeys;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineTableKey {
    public String[][] arrButtons;

    public String tableName;
    public InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


    public  InlineTableKey(){
    }

    public InlineTableKey(String tableName, String[][] arrButtons) {
        this.arrButtons = arrButtons;
        this.tableName = tableName;


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (String[] rowButtons :
                arrButtons) {

            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            for (String button :
                    rowButtons) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

                if (button.contains("/")){

                    inlineKeyboardButton.setCallbackData((button.substring(button.indexOf("/")+1)).trim());//данные кнопки
                    inlineKeyboardButton.setText(button.substring(0,button.indexOf("/")));//имя кнопки
                } else {
                    inlineKeyboardButton.setCallbackData(button);//данные кнопки
                    inlineKeyboardButton.setText(button);//имя кнопки
                }



                keyboardButtonsRow.add(inlineKeyboardButton);

            }
            rowList.add(keyboardButtonsRow);
        }

        this.inlineKeyboardMarkup.setKeyboard(rowList);
    }


    public InlineKeyboardMarkup getInlineKeyboardMarkup(){

        return  inlineKeyboardMarkup;
    }

    public String getTableName() {
        return tableName;
    }
}
