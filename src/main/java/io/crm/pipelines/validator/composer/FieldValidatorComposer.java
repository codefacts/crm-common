package io.crm.pipelines.validator.composer;

import io.crm.MessageBundle;
import io.crm.pipelines.validator.ValidatorPipeline;
import io.crm.pipelines.validator.impl.*;
import io.crm.pipelines.validator.impl.type.*;
import io.vertx.core.json.JsonObject;

import java.util.regex.Pattern;

/**
 * Created by shahadat on 2/28/16.
 */
public class FieldValidatorComposer {
    private final String field;
    private final ValidatorPipeline<JsonObject> validatorPipeline;
    private final MessageBundle messageBundle;

    public FieldValidatorComposer(String field,
                                  ValidatorPipeline<JsonObject> validatorPipeline, MessageBundle messageBundle) {
        this.field = field;
        this.validatorPipeline = validatorPipeline;
        this.messageBundle = messageBundle;
    }

    public FieldValidatorComposer numberType() {
        validatorPipeline.add(new NumberValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer booleanType() {
        validatorPipeline.add(new BooleanValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer integerType() {
        validatorPipeline.add(new IntegerValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer longType() {
        validatorPipeline.add(new LongValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer floatType() {
        validatorPipeline.add(new FloatValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer doubleType() {
        validatorPipeline.add(new DoubleValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer stringType() {
        validatorPipeline.add(new StringValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer doubleRange() {
        validatorPipeline.add(new DoubleValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer email() {
        validatorPipeline.add(new EmailValidationError(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer length(int minLength, int maxLength) {
        validatorPipeline.add(new LengthValidationError(messageBundle, field, minLength, maxLength));
        return this;
    }

    public FieldValidatorComposer maxLength(int maxLength) {
        validatorPipeline.add(new MaxLengthValidationError(messageBundle, field, maxLength));
        return this;
    }

    public FieldValidatorComposer minLength(int minLength) {
        validatorPipeline.add(new MinLengthValidationError(messageBundle, field, minLength));
        return this;
    }

    public FieldValidatorComposer notNullEmptyOrWhiteSpace() {
        validatorPipeline.add(new NotNullEmptyOrWhiteSpace(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer notNull() {
        validatorPipeline.add(new NotNullValidator(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer pattern(Pattern pattern) {
        validatorPipeline.add(new PatternValidationError(messageBundle, field, pattern));
        return this;
    }

    public FieldValidatorComposer phone() {
        validatorPipeline.add(new PhoneValidationError(messageBundle, field));
        return this;
    }

    public FieldValidatorComposer range(long min, long max) {
        validatorPipeline.add(new RangeValidator(messageBundle, field, min, max));
        return this;
    }

}
