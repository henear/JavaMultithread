import lombok.Data;

@Data
public class ShadowResult<T> {
    private final T teamAResult;
    private final T teamBResult;
}
