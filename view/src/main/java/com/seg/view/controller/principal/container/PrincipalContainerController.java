 package com.seg.view.controller.principal.container;

import static com.seg.view.utils.AnimUtils.initFadeOut;
import static com.seg.view.utils.NodeUtils.createTooltip;
import static com.seg.view.utils.NodeUtils.scaleNode;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTooltip;
import com.seg.view.controller.blueprint.ParentControl;
import com.seg.view.controller.principal.menu.MenuControl;
import com.seg.view.utils.Dimension;
import com.seg.view.utils.DocUtils;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.manager.SceneManager;

import org.springframework.stereotype.Component;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

@Component
public final class PrincipalContainerController extends ParentControl implements NotificationControl{    

    @FXML
    private StackPane stackPane;
    @FXML
    private StackPane innerStackPane;
    @FXML
    private VBox logoVbox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private ToggleGroup menuToggleGroup;
    @FXML
    private ToggleGroup toggleBtnGroup;
    @FXML
    private ToggleButton homeToggleBtn;
    @FXML
    private ToggleButton commissionToggleBtn;
    @FXML
    private ToggleButton adminToggleBtn;
    @FXML
    private ToggleButton logoutToggleBtn;
    @FXML
    private ToggleButton configToggleBtn;
    @FXML
    private ToggleButton pdfToggleBtn;
    @FXML
    private ToggleButton accountToggleBtn;
    @FXML
    private Button minimizeBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private SVGPath logoSvg;
    @FXML
    private SVGPath commissionSvg;
    @FXML
    private SVGPath accountSvg;
    @FXML
    private SVGPath adminSvg;
    @FXML
    private SVGPath pdfSvg;
    @FXML
    private SVGPath logoutSvg;
    @FXML
    private SVGPath configSvg;
    @FXML
    private SVGPath homeSvg;
    @FXML
    private SVGPath minimizeSvg;
    @FXML
    private SVGPath closeSvg;
    @FXML
    private SVGPath warningSvg;
    
    private static final StringProperty NOTIFICATION_MESSAGE = new SimpleStringProperty();

    private static final double DRAWER_SIZE_PERCENTAGE = 0.20;
    private static final double MENU_BTN_SCALE = 50;
    private static final double LOGO_MENU_SCALE = 45;
    private static final double CLOSE_BTN_SCALE = 35;
    private static final double NOTIFICATION_SVG_SCALE = 35;
    
    private final List<MenuControl> menuControls;

    private Parent commissionMenu;
    private Parent accountMenu;
    private Parent adminMenu;

    public PrincipalContainerController(final SceneManager sceneManager, final List<MenuControl> menuControls) {
        super(sceneManager);
        this.menuControls = menuControls;
    }

    @FXML
    private void homeBtnClick(final ActionEvent event) {                
        sceneManager.changeChildScene(Path.WELCOME, getChildSceneContainer());                 
    }

    @FXML
    void logoutBtnClick(final ActionEvent event) {
        sceneManager.changeParentScene(Path.LOGIN_CONTAINER);
    }

    @FXML
    void configBtnClick(final ActionEvent event) {
        sceneManager.changeChildScene(Path.CONFIGURATION, getChildSceneContainer());
    }

    @FXML
    private void pdfBtnClick(final ActionEvent event) {
        final FileChooser fileChooser = new FileChooser ();
        final File file = fileChooser.showOpenDialog(eventWindow(event));        
        if (file != null) DocUtils.fileToPdf(file);        
    }     
    
    @FXML
    private void minimizeBtnClick(final ActionEvent event) {
        final Stage stage = (Stage) eventWindow(event);
        stage.setIconified(true);        
    }

    @FXML
    private void closeBtnClick(final ActionEvent event) {
        Platform.exit();
    }
    
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {                      
        super.initialize(url, rb);
        super.movableNode(stackPane);                             
        sceneManager.changeChildScene(Path.WELCOME, getChildSceneContainer());                          
    }
    
    @Override
    protected void initNodes() {        
        initDrawer(drawer); 
        initNotificacionPanel(spinner, warningSvg);
    }

    @Override
    protected void initProperties() {
        initMenuBtnProperties(menuControls);
        sideMenuToggleGroupProperties(menuToggleGroup, toggleBtnGroup);        
        hideDrawerOnHover(innerStackPane);
        showDrawerOnHover(menuToggleGroup, Arrays.asList(commissionMenu, adminMenu, accountMenu));           
                
        for(final Toggle t: toggleBtnGroup.getToggles()){
            final ToggleButton tb = (ToggleButton) t;
            hideDrawerOnHover(tb);
        }
    }

