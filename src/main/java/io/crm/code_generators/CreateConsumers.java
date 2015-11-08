package io.crm.code_generators;

import com.google.common.collect.ImmutableList;
import io.crm.util.ExceptionUtil;
import io.crm.util.SimpleCounter;
import io.crm.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * Created by someone on 08/11/2015.
 */
public class CreateConsumers {

    public static void main(String... args) {
        String dir = "D:\\IdeaProjects\\crm-common\\src\\main\\java\\io\\crm\\intfs";
        String[] strings = {"Peta", "Hexa", "Seven", "Octa"};

        final SimpleCounter cc = new SimpleCounter(5);
        Arrays.asList(strings).forEach(nm -> {
            String fs = "package io.crm.intfs;\n" +
                    "\n" +
                    "/**\n" +
                    " * Created by someone on 08/11/2015.\n" +
                    " */\n" +
                    "public interface " + nm + "ConsumerUnchecked<" + ts(cc.counter) + "> {\n" +
                    "    public void accept(" + tsv(cc.counter) + ") throws Exception;\n" +
                    "}\n";
            cc.counter++;

            Util.accept(new File(dir, nm + "ConsumerUnchecked" + ".java"), file -> {
                PrintStream stream = null;
                try {
                    file.createNewFile();
                    stream = new PrintStream(file);
                    stream.print(fs);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    stream.close();
                }
            });

        });
    }

    static String ts(int n) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (int i = 1; i <= n; i++) {
            builder.add("T" + i);
        }
        return String.join(", ", builder.build());
    }

    static String tsv(int n) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (int i = 1; i <= n; i++) {
            builder.add("T" + i + " t" + i);
        }
        return String.join(", ", builder.build());
    }
}
