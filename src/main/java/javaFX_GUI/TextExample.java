package javaFX_GUI;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author xiaosongChen
 * @create 2022-11-06 18:25
 * @description :JavaFX 文本示例
 */
public class TextExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    String      fontFamily  = "Arial";//字体系列
    double      fontSize    = 13;//字体大小
    FontWeight  fontWeight  = FontWeight.BOLD;//字体粗细
    FontPosture fontPosture = FontPosture.ITALIC;//字体姿势

    Font font1 = Font.font(fontFamily);
    Font font2 = Font.font(fontSize);
    Font font3 = Font.font(fontFamily, fontSize);
    Font font4 = Font.font(fontFamily, fontWeight , fontSize);
    Font font5 = Font.font(fontFamily, fontPosture, fontSize);
    Font font6 = Font.font(fontFamily, fontWeight , fontPosture, fontSize);
    @Override
    public void start(Stage primaryStage) throws Exception {
        Text text = new Text("This is the text..... to display\nLine2");
//        text.setWrappingWidth(80);//设置文本环绕宽度:文本宽度大于环绕宽度会换行
        text.setFont(font6);
//        text.setFont(Font.font("Arial", FontWeight.BOLD,15));//指定字体粗细和字体大小
        text.setFill(Color.YELLOW);//绘制文本的“内部”颜色
        text.setStroke(Color.GREEN);//用于绘制文本的“轮廓”或“边界”颜色
        //确定文本控件在其父容器元素中的显示位置 - 前提是父容器遵循此位置（[Pane]) 遵循，[VBox]() 不遵循）
        text.setX(50);
        text.setY(25);
        text.setStrikethrough(false);//启用删除线修饰
        text.setUnderline(true);//启用下划线修饰
//        text.setFontSmoothingType(FontSmoothingType.GRAY);//字体平滑（抗锯齿）技术
        text.setFontSmoothingType(FontSmoothingType.LCD);//字体平滑（抗锯齿）技术
//        text.setTextOrigin(VPos.BASELINE);//所显示文本的 Y 基线。文本显示在基线的正上方，某些字符延伸到基线下方
//        text.setTextOrigin(VPos.BOTTOM);//显示文本的底部。这低于基线。
//        text.setTextOrigin(VPos.CENTER);//Text 控件的 Y 位置被解释为表示垂直文本的中心。
        text.setTextOrigin(VPos.TOP);//Text 控件的 Y 位置被解释为表示垂直表示文本的顶部。

        //列出字体系列和字体名称
        List<String> fontFamilies = Font.getFamilies();
        List<String> fontNames    = Font.getFontNames();
        System.out.println(fontFamilies);//Adobe 宋体 Std L, Adobe 黑体 Std R, Agency FB, Algerian, Arial, Arial Black, rd MT Condensed.....
        System.out.println(fontNames);//[AdobeHeitiStd-Regular, AdobeSongStd-Light, Agency FB, Agency FB Bold, Algerian, Arial, Arial Black....

        Scene scene = new Scene(new Pane(text),400,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
