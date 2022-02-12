
package com.seg.view.controller.principal.commission;

import static com.seg.view.utils.NodeUtils.createSvg;
import static com.seg.view.utils.NodeUtils.createTooltip;
import static com.seg.view.utils.NodeUtils.scaleNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.seg.client.web.document.DocumentWebClient;
import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.document.fx.DocumentManageFx;
import com.seg.domain.enumeration.Action;
import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.enumeration.Role;
import com.seg.domain.observable.list.DocumentManageList;
import com.seg.view.concurrent.service.principal.commission.ManagerDocumentService;
import com.seg.view.controller.blueprint.Logout;
import com.seg.view.controller.blueprint.TableControl;
import com.seg.view.controller.principal.container.NotificationControl;
import com.seg.view.session.SessionManager;
import com.seg.view.utils.Dimension;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneControl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManagerTableController extends TableControl implements Logout{

    @FXML
    private GridPane gridPane;
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
    private SVGPath glassSvg;
    @FXML
    private SVGPath refreshSvg;
    @FXML
    private SVGPath approveSvg;
    @FXML
    private SVGPath disapproveSvg;
    @FXML
    private Button glassBtn;
    @FXML
    private JFXButton approveBtn;
    @FXML
    private JFXButton disapproveBtn;
    @FXML
    private JFXButton refreshBtn;    
    @FXML
    private JFXTextField searchTextfield;
    @FXML
    private JFXComboBox<TreeTableColumn<DocumentManageFx, ?>> sortComboBox;
    @FXML
    private JFXComboBox<Role> commissionComboBox;
    @FXML
    private JFXTreeTableView<DocumentManageFx> tableView;
    @FXML
    private TreeTableColumn<DocumentManageFx, Action> actionColumn;
    @FXML
    private TreeTableColumn<DocumentManageFx, String> titleColumn;
    @FXML
    private TreeTableColumn<DocumentManageFx, CommissionSummary> commissionColumn;
    @FXML
    private TreeTableColumn<DocumentManageFx, DocumentType> documentTypeColumn;
    @FXML
    private TreeTableColumn<DocumentManageFx, DocumentManageFx> downloadColumn;
       
    private static final String TABLE_TITLE = "Encargados";

    private final DocumentManageList documentManageList;
    private final DocumentWebClient documentWebClient;
    private final NotificationControl notificationControl;
    private final SceneControl sceneControl;
    private final ModelMapper modelMapper;
    
    private DocumentManageFx selectedDocument;         
    private SessionManager sessionManager;      

    @FXML
    private void refreshBtnClick(final ActionEvent event) {

    }
    
    @FXML
    void approveBtnClick(final ActionEvent event) {
        if(selectedDocument != null)
            approveDocument();
    }

    @FXML
    void disapproveBtnClick(final ActionEvent event) {
        if(selectedDocument != null)
            disapproveDocuemnt();
    }

    @FXML
    void tableViewClick(final MouseEvent event) {
        if (event.getClickCount() == 1 && event.getButton() == MouseButton.SECONDARY){
            super.createPopup().show(tableView, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
        }
    }

    @Override
    protected void init() {
        sessionManager = SessionManager.instance();
        seeDocuments(documentWebClient, modelMapper, notificationControl, documentManageList);
    }

    @Override
    public void initNodes() {                
        super.initSortComboBox(tableView, sortComboBox, Arrays.asList(
                                actionColumn, titleColumn, commissionColumn, documentTypeColumn));
        initCommissionComboBox(commissionComboBox);
        commissionLabel.setText(TABLE_TITLE);
    }

    @Override
    public void initProperties() {
        clearTableSelectionProperty(bottomPane, topPane);                       
        disableBtnProperty(approveBtn);
        disableBtnProperty(disapproveBtn);
        
        countLabel.textProperty().bind(Bindings.createStringBinding(
                                        () -> "(" + tableView.getCurrentItemsCount() + ")", 
                                        tableView.currentItemsCountProperty()));            
    }

    @Override
    public void initScales(){                
                                   
    }

    @Override
    public void restartFields() {
        selectedDocument = null;           
    }

    @Override
    public void initColumns(){        
        actionColumn.setCellValueFactory(c -> c.getValue().getValue().actionProperty());
        titleColumn.setCellValueFactory(c -> c.getValue().getValue().titleProperty());
        commissionColumn.setCellValueFactory(c -> c.getValue().getValue().commissionProperty());
        documentTypeColumn.setCellValueFactory(c -> c.getValue().getValue().documentTypeProperty());
        downloadColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<DocumentManageFx>(c.getValue().getValue()));      
        
        actionColumn.setCellFactory(col -> {
            return new TreeTableCell<DocumentManageFx, Action>(){
                  
                @Override
                protected void updateItem(final Action item, final boolean empty) {                    
                    super.updateItem(item, empty);
                    if (item == null || empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{                                                
                        final SVGPath svg; 
                        final Color color;

                        if (item != Action.NONE){                                                       
                            switch(item){
                                case ADD:
                                    color = Color.GREEN;
                                    break;
                                case DELETE:
                                    color = Color.RED;
                                    break;
                                case EDIT:
                                    color = Color.ROYALBLUE;
                                    break;                                                                
                                default:
                                    color = Color.BLACK;
                                    break;
                            }
                            
                            svg = createSvg(item.getSvg(), color);
                            scaleNode(35, Dimension.HEIGHT, getTreeTableRow(), svg);
                            createTooltip(svg, item.toString(), Pos.TOP_CENTER);
                            setGraphic(svg);
                        }
                        else{
                            setGraphic(null);
                            setText(null);
                        }  
                    }
                }   
                
            };
        });

        commissionColumn.setCellFactory(col -> {
            return new TreeTableCell<DocumentManageFx, CommissionSummary>(){

                @Override
                protected void updateItem(final CommissionSummary item, final boolean empty) {                    
                    super.updateItem(item, empty);
                    if (item == null || empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        final String commission = item.toString();                        
                        setText(commission);
                    }
                }
                
            };
        });
        
        documentTypeColumn.setCellFactory(col ->{
            return new TreeTableCell<DocumentManageFx, DocumentType>(){

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

        downloadColumn.getStyleClass().addAll("center-column");
        downloadColumn.setCellFactory(col -> {
            return new TreeTableCell<DocumentManageFx, DocumentManageFx>(){

                final JFXButton downloadBtn = new JFXButton();
                final String content = "M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z"; 
                final Color fill = Color.rgb(56, 136, 255);   
                final SVGPath svg = createSvg(content, fill);                                            

                @Override
                protected void updateItem(final DocumentManageFx item, final boolean empty) {                    
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
        tableView.setRowFactory(tableView -> {
            final TreeTableRow<DocumentManageFx> tableRow = new TreeTableRow<>();

            tableRow.setOnMouseClicked(event -> {      
                selectedDocument = tableRow.getItem();                   

                if (!tableRow.isEmpty()){     
                    // TO DO    
                    // sessionManager.setTableDocument(tableDocument);    
                    if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY){                        
                        sceneControl.changeChildScene(Path.SEE_FILE_MANAGER, getChildContainer());
                    }                        
                }
                else{                    
                    tableView.getSelectionModel().clearSelection();
                }                
            });
            return tableRow;
        });
    }

    @Override
    public void initTableElements (){        
        final TreeItem<DocumentManageFx> root = new RecursiveTreeItem<DocumentManageFx>(
                                        documentManageList.getList(), RecursiveTreeObject::getChildren);
        documentManageList.getCountBinding().addListener((o, oldVal, newVal) -> {
            if (newVal != null){
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        tableView.setCurrentItemsCount(documentManageList.getList().size());
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
                (t) -> t.getValue().getAction().toString().toLowerCase().contains(newText)                
                    || t.getValue().getTitle().toLowerCase().contains(newText)
                    || t.getValue().getStatus().toString().toLowerCase().contains(newText)
                    || t.getValue().getCommission().toString().toLowerCase().contains(newText)
                    || t.getValue().getDocumentType().toString().contains(newText));
        });
    }

    @Override
    protected VBox initContextMenu(){
        final String css = "menubutton";
        final VBox vBox = new VBox();
        final JFXButton downloadBtn = new JFXButton("Descargar");
        final JFXButton approveBtn = new JFXButton("Aprobar");
        final JFXButton disapproveBtn = new JFXButton("Denegar");        

        downloadBtn.setOnAction(new EventHandler<ActionEvent> () {

            @Override
            public void handle(final ActionEvent arg0) {
                downloadDocument();
            }

        });

        approveBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                approveBtnClick(event);
            }
            
        });
        disapproveBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                disapproveBtnClick(event);
            }
            
        });

        disableBtnProperty(approveBtn);
        disableBtnProperty(disapproveBtn);

        vBox.getChildren().addAll(downloadBtn, approveBtn, disapproveBtn);
        vBox.getChildren().forEach((btn) -> {
            btn.getStyleClass().add(css);
        });
        
        return vBox;
    }

    private void disableBtnProperty (final JFXButton button){
        button.disableProperty().bind(Bindings.createBooleanBinding(
                                        () -> tableView.getSelectionModel().isEmpty(), 
                                        tableView.getSelectionModel().selectedItemProperty()));        
    }

    private void clearTableSelectionProperty(final Pane... pane){
        for (final Pane p: pane){
            p.pressedProperty().addListener((o, oldVal, newVal) -> {
                if (newVal) tableView.getSelectionModel().clearSelection();
            });
        }
    }

    private void downloadDocument(){
        // TO DO
    }

    private void disapproveDocuemnt(){
        // TO DO
    }

    private void approveDocument(){
        // TO DO
    }

    private void initCommissionComboBox (final JFXComboBox<Role> comboBox){
        final List <Role> roles = new ArrayList<>(List.of(Role.values()));
        roles.remove(Role.GUEST);
        roles.remove(Role.ADMINISTRATOR);

        comboBox.getItems().addAll(roles);
        comboBox.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null){
                if (newVal == Role.ALL) tableView.setPredicate((t) -> true);
                else{
                    tableView.setPredicate(
                        (t) -> t.getValue().getCommission().getRole() == newVal);
                }          
            }
        });
    }

    private void seeDocuments(final DocumentWebClient documentWebClient, final ModelMapper modelMapper, final NotificationControl notificationControl, final DocumentManageList documentManageList){
        final ManagerDocumentService commissionDocumentService = new ManagerDocumentService(documentWebClient, notificationControl, documentManageList, modelMapper);
        commissionDocumentService.start();
    }
}

