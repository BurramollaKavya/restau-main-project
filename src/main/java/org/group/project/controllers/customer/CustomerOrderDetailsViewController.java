package org.group.project.controllers.customer;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.Customer;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Order;
import org.group.project.scenes.customer.stackViews.MenuController;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;
import org.group.project.test.generators.CustomerGenerator;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CustomerOrderDetailsViewController {

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView bgImage;

    private List<FoodDrink> orderList;

    private List<Order> newOrder;

    @FXML
    private TableColumn<FoodDrink, String> noColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemNameColumn;

    @FXML
    private TableColumn<FoodDrink, String> itemTypeColumn;

    @FXML
    private TableColumn<FoodDrink, String> quantityColumn;

    @FXML
    private TableColumn<FoodDrink, Button> actionButtonColumn;

    @FXML
    private TableColumn<FoodDrink, Button> actionButtonColumn1;

    @FXML
    private TableColumn<FoodDrink, Button> actionButtonColumn2;

    @FXML
    private TableView<FoodDrink> orderDetailsTable =
            new TableView<>();

    private ObservableList<FoodDrink> data =
            FXCollections.observableArrayList();

    public CustomerOrderDetailsViewController(List<FoodDrink> orderList,
                                              List<Order> newOrder) {
        this.orderList = orderList;
        this.newOrder = newOrder;
    }

    public void initialize() throws URISyntaxException {

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

        choiceBox.getItems().add("Delivery Order");
        choiceBox.getItems().add("Takeaway Order");

        choiceBox.setValue("Delivery Order");

        data.addAll(orderList);

        noColumn.setText("No.");
        noColumn.setMinWidth(40);
        noColumn.setStyle("-fx-alignment: CENTER;");
        noColumn.setCellValueFactory(cellData -> {
            int index =
                    cellData.getTableView().getItems().indexOf(cellData.getValue());
            index++;
            return new SimpleObjectProperty<>(index).asString();
        });

        itemNameColumn.setText("Item");
        itemNameColumn.setMinWidth(150);
        itemNameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        itemNameColumn.setCellValueFactory(cellData -> {
            String itemName = cellData.getValue().getItemNameForDisplay();
            return new SimpleObjectProperty<>(itemName);
        });

        itemTypeColumn.setText("Type");
        itemTypeColumn.setMinWidth(75);
        itemTypeColumn.setStyle("-fx-alignment: CENTER;");
        itemTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("itemType"));

        quantityColumn.setText("Quantity");
        quantityColumn.setMinWidth(75);
        quantityColumn.setStyle("-fx-alignment: CENTER;");
        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        actionButtonColumn.setText("Action");

        orderDetailsTable.setItems(data);

        confirmButton.setOnMousePressed(e -> {
            newOrder.clear();
            newOrder.add(createNewOrder());
            OrderDetailsController.presenter.goToOrderConfirmation();
        });

        // TODO cancel button returns to menu with orderlist erased?
        cancelButton.setOnMousePressed(e -> {
            OrderDetailsController.presenter.returnToMenu();
            MenuController.orderList.clear();
        });

    }

    public Order createNewOrder() {
        // TODO get user from main homepage, temp using dummy user
        Customer newCustomer = CustomerGenerator.createCustomer1();

        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();

        // TODO choicebox must not be empty
        String orderType = choiceBox.getValue();
        if (orderType.equalsIgnoreCase("delivery order")) {
            orderType = "delivery";
        } else {
            orderType = "takeaway";
        }

        // TODO constant variables, where to final static
        String orderStatus = "";
        if (orderType.equalsIgnoreCase("delivery order")) {
            orderStatus = "pending-approval";
        } else {
            orderStatus = "pending";
        }

        Order orderDetails = new Order(
                newCustomer,
                dateNow,
                timeNow,
                orderType,
                orderStatus
        );

        for (FoodDrink item : orderList) {
            orderDetails.addItemToOrderList(item);
        }

        return orderDetails;
    }
}