package real.al.knowles.ratpack.learning.database

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ratpack.exec.ExecResult
import ratpack.exec.Promise
import ratpack.test.exec.ExecHarness
import real.al.knowles.ratpack.learning.retry.RetryEvaluator

import javax.sql.DataSource
import java.sql.Connection
import java.sql.SQLException
import java.time.Duration
import java.util.concurrent.ThreadLocalRandom

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.ArgumentMatchers.*
import static org.mockito.Mockito.*

@RunWith(JUnitParamsRunner)
class DatabaseExecutorSpec {

    private Connection connection = mock(Connection)

    private DataSource dataSource = mock(DataSource)

    private RetryEvaluator retryEvaluator = mock(RetryEvaluator)

    private DatabaseExecutor databaseExecutor

    @Before
    void setUp() {
        databaseExecutor = new DatabaseExecutor(2, 1, dataSource, retryEvaluator)
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
    @Parameters(['1205, 3, 2', '1213, 3, 2', '1300, 1, 1'])
    void execute_retryWhenReturnAndSqlError(int sqlErrorCode, int expectedAttempts, int expectedRetries) {
        ratpack.func.Factory<String> work = mock(ratpack.func.Factory)
        SQLException exception = mock(SQLException)
        when(exception.getErrorCode()).thenReturn(sqlErrorCode)
        when(work.create()).thenThrow(exception)

        when(retryEvaluator.withFullJitter(eq(1), anyInt(), any(ThreadLocalRandom), any(DatabaseException)))
                .thenAnswer({
                    invocation ->
                        DatabaseException throwable = invocation.getArgument(3, DatabaseException)
                        if (throwable.isRetryable()) {
                            return Promise.value(Duration.ofMillis(1))
                        }

                        throw throwable
                })

        ExecResult<String> result =
                ExecHarness.harness().yield({
                    databaseExecutor.execute(work)
                })

        Throwable thrownException = result.throwable
        assertThat(thrownException)
                .isExactlyInstanceOf(DatabaseException)
                .hasMessage('Operation failed')
                .hasCause(exception)

        verify(work, times(expectedAttempts)).create()
        verify(retryEvaluator, times(expectedRetries))
                .withFullJitter(eq(1), anyInt(), any(ThreadLocalRandom), any(DatabaseException))
    }

    @Test
    void execute_noRetryWhenReturnAndNotSqlError() {
        ratpack.func.Factory<String> work = mock(ratpack.func.Factory)
        RuntimeException exception = new RuntimeException('I failed')
        when(work.create()).thenThrow(exception)

        when(retryEvaluator.withFullJitter(eq(1), anyInt(), any(ThreadLocalRandom), any(DatabaseException)))
                .thenAnswer({
                    invocation ->
                        throw invocation.getArgument(3, DatabaseException)
                })

        ExecResult<String> result =
                ExecHarness.harness().yield({
                    databaseExecutor.execute(work)
                })

        Throwable thrownException = result.throwable
        assertThat(thrownException)
                .isExactlyInstanceOf(DatabaseException)
                .hasMessage('Operation failed')
                .hasCause(exception)

        verify(work, times(1)).create()
    }

}
