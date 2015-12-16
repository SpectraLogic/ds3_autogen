<#include "../copyright.ftl"/>

package ${packageName};

public abstract class ${name} {

    public enum Type {
${javaHelper.getEnumValues(javaHelper.addEnum(enumConstants, "NONE"), 2)}
    }

    private static final ${name} none = new None();
    private static final ${name} compute = new Compute();

    public static ${name} none() {
        return none;
    }

    public static ${name} compute() {
        return compute;
    }

    public static ${name} value(final byte[] hash) {
        return new Value(hash);
    }

    public abstract <T, E extends Throwable> T match(final MatchHandler<T, E> handler) throws E;

    public interface MatchHandler<T, E extends Throwable> {
        T none() throws E;
        T compute() throws E;
        T value(final byte[] hash) throws E;
    }

    private ${name}() {
        // Prevent external instantiation.
    }

    private static class None extends ${name} {
        @Override
        public <T, E extends Throwable> T match(final MatchHandler<T, E> handler) throws E {
            return handler.none();
        }
    }

    private static class Compute extends ${name} {
        @Override
        public <T, E extends Throwable> T match(final MatchHandler<T, E> handler) throws E {
            return handler.compute();
        }
    }

    private static class Value extends ${name} {
        private final byte[] hash;

        public Value(final byte[] hash) {
            this.hash = hash;
        }

        @Override
        public <T, E extends Throwable> T match(final MatchHandler<T, E> handler) throws E {
            return handler.value(this.hash);
        }
    }
}
