package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{

	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartments;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btnNew;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtnNewAction(ActionEvent event) 
	{
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}
	
	public void setDepartmentService(DepartmentService service) {
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
		tableViewDepartments.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	//Acessa o servico - carrega os departamentos na lista obsList
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException();
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartments.setItems(obsList);
	}
	
	//Ao criar uma janela de dialago deve informar a janela quem a criou
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			//Instanciar um novo stage (palco)
			Stage dialogStage = new Stage();
			
			
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(new DepartmentService());//Injecao de dependencia
			controller.updateFormData();
			
			
			//Configurar o palco
			dialogStage.setTitle("Enter Department data");//Titulo da janela
			dialogStage.setScene(new Scene(pane));//Instanciar uma nova cena
			//Bloquear o redimensionamento da janela
			dialogStage.setResizable(false);
			//Informar a janela pai
			dialogStage.initOwner(parentStage);
			//Definir a janela como Modal
			dialogStage.initModality(Modality.WINDOW_MODAL);//O usuario nao pode clicar em nada fora da janela ate encerra-la
			//Executar a janela
			dialogStage.showAndWait();//Exibe e aguarda ser fechada pelo usuario
			
			
		}catch(IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
