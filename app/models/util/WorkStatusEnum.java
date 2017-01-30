package models.util;

public enum WorkStatusEnum {
	DEFAULT("Default", "00"),
	NOT_YET("Not Yet", "10"),
	PROCESSING("Processing", "20"),
	DONE("Done", "30"),
	ERROR("Error", "90"),
    ;

    private final String label;
    private final String code;

    private WorkStatusEnum(final String label, final String code) {
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
