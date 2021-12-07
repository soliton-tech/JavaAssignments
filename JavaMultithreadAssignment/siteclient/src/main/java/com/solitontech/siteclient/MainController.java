package com.solitontech.siteclient;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import com.solitontech.siteserver.proto.Result;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;

public class MainController implements Initializable{
	
	private MainService service;
	
	@FXML Spinner<Integer> spinNoOfSites;
	@FXML Button btnUpdateSiteInfo;
	@FXML TextField txtFilePath;
	@FXML Label lblMessage;
	@FXML Button btnBurnData;
	@FXML Button btnValidateData;
	@FXML ProgressBar progStatus;

	List<Control> controls;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		service = new MainService();
		service.fetchNoOfSites();
		
		controls = List.of(spinNoOfSites, btnUpdateSiteInfo, txtFilePath, btnBurnData, btnValidateData);
		
		lblMessage.setVisible(false);
		progStatus.setVisible(false);
		
		btnUpdateSiteInfo.setVisible(false);
		
		var spinNoOfSitesValueFactory = new IntegerSpinnerValueFactory(1, 255);
		spinNoOfSites.setValueFactory(spinNoOfSitesValueFactory);
		spinNoOfSites.valueProperty().addListener(
				(observable, oldValue, newValue) -> spinNoOfSitesValueChanged(observable, oldValue, newValue)
				);		
		spinNoOfSitesUpdateValue();
		btnNoOfSitesSetVisible();
	}
	
	private void spinNoOfSitesValueChanged(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
		btnNoOfSitesSetVisible();
	}

	private void spinNoOfSitesUpdateValue() {
		spinNoOfSites.getValueFactory().setValue(service.getNoOfSites());
	}
	
	@FXML
	private void btnUpdateSiteInfoOnAction(ActionEvent event) {
		disable(true);
		
		service.setNoOfSites(spinNoOfSites.getValue());
		spinNoOfSitesUpdateValue();
		btnNoOfSitesSetVisible();

		disable(false);
	}

	private void btnNoOfSitesSetVisible() {
		btnUpdateSiteInfo.setVisible(service.getNoOfSites() != spinNoOfSites.getValue());
	}

	@FXML
	private void btnBurnDataOnAction(ActionEvent event) {
		CompletableFuture.runAsync( () -> {
			disable(true);
			lblMessageUpdateText("Burning in progress...");
			Result response = service.burnData(txtFilePath.getText());

			String message = "";
			if(response.getFailedSitesCount() == 0) {
				message = "All the sites are written successfully. Total time taken: "
						+ response.getElapsedTimeMs()
						+ " ms";
			} else {
				message = "The following sites are failed "
						+ response.getFailedSitesList().toString()
						+ ". Total time taken: "
						+ response.getElapsedTimeMs()
						+ " ms";
			}
			
			lblMessageUpdateText(message);
			
			disable(false);
		});
	}

	private void lblMessageUpdateText(String message) {
		Platform.runLater(() -> {
			lblMessage.setVisible(true);
			lblMessage.setText(message);
		});
	}
	
	@FXML
	private void btnValidateDataOnAction(ActionEvent event) {
		CompletableFuture.runAsync( () -> {
			disable(true);
			lblMessageUpdateText("Validation in progress...");
			Result response = service.validateData(txtFilePath.getText());

			String message = "";
			if(response.getFailedSitesCount() == 0) {
				message = "All the sites are written successfully. Total time taken: "
						+ response.getElapsedTimeMs()
						+ " ms";
			} else {
				message = "The following sites are failed "
						+ response.getFailedSitesList().toString()
						+ ". Total time taken: "
						+ response.getElapsedTimeMs()
						+ " ms";
			}
			
			lblMessageUpdateText(message);
			
			disable(false);
		});
	}
	
	private void disable(boolean disable) {
		Platform.runLater(() -> {
			controls.stream().forEach(control -> control.setDisable(disable));
			progStatus.setVisible(disable);
			progStatus.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);			
		});
	}

}
