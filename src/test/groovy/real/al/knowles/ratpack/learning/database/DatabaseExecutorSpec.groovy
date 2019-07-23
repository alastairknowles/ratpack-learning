package real.al.knowles.ratpack.learning.database

import net.jodah.failsafe.RetryPolicy
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ratpack.exec.ExecResult
import ratpack.exec.Promise
import ratpack.test.exec.ExecHarness

import javax.sql.DataSource
import java.sql.Connection
import java.time.Duration

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner)
class DatabaseExecutorSpec {

    @Mock
    private Connection connection

    @Mock
    private DataSource dataSource

    private DatabaseExecutor databaseExecutor

    private RetryPolicy<Promise> retryPolicy =
            new RetryPolicy<Promise>()
                    .withMaxAttempts(2)
                    .withDelay(Duration.ofMillis(1))
                    .onFailure({ completedEvent ->
                        throw new DatabaseException("Operation failed", completedEvent.getFailure())
                    })

    @Before
    void setUp() {
        when(dataSource.getConnection()).thenReturn(connection)

        databaseExecutor = new DatabaseExecutor(dataSource, retryPolicy)
    }

    @Test
    void execute_successWhenReturn() {
        ratpack.func.Factory<String> work = mock(ratpack.func.Factory)
        when(work.create()).thenReturn('success')

        ExecResult<String> result =
                ExecHarness.harness().yield({
                    databaseExecutor.execute(work)
                })

        assertThat(result.value).isEqualTo('success')

        verify(work, times(1)).create()
    }

    @Test
    void execute_retryWhenReturnAndError() {
        ratpack.func.Factory<String> work = mock(ratpack.func.Factory)
        RuntimeException exception = new RuntimeException('I failed')
        when(work.create()).thenThrow(exception)

        ExecResult<String> result =
                ExecHarness.harness().yield({
                    databaseExecutor.execute(work)
                })

        Throwable thrownException = result.throwable
        assertThat(thrownException)
                .isExactlyInstanceOf(DatabaseException)
                .hasMessage('Operation failed')
                .hasCause(exception)

        verify(work, times(2)).create()
    }

}
