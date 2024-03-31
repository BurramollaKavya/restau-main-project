package org.group.project.controller.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.CustomerMapsMain;
import org.group.project.scenes.CustomerScenesMap;

public class CustomerMenuOrderNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button cartButton;

    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25, "home");

        homeButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.HOME));
        });

        ImageLoader.setUpGraphicButton(cartButton, 25, 25, "cart");

        cartButton.setOnMousePressed(e -> {
//            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.HOME));
        });

    }


}