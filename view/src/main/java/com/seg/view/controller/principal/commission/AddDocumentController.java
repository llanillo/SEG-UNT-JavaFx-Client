package com.seg.view.controller.principal.commission;

import static com.seg.view.utils.DocUtils.sizeToString;
import static com.seg.view.utils.NodeUtils.createTooltip;
import static com.seg.view.utils.NodeUtils.scaleNode;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTooltip;
import com.jfoenix.skins.JFXComboBoxListViewSkin;
import com.jfoenix.validation.RequiredFieldValidator;
import com.seg.client.web.document.DocumentWebClient;
import com.seg.client.web.user.UserWebClient;
import com.seg.configuration.event.DragAndDrop;
import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.document.dto.DocumentProperties;
import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.manager.impl.DocumentManager;
import com.seg.domain.observable.list.DocumentList;
import com.seg.domain.observable.list.UserBasicList;
import com.seg.domain.user.dto.UserBasic;
import com.seg.domain.user.fx.UserBasicFx;
import com.seg.view.concurrent.service.principal.commission.UserTableService;
import com.seg.view.concurrent.service.principal.general.UploadDocumentService;
import com.seg.view.controller.blueprint.EditorControl;
import com.seg.view.controller.blueprint.Logout;
import com.seg.view.controller.principal.container.NotificationControl;
import com.seg.view.session.SessionManager;
import com.seg.view.utils.Dimension;
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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddDocumentController extends EditorControl implements Logout, ApplicationListener<DragAndDrop>, Ordered, FxmlLoader{

    @FXML
    private GridPane gridPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Pane coAuthorPane;
    @FXML
    private RowConstraints documentRow;
    @FXML
    private Label commissionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private JFXTextField titleTextfield;
    @FXML
    private JFXTextField authorTextfield;
    @FXML
    private JFXTextArea descriptionTextarea;
    @FXML
    private TextField documentTextfield;
    @FXML
    private TextField sizeTextfield;
    @FXML
    private JFXComboBox<DocumentType> documentTypeCombo;
    @FXML
    private JFXComboBox<UserBasicFx> coAuthorCombo;
    @FXML
    private JFXButton sendBtn;
    @FXML
    private JFXButton resetBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private SVGPath sendSvg;
    @FXML
    private SVGPath removeSvg;
    
    private static final String IMAGE_PATH = "/com/seg/image/extra";
    private static final String REQUIRED_FIELD = "Este campo es obligatorio";
    private static final ObservableList<UserBasicFx> COAUTHORS = FXCollections.observableArrayList();  

    private static final BooleanProperty FILE_SELECTED = new SimpleBooleanProperty(false);         
    private static final StringProperty COAUTHOR_TOOLTIP_TEXT = new SimpleStringProperty();  
    
    private static final double SVG_SCALE = 50;
    private static final int MAX_COMBO_ROWS = 8;    

    private final NotificationControl notificationControl;
    private final UserBasicList userBasicList;
    private final UserWebClient userWebClient;
    private final DocumentWebClient documentWebClient;
    private final SceneControl sceneControl;
    private final DocumentList documentList;
    private final ModelMapper modelMapper;

    private SessionManager sessionmanager;              
    private File file; 

    @FXML
    private void backBtnClick(final ActionEvent event) {        
        sceneControl.changeChildScene(Path.COMMISSION_TABLE, getChildContainer());
    }

    @FXML
    private void sendBtnClick(final ActionEvent event) {                                                
        uploadDocument(documentWebClient, documentList, notificationControl, modelMapper, sessionmanager);
        backBtnClick(event);
    }

    @FXML
    void resetBtnClick(final ActionEvent event) {   
        FILE_SELECTED.set(false);     
        COAUTHORS.clear();
        descriptionTextarea.clear();
    }

    @FXML
    void removeBtnClick(final ActionEvent event) {
        FILE_SELECTED.set(false);
    }
    
    @Override
    public void onApplicationEvent(final DragAndDrop event) {        
        FILE_SELECTED.set(true);   
        file = event.getFile();      
        documentTextfield.setText(file.getName());
        sizeTextfield.setText(sizeToString(file.length()));
    }

    @Override
    public int getOrder() {        
        return 1;
    }

    @Override
    protected void init() {
        path = Path.ADD_FILE;
        sessionmanager = SessionManager.instance();
        seeUsers(userWebClient, userBasicList, notificationControl, modelMapper);
    }

    @Override
    public void initNodes(){
        initLabels(dateLabel, commissionLabel, authorTextfield);
        initComboAuthors(coAuthorCombo, COAUTHORS);    
        initAuthorsCircles(coAuthorPane);
        initDragAndDropPane(stackPane);        
    }

    @Override
    protected void initProperties() {
        removeBtn.visibleProperty().bind(FILE_SELECTED);
        documentTextfield.visibleProperty().bind(FILE_SELECTED);
        sizeTextfield.visibleProperty().bind(FILE_SELECTED);        
        
        removeBtn.managedProperty().bind(FILE_SELECTED);
        documentTextfield.managedProperty().bind(FILE_SELECTED);        
        sizeTextfield.managedProperty().bind(FILE_SELECTED);         
        
        final double file_row_height = 4;    
        documentRow.percentHeightProperty().bind(Bindings.createDoubleBinding(
                                            () -> FILE_SELECTED.get() ? file_row_height : 0, FILE_SELECTED));        

        validateProperty(documentTypeCombo);
        validateProperty(titleTextfield);

        sendBtn.disableProperty().bind(
                    documentTypeCombo.valueProperty().isNull()
                    .or(titleTextfield.textProperty().isEmpty()
                    .or(FILE_SELECTED.not())));
    }

    @Override
    protected void initScales() {
        final Dimension dimension = Dimension.HEIGHT;
        scaleNode(SVG_SCALE, dimension, sendBtn, sendSvg);
        scaleNode(SVG_SCALE, dimension, removeBtn, removeSvg);
    }

    @Override
    protected void fieldFormat() {
        final RequiredFieldValidator rfv = new RequiredFieldValidator(REQUIRED_FIELD);        
        titleTextfield.getValidators().add(rfv);
        documentTypeCombo.getValidators().add(rfv);
    }

    @Override
    public void restartFields() {
        file = null;        
        FILE_SELECTED.set(false);        
    }

    private void initComboAuthors(final JFXComboBox<UserBasicFx> comboBox, final ObservableList<UserBasicFx> userList){
        comboBox.setItems(userBasicList.getList());  
        comboBox.getEditor().textProperty().addListener((o, oldVal, newVal) -> {
            final UserBasicFx user = comboBox.getSelectionModel().getSelectedItem();
            final TextField editor = comboBox.getEditor();
            final String newText = newVal.toLowerCase();

            if (user == null){                
                final Predicate <UserBasicFx> filtro = (t) -> t.toString().toLowerCase().contains(newText)
                                                                || t.getEmail().toLowerCase().contains(newText);

                userBasicList.filterProperty().set(filtro);
                final int filtered = comboBox.getItems().size();                   
                
                if (filtered == 0 || editor.getText().isBlank()){
                    comboBox.hide();
                    comboBox.setVisibleRowCount(MAX_COMBO_ROWS);                        
                }
                else if (filtered < MAX_COMBO_ROWS) changeComboVisibleRows(filtered, comboBox);
                else changeComboVisibleRows(MAX_COMBO_ROWS, comboBox);
            }
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null){                
                Platform.runLater(() -> {
                    if (!userList.contains(newVal)) userList.add(newVal);
                    else userList.remove(newVal);
                    comboBox.getSelectionModel().clearSelection();
                });           
            }
        });

        userList.addListener((ListChangeListener<UserBasicFx>) change -> {
            while (change.next()){                                                
                if (change.wasAdded() || change.wasRemoved())
                    updateAuthors(coAuthorPane, userList);
            }            
        });

        comboBox.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                comboBox.getSelectionModel().clearSelection();
                comboBox.getEditor().clear();
            }
        });
        
        comboBox.setOnShown((event) -> {            
            final int filtered = comboBox.getItems().size();            
            if (filtered == 0) comboBox.hide();
        });


        comboBox.setConverter(new StringConverter<UserBasicFx>() {

            @Override
            public UserBasicFx fromString(final String item) {                                
                return null;
            }

            @Override
            public String toString(final UserBasicFx user) {
                if (user == null) return null;
                return user.toString();
            }
            
        });

        /*
            Possible javafx bug
            https://bugs.openjdk.java.net/browse/JDK-8209991
            https://bugs.openjdk.java.net/browse/JDK-8087549
        */
        final JFXComboBoxListViewSkin<UserBasicFx> comboSkin = new JFXComboBoxListViewSkin<UserBasicFx>(comboBox);
        comboSkin.getPopupContent().addEventFilter(KeyEvent.ANY, (event) -> {
            if(event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
        comboBox.setSkin(comboSkin);

        comboBox.setCellFactory((listView) -> createComboCellFactory(COAUTHORS));
        comboBox.setButtonCell(createComboCellFactory(COAUTHORS));
    }
    
    private ListCell<UserBasicFx> createComboCellFactory(final ObservableList<UserBasicFx> userList){
        return new ListCell <UserBasicFx>(){
            
            @Override
            protected void updateItem(final UserBasicFx item, final boolean empty) {                    
                super.updateItem(item, empty);
                if(item == null || empty){
                    setText(null);
                    setGraphic(null);
                }
                else{
                    if (userList.contains(item))
                        setText(item.toString() + " (Remove)");
                    else
                        setText(item.toString());
                }
            } 

        };
    }

    private void changeComboVisibleRows (final int max, final JFXComboBox<UserBasicFx> comboBox){
        comboBox.hide();
        comboBox.setVisibleRowCount(max);
        comboBox.show();
    }

    private void initDragAndDropPane(final StackPane stackPane){
        final Pane pane = (Pane) loadFxml(Path.DRAG_AND_DROP);
        pane.prefHeightProperty().bind(stackPane.prefHeightProperty());
        pane.prefWidthProperty().bind(stackPane.prefWidthProperty());
        stackPane.getChildren().add(pane);    
    }

    private void initAuthorsCircles(final Pane pane){
        Platform.runLater(() -> {
            final double radius = pane.getHeight() * 1.05/2;        
            final double xOffset = 2 * radius * 0.8;      
            final double maxDistance = pane.getWidth() - 2 * xOffset;      
            final double widthBorder = 1;
            double currentDistance = 0;    
            int viewOrder = 1;

            for (int i = 0; i < viewOrder; i++) {
                if (currentDistance < maxDistance){       
                    final Circle circle = new Circle();             
                    currentDistance = xOffset * i;                                                                                                
                    pane.getChildren().add(circle);
                    circle.setRadius(radius);                    
                    circle.setLayoutX(currentDistance);
                    circle.setStroke(Color.BLACK);   
                    circle.setStrokeWidth(widthBorder);
                    circle.setViewOrder(viewOrder++);
                    circle.layoutYProperty().bind(pane.heightProperty().divide(2));  
                    circle.hoverProperty().addListener((o, oldVal, newVal) -> {
                        final double zOrden = circle.getViewOrder();
                        if (newVal) circle.setViewOrder(-1);
                        else circle.setViewOrder(zOrden);
                    });
                    circle.setVisible(false);                                                                       
                }
                else break;         
            }
        });
    }

    private void updateAuthors (final Pane pane, final ObservableList<UserBasicFx> userList){
        final int paneSize = pane.getChildren().size();        
        final int listSize = userList.size();                        

        for (int i = 0; i < paneSize; i++) {
            final Circle circle = (Circle) pane.getChildren().get(i);    

            if (i < listSize && i != paneSize - 1){
                final UserBasicFx author = userList.get(i);
                final JFXTooltip tooltip = createTooltip(author.toString(), Pos.TOP_CENTER);
                final Image icono = new Image(IMAGE_PATH + "/user.jpg");   
                addToCircle(icono, circle, tooltip);
            }
  
            else if(listSize == paneSize && i == paneSize - 1){   
                final UserBasicFx autor = userList.get(i);  
                final JFXTooltip tooltip = createTooltip(Pos.TOP_CENTER);
                final Image icon = new Image(IMAGE_PATH + "/extra/no_image.png");   
                tooltip.textProperty().bind(COAUTHOR_TOOLTIP_TEXT);                                                                     
                COAUTHOR_TOOLTIP_TEXT.set(autor.toString());              
                addToCircle(icon, circle, tooltip);  
            }
            
            else if(listSize > paneSize){   
                final StringBuilder authorNames = new StringBuilder();
                final int maxVisibleAuthors = 5; 
                int count = 0;            

                for (int j = paneSize - 1; j < listSize; j++) {     
                    if (count++ < maxVisibleAuthors){
                        final String name = userList.get(j).toString();
                        authorNames.append(name);
                        authorNames.append("\n");                        
                    }               
                    else{
                        final String last = "...";
                        authorNames.append(last);
                        break;
                    }                     
                }                        
                COAUTHOR_TOOLTIP_TEXT.set(authorNames.toString());                                                
            }

            else{
                JFXTooltip.uninstall(circle);
                circle.setVisible(false);
            }
        }
    }

    private void addToCircle (final Image image, final Circle circle, final JFXTooltip tooltip){                                           
        circle.setFill(new ImagePattern(image));                
        circle.setVisible(true);                        
        JFXTooltip.install(circle, tooltip);                    
    }

    private void initLabels(final Label dateLabel, final Label commissionLabel, final JFXTextField authorTextfield){        
        final LocalDateTime currentDate = LocalDateTime.now(); 
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");                    
                
        dateLabel.setText(currentDate.format(format));
        commissionLabel.setText("(" + sessionmanager.getRole().toString() + ")");
        authorTextfield.setText(sessionmanager.getUser().toString());
        documentTypeCombo.getItems().addAll(DocumentType.values());
    }

    private void seeUsers(final UserWebClient userWebClient, final UserBasicList userBasicList, final NotificationControl notificationControl, final ModelMapper modelmapper){
        final UserTableService servicioUsuariosBasicos = new UserTableService(userWebClient, notificationControl, userBasicList, modelmapper);
        servicioUsuariosBasicos.start();
    }

    private void uploadDocument(final DocumentWebClient documentWebClient, final DocumentList documentList, 
                final NotificationControl notificationControl, final ModelMapper modelMapper, final SessionManager sessionManager){
        final DocumentManager documentManager = new DocumentManager();
        final CommissionSummary commissionSummary = new CommissionSummary(null, sessionmanager.getRole());
        final UserBasic userBasic = modelMapper.map(sessionmanager.getUser(), UserBasic.class);
        final DocumentProperties documentProperties = documentManager.create(userBasic, getTxtTitle(), getTxtDescription(), LocalDateTime.now(), getDocumentType(), commissionSummary, file);
        final UploadDocumentService uploadDocumentSerivce = new UploadDocumentService(documentWebClient, notificationControl, documentProperties, documentList, modelMapper);
        uploadDocumentSerivce.start();
    }

    private String getTxtTitle(){
        return titleTextfield.getText().trim();
    }
    
    private String getTxtDescription(){
        return descriptionTextarea.getText().trim();
    }
    
    private DocumentType getDocumentType(){
        return documentTypeCombo.getValue();
    }
}
