package org.group.project.controllers.customer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.DataFileStructure;
import org.group.project.classes.DataManager;
import org.group.project.classes.ImageLoader;
import org.group.project.classes.Notification;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CustomerNotificationViewController {

    @FXML
    private TableColumn<Notification, String> dateColumn;

    @FXML
    private TableColumn<Notification, String> timeColumn;

    @FXML
    private TableColumn<Notification, String> typeColumn;

    @FXML
    private TableColumn<Notification, String> bodyColumn;

    @FXML
    private TableColumn<Notification, Button> actionButtonColumn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView bgImage;

    private String userId;

    private List<String> notificationList;

    @FXML
    private TableView<Notification> notificationTable = new TableView<>();
    private ObservableList<Notification> data =
            FXCollections.observableArrayList();

    public void initialize() throws URISyntaxException, FileNotFoundException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/main-bg" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        // TODO get userId from the main.
        userId = "1";

        refreshNotificationList();

        dateColumn.setText("Date");
        dateColumn.setMinWidth(150);
        dateColumn.setStyle("-fx-alignment: CENTER;");
        dateColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getNotificationDateInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        timeColumn.setText("Time");
        timeColumn.setMinWidth(150);
        timeColumn.setStyle("-fx-alignment: CENTER;");
        timeColumn.setCellValueFactory(cellData -> {
            String formattedDate =
                    cellData.getValue().getNotificationTimeInFormat();
            return new SimpleObjectProperty<>(formattedDate);
        });

        typeColumn.setText("Type");
        typeColumn.setMinWidth(150);
        typeColumn.setStyle("-fx-alignment: CENTER;");
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("notificationType"));

        bodyColumn.setText("Message Body");
        bodyColumn.setMinWidth(150);
        bodyColumn.setStyle("-fx-alignment: CENTER;");
        bodyColumn.setCellValueFactory(
                new PropertyValueFactory<>("messageBody"));

        actionButtonColumn.setText("Action");
        actionButtonColumn.setMinWidth(60);
        actionButtonColumn.setStyle("-fx-alignment: CENTER;");
        actionButtonColumn.setCellValueFactory(cellData -> {
            Button viewButton = new Button();
            // TODO use tool tips for other buttons, where necessary
            viewButton.setTooltip(new Tooltip("View details"));
            ImageLoader.setUpGraphicButton(viewButton, 15, 15, "view-details");


            viewButton.setOnAction(e -> {


            });


            return new SimpleObjectProperty<>(viewButton);
        });

        notificationTable.setItems(data);

    }

    private void refreshNotificationList() throws FileNotFoundException {

        // TODO comment
        notificationTable.getItems().clear();
        data.clear();

        notificationList = DataManager.allDataFromFile("NOTIFICATION");

        for (String notification : notificationList) {
            List<String> notificationDetails = List.of(notification.split(","));

            // notification id
            int notifcationid = Integer.parseInt(notificationDetails.get(DataFileStructure.getIndexColOfUniqueId("NOTIFICATION")));

            // userId
            int userId = Integer.parseInt(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "userId")));

            // notificationDate
            List<String> notificationDateDetails = List.of(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "notificationDate")).split("-"));
            LocalDate notificationDate =
                    LocalDate.of(Integer.parseInt(notificationDateDetails.get(0)),
                            Integer.parseInt(notificationDateDetails.get(1)),
                            Integer.parseInt(notificationDateDetails.get(2)));

            // notificationTime
            List<String> notificationTimeDetails = List.of(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "notificationTime")).split("-"));

            LocalTime notificationTime =
                    LocalTime.of(Integer.parseInt(notificationTimeDetails.get(0)),
                            Integer.parseInt(notificationTimeDetails.get(1)));

            // notificationType
            String notificationType = notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "notificationType"));

            // readStatus
            boolean readStatus = Boolean.parseBoolean(notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "readStatus")));

            // messageBody
            String messageBody = notificationDetails.get(DataFileStructure.getIndexByColName("NOTIFICATION", "messageBody"));

            data.add(new Notification(
                    notifcationid,
                    userId,
                    notificationDate,
                    notificationTime,
                    notificationType,
                    messageBody,
                    readStatus
            ));
        }
    }
}
