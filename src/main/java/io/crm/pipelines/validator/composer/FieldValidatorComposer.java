package io.crm.pipelines.validator.composer;

import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidationPipeline;
import io.crm.pipelines.validator.impl.*;
import io.crm.pipelines.validator.impl.type.*;
import io.vertx.core.json.JsonObject;

import java.util.regex.Pattern;

/**
 * Created by shahadat on 2/28/16.
 */
public class FieldValidatorComposer {
    private final String field;
    private final ValidationPipeline<JsonObject> validationPipeline;
    private final MessageBundle messageBundle;

    public FieldValidatorComposer(String field,
                                  ValidationPipeline<JsonObject> validationPipeline, MessageBundle messageBundle) {
        this.field = field;
        this.validationPipeline = validationPipeline;
        this.messageBundle = messageBundle;
    }

    public FieldValidatorComposer numberType() {
        validationPipeline.add(new NumberValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer booleanType() {
        validationPipeline.add(new BooleanValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer integerType() {
        validationPipeline.add(new IntegerValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer longType() {
        validationPipeline.add(new LongValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer floatType() {
        validationPipeline.add(new FloatValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer doubleType() {
        validationPipeline.add(new DoubleValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer stringType() {
        validationPipeline.add(new StringValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer doubleRange() {
        validationPipeline.add(new DoubleValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer email() {
        validationPipeline.add(new EmailValidationError(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer length(int minLength, int maxLength) {
        validationPipeline.add(new LengthValidationError(messageBundle, field, minLength, maxLength));
        return this;
    }

    public FieldValidatorComposer maxLength(int maxLength) {
        validationPipeline.add(new MaxLengthValidationError(messageBundle, field, maxLength));
        return this;
    }

    public FieldValidatorComposer minLength(int minLength) {
        validationPipeline.add(new MinLengthValidationError(messageBundle, field, minLength));
        return this;
    }

    public FieldValidatorComposer notNullEmptyOrWhiteSpace() {
        validationPipeline.add(new NotNullEmptyOrWhiteSpace(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer notNull() {
        validationPipeline.add(new NotNullValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer pattern(Pattern pattern) {
        validationPipeline.add(new PatternValidationError(messageBundle, field, pattern));
        return this;
    }

    public FieldValidatorComposer phone() {
        validationPipeline.add(new PhoneValidationError(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer range(long min, long max) {
        validationPipeline.add(new RangeValidator(messageBundle, field, min, max));
        return this;
    }

}
