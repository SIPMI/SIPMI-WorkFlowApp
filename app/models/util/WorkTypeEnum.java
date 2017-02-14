package models.util;

public enum WorkTypeEnum {
	INPUT("Input", "1"),
	FUNCTION("Function", "2"),
	OUTPUT("Output", "3"),
	VALUE("Value", "4"),
	LOGIC("Logic", "5"),
    ;

    private final String label;
    private final String code;

    private WorkTypeEnum(final String label, final String code) {
       this.label = label;
       this.code = code;
    }

    public String getLabel() {
        return this.label;
    }

    public String getCode() {
        return this.code;
    }
}
