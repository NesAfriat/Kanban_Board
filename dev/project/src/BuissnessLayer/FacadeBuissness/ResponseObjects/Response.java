package BuissnessLayer.FacadeBuissness.ResponseObjects;

public class Response {
    public String ErrorMessage;

    public boolean ErrorOccured(){
        return ErrorMessage!=null;
    }

    public Response(){
        ErrorMessage=null;
    }
    public Response(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
