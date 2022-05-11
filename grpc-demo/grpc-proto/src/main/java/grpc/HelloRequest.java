package grpc;

/**
 * @author djl
 */
public class HelloRequest extends Request {
    @Override
    String getType() {
        return "hello";
    }
}
