package GUI;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author xiaosongChen
 * @create 2022-11-03 9:39
 * @description :鼠标样式、场景
 */
public class MyfxScene extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox(new Label("A JavaFX Lable"));
        Label label = new Label("这是Lable");
//        Scene scene = new Scene(vBox);
        Scene scene = new Scene(label);
//        scene.setCursor(Cursor.OPEN_HAND);//鼠标光标在场景中变成伸手的样子
//        scene.setCursor(Cursor.CLOSED_HAND);//鼠标光标在场景中变成缩手的样子
//        scene.setCursor(Cursor.CROSSHAIR);//鼠标光标在场景中变成十字光标
//        scene.setCursor(Cursor.DEFAULT);//默认普通的光标
//        scene.setCursor(Cursor.HAND);//变成食指手样
//        scene.setCursor(Cursor.WAIT);//变成转圈圈的等待样式
//        scene.setCursor(Cursor.H_RESIZE);//变成更改宽度的样式
//        scene.setCursor(Cursor.V_RESIZE);//变成更改高度的样式
//        scene.setCursor(Cursor.MOVE);//变成移动的十字箭头样式
        scene.setCursor(Cursor.TEXT);//变成输入文本的样式
        Stage stage = new Stage();
        stage.setWidth(600);
        stage.setHeight(300);
        stage.setScene(scene);
        stage.show();
    }
}
