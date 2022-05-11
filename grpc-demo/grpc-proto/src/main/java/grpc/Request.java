package grpc;

/**
 * @author djl
 */
public abstract class Request implements Payload {

    /**
     * 请求类型
     *
     * @return
     */
    abstract String getType();


}
