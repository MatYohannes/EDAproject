/**
 * The Characteristics class represents characteristics associated with a custom ID.
 * It includes attributes and their corresponding values.
 */
public class Characteristics {
    private int customId; // Custom ID associated with the characteristics
    private String attributes; // Attributes describing the characteristics
    private String value; // Value of the characteristics

    /**
     * Default constructor for the Characteristics class.
     * Initializes all fields to default values.
     */
    public Characteristics() {
    }

    /**
     * Parameterized constructor for the Characteristics class.
     * Initializes the custom ID, attributes, and value with provided values.
     *
     * @param customId   The custom ID associated with the characteristics.
     * @param attributes The attributes describing the characteristics.
     * @param value      The value of the characteristics.
     */
    public Characteristics(int customId, String attributes, String value) {
        this.customId = customId;
        this.attributes = attributes;
        this.value = value;
    }

    // Getters and setters
    public int getCustomId() { return customId;}
    public void setCustomId(int customId) {this.customId = customId;}
    public String getAttributes() {return attributes;}
    public void setAttributes(String attributes) {this.attributes = attributes;}
    public String getValue() {return value;}
    public void setValue(String value) {this.value = value;}

}
