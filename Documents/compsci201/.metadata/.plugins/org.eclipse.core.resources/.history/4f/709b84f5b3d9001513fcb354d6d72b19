package View;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class CommandHistoryViewer<E> implements HistoryViewer {
	
	List<E> myHistory;


	public Region getPane() {

		ScrollPane scrollDisplay = new ScrollPane();
		Pane innerDisplay = new Pane();
		scrollDisplay.setContent(innerDisplay);

		innerDisplay.setStyle("-fx-background-color: white;");

		for (E node : myHistory) {
			innerDisplay.getChildren().add(createText(node));
		}
		Scene scene = new Scene(innerDisplay, 200,200);
		return scrollDisplay;
	}

	private Text createText(E commandNode) {
		Text newCommand = new Text(commandNode.toString());
		// Code for ActionEvent when clicked
		return newCommand;
	}



	@Override
	public void getHistory(List history) {
		// TODO Auto-generated method stub
		myHistory = history;
		
	}

	@Override
	public String getClickedCommand(ActionEvent e) {
		// TODO Auto-generated method stub
		return null;
	}}
