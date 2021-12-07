package com.solitontech.site.clientmvvm;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SiteView extends VBox{
	
	public SiteView() {
		super();
		createView();
	}

	private void createView() {
		Label labelNoOfSites = new  Label("No Of Sites");
		Spinner<Integer> spinnerNoOfSites = new Spinner<Integer>(1, 255, 1);
		Button buttonNoOfSites = new Button("Update");
		HBox hboxNoOfSites = new HBox(
				App.spacing, //spacing
				labelNoOfSites, 
				spinnerNoOfSites, 
				buttonNoOfSites
				);		

		Label labelFilePath = new  Label("File Path");
		TextField textfieldFilePath = new TextField();
		HBox hboxFilePath = new HBox(
				App.spacing, //spacing
				labelFilePath, 
				textfieldFilePath
				);
		
		this.getChildren().addAll(
				hboxNoOfSites, 
				hboxFilePath
				);
		this.setSpacing(App.spacing);
		this.setPadding(App.padding);
	}

}
