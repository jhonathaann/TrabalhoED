import java.lang.instrument.Instrumentation;

public class Memoria {
    private static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object object) {
        return instrumentation.getObjectSize(object);
    }
}
