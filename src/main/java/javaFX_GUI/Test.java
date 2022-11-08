package javaFX_GUI;

import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * @author xiaosongChen
 * @create 2022-11-07 15:35
 * @description :
 */
public class Test extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("");
        Group root = new Group();
        Scene scene = new Scene(root, Color.WHITE);

        HBox hbox = new HBox(10);//在HBox的控件之间设置空格(空间)
        //填充
        hbox.setPadding(new Insets(150, 12, 5, 20));

        Button button1 = new Button("Add");
        Button button2 = new Button("Remove");
        HBox.setHgrow(button1, Priority.ALWAYS);
        HBox.setHgrow(button2, Priority.ALWAYS);
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        hbox.getChildren().addAll(button1, button2);

//        hbox.setPrefWidth(400);// 设置HBox宽度
        root.getChildren().add(hbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//        Group root = new Group();
//        Scene scene = new Scene(root, 300, 250);
//        //HBox内部控件之间的间隔
//        HBox hbox = new HBox(15);
//        //new Insets(1):距离边框上下左右距离
//        hbox.setPadding(new Insets(1));
//        Rectangle r1 = new Rectangle(10, 10);
//        Rectangle r2 = new Rectangle(20, 100);
//        Rectangle r3 = new Rectangle(50, 20);
//        Rectangle r4 = new Rectangle(20, 50);
//        //构造一个具有四个不同偏移量的新 Insets 实例。
//        // @param top 上偏移
//        // @param right 右偏移
//        // @param bottom 下偏移
//        // @param left 左偏移
//        HBox.setMargin(r1, new Insets(10, 10, 10, 10));
//
//        hbox.getChildren().addAll(r1, r2, r3, r4);
//        root.getChildren().add(hbox);
//
//        stage.setScene(scene);
//        stage.show();

}
