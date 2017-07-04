/*
   Khanh Do
   CIS 2151 – Prof. John P. Baugh
   Summer 2016
   Capstone Project
*/

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * The CharacterCreation program displays a character creation screen, from 
 * which the user can select and save a character's specifications from a list 
 * of options.  The center of the screen leaves room for the addition of 
 * the character's image in a later version.
 * @author Khanh Do
 * @version August 16, 2016 
 */

public class CharacterCreation extends JFrame 
{
    private final int WINDOW_WIDTH = 1100;
    private final int WINDOW_HEIGHT = 800;
    private final int MIN_CHARS = 1;        //minimum number of characters
    private final int MAX_CHARS = 10;       //maximum number of characters
    
    private JPanel headingPanel;    //a panel containing the header label
    private JLabel heading;         //a label containing the header
    private CharacterNamePanel namePanel;   //a panel containing the name input
    private CharacteristicsPanel characterPanel;    //a panel of specifications
    private FilePanel fileOptionsPanel;     //a panel containing file options
    
    /**
     * The constructor builds the character creation screen and displays it on
     * the console.
     */
    
    public CharacterCreation()
    {
        setTitle("Character Creation");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        headingPanel = new JPanel();
        heading = new JLabel("Create Character");
        headingPanel.add(heading);
       
        namePanel = new CharacterNamePanel();
        characterPanel = new CharacteristicsPanel();
        fileOptionsPanel = new FilePanel();
       
        add(headingPanel, BorderLayout.NORTH);
        add(namePanel, BorderLayout.WEST);
        add(characterPanel, BorderLayout.EAST);
        add(fileOptionsPanel, BorderLayout.SOUTH);
       
        setVisible(true);       
    }//end CharacterCreation constructor
    
    /**
     * The CharacterNamePanel class builds the panel which contains a text field
     * where the user can input the character's name.
     */
    
    private class CharacterNamePanel extends JPanel
    {
        private JLabel nameLabel;       //a label describing the text field
        private JTextField nameInput;   //a field for the character's name
        
        /**
         * The constructor builds the CharacterNamePanel panel.
         */    
        
        public CharacterNamePanel()
        {
            nameLabel = new JLabel("Character Name:");
            nameInput = new JTextField(10);
            nameInput.addActionListener( new NameHandler() );
                    
            add(nameLabel);
            add(nameInput);
        }//end CharacterNamePanel constructor
   
        /**
         * The setNameInput method receives a string, checks its length and then
         * sets it in the character name text field.  It allows for zero 
         * characters in the event of resetting the text field back to empty.
         * @param str the string to be set in the name text field
         */
        
        public void setNameInput(String str)
        {
            if( str.length() <= MAX_CHARS )
                nameInput.setText(str);
        }//end setNameInput method
        
        /**
         * The NameHandler class handles the event that is generated when the 
         * user presses the Enter key from the name text field. Its 
         * actionPerformed method checks the length of the string inputted into
         * the text field.  It prompts the user if the length is too short or 
         * too long.
         */
        
