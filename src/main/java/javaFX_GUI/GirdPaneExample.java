package javaFX_GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author xiaosongChen
 * @create 2022-11-07 21:20
 * @description :BorderPane\GridPane\ FileChooser文件选择器
 */
public class GirdPaneExample extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 380, 300, Color.WHITE);

        GridPane gridPane = new GridPane();
        //表格实线
        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(5));
        //水平间距
        gridPane.setHgap(5);
        //垂直间距
        gridPane.setVgap(30);

        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50,150,300);
        column2.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(column1,column2);

        Label fNameLable = new Label("First Name");
        Label LNameLable = new Label("Last Name");
        TextField FNameField = new TextField();
        TextField LNameField = new TextField();
        Button button = new Button("Save");
        Button openbutton = new Button("Open");

        //First name lable
        GridPane.setHalignment(fNameLable, HPos.RIGHT);
        gridPane.add(fNameLable,0,0);

        //Last name lable
        GridPane.setHalignment(LNameLable,HPos.RIGHT);
        gridPane.add(LNameLable,0,1);

        //First name field
        GridPane.setHalignment(FNameField,HPos.LEFT);
        gridPane.add(FNameField,1,0);

        //Last name field
        GridPane.setHalignment(LNameField,HPos.LEFT);
        gridPane.add(LNameField,1,1);

        //Save button
        GridPane.setHalignment(button,HPos.RIGHT);
        gridPane.add(button,1,2);

        //Open button
        GridPane.setHalignment(openbutton,HPos.LEFT);
        gridPane.add(openbutton,0,2);
        //创建文件选择器
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("HTML Files", "*.htm"),
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //保存文件窗口
                fileChooser.setTitle("Save Resource File");
                File file = fileChooser.showSaveDialog(primaryStage);

            }
        });
        openbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //打开文件的窗口
                fileChooser.setTitle("Open Resource File");
                File showOpenDialog = fileChooser.showOpenDialog(primaryStage);
            }
        });


        borderPane.setCenter(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
