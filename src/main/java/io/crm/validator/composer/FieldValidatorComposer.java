package io.crm.validator.composer;

/**
 * Created by shahadat on 2/28/16.
 */
public class FieldValidatorComposer {
    private final String field;

    public FieldValidatorComposer(String field) {
        this.field = field;
    }

    public FieldValidatorComposer stringType() {
        return this;
    }

    public FieldValidatorComposer booleanType() {
        return this;
    }

    public FieldValidatorComposer integerType() {
        return this;
    }

    public FieldValidatorComposer longType() {
        return this;
    }

    public FieldValidatorComposer doubleType() {

    }

    public FieldValidatorComposer floatType() {
        return this;
    }
}
