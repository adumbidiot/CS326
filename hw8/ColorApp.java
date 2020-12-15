import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*; 
import java.util.ArrayList;
import javax.swing.border.*;

public class ColorApp extends JFrame implements ActionListener, ListSelectionListener, WindowListener { 
    ColorDisplay colorDisplay = new ColorDisplay();
    
    JTextField redInputField = new JTextField();
    JButton redMinusButton = new JButton("-");
    JButton redPlusButton = new JButton("+");
    JButton greenMinusButton = new JButton("-");
    JButton greenPlusButton = new JButton("+");
    JButton blueMinusButton = new JButton("-");
    JButton bluePlusButton = new JButton("+");
    
    JButton saveButton = new JButton("Save");
    JButton resetButton = new JButton("Reset");
    
    JTextField greenInputField = new JTextField();
    JTextField blueInputField = new JTextField();
    
    JList<String> colorList = new JList<String>();
    
    ArrayList<NamedColor> namedColors = new ArrayList<NamedColor>();
    ArrayList<NamedColor> savedNamedColors = new ArrayList<NamedColor>();
    boolean dirty = false;
    int lastIndex = 0;
    
    public static void main (String argv []) { 
        try {
            new ColorApp();
        } catch(FileNotFoundException e) {
            System.out.println("Failed to open `colors.txt`: " + e);
        } catch(IOException e) {
            System.out.println("Failed to read `colors.txt`: " + e);
        }
    }
    
    public ColorApp() throws FileNotFoundException, IOException {
        super("Color Sampler");
        
        loadColorFile();
        updateColorList();
        
        colorList.setSelectedIndex(0);
        colorList.addListSelectionListener(this);
        updateCurrentColor();
        
        JPanel colorPickerPanelControls = new JPanel();
        colorPickerPanelControls.setLayout(new GridLayout(3, 4));
        
        redInputField.addActionListener(this);
        redMinusButton.addActionListener(this);
        redPlusButton.addActionListener(this);
        
        colorPickerPanelControls.add(new JLabel("Red: "));
        colorPickerPanelControls.add(redInputField);
        colorPickerPanelControls.add(redMinusButton);
        colorPickerPanelControls.add(redPlusButton);
        
        greenInputField.addActionListener(this);
        greenMinusButton.addActionListener(this);
        greenPlusButton.addActionListener(this);
        
        colorPickerPanelControls.add(new JLabel("Green: "));
        colorPickerPanelControls.add(greenInputField);
        colorPickerPanelControls.add(greenMinusButton);
        colorPickerPanelControls.add(greenPlusButton);
        
        blueInputField.addActionListener(this);
        blueMinusButton.addActionListener(this);
        bluePlusButton.addActionListener(this);
        
        colorPickerPanelControls.add(new JLabel("Blue: "));
        colorPickerPanelControls.add(blueInputField);
        colorPickerPanelControls.add(blueMinusButton);
        colorPickerPanelControls.add(bluePlusButton);
        
        JPanel savePanel = new JPanel();
        savePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        savePanel.setLayout(new GridLayout(1, 2));
        
        saveButton.addActionListener(this);
        resetButton.addActionListener(this);
        
        savePanel.add(saveButton);
        savePanel.add(resetButton);
        
        JPanel colorPickerPanel = new JPanel();
        colorPickerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        colorPickerPanel.setLayout(new GridLayout(3, 1, 10, 10));
        colorPickerPanel.add(colorDisplay);
        colorPickerPanel.add(colorPickerPanelControls);
        colorPickerPanel.add(savePanel);
        
        JPanel colorListPanel = new JPanel();
        colorListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        colorListPanel.setLayout(new GridLayout(1, 1));
        colorListPanel.add(colorList);
        
        getContentPane().setLayout(new GridLayout(1, 3));
        getContentPane().add(colorPickerPanel);
        getContentPane().add(colorListPanel);
        
        setBounds(100, 100, 500, 350);
        addWindowListener(this);
        setVisible(true);
    }
    
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            resetColor(lastIndex);
            clearDirty();
            lastIndex = currentColorIndex();
            updateCurrentColor();
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        int index = currentColorIndex();
        Object source = e.getSource();
        NamedColor color = null;
        
