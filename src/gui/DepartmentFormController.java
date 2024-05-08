package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{

	//Entidade relacionada ao formulario DepartmentForm
	private Department entity;
	
	
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
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	
	
	@FXML
	public void onBtnSaveAction() {
		System.out.println("onBtnSaveAction");
	}
	
	@FXML
	public void onBtnCancelAction() {
		System.out.println("onBtnSaveAction");
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
	
	
	//Responsavel por pegar os dados da entity e popular o formulario DepartmentForm
	public void updateFormData() {
		//A caixa de texto travalha com String -> String.valueOf() converte os valores para texto
		if(entity == null) {
			throw new IllegalStateException("Entity was null!");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
