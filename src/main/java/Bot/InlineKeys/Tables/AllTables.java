package Bot.InlineKeys.Tables;

import Bot.InlineKeys.InlineTableKey;

import java.util.ArrayList;
import java.util.List;

public class AllTables {
    List<InlineTableKey> alltables = new ArrayList<>();
    public AllTables() {

//        this.alltables.add(new InlineTableKey("Открыть меню",
//                new String[][]{
//                        {"Запустить бота"},
//                        {"Состояние входов"},
//                        {"Управление выходами"}}));

        this.alltables.add(new InlineTableKey("Запустить бота",
                new String[][]{{"Открыть меню"}, {"Закрыть меню"}}));


        this.alltables.add(new InlineTableKey("Открыть меню",
                new String[][]{
                        {"Состояние входов"},
                        {"Управление выходами"},
                        {"Назад/Запустить бота"}}));
        this.alltables.add(new InlineTableKey("Состояние входов",
                new String[][]{
                        {"Получить состояние"},
                        {"Назад/Открыть меню"}}));

        this.alltables.add(new InlineTableKey("Управление выходами",
                new String[][]{
                        {"вых1", "вых5"},
                        {"вых2", "вых6"},
                        {"вых3", "вых7"},
                        {"вых4", "вых8"},
                        {"Назад/Открыть меню"}
                }));

    }

    public List<InlineTableKey> getAlltables() {
        return this.alltables;
    }

    //надо метод обновления значений полей таблиц
    public void onOut(String name, String out){

    }

}
