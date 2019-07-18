package real.al.knowles.ratpack.learning.chain;

import ratpack.handling.Context;

public class ChainUtils {

    public static Long pathTokenAsLong(Context context, String name) {
        try {
            return context.getAllPathTokens().asLong(name);
        } catch (Exception exception) {
            throw new ChainException("Unable to parse path token", exception);
        }
    }

}
