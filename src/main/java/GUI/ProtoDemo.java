package GUI;

import com.jme3.app.BasicProfilerState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedList;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;

/**
 * @author xiaosongChen
 * @create 2022-10-23 10:08
 * @description :
 */
public class ProtoDemo extends SimpleApplication {

    private ListBox<String> listBox;
    private VersionedList<String> testList = new VersionedList<String>();

    public static void main( String... args ) {
        ProtoDemo main = new ProtoDemo();
        main.start();
    }

    public ProtoDemo() {
        super(new StatsAppState(), new DebugKeysAppState(), new BasicProfilerState(false),
                new OptionPanelState("glass"),
                new ScreenshotAppState("", System.currentTimeMillis()));
    }

    @Override
    public void simpleInitApp() {

        // Initialize the globals access so that the defualt
        // components can find what they need.
        GuiGlobals.initialize(this);

        BaseStyles.loadGlassStyle();

        // Create a window to hold our demo elements and add a title label
        Container window = new Container("glass");
        window.addChild(new Label("Test List", new ElementId("title"), "glass"));

        // Make some test data for the list.
        for( int i = 0; i < 10; i++ ) {
            testList.add("Item " + (i+1));
        }

        // Create a list box for the test data and add it to the window
        listBox = new ListBox<>(testList, "glass");
        window.addChild(listBox);

        // Create some actions
        final Action add = new Action("Add") {
            @Override
            public void execute( Button b ) {
                testList.add("New Item " + (testList.size() + 1));
            }
        };
        final Action delete = new Action("Delete") {
            @Override
            public void execute( Button b ) {
                Integer selected = listBox.getSelectionModel().getSelection();
                if( selected != null && selected < testList.size() ) {
                    testList.remove((int)selected);
                }
            }
        };
        final Action cancel = new Action("Cancel") {
            @Override
            public void execute( Button b ) {
            }
        };

        // Safe delete is a special action because it will pop open
        // the option panel and delegate to the other delete action.
        final Action safeDelete = new Action("Safe Delete") {
            @Override
            public void execute( Button b ) {
                Integer selected = listBox.getSelectionModel().getSelection();
                if( selected == null || selected >= testList.size() ) {
                    return;
                }
                String val = testList.get(selected);
                OptionPanelState ops = stateManager.getState(OptionPanelState.class);
                ops.show("Delete", "Really delete '" + val + "'?", delete, cancel);
            }
        };

        // Create the button panel at the bottom of the window
        Container buttons = new Container(new SpringGridLayout(Axis.X, Axis.Y, FillMode.Even, FillMode.Even));
        window.addChild(buttons);
        buttons.addChild(new ActionButton(add, "glass"));
        buttons.addChild(new ActionButton(safeDelete, "glass"));
        buttons.addChild(new ActionButton(delete, "glass"));


        // And stick the window somewhere we will see it
        window.setLocalTranslation(300, 600, 0);
        guiNode.attachChild(window);


        window = new Container("glass");
        window.addChild(new Label("Test Color Chooser", new ElementId("title"), "glass"));
        ColorChooser colors = window.addChild(new ColorChooser("glass"));
        colors.setPreferredSize(new Vector3f(300, 90, 0));

        // And stick the window somewhere we will see it
        window.setLocalTranslation(100, 400, 0);
        guiNode.attachChild(window);
    }
}

