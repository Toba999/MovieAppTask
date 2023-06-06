import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pop_flake.BaseTestClassWithRules
import com.example.pop_flake.data.local.DataStoreManager
import com.example.pop_flake.data.local.DataStoreManagerInterface
import com.example.pop_flake.data.pojo.SettingsState
import com.example.pop_flake.ui.settings.SettingsViewModel
import com.example.pop_flake.utils.Constants.DARK_MODE_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.AdditionalMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@ExperimentalCoroutinesApi
class SettingsViewModelTest : BaseTestClassWithRules() {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataStoreManager: DataStoreManagerInterface

    private lateinit var viewModel: SettingsViewModel

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SettingsViewModel(dataStoreManager)
    }

    @Test
    fun `getDarkModeOption should update state with the value from DataStore`() = runBlocking {
        // Mock the expected values
        val darkModeOption = AppCompatDelegate.MODE_NIGHT_YES
        val initialState = SettingsState()

        // Mock the DataStore response
        `when`(
            dataStoreManager.getInt(
                DARK_MODE_KEY,
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            )
        ).thenReturn(flowOf(darkModeOption))

        // Set the initial state
        viewModel._state.value = initialState

        // Call the function under test
        viewModel.getDarkModeOption()

        // Verify that the DataStoreManager getInt function was called with the correct parameters
        verify(dataStoreManager).getInt(DARK_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Verify that the state was updated with the expected value
        val updatedState = viewModel.state.first()
        assertEquals(darkModeOption, updatedState.darkModeOption)
        assertEquals(
            initialState.list,
            updatedState.list
        ) // Verify that other properties remain unchanged
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }


}
