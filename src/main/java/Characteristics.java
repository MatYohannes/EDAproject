import java.util.List;

public class Characteristics {
    private int customId;
    private String attributes;
    private String value;

    public Characteristics() {
    }

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
