package ru.pimalex78;

import org.xml.sax.SAXException;
import ru.pimalex78.entities.Box;
import ru.pimalex78.entities.Item;
import ru.pimalex78.parser.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * В качестве БД используем H2.
 * Чтоб посмотреть, как заполняются таблицы, можно открыть Консоль H2.
 * Установить БД с http://www.h2database.com/.
 * Открыть браузер файлов, перейти к  h2/bin и дважды щелкнуть по h2.bat.
 * Появится окно консоли. Если есть проблема, вы увидим сообщение об ошибке в этом
 * окне. Откроется окно браузера и укажет на страницу входа (URL:) http://localhost:8082.
 * Запускаем консоль с такими настройками:
 * Класс драйвера: org.h2.Driver
 * JDBC URL: jdbc:h2:~/test
 * Имя пользователя: root
 * Пароль:        (отсутствует)
 */

public class MainApp {

    /**
     * Запрос на создание двух таблиц.
     */
    private static final String CREATE_BOX_QUERY =
            "CREATE TABLE IF NOT EXISTS BOX (id INTEGER PRIMARY KEY, contained_in INTEGER)";
    private static final String CREATE_ITEM_QUERY =
            "CREATE TABLE IF NOT EXISTS ITEM (id INTEGER PRIMARY KEY, contained_in INTEGER REFERENCES box(id), color VARCHAR(100))";

    private MainApp() {
    }

    /**
     * Entry point.
     *
     * @param args Command line args. Not used.
     */
    public static void main(final String[] args) {

        XMLParser xmlParser = new XMLParser();

        //String fileName = "C:/projects/box-app/src/main/resources/Storage.xml";

        //Получим путь к нашему xml файлу Storage.xml
        ClassLoader classLoader = MainApp.class.getClassLoader();
        String fileName = classLoader.getResource("Storage.xml").getPath();

        try {
            xmlParser.parseXML(fileName);
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "root", "")) {


            try (Statement dataQuery = connection.createStatement()) {
                dataQuery.execute(CREATE_BOX_QUERY);
                dataQuery.execute(CREATE_ITEM_QUERY);

                List<Box> boxes = xmlParser.getBoxes();
                for (Box box : boxes) {
                    dataQuery.executeUpdate("INSERT INTO BOX (id, contained_in) VALUES (" + box.getId() + ", " + box.getParentId() + ");");
                }
                List<Item> items = xmlParser.getItems();
                for (Item item : items) {
                    dataQuery.executeUpdate("INSERT INTO ITEM (id, contained_in, color) VALUES (" + item.getId() + ","
                            + item.getParentId() + "," + "'" + item.getColor() + "'" + ");");
                }
            }

            try (PreparedStatement query =
                         connection.prepareStatement("SELECT * FROM BOX")) {
                ResultSet rs = query.executeQuery();
                System.out.println("=== Данные таблицы BOX ===");
                while (rs.next()) {
                    System.out.println(String.format("%d, %d",
                            rs.getInt(1),
                            rs.getInt(2))
                    );
                }
                rs.close();
            }
            try (PreparedStatement query =
                         connection.prepareStatement("SELECT * FROM ITEM")) {
                ResultSet rs = query.executeQuery();
                System.out.println("=== Данные таблицы ITEM ===");
                while (rs.next()) {
                    System.out.println(String.format("%d, %d, %s",
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3))
                    );
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Database connection failure: "
                    + ex.getMessage());
        }
    }
}
