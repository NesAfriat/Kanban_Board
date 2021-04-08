package BuissnessLayer.FacadeBuissness.ResponseObjects;

public class ResponseT<T> extends Response {
    public T value;
    public ResponseT(String message,T value){
        super(message);
        this.value=value;
    }
    public ResponseT(T value){
        this.value=value;
    }
}
