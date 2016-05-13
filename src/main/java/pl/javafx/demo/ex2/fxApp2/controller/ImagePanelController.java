package pl.javafx.demo.ex2.fxApp2.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import pl.javafx.demo.ex2.fxApp2.dataprovider.ImageVo;
import pl.javafx.demo.ex2.fxApp2.dataprovider.data.IImageViewProvider;
import pl.javafx.demo.ex2.fxApp2.model.ImageSearchModel;

public class ImagePanelController {

	private static final Logger LOG = Logger.getLogger(ImagePanelController.class);
	private final ImageSearchModel model = new ImageSearchModel();
	private final IImageViewProvider imageViewProvider = IImageViewProvider.INSTANCE;

	@FXML
	private ResourceBundle resources;

	@FXML
	Button previousImageButton;

	@FXML
	Button nextImageButton;

	@FXML
	Button slideShowButton;

	@FXML
	Button slideShowEndButton;

	@FXML
	private URL location;

	@FXML
	Button browseDirectoryButton;

	@FXML
	ImageView imageView;

	@FXML
	AnchorPane panel;

	@FXML
	TableView<ImageVo> resultTable;

	@FXML
	TableColumn<ImageVo, String> idColumn;

	@FXML
	TableColumn<ImageVo, String> nameColumn;
	
	int count = 0;

	Timer timer;

	boolean isSlideShowStarted;

	public ImagePanelController() {
	};

	@FXML
	private void initialize() {
		initializeResultTable();
		resultTable.itemsProperty().bind(model.resultProperty());
		previousImageButton.disableProperty().bind(resultTable.getSelectionModel().selectedItemProperty().isNull());
		nextImageButton.disableProperty().bind(resultTable.getSelectionModel().selectedItemProperty().isNull());
		slideShowEndButton.disableProperty().bind(Bindings.size(resultTable.getItems()).isEqualTo(0));
		slideShowButton.disableProperty().bind(Bindings.size(resultTable.getItems()).isEqualTo(0));

	}

	@FXML
	public void searchButtonAction(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File directory = directoryChooser.showDialog(panel.getScene().getWindow());
		model.setResult(imageViewProvider.searchForImages(directory));
	}

	private void initializeResultTable() {

		idColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getId().toString()));
		nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFile().getName()));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		resultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImageVo>() {

			@Override
			public void changed(ObservableValue<? extends ImageVo> observable, ImageVo oldValue, ImageVo newValue) {
				Task<Void> imageTask = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						Image img = new Image("file:" + newValue.getFile().toString());
						LOG.debug(img);
						imageView.setImage(img);
						imageView.autosize();
						return null;
					}
				};
				new Thread(imageTask).start();
			}

		});

	}

	@FXML
	public void nextButtonAction(ActionEvent event) {

		Long id = resultTable.getSelectionModel().selectedItemProperty().getValue().getId();
		int size = resultTable.getItems().size();
		if (id == size - 1) {
			id = -1L;
		}
		resultTable.scrollTo(resultTable.getItems().get((++id).intValue()));
		resultTable.getSelectionModel().select((id).intValue());
	}

	@FXML
	public void previousButtonAction(ActionEvent event) {

		Long id = resultTable.getSelectionModel().selectedItemProperty().getValue().getId();
		int size = resultTable.getItems().size();
		if (id == 0) {
			id = (long) size;
		}
		resultTable.scrollTo(resultTable.getItems().get((--id).intValue()));
		resultTable.getSelectionModel().select((id).intValue());
	}

	@FXML
	public void slideShowButton(ActionEvent event) {

		LOG.debug("backgroundTask.run() called");
		browseDirectoryButton.setDisable(true);

		long delay = 2000; // update once per 2 seconds.
		if (!isSlideShowStarted) {
			resultTable.setMouseTransparent(true);
			resultTable.setFocusTraversable(true);
			resultTable.getSelectionModel().select(null);
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					Platform.runLater(new Runnable() {
						public void run() {
							isSlideShowStarted = true;
							Image img = new Image("file:" + resultTable.getItems().get(count++).getFile().toString());
							LOG.debug(img);
							imageView.setImage(img);
							if (count >= resultTable.getItems().size()) {
								count = 0;
							}
						}
					});

				}
			}, 0, delay);
		}
	}

	@FXML
	public void slideShowEndButton(ActionEvent event) {

		if(isSlideShowStarted)
		{
		timer.cancel();
		resultTable.setMouseTransparent(false);
		resultTable.setFocusTraversable(false);
		browseDirectoryButton.setDisable(false);
		isSlideShowStarted = false;
		resultTable.getSelectionModel().select(0);
		count = 0;
		}

	}
}
