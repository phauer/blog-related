package functions;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class ProductClient {

    public Product parseProductFromHttpBody(Response response){
        if (response == null){
            throw new ProductClientException("Response is null!");
        }
        int code = response.code();
        if (code == 200 || code == 201){
            return mapToDTO(response.body());
        }
        if (code >= 400 && code <= 499){
            throw new ProductClientException("Send an invalid request.");
        }
        if (code >= 500 && code <= 599){
            throw new ProductClientException("Server error.");
        }
        throw new ProductClientException("Unknown code " + code);
    }

    private Product mapToDTO(ResponseBody body) {
        return null;
    }

    public static class Product{

    }

    public static class ProductClientException extends RuntimeException{
        public ProductClientException(String message) {
            super(message);
        }
    }
}
