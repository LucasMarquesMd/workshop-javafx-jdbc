package gui.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
	
	//Retorna um Integer dado uma string
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
			
		}catch (NumberFormatException e) {
			//Retorna nulo caso a conversao falhe
			return null;
		}
		
	}
	
	public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(format);
				
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
						
					}else {
						setText(sdf.format(item));
					}
				}
			};
			
			return cell;
		});
	}//end
	
	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					}else {
						Locale.setDefault(Locale.US); 
						setText(String.format("%."+decimalPlaces+"f", item));
					}
				}
			};
			return cell;
		});
	}//end
	
	
	public static void formatDatePicker(DatePicker datePicker, String format) {
		datePicker.setConverter(new StringConverter<LocalDate>() {
			
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
			
			{
				datePicker.setPromptText(format.toLowerCase());
			}
			
			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				}else {
					return "";
				}
			}
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				}else {
					return null;
				}
			}
		});
	}//end
	
	
}
