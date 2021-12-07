package com.solitontech.site.clientmvvm;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
	static final Insets padding = new Insets(10);
	static final double spacing = 10;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox siteview = new SiteView();
		
		Scene scene = new Scene(siteview);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
