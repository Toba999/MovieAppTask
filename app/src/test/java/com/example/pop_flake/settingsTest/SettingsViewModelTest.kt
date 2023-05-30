import androidx.appcompat.app.AppCompatDelegate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pop_flake.data.local.DataStoreManager
import com.example.pop_flake.data.pojo.ComplaintModel
import com.example.pop_flake.data.pojo.SettingsState
import com.example.pop_flake.ui.settings.SettingsViewModel
import com.example.pop_flake.utils.Constants.DARK_MODE_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule


@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockDataStoreManager: DataStoreManager

    private lateinit var viewModel: SettingsViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SettingsViewModel(mockDataStoreManager)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun testGetDarkModeOption() = testScope.runTest {
        val testValue = AppCompatDelegate.MODE_NIGHT_YES
        val mockState = SettingsState(darkModeOption = testValue)
        val flow = flowOf(testValue)

        `when`(mockDataStoreManager.getInt(DARK_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)).thenReturn(flow)

        viewModel.getDarkModeOption()

        testScheduler.apply { advanceTimeBy(1); runCurrent() }

        verify(mockDataStoreManager).getInt(DARK_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        assert(viewModel.state.first() == mockState)
    }

    @Test
    fun testAddComplaintItem() {
        val mockComplaintModel = ComplaintModel("Test Complaint","desc")
        val updatedState = SettingsState(list = mutableListOf(mockComplaintModel))

        viewModel._state.value = viewModel._state.value.copy(list = mutableListOf(mockComplaintModel))

        viewModel.addComplaintItem(mockComplaintModel)

        assert(viewModel.state.value == updatedState)
    }

    @Test
    fun testSetDarkModeOption() = testScope.runBlockingTest {
        val testOption = AppCompatDelegate.MODE_NIGHT_NO

        viewModel.setDarkModeOption(testOption)

        verify(mockDataStoreManager, times(1)).setInt(DARK_MODE_KEY, testOption)
    }

}
