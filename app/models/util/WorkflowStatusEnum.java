package models.util;

public enum WorkflowStatusEnum {
	DEFAULT("Default", "00"),
	UNEXECUTED("Unexecuted", "10"),
	PROCESSING("Processing", "20"),
	FINISHED("Finished", "30"),
	ERROR("Error", "90"),
    ;

    private final String label;
    private final String code;

    private WorkflowStatusEnum(final String label, final String code) {
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

