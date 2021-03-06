package windowBuilder.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JSlider;
import javax.swing.JComboBox;

import windowBuilder.views.*;

/**
 * Creates enums for all rtlsdrd dameon parameters
 * Created by jamespatrizi on 9/30/17.
 * @author Jim Patrizi
 */

public enum Parameters {
    FREQUENCY("FREQUENCY"),
    SQUELCH_LEVEL("SQUELCH_LEVEL"),
    SQUELCH_DELAY("SQUELCH_DELAY"),
    TUNER_GAIN("TUNER_GAIN"),
    MODULATION_MODE("MODULATION_MODE"),
    OVERSAMPLING("OVERSAMPLING"),
    ENABLE_OPTION("ENABLE_OPTION"),
    SCANNABLE_FREQUENCY("SCANNABLE_FREQUENCY"),
    PPM_ERROR("PPM_ERROR"),
    SAMPLE_RATE("SAMPLE_RATE"),
    RESAMPLE_RATE("RESAMPLE_RATE"),
    ATAN_MATH("ATAN_MATH"),
    FIR_SIZE("FIR_SIZE"),
    VOLUME("VOLUME");

    /**
     * This parameter to a command indicates that the default action is to be used
     */
    public static final String DEFAULT_SPECIFIER = "default";

    /**
     * Each enum
     */
    private final String FUNCTION;

    /**
     * Array of Strings for each enum to hold their params
     */
    private List<String> values = new ArrayList<>();

    /**
     * Associated ui element with parameter
     */
    private Object uiElement;

    /**
     * The ui elements type
     */
    private Class uiElementSpecificType;

    /**
     * Constructor, for the enums allowed sets each to FUNCTION
     * @param function - the current enum
     */
    Parameters(final String function)
    {
        this.FUNCTION = function;
    }

    /**
     * Appends the value to that enums list
     * @param val
     */
    public synchronized void append(String val)
    {
        for (String s:values) {
            if(val.equals(s))
            {
//                showGotItDialog("Parameter Warning!" , "Attempting to set" + this.FUNCTION + "with: " + val + "more than once", false);
                System.out.println("Attempting to set" + this.FUNCTION + "with: " + val + "more than once");
                return;
            }
        }
        values.add(val);
    }

    /**
     * Associates ui member with this parameter
     * @param uiElement
     * @param uiElementSpecificType
     */
    public synchronized void setUiMembers(Object uiElement, Class uiElementSpecificType)
    {
        this.uiElement = uiElement;
        this.uiElementSpecificType = uiElementSpecificType;
    }

    public void updateField(homeScreen mainActivity, final String newVal)
    {
        System.out.println("NEW VALUE: " + newVal);
        System.out.println("UPDATING FIELD: " + FUNCTION + " with val: " + newVal);
        
        //Checks for JFormattedTextField
        if (uiElementSpecificType.equals(JFormattedTextField.class))
        {
            ((JFormattedTextField)uiElement).setValue((String)newVal);
        }
        //Checks for JComboBox
        else if (uiElementSpecificType.equals(JComboBox.class))
        {
            ((JComboBox)uiElement).setSelectedItem(newVal);
        }
        //Checks for JSlider
        else if (uiElementSpecificType.equals(JSlider.class))
        {
            ((JSlider)uiElement).setValue(Integer.parseInt((String)newVal));
        }
        //Checks for JRadioButtons
        else if (uiElementSpecificType.equals(EnableOptionUiMatcher.class))
        {
            ((EnableOptionUiMatcher)uiElement).enableSwitchByString(homeScreen.getInstance(), newVal);
        }    
    }
    
//   
    /**
     * Get uiElement for Parameter
     * @return returns UI element for this parameter
     */
    public synchronized Object getUiElement()
    {
        return uiElement;
    }

    /**
     * Removes val from list of values for Parameter
     * @param val - value to be removed
     * @return - returns true if value is successfully removed
     */
    public synchronized boolean remove(String val)
    {
        return values.remove(val);
    }

    /**
     * Clear all values for this parameter
     */
    public synchronized void resetValues()
    {
        values.clear();
    }

    /**
     * Return the value at this index
     * @param idx - index to lookup value at
     * @return - returns string value at the idx
     */
    public synchronized String getByIndex(int idx)
    {
        return values.get(idx);
    }

    /**
     * Checks if the index is valid
     * @param idx - index to check
     * @return - returns boolean of condition idx < values.size()
     */
    public synchronized boolean isIndexValid(int idx)
    {
        if (idx < 0)
        {
            return false;
        }

        return idx < values.size();
    }

    /**
     * Replaces current index with val
     * @param idx - index to replace
     * @param val - value to replace in index
     * @return - if index doesn't exist, return false
     */
    public synchronized boolean replaceIndex(int idx, String val)
    {
        if (!isIndexValid(idx))
        {
            return false;
        }
        else
        {
            values.set(idx, val);
            return true;
        }
    }

    /**
     * Get all values of arraylist
     * @return - array list of values
     */
    public synchronized List<String> getValues()
    {
        return new ArrayList<>(values);
    }

    /**
     * Makes a new list of daemon formatted strings to send to the server
     * @return - list of daemon formatted strings
     */
    public synchronized List<String> getDameonCallableStrings(){
        List<String> dameonStrings = new ArrayList<>();
        for(String s : values){
            dameonStrings.add(FUNCTION + "=" + s);
        }
        return dameonStrings;
    }

    /**
     * Returns the current parameter
     * @return - this parameter
     */
    public String getFunction()
    {
        return FUNCTION;
    }
}
