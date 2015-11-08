package io.crm.code_generators;

import com.google.common.collect.ImmutableList;
import io.crm.util.SimpleCounter;
import io.crm.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * Created by someone on 08/11/2015.
 */
public class CreateFunctions {
    public static void main(String... args) {
        String dir = "D:\\IdeaProjects\\crm-common\\src\\main\\java\\io\\crm\\intfs";
        String[] strings = {"Tri", "Quad", "Peta", "hexa", "Seven", "Octa"};

        final SimpleCounter cc = new SimpleCounter(3);
        Arrays.asList(strings).forEach(nm -> {
            String fs = "package io.crm.intfs;\n" +
                    "\n" +
                    "/**\n" +
                    " * Created by someone on 08/11/2015.\n" +
                    " */\n" +
                    "public interface " + nm + "FunctionUnchecked<" + ts(cc.counter) + ", R> {\n" +
                    "    public R apply(" + tsv(cc.counter) + ") throws Exception;\n" +
                    "}\n";

            Util.accept(new File(dir, nm + "FunctionUnchecked" + ".java"), file -> {
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
            cc.counter++;
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
