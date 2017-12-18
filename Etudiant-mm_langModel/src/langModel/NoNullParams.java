package langModel;

import java.util.Arrays;

public abstract class NoNullParams {
    public static void assertNoneNull(Object... params){
        if(params.length == 0)
            return;

        long nullCount = Arrays.stream(params)
        .filter(param -> param==null)
        .count();

        if(nullCount != 0)
            throw new IllegalArgumentException("Parameters must not be null");
    }
}
