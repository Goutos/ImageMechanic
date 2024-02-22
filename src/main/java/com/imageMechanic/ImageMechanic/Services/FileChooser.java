package com.imageMechanic.ImageMechanic.Services;

import javax.swing.*;

public class FileChooser {
  private JFrame parent;

  public FileChooser(JFrame parent) {
    this.parent = parent;
  }

  public String showFileChooser() {
    JFileChooser chooser = new JFileChooser();

    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int returnVal = chooser.showOpenDialog(parent);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      return chooser.getSelectedFile().getAbsolutePath();
    }
    return "";
  }
}
