package de.mirkosertic.gamecomposer.contentarea.eventsheet.setproperty;

import de.mirkosertic.gamecomposer.contentarea.eventsheet.ActionController;
import de.mirkosertic.gameengine.ArrayUtils;
import de.mirkosertic.gameengine.camera.Camera;
import de.mirkosertic.gameengine.core.GameObject;
import de.mirkosertic.gameengine.core.GameRule;
import de.mirkosertic.gameengine.core.GameScene;
import de.mirkosertic.gameengine.core.SetPropertyAction;
import de.mirkosertic.gameengine.physic.Physics;
import de.mirkosertic.gameengine.physic.Platform;
import de.mirkosertic.gameengine.physic.Static;
import de.mirkosertic.gameengine.playerscore.PlayerScore;
import de.mirkosertic.gameengine.sprite.Sprite;
import de.mirkosertic.gameengine.text.Text;
import de.mirkosertic.gameengine.type.TextExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetPropertyEditorController implements ActionController {

    @FXML
    ComboBox propertyName;

    @FXML
    TextField propertyValueTextField;

    private GameScene gameScene;
    private SetPropertyAction action;
    private Node view;

    SetPropertyEditorController initialize(Node aView, GameScene aGameScene, GameRule aRule, SetPropertyAction aAction) {
        gameScene = aGameScene;
        view = aView;
        action = aAction;

        // Gather the known properties
        Set<String> theAvailableProperties = new HashSet<>();
        theAvailableProperties.addAll(ArrayUtils.asList(GameObject.EDITABLE_PROPERTIES));
        theAvailableProperties.addAll(ArrayUtils.asList(Camera.EDITABLE_PROPERTIES));
        theAvailableProperties.addAll(ArrayUtils.asList(Physics.EDITABLE_PROPERTIES));
        theAvailableProperties.addAll(ArrayUtils.asList(Platform.EDITABLE_PROPERTIES));
        theAvailableProperties.addAll(ArrayUtils.asList(Static.EDITABLE_PROPERTIES));
        theAvailableProperties.addAll(ArrayUtils.asList(PlayerScore.EDITABLE_PROPERTIES));
        theAvailableProperties.addAll(ArrayUtils.asList(Sprite.EDITABLE_PROPERTIES));
        theAvailableProperties.addAll(ArrayUtils.asList(Text.EDITABLE_PROPERTIES));

        // Sort them by name
        List<String> theSupportedProperties = new ArrayList<>();
        theSupportedProperties.addAll(theAvailableProperties);
        Collections.sort(theSupportedProperties);

        propertyName.getItems().clear();

        TextExpression theExpression = action.propertyValueProperty().get();
        if (theExpression != null) {
            propertyValueTextField.setText(theExpression.expression);
        }
        propertyValueTextField.setVisible(false);
        propertyValueTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                onTextfieldChange();
            }
        });

        propertyName.getItems().addAll(theSupportedProperties);
        if (action.propertyNameProperty().isNull()) {
            propertyName.getSelectionModel().select(0);
        } else {
            for (int i = 0; i < theSupportedProperties.size(); i++) {
                String theProp = theSupportedProperties.get(i);
                if (theProp.equals(action.propertyNameProperty().get())) {
                    propertyName.getSelectionModel().select(i);
                }
            }
        }

        return this;
    }

    @FXML
    public void onSelectProperty() {
        String theSelectedProperty = (String) propertyName.getSelectionModel().getSelectedItem();
        action.propertyNameProperty().set(theSelectedProperty);

        propertyValueTextField.setVisible(true);
    }

    private void onTextfieldChange() {
        action.propertyValueProperty().set(new TextExpression(propertyValueTextField.getText()));
    }

    public Node getView() {
        return view;
    }
}
