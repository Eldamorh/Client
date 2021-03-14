package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private Button insert;

    @FXML
    private Button delete;

    @FXML
    private Button select;

    @FXML
    private Button update;

    @FXML
    private AnchorPane tablePane;

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> yearColumn;

    @FXML
    private TableColumn<Book, String> priceColumn;

    @FXML
    private TableColumn<Book, String> amountColumn;

    @FXML
    private AnchorPane insertPane;

    @FXML
    private TextArea nameOfBookInsert;

    @FXML
    private TextArea authorInsert;

    @FXML
    private TextArea yearInsert;

    @FXML
    private TextArea priceInsert;

    @FXML
    private TextArea amountInsert;

    @FXML
    private Button addBookBtn;

    @FXML
    private AnchorPane deletePane;

    @FXML
    private ComboBox<?> deleteBox;

    @FXML
    private String nameOfBookDelete;

    @FXML
    private String authorDelete;

    @FXML
    private String yearOfWritingDelete;

    @FXML
    private String priceDelete;

    @FXML
    private String amountDelete;

    @FXML
    private TextArea deleteText;

    @FXML
    private Button deleteAllBtn;

    @FXML
    private Button deleteBookBtn;

    @FXML
    private AnchorPane updatePane;

    @FXML
    private ComboBox<?> UpdateBox;

    @FXML
    private String nameOfBookUpdate;

    @FXML
    private String authorUpdate;

    @FXML
    private String yearOfWritingUpdate;

    @FXML
    private String priceUpdate;

    @FXML
    private String amountUpdate;

    @FXML
    private TextArea updateText;

    @FXML
    private Button updateBookBtn;

    @FXML
    private AnchorPane SelectPane;

    @FXML
    private Button selectAllBtn;

    @FXML
    private ComboBox<?> selectBox;

    @FXML
    private String nameOfBookSelect;

    @FXML
    private String authorSelect;

    @FXML
    private String yearOfWritingSelect;

    @FXML
    private String priceSelect;

    @FXML
    private String amountSelect;

    @FXML
    private TextArea selectText;

    @FXML
    private Button selectBookBtn;

    @FXML
    void initialize(){
        CloseableHttpClient httpclient = HttpClients.createDefault();

        insert.setOnAction(event -> {
             panesOff();
             insertPane.setVisible(true);
             addBookBtn.setOnAction(event2 ->{
                 StringBuilder stringBuilder = new StringBuilder("http://localhost:8080/Shop/addBook?nameOfBook=");
                 stringBuilder.append(nameOfBookInsert.getText());
                 stringBuilder.append("&author=");
                 stringBuilder.append(authorInsert.getText());
                 stringBuilder.append("&yearOfWriting=");
                 stringBuilder.append(yearInsert.getText());
                 stringBuilder.append("&price=");
                 stringBuilder.append(priceInsert.getText());
                 stringBuilder.append("&amount=");
                 stringBuilder.append(amountInsert.getText());
                 HttpGet httpget = new HttpGet(stringBuilder.toString());

                 try {
                     httpclient.execute(httpget);
                 } catch (
                         IOException e) {
                     e.printStackTrace();
                 }

             });
         });

         delete.setOnAction(event -> {
             panesOff();
             deletePane.setVisible(true);
             deleteAllBtn.setOnAction(event1 -> {
                 HttpGet httpget = new HttpGet("http://localhost:8080/Shop/deleteBook?nameOfColumn=all");

                 try {
                     httpclient.execute(httpget);
                 } catch (
                         IOException e) {
                     e.printStackTrace();
                 }
             });
             deleteBookBtn.setOnAction(event1 -> {
                 StringBuilder stringBuilder = new StringBuilder("http://localhost:8080/Shop/deleteBook?nameOfColumn=");
                 stringBuilder.append(comboChecker(deleteBox.getValue().toString()));
                 stringBuilder.append("&value=");
                 stringBuilder.append(deleteText.getText());
                 System.out.println(stringBuilder);
                 HttpGet httpget = new HttpGet(stringBuilder.toString());

                 try {
                     httpclient.execute(httpget);
                 } catch (
                         IOException e) {
                     e.printStackTrace();
                 }
             });

         });


         update.setOnAction(event -> {
             panesOff();
             updatePane.setVisible(true);
             updateBookBtn.setOnAction(event1 -> {
                 StringBuilder stringBuilder = new StringBuilder("http://localhost:8080/Shop/updateBook?nameOfColumn=");
                 stringBuilder.append(comboChecker(UpdateBox.getValue().toString()));
                 stringBuilder.append("&value=");
                 stringBuilder.append(updateText.getText());
                 HttpGet httpget = new HttpGet(stringBuilder.toString());

                 try {
                     httpclient.execute(httpget);
                 } catch (
                         IOException e) {
                     e.printStackTrace();
                 }
             });
         });

         select.setOnAction(event -> {
             panesOff();
             SelectPane.setVisible(true);
             selectAllBtn.setOnAction(event1 -> {
                 table.getItems().clear();
                 try {
                     bookAdder("http://localhost:8080/Shop/selectBook?nameOfColumn=all");
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

                 nameColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("name"));
                 authorColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
                 yearColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("year"));
                 priceColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("price"));
                 amountColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("amount"));


                 table.setItems(books);
                 panesOff();
                 tablePane.setVisible(true);

             });



             selectBookBtn.setOnAction(event1 -> {
                 table.getItems().clear();
                 StringBuilder stringBuilder = new StringBuilder("http://localhost:8080/Shop/selectBook?nameOfColumn=");
                 stringBuilder.append(comboChecker(selectBox.getValue().toString()));
                 stringBuilder.append("&value=");
                 stringBuilder.append(selectText.getText());
                 try {
                     bookAdder(stringBuilder.toString());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 nameColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("name"));
                 authorColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
                 yearColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("year"));
                 priceColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("price"));
                 amountColumn.setCellValueFactory(new PropertyValueFactory<Book,String>("amount"));


                 table.setItems(books);
                 panesOff();
                 tablePane.setVisible(true);
             });
         });
    }




    public void bookAdder(String url) throws IOException {
        String responseBody = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {



            final HttpGet httpGet = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println(responseBody);
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(responseBody);
        while (matcher.find()) {
            Book book = new Book();
            book.name = responseBody.substring(matcher.start(),matcher.end());
            matcher.find();
            book.author = responseBody.substring(matcher.start(),matcher.end());
            matcher.find();
            book.year = responseBody.substring(matcher.start(),matcher.end());
            matcher.find();
            book.price = responseBody.substring(matcher.start(),matcher.end());
            matcher.find();
            book.amount = responseBody.substring(matcher.start(),matcher.end());
            books.add(book);
        }


    }



    String comboChecker(String s){
        if(s.equals("Названию")){
            return "nameOfBook";
        }
        if(s.equals("Автору")){
            return "author";
        }
        if(s.equals("Году написания")){
            return "yearOfWriting";
        }
        if(s.equals("Цене")){
            return "price";
        }
        return "amount";
    }

    void panesOff(){
        tablePane.setVisible(false);
        insertPane.setVisible(false);
        deletePane.setVisible(false);
        updatePane.setVisible(false);
        SelectPane.setVisible(false);
    }

}