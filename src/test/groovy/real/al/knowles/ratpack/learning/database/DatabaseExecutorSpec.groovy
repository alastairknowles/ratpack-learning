package real.al.knowles.ratpack.learning.database

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import ratpack.exec.ExecResult
import ratpack.test.exec.ExecHarness

import javax.sql.DataSource
import java.sql.Connection
import java.sql.SQLException

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.*

@RunWith(JUnitParamsRunner)
class DatabaseExecutorSpec {

    @Mock
    private Connection connection

    @Mock
    private DataSource dataSource

    private DatabaseExecutor databaseExecutor

    @Before
    void setUp() {
        databaseExecutor = new DatabaseExecutor(2, 1, dataSource)
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
    @Parameters(['1205, 3', '1213, 3', '1300, 1'])
    void execute_retryWhenReturnAndSqlError(int sqlErrorCode, int expectedAttempts) {
        ratpack.func.Factory<String> work = mock(ratpack.func.Factory)
        SQLException exception = mock(SQLException)
        when(exception.getErrorCode()).thenReturn(sqlErrorCode)
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

        verify(work, times(expectedAttempts)).create()
    }

    @Test
    void execute_noRetryWhenReturnAndNotSqlError() {
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

        verify(work, times(1)).create()
    }

}
