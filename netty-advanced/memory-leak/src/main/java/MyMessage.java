import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author djl
 */
@Data
@AllArgsConstructor
@ToString
public class MyMessage {
    private Integer requestId;
    private String content;
}
