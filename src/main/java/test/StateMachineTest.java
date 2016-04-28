package test;

import io.crm.promise.Promises;
import io.crm.statemachine.CallbacksBuilder;
import io.crm.statemachine.StateMachine;
import io.crm.statemachine.States;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.Collections;

import static io.crm.statemachine.States.on;

/**
 * Created by shahadat on 4/28/16.
 */
public class StateMachineTest {
    public static void main(String[] args) {
        final StateMachine stateMachine = States
            .withInitial("start")
            .from("start",
                on("created").to("success"),
                on("validationError").to("ValidationError"),
                on("authFailed").to("Unauthorized")
            )
            .callbacks("start", CallbacksBuilder.<Message<JsonObject>>getInstance()
                .setOnEnter(message -> message.headers())
                .setOnExit(message -> {
                })
                .setInitialize(message -> {
                    return Promises.from(Collections.EMPTY_MAP);
                })
                .setExecute((context, message) -> Promises.from(Collections.EMPTY_MAP))
                .setCleanup((context, message) -> {
                    return Promises.from();
                })
                .createState())
            .build();
        stateMachine.start("");
    }
}
