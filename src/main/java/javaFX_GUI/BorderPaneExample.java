package javaFX_GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author xiaosongChen
 * @create 2022-11-07 16:28
 * @description :
 */
public class BorderPaneExample extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();

        HBox top = new HBox();
        top.setMinHeight(60);

        Text text = new Text("Welcome 进销存");
        text.setFont(Font.font("宋体", FontWeight.BOLD,20));
        top.setAlignment(Pos.CENTER);

        top.getChildren().add(text);
        borderPane.setTop(top);

        VBox left = new VBox(10);
        left.setPadding(new Insets(10));
        left.setMinWidth(100);
        Button system = new Button("系统设置");
        left.getChildren().addAll(system,new Button("商品管理"),new Button("关于我们"),new Button("联系我们"));

        borderPane.setLeft(left);

        GridPane gridPane = new GridPane();
        gridPane.setMinWidth(400);
        gridPane.setMinHeight(240);

        borderPane.setCenter(gridPane);
        //borderPane.setRight(new Button("RIGHT"));

        system.setOnAction(e->{
        });

        HBox buttom = new HBox(10);
        buttom.setPadding(new Insets(10));
        buttom.setAlignment(Pos.CENTER);

        buttom.getChildren().addAll(new Button("系统设置"),new Button("商品管理"),new Button("关于我们"),new Button("联系我们"));

        borderPane.setBottom(buttom);

        Scene scene = new Scene(borderPane,600,400);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
