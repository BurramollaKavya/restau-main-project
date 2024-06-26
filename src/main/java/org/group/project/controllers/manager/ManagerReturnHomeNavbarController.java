package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.manager.ManagerMapsMain;
import org.group.project.scenes.manager.ManagerScenesMap;

import java.net.URISyntaxException;

public class ManagerReturnHomeNavbarController {

    @FXML
    private Button homeButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton,
                25, 25,
                "undo");

        homeButton.setOnMousePressed(e -> {
            ManagerScenesMap.getManagerStage().setScene(
                    ManagerScenesMap.getScenes()
                            .get(ManagerMapsMain.HOME));
        });

    }


}
