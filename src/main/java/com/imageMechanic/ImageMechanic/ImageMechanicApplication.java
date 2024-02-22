package com.imageMechanic.ImageMechanic;

import com.imageMechanic.ImageMechanic.UI.GUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class ImageMechanicApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(ImageMechanicApplication.class, args);
		SwingUtilities.invokeLater(
				() -> {
					GUI gui = new GUI();
					gui.setVisible(true);
				});
	}

}
