package langModel;

import java.util.Arrays;
import java.util.Objects;

public abstract class NoNullParams {
    public static void assertNoneNull(Object... params){
        if(!noneIsNull(params))
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
    }

    public static boolean noneIsNull(Object... params){
        if(params.length == 0)
            return true;

        long nullCount = Arrays.stream(params)
                .filter(Objects::isNull)
                .count();

        return nullCount == 0;
    }

    public final static String NULL_ERROR_MESSAGE = "Parameters must not be null";
}
