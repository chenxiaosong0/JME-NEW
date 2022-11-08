package javaFX_GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author xiaosongChen
 * @create 2022-11-07 10:29
 * @description :
 */
public class HyperlinkExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX App");
        Hyperlink hyperlink = new Hyperlink("Hyperlink ...Click me...");
        VBox vBox = new VBox(hyperlink);
        Scene scene = new Scene(vBox,600,400);
        hyperlink.setOnAction(event -> {
            System.out.println("The Hyperlink was clicked!");
            Label label = new Label("The Hyperlink !");
            HBox hBox = new HBox(label);
            Scene scene1 = new Scene(hBox,300,200);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(scene1);
            stage.show();
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
