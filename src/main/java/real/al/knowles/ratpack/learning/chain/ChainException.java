package real.al.knowles.ratpack.learning.chain;

public class ChainException extends RuntimeException {

    public ChainException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
