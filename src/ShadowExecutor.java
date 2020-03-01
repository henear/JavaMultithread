import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShadowExecutor {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public static ShadowResult<Integer> getCount() throws ExecutionException, InterruptedException {
        final CompletableFuture<Integer> teamAResult = callAsync(1);
        final CompletableFuture<Integer> teamBResult = callAsync(2);
        return getShadowResult(teamAResult, teamBResult);

    }

    public static CompletableFuture<Integer> callAsync(int idx){
        return CompletableFuture.supplyAsync(() -> idx * idx);
    }

    private static <T> ShadowResult<T> getShadowResult(final CompletableFuture<T> teamAResult, final CompletableFuture<T> teamBResult) throws ExecutionException, InterruptedException {
        waitForCompletion(teamAResult, teamBResult);
        try {
            return new ShadowResult<>(teamAResult.get(), teamBResult.get());
        } catch (Exception e) {
            System.out.println("got exception");
            throw new RuntimeException(e);
        }
    }

    private static <T> void waitForCompletion(final CompletableFuture<T> teamAResult, final CompletableFuture<T> teamBResult) throws ExecutionException, InterruptedException {
        T taResult = teamAResult.get();
        T tbResult = teamBResult.get();
        ArrayList<T> res = new ArrayList<>();
        res.add(taResult);
        res.add(tbResult);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ShadowResult<Integer> result = getCount();
        int a = result.getTeamAResult();
        int b = result.getTeamBResult();
        System.out.println("TeamAResult: " + a + " TeamBResult: " + b);
    }
}
