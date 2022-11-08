package javaFX_GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * @author xiaosongChen
 * @create 2022-11-07 10:39
 * @description :在标签文本旁边的标签内显示图像
 * 使用 JavaFX ImageView 组件将图像添加到标签中：LabelNode
 */
public class LabelExperiments extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HBox Experiment 1");

        FileInputStream input = new FileInputStream("src/main/resources/Models/Tree/Leaves.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);//设置图像大小
        imageView.setFitHeight(40);

        Label label = new Label("My Label", imageView);
        Button button = new Button("BUtton..");
        HBox hBox = new HBox(button,label);
        button.setMnemonicParsing(true);//助记符

        Scale scaleTransformation = new Scale();//按钮变换
        scaleTransformation.setX(2.0);
        scaleTransformation.setY(1);
        scaleTransformation.setPivotX(10);
        scaleTransformation.setPivotY(20);

        button.getTransforms().add(scaleTransformation);
        button.setText("_BUtton..");

        button.setDisable(false);//禁用按钮
        Scene scene = new Scene(hBox, 600, 400);
        button.setDefaultButton(false);//button默认模式

        MenuItem xxx1 = new MenuItem("xxx1");
        MenuItem xxx2 = new MenuItem("xxx2");
        MenuItem xxx3 = new MenuItem("xxx3");

        MenuButton options = new MenuButton("Options", imageView, xxx1, xxx2, xxx3);
        Font font = Font.font("Courier New", FontWeight.BOLD, 36);
        Font font2 = Font.font("Courier New", FontWeight.BOLD, 20);
        options.setFont(font);//设置菜单按钮字体
        hBox.getChildren().add(options);
        primaryStage.setScene(scene);
        primaryStage.show();
        button.setOnMouseClicked(event -> {
            label.setText("lable is Clicked!");
            label.setFont(new Font("Arial", 24));
//            button.setText("Clicked");
            System.out.println("Clicked");
        });
    }
}
