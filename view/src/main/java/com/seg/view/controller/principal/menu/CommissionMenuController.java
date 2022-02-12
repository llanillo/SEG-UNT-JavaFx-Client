package com.seg.view.controller.principal.menu;

import static com.seg.view.utils.NodeUtils.scaleNode;

import com.seg.domain.enumeration.Role;
import com.seg.view.controller.blueprint.Control;
import com.seg.view.session.SessionManager;
import com.seg.view.utils.Dimension;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneControl;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.SVGPath;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommissionMenuController extends Control implements MenuControl{

    @FXML
    private ToggleButton managerToggleBtn;
    @FXML
    private ToggleGroup menuToggleGroup;
    @FXML
    private SVGPath managerSvg;
    @FXML
    private SVGPath chemistrySvg;
    @FXML
    private SVGPath censusSvg;
    @FXML
    private SVGPath geophysicsSvg;
    @FXML
    private SVGPath geologySvg;
    @FXML
    private SVGPath matSvg;
    @FXML
    private SVGPath pressSvg;
    @FXML
    private SVGPath draftSvg;
    @FXML
    private SVGPath treasurySvg;

    private final SceneControl sceneControl;

    @FXML
    void managerBtnClick(final ActionEvent event) {
        sceneControl.changeChildScene(Path.MANAGEMENT_TABLE, getChildContainer());
    }

    @FXML
    void commissionBtnClick(ActionEvent event) {
        final Node commissionBtn = (Node) event.getSource();
        final Role role = Role.valueOf(commissionBtn.getId()); 
        SessionManager.instance().setRole(role);
        sceneControl.changeChildScene(Path.COMMISSION_TABLE, getChildContainer());       
    }

    @Override
    public void initScales() {
        scaleNode(ICON_SCALE, Dimension.HEIGHT, managerToggleBtn, 
                managerSvg, chemistrySvg, censusSvg, geophysicsSvg, geologySvg, matSvg, pressSvg, draftSvg, treasurySvg);
    }

    @Override
    public ToggleGroup getToggleGroup() {
        return menuToggleGroup;
    }
}
