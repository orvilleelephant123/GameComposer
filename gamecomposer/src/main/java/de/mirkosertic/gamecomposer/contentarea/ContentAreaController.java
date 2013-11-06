package de.mirkosertic.gamecomposer.contentarea;

import de.mirkosertic.gamecomposer.*;
import de.mirkosertic.gamecomposer.contentarea.gamescene.GameSceneEditorController;
import de.mirkosertic.gamecomposer.contentarea.gamescene.GameSceneEditorControllerFactory;
import de.mirkosertic.gameengine.core.GameScene;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ContentAreaController implements ChildController {

    @FXML
    TabPane editorTabPane;

    @FXML
    BorderPane welcomeBorderPane;

    @Inject
    GameSceneEditorControllerFactory sceneEditorControllerFactory;

    private Node view;
    private Map<ContentChildController, Tab> visibleScenes;

    public ContentAreaController() {
        visibleScenes = new HashMap<>();
    }

    ContentAreaController initialize(Node aView) {
        view = aView;
        editorTabPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent aKeyEvent) {
                Tab theSelectedTab = editorTabPane.getSelectionModel().getSelectedItem();
                if (theSelectedTab != null) {
                    for (Map.Entry<ContentChildController, Tab> theTab : visibleScenes.entrySet()) {
                        if (theTab.getValue() == theSelectedTab) {
                            theTab.getKey().processKeyPressedEvent(aKeyEvent);
                        }
                    }
                }
            }
        });
        editorTabPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent aKeyEvent) {
                Tab theSelectedTab = editorTabPane.getSelectionModel().getSelectedItem();
                if (theSelectedTab != null) {
                    for (Map.Entry<ContentChildController, Tab> theTab : visibleScenes.entrySet()) {
                        if (theTab.getValue() == theSelectedTab) {
                            theTab.getKey().processKeyReleasedEvent(aKeyEvent);
                        }
                    }
                }
            }
        });
        return this;
    }

    @Override
    public Node getView() {
        return view;
    }

    public void onApplicationStarted(@Observes ApplicationStartedEvent aEvent) {
        editorTabPane.getTabs().clear();
        editorTabPane.setVisible(false);
        welcomeBorderPane.setVisible(true);
    }

    void addTab(final Tab aTab) {
        editorTabPane.setVisible(true);
        welcomeBorderPane.setVisible(false);
        editorTabPane.getTabs().add(aTab);
        aTab.setClosable(true);
        aTab.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event aEvent) {
                onTabClose(aTab);
            }
        });
    }

    void onTabClose(Tab aTab) {
        for (Map.Entry<ContentChildController, Tab> theTabEntry : visibleScenes.entrySet()) {
            if (theTabEntry.getValue() == aTab) {
                theTabEntry.getKey().removed();
                visibleScenes.remove(theTabEntry.getKey());
                return;
            }
        }
    }

    public void onObjectUpdated(@Observes ObjectUpdatedEvent aEvent) {
        for (Map.Entry<ContentChildController, Tab> theTabEntry : visibleScenes.entrySet()) {
            theTabEntry.getKey().onObjectUpdated(theTabEntry.getValue(), aEvent);
        }
    }

    public void onShutdown(@Observes ShutdownEvent aEvent) {
        for (Map.Entry<ContentChildController, Tab> theTabEntry : visibleScenes.entrySet()) {
            theTabEntry.getKey().onShutdown(aEvent);
        }
    }

    public void onObjectSelected(@Observes ObjectSelectedEvent aEvent) {
        for (Map.Entry<ContentChildController, Tab> theTabEntry : visibleScenes.entrySet()) {
            theTabEntry.getKey().onObjectSelected(aEvent);
        }
    }

    public void onGameSceneSelected(@Observes GameSceneSelectedEvent aEvent) {
        GameScene theScene = aEvent.getScene();
        for (Map.Entry<ContentChildController, Tab> theTabEntry : visibleScenes.entrySet()) {
            if (theTabEntry.getKey().getEditingObject() == theScene) {
                editorTabPane.getSelectionModel().select(theTabEntry.getValue());
                return;
            }
        }

        GameSceneEditorController theSceneEditorController = sceneEditorControllerFactory.createFor(theScene);
        Tab theTab = new Tab(theScene.getName());
        theTab.setContent(theSceneEditorController.getView());

        addTab(theTab);

        visibleScenes.put(theSceneEditorController, theTab);

        theSceneEditorController.addedAsTab();
    }

    public void onFlushResourceCache(@Observes FlushResourceCacheEvent aEvent) {
        for (Map.Entry<ContentChildController, Tab> theTabEntry : visibleScenes.entrySet()) {
            theTabEntry.getKey().onFlushResourceCache(aEvent);
        }
    }
}