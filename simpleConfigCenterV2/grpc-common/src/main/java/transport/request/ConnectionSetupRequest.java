package transport.request;

import lombok.Data;

/**
 * 客户端链接到服务端标记
 *
 * @author djl
 */
@Data
public class ConnectionSetupRequest implements IRequest {

    /**
     * 标记当前客户端需要监听的配置键
     */
    private String listenConfigKey;
}
