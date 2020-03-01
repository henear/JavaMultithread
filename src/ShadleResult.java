import lombok.Data;

@Data
public class ShadleResult<T> {
    private final T teamAResult;
    private final T teamBResult;
}
