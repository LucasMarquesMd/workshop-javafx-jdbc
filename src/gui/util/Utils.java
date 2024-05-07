package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	//Classe para obter o stage onde o controle que iniciou o evento esta
	public static Stage currentStage(ActionEvent event) {
		
		//getSource() -> retorna o objeto origem que iniciou o evento
		//(Node) -> casting devido o objeto retornado ser do tipo node (botoes, textfilds,...)
		//getScene() -> retorna a cena do objeto origem
		//getWindoe() -> Super-classe da Stage, retorna o objeto janela que a cena pertence
		//(Stage) -> Down-casting pois queremos o Stage 
		
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
}
