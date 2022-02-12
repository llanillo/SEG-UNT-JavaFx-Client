package com.seg.view.controller.principal.menu;

import static com.seg.view.utils.NodeUtils.scaleNode;

import com.seg.view.controller.blueprint.Control;
import com.seg.view.utils.Dimension;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneControl;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.SVGPath;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminMenuController extends Control implements MenuControl{

    @FXML
    private ToggleButton userToggleBtn;
    @FXML
    private ToggleGroup menuToggleBtns;
    @FXML
    private SVGPath userSvg;
    @FXML
    private SVGPath managerSvg;
    @FXML
    private SVGPath newsSvg;
    @FXML
    private SVGPath recordSvg;
    
    private final SceneControl sceneControl;

    @FXML
    void newsBtnClick(final ActionEvent event) {
        
    }

    @FXML
    void managerBtnClick(final ActionEvent event) {
        sceneControl.changeChildScene(Path.MANAGER_TABLE, getChildContainer());
    }

    @FXML
    void recordBtnClick(final ActionEvent event) {

    }

    @FXML
    void userBtnClick(final ActionEvent event) {

    }

    @Override
    public void initScales() {
        scaleNode(ICON_SCALE, Dimension.HEIGHT, userToggleBtn, userSvg, managerSvg, newsSvg, recordSvg);
    }

    @Override
    public ToggleGroup getToggleGroup() {
        return menuToggleBtns;
    }
}
