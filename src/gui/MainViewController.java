package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", 
				(SellerListController controller) ->{
					controller.setSellerService(new SellerService());
					controller.updateTableView();
				});
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml",
			(DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});//Acao de inicializacao do controle da classe de departamentos (funcao lambda)
	}

	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});//A tela About leva uma funcao lambda vazia pois nao precisa inicializar nada
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	//Funcao para abrir outras telas
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			 // Carrega o novo arquivo FXML que define a interface do usuário
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			// Carrega e instancia o novo layout principal da cena a partir do arquivo FXML
			VBox newVBox = loader.load();
			
			//Cira uma nova cena
			Scene mainScene = Main.getMainScene();
			//Pega o conteudo dentro do ScrollPane da MainView - Pega todo o VBox 
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			//
			//Salva apenas o filho menubar do VBox da MainView
			Node mainMenu = mainVBox.getChildren().get(0);
			//Limpa a tela princiapal - apaga os itens do VBox
			mainVBox.getChildren().clear();
			//Adiociona o menubar devolta a tela principal
			mainVBox.getChildren().add(mainMenu);
			//Adiciona os novos itens (filhos) da nova tela a tela principal - dentro do VBox
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			
			//Comandos para ativar a funcao passado no segundo parametro
			T controller = loader.getController();//O getController() retorna um controlador do tipo //instanciado no segundo parametro
			initializingAction.accept(controller);						  
			
		}catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading View", e.getMessage(), AlertType.ERROR);
			
		}
		
	}
	
	private synchronized void loadView2(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			DepartmentListController controller = loader.getController();
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
			
		}catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading View", e.getMessage(), AlertType.ERROR);
			
		}
		
	}

}
