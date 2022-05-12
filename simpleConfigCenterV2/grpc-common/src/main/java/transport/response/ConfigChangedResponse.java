package transport.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author djl
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigChangedResponse extends BaseResponse implements IResponse {
    private String changedConfigKey;
}