        if(source == redInputField) {
            color = namedColors.get(index);
            try {
                color.r = Integer.parseInt(redInputField.getText());
            } catch(NumberFormatException ex) {}
            
            if(color.r < 0) color.r = 0;
            if(color.r > 255) color.r = 255;
            updateCurrentColor();
        } else if(source == greenInputField) {
            color = namedColors.get(index);
            try {
                color.g = Integer.parseInt(greenInputField.getText());
            } catch(NumberFormatException ex) {}
            
            if(color.g < 0) color.g = 0;
            if(color.g > 255) color.g = 255;
            updateCurrentColor();
        } else if(source == blueInputField) {
            color = namedColors.get(index);
            try {
                color.b = Integer.parseInt(blueInputField.getText());
            } catch(NumberFormatException ex) {}
            
            if(color.b < 0) color.b = 0;
            if(color.b > 255) color.b = 255;
            updateCurrentColor();
        } else if(source == redMinusButton) {
            color = namedColors.get(index);
            color.r -= 5;
            if(color.r < 0) color.r = 0;
            updateCurrentColor(); 
            setDirty();
        } else if(source == redPlusButton) {
            color = namedColors.get(index);
            color.r += 5;
            if(color.r > 255) color.r = 255;
            updateCurrentColor();  
            setDirty();
        } else if (source == greenMinusButton) {
            color = namedColors.get(index);
            color.g -= 5;
            if(color.g < 0) color.g = 0;
            updateCurrentColor(); 
            setDirty();
        } else if (source == greenPlusButton) {
            color = namedColors.get(index);
            color.g += 5;
            if(color.g > 255) color.g = 255;
            updateCurrentColor();
            setDirty();            
        } else if(source == blueMinusButton) {
            color = namedColors.get(index);
            color.b -= 5;
            if(color.b < 0) color.b = 0;
            updateCurrentColor();                
            setDirty();
        } else if (source == bluePlusButton) {
            color = namedColors.get(index);
            color.b += 5;
            if(color.b > 255) color.b = 255;
            updateCurrentColor();   
            setDirty();            
        } else if (source == saveButton) {
            saveColor(currentColorIndex());
            clearDirty();
        } else if (source == resetButton) {
            resetColor(currentColorIndex());
            clearDirty();
            updateCurrentColor();
        }
    }
    
    public void windowClosing(WindowEvent e) {
        try {
            FileOutputStream ostream = new FileOutputStream("colors.txt");
            PrintWriter writer = new PrintWriter(ostream);
            
            for(int i = 0; i < savedNamedColors.size(); i++){
                NamedColor color = savedNamedColors.get(i);
                writer.println(color.name + " " + color.r + " " + color.g + " " + color.b);
            }
            
            writer.flush();
            ostream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to open `colors.txt` for saving: " + ex);
        } catch (IOException ex) {
            System.out.println("Failed to save `colors.txt`: " + ex);
        }
        
        System.out.flush();
        System.exit(0); 
    }
    
    public void windowActivated(WindowEvent e) {} 
    public void windowClosed(WindowEvent e) {} 
    public void windowDeactivated(WindowEvent e) {} 
    public void windowDeiconified(WindowEvent e) {} 
    public void windowIconified(WindowEvent e) {} 
    public void windowOpened(WindowEvent e) {}
    
    void loadColorFile() throws FileNotFoundException, IOException {
        FileInputStream stream = new FileInputStream("colors.txt");
        InputStreamReader reader = new InputStreamReader(stream); 
        StreamTokenizer tokens = new StreamTokenizer(reader);
        String name = null;
        int r = 0;
        int g = 0;
        int b = 0;
        int token = tokens.nextToken();
        while (token != tokens.TT_EOF) {
            name = tokens.sval; 
            token = tokens.nextToken();
            
            r = (int) tokens.nval; 
            token = tokens.nextToken(); 
            
            g = (int) tokens.nval; 
            token = tokens.nextToken(); 
            
            b = (int) tokens.nval;             
            token = tokens.nextToken();
            
            namedColors.add(new NamedColor(name, r, g, b));
            savedNamedColors.add(new NamedColor(name, r, g, b));
        }
        stream.close();
    }
    
    void updateColorList() {
        String[] names = new String[namedColors.size()];
        
        for(int i = 0; i < names.length; i++) 
            names[i] = namedColors.get(i).name;
        
        colorList.setListData(names);
    }
    
    void updateCurrentColor() {
        int index = currentColorIndex();
        NamedColor color = namedColors.get(index);
        
        redInputField.setText(String.valueOf(color.r));
        greenInputField.setText(String.valueOf(color.g));
        blueInputField.setText(String.valueOf(color.b));
        
        colorDisplay.setColor(color);
        colorDisplay.repaint(colorDisplay.getVisibleRect());
    }
    
    int currentColorIndex() {
        return colorList.getSelectedIndex();
    }
    
    void setDirty() {
        this.dirty = true;
        this.setTitle("Color Sampler*");
    }
    
    void clearDirty() {
        this.dirty = false;
        this.setTitle("Color Sampler");
    }
    
    void saveColor(int index) {
        NamedColor savedColor = savedNamedColors.get(index);
        NamedColor namedColor = namedColors.get(index);
            
        savedColor.r = namedColor.r;
        savedColor.g = namedColor.g;
        savedColor.b = namedColor.b;
    }
    
    void saveAllColors() {
        for(int i = 0; i < namedColors.size(); i++) 
            saveColor(i);
        clearDirty();
    }
    
    void resetColor(int index) {
        NamedColor namedColor = namedColors.get(index);
        NamedColor savedColor = savedNamedColors.get(index);
            
        namedColor.r = savedColor.r;
        namedColor.g = savedColor.g;
        namedColor.b = savedColor.b;
    }
    
    void resetAllColors() {
        for(int i = 0; i < namedColors.size(); i++) 
            resetColor(i);
        clearDirty();
    }
}

class NamedColor {
    public String name = null;
    public int r = 0;
    public int g = 0;
    public int b = 0;
    
    public NamedColor(String name, int r, int g, int b) {
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
    }
}

class ColorDisplay extends JComponent { 
    Color color = Color.yellow;
    
    public ColorDisplay(){
        super();
    }
    
    public void paint(Graphics g){
        Dimension d = getSize();
        g.setColor(color);
        g.fillRect(1, 1, d.width - 2, d.height - 2);
    }
    
    public void setColor(NamedColor color) {
        this.color = new Color(color.r, color.g, color.b);
    }
}