package com.kits.ecommerce.exeptions;


public class ResoureNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    String resourceName;
    String field;
    String fieldName;
    int fieldId;
    public ResoureNotFoundException() {
    }

    public ResoureNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s: %s",resourceName,field,fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }
    public ResoureNotFoundException(String resourceName, String field, int fieldId) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
