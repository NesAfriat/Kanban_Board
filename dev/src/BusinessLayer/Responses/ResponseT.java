package BusinessLayer.Responses;

public class ResponseT<T> extends Response {
    private final T value;
    public ResponseT(String errorMessage) {
        super(errorMessage);
        value = null;
    }

    public ResponseT(T value) {
        super();
        this.value = value;
    }

    public ResponseT(T value, String msg) {
        super(msg);
        this.value = value;
    }

}
