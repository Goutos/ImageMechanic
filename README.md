# ImageMechanic Application
## Description
This Application was my Bachelor Thesis titled "Java Application for massive Image Processing". ImageMechanic offers an image editing solution where the user can edit all images within a directory of his choice. 

### Walkthrough of the application

The UI lets the user select an import folder, where the images to be edited are located. 

Once a selection is made, the "Before and After" panels show the first image of this directory, while below the panels, a label indicates the number of images found in this folder.

The user can then select how he wants to manipulate his images, with a variety of options such as Color Filters, Compression, and image flipping (Mirroring). 

While making selections, the user can click through the "Previous" and "Next" buttons, located between the Preview Panels, to cycle through the images.

Finally, the user can select an export folder, where the processed images will appear after clicking "Export"

## Technical Description

The Application is a monorepo featuring a layered architecture. 

ImageMechanic is written in java utilizing the Spring Boot framework for the Backend, while the Frontend does not have a framework of its own. 

The UI was created by using the Java Swing library.

## How to run

You can run the application by running it through an IDE, or by running "mvn clean package" and running the executable JAR file that is created under "/target/ImageMechanic-0.0.1-SNAPSHOT"
