package gui;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService service;

	@FXML
	private TableView<Seller> tableViewSellers;

	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;

	@FXML
	private TableColumn<Seller, Seller> tableCollumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button btnNew;

	private ObservableList<Seller> obsList;

	@FXML
	public void onBtnNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller();

		//createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSellers.prefHeightProperty().bind(stage.heightProperty());

	}

	// Acessa o servico - carrega os departamentos na lista obsList
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException();
		}
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSellers.setItems(obsList);
		//initEditButtons();
		//initRemoveButtons();
	}

	// Ao criar uma janela de dialago deve informar a janela quem a criou
//	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			Pane pane = loader.load();
//
//			// Instanciar um novo stage (palco)
//			Stage dialogStage = new Stage();
//
//			SellerFormController controller = loader.getController();
//			controller.setSeller(obj);
//			controller.setSellerService(new SellerService());// Injecao de dependencia
//			controller.subscribeDataChengeListener(this);// Inscreve-se para receber o evento
//			controller.updateFormData();
//
//			// Configurar o palco
//			dialogStage.setTitle("Enter Seller data");// Titulo da janela
//			dialogStage.setScene(new Scene(pane));// Instanciar uma nova cena
//			// Bloquear o redimensionamento da janela
//			dialogStage.setResizable(false);
//			// Informar a janela pai
//			dialogStage.initOwner(parentStage);
//			// Definir a janela como Modal
//			dialogStage.initModality(Modality.WINDOW_MODAL);// O usuario nao pode clicar em nada fora da janela ate
//															// encerra-la
//			// Executar a janela
//			dialogStage.showAndWait();// Exibe e aguarda ser fechada pelo usuario
//
//		} catch (IOException e) {
//			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
//		}
//	}

	@Override
	public void onDataChenged() {
		updateTableView();

	}
//
////	private void initEditButtons() {
////		tableCollumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
////		tableCollumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
////			private final Button button = new Button("edit");
////
////			@Override
////			protected void updateItem(Seller obj, boolean empty) {
////				super.updateItem(obj, empty);
////
////				if (obj == null) {
////					setGraphic(null);
////					return;
////				}
////
////				setGraphic(button);
////				button.setOnAction(
////						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
////			}
////		});
////	}// End initEditButtons
////
////	private void initRemoveButtons() {
////		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
////		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
////			private final Button button = new Button("remove");
////
////			@Override
////			protected void updateItem(Seller obj, boolean empty) {
////				super.updateItem(obj, empty);
////				if (obj == null) {
////					setGraphic(null);
////					return;
////				}
////				setGraphic(button);
////				button.setOnAction(event -> removeEntity(obj));
////			}
////		});
////	}//end initRemoveButtons

	private void removeEntity(Seller obj) {
		//Optional -> objeto que carrega outro objeto dentro dele
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
			
	}

}
