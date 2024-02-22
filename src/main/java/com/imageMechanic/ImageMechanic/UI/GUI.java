package com.imageMechanic.ImageMechanic.UI;

import com.imageMechanic.ImageMechanic.Services.FileChooser;
import com.imageMechanic.ImageMechanic.Services.FileGrabber;
import com.imageMechanic.ImageMechanic.Services.ImagePreview;
import com.imageMechanic.ImageMechanic.Services.ImageProcessing;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class GUI extends JFrame {

  protected static int numberoffiles = 0;
  private JPanel contentPane;
  protected static String selectedFolder;
  protected static String[] fileList;
  protected static String selectedFileExport;

  private final ButtonGroup actionButtonGroup = new ButtonGroup();
  JLabel selectedPathLabel = new JLabel("Selected path:");
  private JLabel importPathLabel = new JLabel("C:\\");
  private JLabel exportPathLabel = new JLabel("C:\\");
  JLabel imageLabel = new JLabel();
  JLabel imageLabelAfter = new JLabel();
  JLabel selectImportFolderLabel = new JLabel("Select Import Folder");
  JLabel importSelectedPathLabel = new JLabel("Selected path:");
  JLabel selectActionLabel = new JLabel("Select Action:");
  JLabel selectCompressionLabel = new JLabel("Select Compression:");
  JLabel selectExportPathLabel = new JLabel("Select Export Folder");

  public GUI() {

    setResizable(false);
    setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
    setForeground(new Color(245, 222, 179));
    setBackground(new Color(245, 222, 179));
    setTitle("Image Mechanic");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 826, 567);

    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream("ImageMechanicLogo.png");
      if (is == null) {
        throw new IllegalArgumentException("Could not find image");
      }
      Image image = ImageIO.read(is);
      setIconImage(image);
    } catch (IOException | IllegalArgumentException e) {
      e.printStackTrace();
    }


    contentPane = new JPanel();
    contentPane.setForeground(new Color(0, 0, 0));
    contentPane.setBackground(new Color(221, 216, 196));
    contentPane.setBorder(null);
    setContentPane(contentPane);

    selectImportFolderLabel.setForeground(new Color(35, 32, 32));
    selectImportFolderLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));

    JLabel numberOfSelectedFilesLabel = new JLabel(numberoffiles + " files have been selected");


    JCheckBox chckbxFlipHorizontally = new JCheckBox("Flip Horizontally");
    JCheckBox chckbxFlipVertically = new JCheckBox("Flip Vertically");
    chckbxFlipHorizontally.setBackground(new Color(221, 216, 196));
    chckbxFlipVertically.setBackground(new Color(221, 216, 196));

    JButton ImportFolderButton = new JButton("Select Folder");
    ImportFolderButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JFrame containingFrame = (JFrame) SwingUtilities.getWindowAncestor(contentPane);
            FileChooser fileChooser = new FileChooser(containingFrame);
            selectedFolder = fileChooser.showFileChooser();
            importPathLabel.setText(GUI.selectedFolder);
            fileList = FileGrabber.FileGrabberMethod(selectedFolder);
            numberoffiles = fileList.length;
            numberOfSelectedFilesLabel.setText(numberoffiles + " files have been selected");

            String folderpath = selectedFolder.replace("\\", "/");
            String firstImagePath = folderpath + "/" + fileList[0];

            ImageIcon tempIcon = new ImageIcon(firstImagePath);
            Image image =
                tempIcon
                    .getImage()
                    .getScaledInstance(
                        imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            imageLabel.setIcon(scaledIcon);

            ImageIcon scaledIconAfter = new ImageIcon(image);
            String ActionSelected = actionButtonGroup.getSelection().getActionCommand();
            boolean FlipH = chckbxFlipHorizontally.isSelected();
            boolean FlipV = chckbxFlipVertically.isSelected();
            scaledIconAfter.setImage(
                ImagePreview.toBufferedImage(image, ActionSelected, FlipH, FlipV));
            imageLabelAfter.setIcon(scaledIconAfter);
          }
        });

    ImportFolderButton.setForeground(new Color(0, 0, 0));
    ImportFolderButton.setBackground(new Color(245, 222, 179));



    JRadioButton RadioButtonNoAction = new JRadioButton("No Action");
    JRadioButton RadioButtonBNW = new JRadioButton("Black and White");
    JRadioButton RadioButtonSepia = new JRadioButton("Sepia");
    JRadioButton RadioButtonNegative = new JRadioButton("Negative");

    RadioButtonNoAction.setSelected(true);

    RadioButtonNoAction.setActionCommand("NoAction");
    RadioButtonBNW.setActionCommand("BNW");
    RadioButtonSepia.setActionCommand("Sepia");
    RadioButtonNegative.setActionCommand("Negative");

    RadioButtonNoAction.setBackground(contentPane.getBackground());
    RadioButtonBNW.setBackground(contentPane.getBackground());
    RadioButtonSepia.setBackground(contentPane.getBackground());
    RadioButtonNegative.setBackground(contentPane.getBackground());

    actionButtonGroup.add(RadioButtonNoAction);
    actionButtonGroup.add(RadioButtonBNW);
    actionButtonGroup.add(RadioButtonSepia);
    actionButtonGroup.add(RadioButtonNegative);




    JComboBox comboBoxCompression = new JComboBox();
    comboBoxCompression.setBackground(new Color(250, 236, 211));
    comboBoxCompression.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
    comboBoxCompression.setModel(
        new DefaultComboBoxModel(new String[] {"0%", "25%", "50%", "75%"})
    );
    comboBoxCompression.setSelectedIndex(1);


    selectExportPathLabel.setForeground(new Color(35, 32, 32));
    selectExportPathLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));

    JButton ExportFolderButton = new JButton("Select Folder");
    ExportFolderButton.setBackground(new Color(245, 222, 179));
    ExportFolderButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JFrame containingFrame = (JFrame) SwingUtilities.getWindowAncestor(contentPane);
            FileChooser fileChooser = new FileChooser(containingFrame);
            GUI.selectedFileExport = fileChooser.showFileChooser();
            GUI.this.exportPathLabel.setText(GUI.selectedFileExport);
            GUI.this.exportPathLabel.setText(GUI.selectedFileExport);
          }
        });


    JButton exportButton = new JButton("Export");
    exportButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            boolean FlipH = chckbxFlipHorizontally.isSelected();
            boolean FlipV = chckbxFlipVertically.isSelected();
            int CompressionIndex = comboBoxCompression.getSelectedIndex();
            if (actionButtonGroup.getSelection() == null) {
              JOptionPane.showMessageDialog(
                  null, "Please select an action", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
              for (String filename : GUI.fileList) {
                if (filename != null) {
                  String ActionSelected = actionButtonGroup.getSelection().getActionCommand();
                  ImageProcessing.ImageIO(
                      GUI.selectedFolder,
                      filename,
                      GUI.selectedFileExport,
                      ActionSelected,
                      FlipH,
                      FlipV,
                      CompressionIndex);
                } else {
                  System.out.println("Filename is null");
                  JOptionPane.showMessageDialog(
                          null, "No images found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
              }
            }
          }
        });
    exportButton.setBackground(new Color(245, 222, 179));

    JPanel previewPanel = new JPanel();
    FlowLayout flowLayout = (FlowLayout) previewPanel.getLayout();
    flowLayout.setAlignment(FlowLayout.RIGHT);
    previewPanel.setBackground(new Color(221, 216, 196));

    Image image = null;

    InputStream preview = getClass().getClassLoader().getResourceAsStream("DefaultPreview.jpg");
    if (preview == null) {
      throw new IllegalArgumentException("Could not find image");
    }

    try {
      image = ImageIO.read(preview);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (image == null) {
      throw new IllegalArgumentException("Image could not be read");
    }

    final ImageIcon defaultImageIcon = new ImageIcon(image);
    final ImageIcon beforeImageIcon = new ImageIcon(image);
    previewPanel.add(imageLabel);
    imageLabel.setSize(previewPanel.getWidth(), previewPanel.getHeight());
    previewPanel.addComponentListener(
        new ComponentAdapter() {
          @Override
          public void componentResized(ComponentEvent e) {
            Image image =
                defaultImageIcon
                    .getImage()
                    .getScaledInstance(
                        previewPanel.getWidth(), previewPanel.getHeight(), Image.SCALE_SMOOTH);
            beforeImageIcon.setImage(image);
            imageLabel.setIcon(beforeImageIcon);
          }
        });

    JPanel previewAfterPanel = new JPanel();
    previewAfterPanel.setBackground(new Color(221, 216, 196));
    FlowLayout flowLayout_1 = (FlowLayout) previewAfterPanel.getLayout();
    flowLayout_1.setAlignment(FlowLayout.RIGHT);
    previewAfterPanel.add(imageLabelAfter);
    previewAfterPanel.addComponentListener(
        new ComponentAdapter() {
          final ImageIcon afterImageIcon = new ImageIcon(defaultImageIcon.getImage());

          @Override
          public void componentResized(ComponentEvent e) {
            Image image =
                afterImageIcon
                    .getImage()
                    .getScaledInstance(
                        previewAfterPanel.getWidth(),
                        previewAfterPanel.getHeight(),
                        Image.SCALE_SMOOTH);
            afterImageIcon.setImage(image);
            imageLabelAfter.setIcon(afterImageIcon);
          }
        });

    AtomicInteger previewIndex = new AtomicInteger(0);
    JButton previousJButton = new JButton("Previous");
    previousJButton.setBackground(new Color(245, 222, 179));
    previousJButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (previewIndex.get() >= 1 && previewIndex.get() < GUI.fileList.length) {
              previewIndex.decrementAndGet();
            } else if (previewIndex.get() == 0) {
              previewIndex.set(GUI.fileList.length - 1);
            }

            String folderpath = GUI.selectedFolder.replace("\\", "/");
            String ImagePath = folderpath + "/" + GUI.fileList[previewIndex.get()];

            ImageIcon newImageIcon = new ImageIcon(ImagePath);
            Image image =
                newImageIcon
                    .getImage()
                    .getScaledInstance(
                        imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            imageLabel.setIcon(scaledIcon);

            ImageIcon scaledIconAfter = new ImageIcon(image);
            String ActionSelected = actionButtonGroup.getSelection().getActionCommand();
            boolean FlipH = chckbxFlipHorizontally.isSelected();
            boolean FlipV = chckbxFlipVertically.isSelected();
            scaledIconAfter.setImage(
                ImagePreview.toBufferedImage(image, ActionSelected, FlipH, FlipV));
            imageLabelAfter.setIcon(scaledIconAfter);
          }
        });

    JButton nextJButton = new JButton("Next");
    nextJButton.setBackground(new Color(245, 222, 179));
    nextJButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (previewIndex.get() >= 0 && previewIndex.get() < GUI.fileList.length - 1) {
              previewIndex.incrementAndGet();
            } else if (previewIndex.get() == GUI.fileList.length - 1) {
              previewIndex.set(0);
            }

            String folderpath = GUI.selectedFolder.replace("\\", "/");
            String ImagePath = folderpath + "/" + GUI.fileList[previewIndex.get()];

            ImageIcon newImageIcon = new ImageIcon(ImagePath);
            Image image =
                newImageIcon
                    .getImage()
                    .getScaledInstance(
                        imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            imageLabel.setIcon(scaledIcon);

            ImageIcon scaledIconAfter = new ImageIcon(image);
            String ActionSelected = actionButtonGroup.getSelection().getActionCommand();
            boolean FlipH = chckbxFlipHorizontally.isSelected();
            boolean FlipV = chckbxFlipVertically.isSelected();
            scaledIconAfter.setImage(
                ImagePreview.toBufferedImage(image, ActionSelected, FlipH, FlipV));
            imageLabelAfter.setIcon(scaledIconAfter);
          }
        });

    JButton RefreshJbutton = new JButton("Refresh");
    RefreshJbutton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {

            String folderpath = GUI.selectedFolder.replace("\\", "/");
            String ImagePath = folderpath + "/" + GUI.fileList[previewIndex.get()];

            ImageIcon newImageIcon = new ImageIcon(ImagePath);
            Image image =
                newImageIcon
                    .getImage()
                    .getScaledInstance(
                        imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIconAfter = new ImageIcon(image);
            String ActionSelected = actionButtonGroup.getSelection().getActionCommand();
            boolean FlipH = chckbxFlipHorizontally.isSelected();
            boolean FlipV = chckbxFlipVertically.isSelected();
            scaledIconAfter.setImage(
                ImagePreview.toBufferedImage(image, ActionSelected, FlipH, FlipV));
            imageLabelAfter.setIcon(scaledIconAfter);
          }
        });
    RefreshJbutton.setBackground(new Color(245, 222, 179));

    GroupLayout gl_contentPane = new GroupLayout(contentPane);
    gl_contentPane.setHorizontalGroup(
        gl_contentPane
            .createParallelGroup(Alignment.LEADING)
            .addGroup(
                gl_contentPane
                    .createSequentialGroup()
                    .addGroup(
                        gl_contentPane
                            .createParallelGroup(Alignment.LEADING)
                            .addGroup(
                                gl_contentPane
                                    .createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(importSelectedPathLabel)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(importPathLabel))
                            .addGroup(
                                gl_contentPane
                                    .createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.LEADING)
                                            .addGroup(
                                                gl_contentPane
                                                    .createSequentialGroup()
                                                    .addComponent(selectCompressionLabel)
                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                    .addComponent(
                                                        comboBoxCompression,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE))
                                            .addGroup(
                                                gl_contentPane
                                                    .createSequentialGroup()
                                                    .addGap(82)
                                                    .addComponent(selectExportPathLabel)
                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                    .addComponent(ExportFolderButton))
                                            .addGroup(
                                                gl_contentPane
                                                    .createSequentialGroup()
                                                    .addGap(143)
                                                    .addComponent(exportButton))
                                            .addGroup(
                                                gl_contentPane
                                                    .createSequentialGroup()
                                                    .addComponent(selectedPathLabel)
                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                    .addComponent(this.exportPathLabel))))
                            .addGroup(
                                gl_contentPane
                                    .createSequentialGroup()
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.LEADING)
                                            .addGroup(
                                                gl_contentPane
                                                    .createSequentialGroup()
                                                    .addGap(92)
                                                    .addComponent(selectImportFolderLabel)
                                                    .addPreferredGap(ComponentPlacement.RELATED))
                                            .addGroup(
                                                gl_contentPane
                                                    .createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(selectActionLabel)
                                                    .addGap(18)
                                                    .addGroup(
                                                        gl_contentPane
                                                            .createParallelGroup(Alignment.LEADING)
                                                            .addComponent(RadioButtonBNW)
                                                            .addComponent(RadioButtonNoAction)
                                                            .addComponent(RadioButtonSepia)
                                                            .addComponent(RadioButtonNegative))
                                                    .addGap(50)))
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.LEADING)
                                            .addComponent(chckbxFlipHorizontally)
                                            .addComponent(ImportFolderButton)
                                            .addComponent(chckbxFlipVertically))))
                    .addGap(35)
                    .addGroup(
                        gl_contentPane
                            .createParallelGroup(Alignment.LEADING)
                            .addComponent(numberOfSelectedFilesLabel)
                            .addGroup(
                                gl_contentPane
                                    .createParallelGroup(Alignment.TRAILING, false)
                                    .addGroup(
                                        gl_contentPane
                                            .createSequentialGroup()
                                            .addComponent(previousJButton)
                                            .addPreferredGap(
                                                ComponentPlacement.RELATED,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                            .addComponent(RefreshJbutton)
                                            .addGap(73)
                                            .addComponent(
                                                nextJButton,
                                                GroupLayout.PREFERRED_SIZE,
                                                81,
                                                GroupLayout.PREFERRED_SIZE))
                                    .addComponent(
                                        previewAfterPanel,
                                        GroupLayout.PREFERRED_SIZE,
                                        376,
                                        GroupLayout.PREFERRED_SIZE)
                                    .addComponent(
                                        previewPanel,
                                        GroupLayout.PREFERRED_SIZE,
                                        376,
                                        GroupLayout.PREFERRED_SIZE)))
                    .addGap(63)));
    gl_contentPane.setVerticalGroup(
        gl_contentPane
            .createParallelGroup(Alignment.LEADING)
            .addGroup(
                gl_contentPane
                    .createSequentialGroup()
                    .addGap(8)
                    .addGroup(
                        gl_contentPane
                            .createParallelGroup(Alignment.LEADING)
                            .addGroup(
                                gl_contentPane
                                    .createSequentialGroup()
                                    .addComponent(
                                        previewPanel,
                                        GroupLayout.PREFERRED_SIZE,
                                        218,
                                        GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.BASELINE)
                                            .addComponent(previousJButton)
                                            .addComponent(nextJButton)
                                            .addComponent(RefreshJbutton))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(
                                        previewAfterPanel,
                                        GroupLayout.PREFERRED_SIZE,
                                        212,
                                        GroupLayout.PREFERRED_SIZE))
                            .addGroup(
                                gl_contentPane
                                    .createSequentialGroup()
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.BASELINE)
                                            .addComponent(selectImportFolderLabel)
                                            .addComponent(ImportFolderButton))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.BASELINE)
                                            .addComponent(importSelectedPathLabel)
                                            .addComponent(importPathLabel))
                                    .addGap(18)
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.BASELINE)
                                            .addComponent(selectActionLabel)
                                            .addComponent(chckbxFlipHorizontally)
                                            .addComponent(RadioButtonNoAction))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.BASELINE)
                                            .addComponent(chckbxFlipVertically)
                                            .addComponent(RadioButtonBNW))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(RadioButtonSepia)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(RadioButtonNegative)
                                    .addGap(47)
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.BASELINE)
                                            .addComponent(selectCompressionLabel)
                                            .addComponent(
                                                comboBoxCompression,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                    .addGap(18)
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.BASELINE)
                                            .addComponent(selectExportPathLabel)
                                            .addComponent(ExportFolderButton))
                                    .addGap(18)
                                    .addGroup(
                                        gl_contentPane
                                            .createParallelGroup(Alignment.BASELINE)
                                            .addComponent(selectedPathLabel)
                                            .addComponent(this.exportPathLabel))
                                    .addGap(18)
                                    .addComponent(exportButton)))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(numberOfSelectedFilesLabel)
                    .addContainerGap(30, Short.MAX_VALUE)));
    contentPane.setLayout(gl_contentPane);
  }
}
