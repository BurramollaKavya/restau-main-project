package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.group.project.Main;

import java.net.URISyntaxException;

public class ChefHomepageController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label mainTitle;

    public void initialize() throws URISyntaxException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/chef-main" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false,
                false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

    }

    public void welcomeChef() {
        mainTitle.setText(
                "Welcome, "
                        + Main.getCurrentUser().getFirstNameForDisplay()
                        + "!"
        );
    }
}