        private class NameHandler implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = nameInput.getText();
                if(input.length() >= MIN_CHARS && input.length() <= MAX_CHARS)
                {    
                    setNameInput(input);
                }
                else
                {    
                    JOptionPane.showMessageDialog(null, "Please enter a name "
                        + "containing one to ten characters."); 
                    nameInput.setText("");
                }
            }
        }//end NameHandler class
    }//end CharacterNamePanel class
    
    /**
     * The CharacteristicsPanel class builds panels that contain gender, class
     * and skills options for the user to select for their character.
     */
    
    private class CharacteristicsPanel extends JPanel
    {
        private final int MAX_POINTS = 100;
    
        private JPanel genderPanel;         //a panel containing gender options
        private JRadioButton maleRadioB;    //the male gender radio button
        private JRadioButton femaleRadioB;  //the female gender radio button
        private JRadioButton hermaphroditeRadioB;   //the hermaphrodite radio
        private ButtonGroup radioButtonGroup;
    
        private JPanel classPanel;          //a panel containing class options
        private JComboBox classBox;         //a drop-down list of classes
        private String[] classes = {"Warrior", "Barbarian", "Mage", "Thief", 
            "Priest"};                      //an array of class descriptions
        
        private JPanel skillsPanel;         //a panel containing skill options
        private JPanel pointsPanel;         //a panel containing the points
        private JLabel pointsLabel;         //a label describing the points
        private JTextField pointsTextField; //a text field with the points value
        private String[] skills;            //an array of skill descriptions
        private JLabel skillLabel;          //a label describing a skill
        private JSlider slider;             //a slider for a skill value
        private JPanel skillPanel;          //a panel containing a skill slider
        private ArrayList<JSlider> sliders = new ArrayList<>();
        private ArrayList<JPanel> panelsList = new ArrayList<>();
        private int sumOfSliders;           //the sum of the values of sliders
  
        /**
         * The constructor builds a panel containing the gender, class, and
         * skills panels. 
         */
        
        public CharacteristicsPanel()
        {
            setLayout( new BorderLayout() );
            setBorder( BorderFactory.createEtchedBorder() );
            buildGenderPanel();
            buildClassPanel();
            buildSkillsPanel();
        
            add(genderPanel, BorderLayout.NORTH);
            add(classPanel, BorderLayout.CENTER);
            add(skillsPanel, BorderLayout.SOUTH);
        }//end CharacteristicsPanel constructor
    
        /**
         * The buildGenderPanel method builds the gender options panel.
         */
        
        private void buildGenderPanel()
        {
            maleRadioB = new JRadioButton("Male", true);
            femaleRadioB = new JRadioButton("Female");
            hermaphroditeRadioB = new JRadioButton("Hermaphrodite");
        
            radioButtonGroup = new ButtonGroup();
            radioButtonGroup.add(maleRadioB);
            radioButtonGroup.add(femaleRadioB);
            radioButtonGroup.add(hermaphroditeRadioB);
                
            genderPanel = new JPanel();
            genderPanel.setBorder( BorderFactory.createTitledBorder("Gender") );
            genderPanel.add(maleRadioB);
            genderPanel.add(femaleRadioB);
            genderPanel.add(hermaphroditeRadioB);
        }//end buildGenderPanel method
        
        /**
         * The buildClassPanel method builds the panel containing class options.
         */
        
        private void buildClassPanel()
        {
            classBox = new JComboBox(classes);        
         
            classPanel = new JPanel();
            classPanel.setBorder( BorderFactory.createTitledBorder("Class") );
            classPanel.add(classBox);
        }//end buildClassPanel method
        
        /**
         * The buildSkillsPanel method builds the panel containing the skill
         * selections.
         */
        
        private void buildSkillsPanel()
        {
            pointsLabel = new JLabel("Points Left to Spend:");
            pointsTextField = new JTextField(Integer.toString(MAX_POINTS), 10);
            pointsTextField.setEditable(false);
            pointsPanel = new JPanel();
            pointsPanel.add(pointsLabel);
            pointsPanel.add(pointsTextField);
            
            //build skill panels, each containing a label and a slider.  Add the
            //sliders to an array list and the panels to an array list
            String[] skills = {"Intelligence", "Dexterity    ", 
                "Strength     ", "Wisdom     "};
            int skillIndex = 0;
            for(String skill : skills)
            {
                skillLabel = new JLabel(skill);
                slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
                slider.setMajorTickSpacing(20);        //Major tick every 20
                slider.setMinorTickSpacing(5);         //Minor tick every 5
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                slider.addChangeListener( new SliderListener() );
                sliders.add(skillIndex, slider);
            
                skillPanel = new JPanel();
                skillPanel.add(skillLabel);
                skillPanel.add(slider);
                panelsList.add(skillIndex, skillPanel);
                
                skillIndex++;
            }
            
            //build the main skills panel
            skillsPanel = new JPanel();
            skillsPanel.setLayout( new GridLayout(5, 1) );
            skillsPanel.setBorder( BorderFactory.createTitledBorder("Skills") );
        
            //add points panel and each skill panel to the main skills panel
            skillsPanel.add(pointsPanel);
            for(int index = 0; index < panelsList.size(); index++)
                skillsPanel.add( panelsList.get(index) );
        }//end buildSkillsPanel method
        
        /**
         * The SliderListener class handles the event that is generated when the
         * user adjusts the position of a slider pointer.  When the user stops
         * adjusting, the stateChanged method determines the sum of the slider
         * values, subtracts it from the maximum points, and displays the 
         * difference in the text field.  It will prompt the user if the sum is 
         * greater than the maximum points available and reset the offending
         * slider value to zero.
         */
        
        private class SliderListener implements ChangeListener
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                JSlider sliderMoved = (JSlider)e.getSource();
                if ( !sliderMoved.getValueIsAdjusting() )
                {
                    sumOfSliders = 0;
                    for(int index = 0; index < sliders.size(); index++)
                        sumOfSliders = sumOfSliders + 
                                sliders.get(index).getValue();
                    if (MAX_POINTS - sumOfSliders >= 0)
                        pointsTextField.setText(
                            Integer.toString(MAX_POINTS - sumOfSliders) );
                    else
                    {
                        JOptionPane.showMessageDialog(null, "You ran out of " +
                            "points. Try again");
                        sliderMoved.setValue(0);
                    }
                }
            }
        }//end SliderListener class  
    }//end CharacteristicsPanel class
    
    /**
     * The FilePanel class builds the panel that contains a menu bar with file
     * open, file save, reset and exit options.
     */
    
    private class FilePanel extends JPanel
    {
        private JMenuBar menuBar;       //a menu bar with File and Options tabs
        private JMenu fileMenu;         //a menu with file save and open options
        private JMenu optionsMenu;      //a menu with reset and exit options
        private JMenuItem openItem;     //the file open menu item
        private JMenuItem saveItem;     //the file save menu item
        private JMenuItem resetItem;    //the reset menu item
        private JMenuItem exitItem;     //the exit menu item
        private JFileChooser chooser;   //the filechooser for file open and save
        private Scanner inputFile;      //used to read a file
        private File file;              
        int status;                     //the integer returned from filechooser
        int status2;                    //the value returned from a dialog
        
        /**
         * The constructor calls the method to build the menu bar and adds it
         * to the file panel.
         */
        
        public FilePanel()
        {
            buildMenuBar();
            add(menuBar);
        }//end constructor
    
        /**
         * The buildMenuBar method builds a menu bar containing file and options 
         * tabs.
         */
        
        private void buildMenuBar()
        {
            menuBar = new JMenuBar();
            buildFileMenu();
            buildOptionsMenu();
        
            menuBar.add(fileMenu);
            menuBar.add(optionsMenu);
            //setJMenuBar(menuBar);
        }//end buildMenuBar method
    
        /**
         * The buildFileMenu method builds the file menu with selections to open 
         * or save a character file.
         */
        
        private void buildFileMenu()
        {
            openItem = new JMenuItem("Open a saved character");
            openItem.setMnemonic(KeyEvent.VK_O);
            openItem.addActionListener( new OpenListener() );
        
            saveItem = new JMenuItem("Save a character");
            saveItem.setMnemonic(KeyEvent.VK_S);
            saveItem.addActionListener( new SaveListener() );
        
            fileMenu = new JMenu("File");
            fileMenu.setMnemonic(KeyEvent.VK_F);
        
            fileMenu.add(openItem);
            fileMenu.add(saveItem);
        }//end buildFileMenu method
    
        /**
         * The buildOptionsMenu method builds the options menu for the user to  
         * reset the selections on the screen or to exit the program.
         */
        
        private void buildOptionsMenu()
        {
            resetItem = new JMenuItem("Reset All");
            resetItem.setMnemonic(KeyEvent.VK_R);
            resetItem.addActionListener( new ResetListener() );
        
            exitItem = new JMenuItem("Exit");
            exitItem.setMnemonic(KeyEvent.VK_X);
            exitItem.addActionListener( new ExitListener() );
        
            optionsMenu = new JMenu("Options");
            optionsMenu.setMnemonic(KeyEvent.VK_P);
        
            optionsMenu.add(resetItem);
            optionsMenu.add(exitItem);
        }//end buildOptionsMenu method
    
        /**
         * The OpenListener class handles the event that is generated when the
         * user selects the "Open a character" menu item.  Its actionPerformed
         * method opens a filechooser dialog for the user to select a file to 
         * open.  It reads the file and sets the screen to reflect the   
         * parameters saved in the file.
         */
        
        private class OpenListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chooser = new JFileChooser();
                chooser.setFileFilter(new FileTypeFilter());
                status = chooser.showOpenDialog(null);
                if(status == JFileChooser.APPROVE_OPTION)
                {
                    file = chooser.getSelectedFile();
                    try
                    {
                        inputFile = new Scanner(file);
                        resetScreen();
                        namePanel.setNameInput( inputFile.nextLine() );
                        switch ( inputFile.nextLine() )
                        {
                            case "Male":
                                characterPanel.maleRadioB.setSelected(true);
                                break;
                            case "Female":
                                characterPanel.femaleRadioB.setSelected(true);
                                break;
                            case "Hermaphrodite":
                                characterPanel.hermaphroditeRadioB.setSelected(
                                        true);
                                break;
                            default:
                                System.out.println("Error in reading file.");
                                break;
                        }
                        switch ( inputFile.nextLine() )
                        {
                            case "Warrior":
                                characterPanel.classBox.setSelectedIndex(0);
                                break;
                            case "Barbarian":
                                characterPanel.classBox.setSelectedIndex(1);
                                break;
                            case "Mage":
                                characterPanel.classBox.setSelectedIndex(2);
                                break;
                            case "Thief":
                                characterPanel.classBox.setSelectedIndex(3);
                                break;
                            case "Priest":
                                characterPanel.classBox.setSelectedIndex(4);
                                break;    
                            default:
                                System.out.println("Error in reading file.");
                                break;
                        }
                        for(int i = 0; i < characterPanel.sliders.size(); i++)
                            characterPanel.sliders.get(i).setValue(
                                inputFile.nextInt() );
                    } catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, 
                                    "Error occurred while opening file.");
                    }
                }
            }
        }//end OpenListener class
    
        /**
         * The SaveListener class handles the event that is generated when the
         * user selects the "Save a character" menu item.  Its actionPerformed
         * method opens a filechooser dialog for the user to select a file to 
         * save.  It calls the saveToFile method which writes the file to 
         * memory. 
         */
        
        private class SaveListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = namePanel.nameInput.getText();
                if(input.length() >= MIN_CHARS && input.length() <= MAX_CHARS)
                {    
                    namePanel.setNameInput(input);
                    saveToFile(input);
                }
                else
                {    
                    JOptionPane.showMessageDialog(null, "Please enter a "
                        + "character name containing one to ten characters."); 
                    namePanel.nameInput.setText("");
                }
            }//end actionPerformed method
            
            /**
             * The saveToFile method writes the character screen selections
             * to a file. It displays dialog boxes to help the user designate 
             * the file directory and file name of the file to be saved.
             * @param fileName the name of the file to be saved (no extension)
             */
            
            private void saveToFile(String fileName)
            {
                chooser = new JFileChooser();
                chooser.setSelectedFile( new File(fileName + ".player") );
                chooser.setFileFilter( new FileTypeFilter() );
                status = chooser.showSaveDialog(null);
                if(status == JFileChooser.APPROVE_OPTION)
                    status2 = JOptionPane.showConfirmDialog(null, 
                            "Are you sure you want to save this character?", 
                            "Confirm Save", JOptionPane.OK_CANCEL_OPTION);
                if(status2 == JOptionPane.OK_OPTION)
                {
                    file = chooser.getSelectedFile();
                    try
                    {
                        FileWriter fwriter = new FileWriter(file.getPath());
                        PrintWriter pwriter = new PrintWriter(fwriter);
                        pwriter.println(fileName);
                        if( characterPanel.hermaphroditeRadioB.isSelected() )
                            pwriter.println("Hermaphrodite");
                        else if( characterPanel.femaleRadioB.isSelected() )
                            pwriter.println("Female");
                        else
                            pwriter.println("Male");
                        pwriter.println(
                                characterPanel.classBox.getSelectedItem());
                        for(int j = 0; j < characterPanel.sliders.size(); j++)
                            pwriter.println(
                                    characterPanel.sliders.get(j).getValue());
                        pwriter.close();
                    }catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, 
                                "Error occurred while saving file.");
                    }
                }    
            }//end saveToFile method
        }//end SaveListener class
    
        /**
         * The ResetListener class handles the event that is generated when the 
         * user selects the "Reset All" option. It calls the resetScreen method.
         */
        
        private class ResetListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                resetScreen();
            }
        }//end ResetListener class
        
        /**
         * The resetScreen method resets the Character Creation GUI components 
         * back to their zero state.
         */
        
        private void resetScreen()
        {
            namePanel.setNameInput("");
            characterPanel.maleRadioB.setSelected(true);
            characterPanel.classBox.setSelectedIndex(0);
            for(int k = 0; k < characterPanel.sliders.size(); k++)
                    characterPanel.sliders.get(k).setValue(0);
        }//end resetScreen method
        
        /**
         * The ExitListener class handles the event that is generated when the
         * user selects the "Exit" option from the file menu bar.
         */
        
        private class ExitListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        }//end ExitListener class
    }//end FilePanel class
    
    /**
       The main method creates an instance of the CharacterCreation class, which
       displays a character creation screen on the console.
    */
    
    public static void main(String[] args)
    {
        new CharacterCreation();
    }//end main
}//end CharacterCreation class