    @Override
    public void initScales(){                
        final Dimension dimension = Dimension.HEIGHT;
        scaleNode(LOGO_MENU_SCALE, dimension, logoVbox, logoSvg);
        scaleNode(MENU_BTN_SCALE, dimension, homeToggleBtn, homeSvg, commissionSvg, 
            accountSvg, adminSvg, pdfSvg, logoutSvg, configSvg);
        scaleNode(CLOSE_BTN_SCALE, dimension, closeBtn, closeSvg, minimizeSvg);  
    }   

    @Override
    public StackPane getChildSceneContainer() {
        return innerStackPane;
    }

    @Override
    public StackPane getParentSceneContainer() {
        return stackPane;
    }       
    
    @Override
    public void showLoad() {                
        spinner.setVisible(true);
    }

    @Override
    public void hideLoad() {
        if (spinner.isVisible())
            spinner.setVisible(false);        
    }
    
    @Override
    public void showWarning(final String message) {
        hideLoad();
        final FadeTransition fadeOut = initFadeOut(Duration.seconds(20), warningSvg, 1);
        NOTIFICATION_MESSAGE.setValue(message);
        warningSvg.setOpacity(1);      
        fadeOut.play();
    }
    
    private void initDrawer(final JFXDrawer drawer){
        commissionMenu = sceneManager.loadFxml(Path.COMMISSION_MENU);    
        adminMenu = sceneManager.loadFxml(Path.ADMIN_MENU);
        accountMenu = sceneManager.loadFxml(Path.ACCOUNT_MENU);
        
        /*
            If drawer is closed, changes its view order so it wond affect the drag and drop view
        */
        drawer.setOnDrawerClosed((event) -> {
            drawer.setViewOrder(1);
            event.consume();
        });                
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {                
                drawer.setDefaultDrawerSize(stackPane.getWidth() * DRAWER_SIZE_PERCENTAGE);         
            }
        });  
    }  

    private void showDrawerOnHover (final ToggleGroup toggleGroup, final List<Parent> parents){
        for (int i = 0; i < parents.size(); i++){
            final ToggleButton tb = (ToggleButton) toggleGroup.getToggles().get(i);
            final Parent p = parents.get(i);
            
            tb.hoverProperty().addListener((o, oldVal, newVal) ->{
                if (newVal){                               
                    if (drawer.isClosed() || drawer.isClosing()){
                        drawer.setViewOrder(-1);
                        drawer.open();
                    }
                    tb.setSelected(true);                     
                    drawer.setSidePane(p);
                }            
            });
        }        
    }
    
    private void hideDrawerOnHover (final Node node){
        node.hoverProperty().addListener( (o, oldVal, newVal) -> {
            if (newVal){
                if (drawer.isOpened() || drawer.isOpening()) drawer.close();                
                menuToggleGroup.selectToggle(null);                          
            }                
        });
    }
    
    private void initMenuBtnProperties (final List <MenuControl> menuControls){
        final ToggleGroup tg = new ToggleGroup();
        menuControls.forEach((m) -> tg.getToggles().addAll(m.getToggleGroup().getToggles()));

        /*
            Buttons wont hold selection status
        */
        tg.selectedToggleProperty().addListener((o, oldVal, newVal) -> {
            if (newVal == null) oldVal.setSelected(true);
        });

        /* 
            Hides the drawer once a btn of the menu is pressed
        */
        for (final Toggle t: tg.getToggles()) {
            final ToggleButton tb = (ToggleButton) t;

            tb.selectedProperty().addListener((o, oldVal, newVal) -> {
                if (newVal != null){
                    drawer.close();
                    menuToggleGroup.selectToggle(null);
                }
            });
        }
    }


    private void sideMenuToggleGroupProperties(final ToggleGroup btnToggleGroup, final ToggleGroup drawerToggleGrorup){        
        /*
            Keeps the last btn selected if mouse leaves the application    
        */
        btnToggleGroup.selectedToggleProperty().addListener((o, oldVal, newVal) -> {
            if (newVal == null && drawer.isOpened() && !innerStackPane.isHover()) oldVal.setSelected(true);      
        });
        
        /*
            Buttons wont hold selection status
        */
        drawerToggleGrorup.selectedToggleProperty().addListener((o, oldVal, newVal) -> {            
            if (newVal != null) newVal.setSelected(false);
        });  
    }

    private void initNotificacionPanel(final JFXSpinner spinner, final SVGPath svgWarning){
        final JFXTooltip tooltip = createTooltip(Pos.CENTER_LEFT);
        tooltip.textProperty().bind(NOTIFICATION_MESSAGE);
        JFXTooltip.install(svgWarning, tooltip);
        scaleNode(NOTIFICATION_SVG_SCALE, Dimension.HEIGHT, closeBtn, svgWarning);
        spinner.radiusProperty().bind(closeBtn.heightProperty().divide(10));
        spinner.setVisible(false);
        svgWarning.setOpacity(0);
    }
}
    
