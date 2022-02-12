package com.seg.view.controller.principal.commission;

import static com.seg.view.utils.Dimension.HEIGHT;
import static com.seg.view.utils.NodeUtils.createSvg;
import static com.seg.view.utils.NodeUtils.createTooltip;
import static com.seg.view.utils.NodeUtils.scaleNode;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.seg.client.web.document.DocumentWebClient;
import com.seg.configuration.event.DragAndDrop;
import com.seg.domain.document.fx.DocumentResponseFx;
import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.enumeration.Role;
import com.seg.domain.manager.impl.UserManager;
import com.seg.domain.observable.list.DocumentList;
import com.seg.domain.user.blueprint.IUserResponse;
import com.seg.view.concurrent.service.principal.commission.CommissionDocumentService;
import com.seg.view.concurrent.service.principal.general.DownloadService;
import com.seg.view.controller.blueprint.Logout;
import com.seg.view.controller.blueprint.TableControl;
import com.seg.view.controller.principal.container.NotificationControl;
import com.seg.view.session.SessionManager;
import com.seg.view.utils.Dimension;
import com.seg.view.utils.DocUtils;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.FxmlLoader;
import com.seg.viewcontainer.principal.blueprint.SceneControl;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.DirectoryChooser;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommissionTableController extends TableControl implements Logout, ApplicationListener<DragAndDrop>, Ordered, FxmlLoader{

    @FXML
    private GridPane gridPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Region darkRegion;
    @FXML
    private Pane topPane;
    @FXML
    private Pane bottomPane;
    @FXML
    private HBox searchHbox;
    @FXML
    private Label commissionLabel;
    @FXML
    private Label countLabel;
    @FXML
    private SVGPath refreshSvg;
    @FXML
    private SVGPath addSvg;
    @FXML
    private SVGPath svgSubir;
    @FXML
    private SVGPath glassSvg;
    @FXML
    private Button glassBtn;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton deleteBtn;
    @FXML
    private JFXButton editBtn;
    @FXML
    private JFXButton refreshBtn;
    @FXML
    private JFXTextField searchTextfield;
    @FXML
    private JFXTreeTableView<DocumentResponseFx> tableView;
    @FXML
    private JFXComboBox<TreeTableColumn<DocumentResponseFx, ?>> sortComboBox;
    @FXML
    private TreeTableColumn<DocumentResponseFx, String> colTitle;
    @FXML
    private TreeTableColumn<DocumentResponseFx, LocalDateTime> colDate;
    @FXML
    private TreeTableColumn<DocumentResponseFx, DocumentType> colDocumentType;
    @FXML
    private TreeTableColumn<DocumentResponseFx, Long> colSize;  
    @FXML
    private TreeTableColumn<DocumentResponseFx, DocumentResponseFx> colDownload;

    private static final String DELETE_DIALOG_CSS_PATH = "/com/seg/css/application/delete_dialog.css";
    private static final String DELETE_CONFIRMATION = "¿Desea eliminar el archivo?";

    private static final BooleanProperty IS_AUTHOR = new SimpleBooleanProperty(false);
    private static final BooleanProperty IS_MEMEBER = new SimpleBooleanProperty(false);    
    
    private static final double SVG_SCALE = 30;    
    private static final double REFRESH_SVG_SCALE = 40;
  
    private final DocumentWebClient documentWebClient;
    private final NotificationControl notificarionControl;
    private final SceneControl sceneControl;
    private final DocumentList documentList;        
    private final ModelMapper modelMapper;
        
    private DocumentResponseFx selectedDocument;           
    private SessionManager sessionManager;

    @FXML
    private void addBtnClick(final ActionEvent event) {        
        sceneControl.changeChildScene(Path.ADD_FILE, getChildContainer());        
    }

    @FXML
    private void editBtnClick(final ActionEvent event) {        
        if (selectedDocument != null) 
            sceneControl.changeChildScene(Path.ADD_FILE, getChildContainer());                   
    }
    
    @FXML
    private void refreshBtnClick(final ActionEvent event) {
        seeDocuments(documentWebClient, modelMapper, notificarionControl, documentList, sessionManager);
    }
        
    @FXML
    private void btnDescargarClic(final ActionEvent event) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final File file = directoryChooser.showDialog(eventWindow(event));
        
        if (file != null && selectedDocument != null)
            downloadDocument();        
    }
    
    @FXML
    private void deleteBtnClick(final ActionEvent event) {           
        if (selectedDocument != null)
            createDeleteDialog(stackPane);             
    }
    
    @FXML
    void tableViewClick(final MouseEvent event) {
        if (event.getClickCount() == 1 && event.getButton() == MouseButton.SECONDARY)
            super.createPopup().show(tableView, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());        
    }

    @Override
    public void onApplicationEvent(final DragAndDrop event) {                
        if (path == Path.COMMISSION_TABLE) 
            sceneControl.changeChildScene(Path.ADD_FILE, getChildContainer());
    }

    @Override
    public int getOrder() {        
        return 0;
    }

    @Override
    protected void init() {
        sessionManager = SessionManager.instance();
        seeDocuments(documentWebClient, modelMapper, notificarionControl, documentList, sessionManager);
    }

    @Override
    public void initNodes() {                       
        super.initSortComboBox(tableView, sortComboBox, Arrays.asList(
                                colTitle, colDate, colDocumentType, colSize));
        initialValues(IS_MEMEBER, commissionLabel);
        initDragAndDropPane(gridPane, stackPane);     
    }
        
    @Override
    public void initProperties() {
        clearTableSelectionProperty(bottomPane, topPane);                       
        addBtnProperty(addBtn);
        editBtnProperty(editBtn);
        deleteBtnProperty(deleteBtn);
        
        countLabel.textProperty().bind(Bindings.createStringBinding(
                                        () -> "(" + tableView.getCurrentItemsCount() + ")", 
                                        tableView.currentItemsCountProperty()));            
    }

    @Override
    public void initScales(){             
        scaleNode(SVG_SCALE, HEIGHT, glassBtn, glassSvg);  
        scaleNode(SVG_SCALE, HEIGHT, addBtn, addSvg);
        scaleNode(REFRESH_SVG_SCALE, HEIGHT, refreshBtn, refreshSvg);
    }

    @Override
    public void restartFields() {
        selectedDocument = null;         
    }

    @Override
    public void initColumns(){        
        colTitle.setCellValueFactory(c -> c.getValue().getValue().titleProperty());
        colDate.setCellValueFactory(c -> c.getValue().getValue().dateProperty());
        colDocumentType.setCellValueFactory(c -> c.getValue().getValue().documentTypeProperty());
        colSize.setCellValueFactory(c -> c.getValue().getValue().sizeProperty().asObject());
        colDownload.setCellValueFactory(c -> new ReadOnlyObjectWrapper<DocumentResponseFx>(c.getValue().getValue()));      

        colDate.setCellFactory(col -> {
            return new TreeTableCell<DocumentResponseFx, LocalDateTime>(){

                @Override
                protected void updateItem(final LocalDateTime item, final boolean empty) {                    
                    super.updateItem(item, empty);
                    if (item == null || empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");                
                        final String fecha = item.format(formato);
                        setText(fecha);                        
                    }
                }
                
            };
        });

        colDocumentType.setCellFactory(col ->{
            return new TreeTableCell<DocumentResponseFx, DocumentType>(){

                @Override
                protected void updateItem(final DocumentType item, final boolean empty) {                    
                    super.updateItem(item, empty);
                    if (item == null || empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        final String documentType = item.toString();
                        setText(documentType);                        
                    }
                }
                
            };
        });

        colSize.setCellFactory(col ->{
            return new TreeTableCell<DocumentResponseFx, Long>() {

                @Override
                protected void updateItem(final Long item, final boolean empty) {                    
                    super.updateItem(item, empty);
                    if (item == null | empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        final String size = DocUtils.sizeToString(item);
                        setText(size);
                    }
                }
                
            };
        });
        
        colDownload.getStyleClass().addAll("center-column");
        colDownload.setCellFactory(col -> {
            return new TreeTableCell<DocumentResponseFx, DocumentResponseFx>(){

                final JFXButton downloadBtn = new JFXButton();
                final String content = "M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z";                
                final Color fill = Color.rgb(56, 136, 255);
                final SVGPath svg = createSvg(content, fill);

                @Override
                protected void updateItem(final DocumentResponseFx item, final boolean empty) {                    
                    super.updateItem(item, empty);
                    if(item == null || empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{                                                                                                                 
                        scaleNode(DOWNLOAD_BTN_SCALE, Dimension.HEIGHT, getTreeTableRow(), svg);
                        createTooltip(downloadBtn, DOWNLOAD_TEXT, Pos.TOP_CENTER);

                        downloadBtn.setGraphic(svg);
                        downloadBtn.visibleProperty().bind(getTreeTableRow().selectedProperty());
                        downloadBtn.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(final ActionEvent arg0) {
                                downloadDocument();
                            }
                            
                        });                        
                        
                        setGraphic(downloadBtn);                        
                    }
                }
                
            };
        });
    }

    @Override
    public void initRows (){      
        tableView.setRowFactory(selector -> {
            final TreeTableRow<DocumentResponseFx> tableRow = new TreeTableRow<>();

            tableRow.setOnMouseClicked(event -> {      
                selectedDocument = tableRow.getItem();                   

                if (!tableRow.isEmpty()){         
                    sessionManager.setTableDocument(selectedDocument);    

                    if (event.getClickCount() == 1 && (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.SECONDARY)){ 

                        final Long usuarioID = sessionManager.getUser().getId();     
                        final Long autorID = selectedDocument.getAuthor().getId();

                        if(usuarioID.equals(autorID)) IS_AUTHOR.set(true);              
                        else IS_AUTHOR.set(false);
                    }           

                    if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY){                        
                        sceneControl.changeChildScene(Path.SEE_FILE, getChildContainer());
                    }                        
                }
                else{                    
                    tableView.getSelectionModel().clearSelection();
                    IS_AUTHOR.set(false);
                }                
            });
            return tableRow;
        });
    }

    @Override
    public void initTableElements (){        
        final TreeItem<DocumentResponseFx> root = new RecursiveTreeItem<DocumentResponseFx>(
                                        documentList.getList(), RecursiveTreeObject::getChildren);
        documentList.getCountBinding().addListener((o, oldVal, newVal) -> {
            if (newVal != null){
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        tableView.setCurrentItemsCount(documentList.getList().size());
                    }
                    
                });
            }
        });
        tableView.setRoot(root);   
        tableView.setShowRoot(false);     
    }

    @Override
    public void filterSearchProperty(){
        searchTextfield.textProperty().addListener((o, oldVal, newVal) -> {
            final String newText = newVal.toLowerCase();
            tableView.setPredicate(
                (t) -> t.getValue().getTitle().toLowerCase().contains(newText)
                || t.getValue().getDate().toString().contains(newText)
                || t.getValue().getDocumentType().toString().contains(newText));
        });
    }

    @Override
    protected VBox initContextMenu(){     
        final double maxWidth = 1.7976931348623157E308;   
        final String btnCss = "menubutton";
        final VBox vBox = new VBox();
        final JFXButton downloadBtn = new JFXButton("Descargar");
        final JFXButton addBtn = new JFXButton("Añadir");
        final JFXButton editBtn = new JFXButton("Editar");
        final JFXButton deleteBtn = new JFXButton("Eliminar");
        
        downloadBtn.setOnAction(new EventHandler<ActionEvent> () {

            @Override
            public void handle(final ActionEvent arg0) {
                downloadDocument();                                
            }

        });

        addBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                  addBtnClick(event);
            }
            
        });
        editBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                editBtnClick(event);                
            }
            
        });
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                deleteBtnClick(event);                
            }
            
        });
                
        downloadBtnProperty(downloadBtn);
        addBtnProperty(addBtn);
        editBtnProperty(editBtn);
        deleteBtnProperty(deleteBtn);
        
        vBox.getStylesheets().addAll(gridPane.getStylesheets());
        vBox.getChildren().addAll(downloadBtn, addBtn, editBtn, deleteBtn);
        vBox.getChildren().forEach((btn) -> {
            final JFXButton b = (JFXButton) btn;
            b.getStyleClass().add(btnCss);         
            b.setMaxWidth(maxWidth);            
        });
        
        downloadBtn.getStyleClass().add("bottom-border");
        return vBox;
    }

    private void initialValues(final BooleanProperty memberProperty, final Label label){     
        final UserManager userManager = new UserManager();
        final IUserResponse user = sessionManager.getUser();
        final Role role = sessionManager.getRole();
        label.setText(role.toString());    
        memberProperty.set(userManager.isMemberOfCommission(user, role)
                    || userManager.isMemberOfCommission(user, Role.ADMINISTRATOR));                    
    }

    private void downloadBtnProperty (final JFXButton btn){
        btn.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
    }

    private void addBtnProperty (final JFXButton btn){
        btn.disableProperty().bind(IS_MEMEBER.not());         
    }

    private void editBtnProperty (final JFXButton btn){
        btn.disableProperty().bind(Bindings.createBooleanBinding(
                                        () -> !IS_MEMEBER.get() || tableView.getSelectionModel().isEmpty(), 
                                        tableView.getSelectionModel().selectedItemProperty()));        
    }

    private void deleteBtnProperty (final JFXButton boton){
        boton.disableProperty().bind(Bindings.createBooleanBinding(
                                        () -> (!IS_AUTHOR.get() && !IS_MEMEBER.get()) || tableView.getSelectionModel().isEmpty(),
                                        tableView.getSelectionModel().selectedItemProperty()));
    }

    private void clearTableSelectionProperty(final Pane... pane){
        for (final Pane p: pane){
            p.pressedProperty().addListener((o, oldVal, newVal) -> {
                if (newVal) tableView.getSelectionModel().clearSelection();
            });
        }
    }

    private void initDragAndDropPane(final Pane gridPane, final StackPane stackPane){
        if (IS_MEMEBER.get()){
            final double scale = 0.6;
            final Pane pane = (Pane) loadFxml(Path.DRAG_AND_DROP);         
            pane.maxHeightProperty().bind(stackPane.heightProperty().multiply(scale));
            pane.maxWidthProperty().bind(stackPane.widthProperty().multiply(scale));

            showDarkRegion(false);
            stackPane.getChildren().add(pane);
            pane.toFront();            

            gridPane.setOnDragEntered((event) -> {
                if (event.getGestureSource() != gridPane){
                    showDarkRegion(true);
                    stackPane.toFront();
                }                
                event.consume();
            });            

            gridPane.setOnDragExited((event) ->{                                            
                showDarkRegion(false);
                stackPane.toBack();
                event.consume();
            }); 
        }
        else{
            showDarkRegion(false);            
            stackPane.toBack();
        }
    }

    private void downloadDocument (){
        // TODO Auto-generated method stub
        final DownloadService downloadService = new DownloadService();
        downloadService.start();
    }

    private void seeDocuments(final DocumentWebClient documentWebClient, final ModelMapper modelMapper, final NotificationControl notificationControl, final DocumentList documentList, final SessionManager sessionManager){
        final Role role = sessionManager.getRole();
        final CommissionDocumentService commissionDocumentService = new CommissionDocumentService(documentWebClient, notificationControl, documentList, modelMapper, role);
        commissionDocumentService.start();
    }

    private void deleteDocument(){
        // TO DO
    }

    private void createDeleteDialog(final StackPane stackPane){
        final JFXDialogLayout dialogLayout = new JFXDialogLayout();        
        final JFXButton acceptBtn = new JFXButton("Aceptar");        
        final JFXButton cancelBtn = new JFXButton("Cancelar");   
        final JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);        

        stackPane.getChildren().stream().forEach((e) -> e.setVisible(false));
        showDarkRegion(true);
        stackPane.toFront();
        
        acceptBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                deleteDocument();
            }
            
        });
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                dialog.close();
                showDarkRegion(false);
                stackPane.toBack();
            }

        });  
        dialog.visibleProperty().addListener((o, oldVal, newVal) -> {
            if(!newVal){
                showDarkRegion(false);
                stackPane.toBack();
            }
        });

        addCssToDialog(DELETE_DIALOG_CSS_PATH, acceptBtn, cancelBtn);
        acceptBtn.getStyleClass().add("accept-btn");
        cancelBtn.getStyleClass().add("cancel-btn");
        dialogLayout.setHeading(new Label(DELETE_CONFIRMATION));        
        dialogLayout.setActions(acceptBtn, cancelBtn);        
        dialog.show(); 
    }

    private void addCssToDialog( final String cssPath, final JFXButton ... buttons){
        for (JFXButton boton : buttons){
            boton.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());    
            boton.setButtonType(JFXButton.ButtonType.RAISED);
        }
    }

    private void showDarkRegion(final boolean value){
        stackPane.setVisible(value);
        darkRegion.setVisible(value);
    }
}

