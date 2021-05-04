package BuisnnesLayer.FacedeModel.Objects;

public class Response {
    private String errorMsg;
    private Boolean errorOccurred;

    public Response() {
        errorOccurred = false;
    }

    public Response(String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorOccurred = true;
    }

    public Boolean getErrorOccurred() {
        return errorOccurred;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String print() {
        if (errorOccurred) {
            return "";
        }
        return errorMsg;
    }

}
