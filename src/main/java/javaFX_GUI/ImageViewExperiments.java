package javaFX_GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * @author xiaosongChen
 * @create 2022-11-06 10:37
 * @description :## 通过将 JavaFXImageView嵌套在 HBox布局组件中来将其附加到场景图的示例,将 ImageView 添加到场景图中
 * 要使其ImageViewl可见，您必须将其添加到场景图中。
 * **这意味着将其添加到 Scene对象中**。
 * 由于ImageView不是它的子类，javafx.scene.Parent 因此无法直接添加到场景图中。它必须嵌套在另一个组件中，例如布局组件**。
 *
 * 这是一个通过将 JavaFXImageView嵌套在 HBox布局组件中来将其附加到场景图的示例
 */
public class ImageViewExperiments extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ImageView Experiment 1");
        FileInputStream inputStream = new FileInputStream("src/main/resources/Models/Jaime/NormalMap.png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        HBox hBox = new HBox(imageView);
        Scene scene = new Scene(hBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
