/*
   Khanh Do
   CIS 2151 – Prof. John P. Baugh
   Summer 2016
   Capstone Project
*/

import java.io.*;

/**
 * The FileTypeFilter class customizes the file filter of filechooser window to  
 * display files with a specified extension and directories.
 * @author Khanh Do
 * @version August 16, 2016
 */

public class FileTypeFilter extends javax.swing.filechooser.FileFilter
{
    private final String EXTENSION = ".player";
    private final String DESCRIPTION = "Character file";
    
    @Override
    public boolean accept(File file)
    {
        return file.isDirectory() || file.getName().toLowerCase().endsWith(
                EXTENSION);
    }
    
    @Override
    public String getDescription()
    {
        return String.format(DESCRIPTION + " (%s)", EXTENSION);
    }
}
