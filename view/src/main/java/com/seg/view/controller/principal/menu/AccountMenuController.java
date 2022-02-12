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
public class AccountMenuController extends Control implements MenuControl{

    @FXML
    private ToggleButton accountToggleBtn;
    @FXML
    private ToggleGroup menuTogglegroup;
    @FXML
    private SVGPath accountSvg;
    @FXML
    private SVGPath documentSvg;

    private final SceneControl sceneControl;

    @FXML
    void documentBtnClick(final ActionEvent event) {
        sceneControl.changeChildScene(Path.MY_DOCUMENTS, getChildContainer());
    }

    @FXML
    void accountBtnClick(final ActionEvent event){
        sceneControl.changeChildScene(Path.ACCOUNT, getChildContainer());
    }
    
    @Override
    public void initScales() {
        scaleNode(ICON_SCALE, Dimension.HEIGHT, accountToggleBtn, accountSvg, documentSvg);
    }

    @Override
    public ToggleGroup getToggleGroup() {
        return menuTogglegroup;
    }  
}
