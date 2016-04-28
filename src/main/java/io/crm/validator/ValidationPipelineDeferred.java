package io.crm.validator;

import com.google.common.collect.ImmutableList;
import io.crm.util.Context;
import io.crm.promise.intfs.Promise;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by shahadat on 4/27/16.
 */
public class ValidationPipelineDeferred {
    private final List<ValidatorDeferred<JsonObject>> list;

    public ValidationPipelineDeferred(List<ValidatorDeferred<JsonObject>> list) {
        this.list = list;
    }

    public Promise<List<ValidationResult>> validate(JsonObject jsonObject, Context context) {

        if (list.isEmpty()) return null;

        final ImmutableList.Builder<ValidationResult> builder = ImmutableList.<ValidationResult>builder();

        Promise<ValidationResult> promise = list.get(0).validate(jsonObject, context)
            .then(val -> {
                if (val != null) {
                    builder.add(val);
                }
            });

        for (int i = 1; i < list.size(); i++) {
            final ValidatorDeferred<JsonObject> validatorDeferred = list.get(i);
            promise = promise
                .mapToPromise(result -> validatorDeferred.validate(jsonObject, context))
                .then(result -> {
                    if (result != null) {
                        builder.add(result);
                    }
                });
        }

        return promise.map(v -> {
            final ImmutableList<ValidationResult> results = builder.build();
            return results.size() <= 0 ? null : results;
        });
    }
}
