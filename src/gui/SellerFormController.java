package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationExceptions;
import model.services.SellerService;

public class SellerFormController implements Initializable{

	//Entidade relacionada ao formulario SellerForm
	private Seller entity;
	
	private SellerService service;
	
	//Recebe as classes que vao receber o evento de disparo
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btnSave;
	@FXML
	private Button btnCancel;
	
	
	//
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void setSellerService(SellerService service) {
		this.service = service;
	}
	
	//Objetos que implementarem a inteface podem se inscrever para receber o disparo do evento
	public void subscribeDataChengeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	
	
	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();//Fecha a janela
			
		}
		catch (ValidationExceptions e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		
		
		
	}
	
	//Lanca para as classes inscritas na lista o disparo do evento
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			//Executa de acordo com a classe
			listener.onDataChenged();//Chama o evento da classe inscrita
		}
		
	}

	private Seller getFormData() {
		Seller obj = new Seller();
		//Instancia a excessao, mas nao lanca
		
		ValidationExceptions exception = new ValidationExceptions("Validation Error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		//Aceita apenas numeros inteiros
		Constraints.setTextFieldInteger(txtId);
		//Define o maximo de caracteres
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	//
	
	//Responsavel por pegar os dados da entity e popular o formulario SellerForm
	public void updateFormData() {
		//A caixa de texto travalha com String -> String.valueOf() converte os valores para texto
		if(entity == null) {
			throw new IllegalStateException("Entity was null!");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if(fields.contains("name"))
			labelErrorName.setText(errors.get("name"));
	}

}
