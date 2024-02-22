package com.imageMechanic.ImageMechanic.Services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

@Service
public class FileGrabber {
  public static String[] FileGrabberMethod(String selectedFile) {
    File folder = new File(selectedFile);
    File[] listOfFiles = folder.listFiles();

    ArrayList<String> filenames = new ArrayList<>();
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        String filename = listOfFiles[i].getName();
        if (filename.toLowerCase().endsWith(".jpeg")
            || filename.toLowerCase().endsWith(".png")
            || filename.toLowerCase().endsWith(".jpg")) {
          filenames.add(filename);
        }
      }
    }
    return filenames.toArray(new String[0]);
  }
}
