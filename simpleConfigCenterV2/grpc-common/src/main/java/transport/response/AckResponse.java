package transport.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author djl
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AckResponse extends BaseResponse implements IResponse {
    private String msg = "ack";
}
