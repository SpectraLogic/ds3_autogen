package com.spectralogic.autogen.cli;

public class Guards {
    public static <T, E> T returnIfNull(final E e, final Convert<T, E> converter) throws Exception {
        if (e != null) {
            return converter.convert(e);
        }
        else {
            return null;
        }
    }

    @FunctionalInterface
    public interface Convert<T, E> {
        T convert(E type) throws Exception;
    }
}
