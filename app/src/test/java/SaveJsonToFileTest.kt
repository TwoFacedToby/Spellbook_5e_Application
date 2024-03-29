import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.robolectric.RobolectricTestRunner
import java.io.File


/**
 * Integration tests for the saveJsonToFile.
 *
 * This test class verifies the functionality of saving a JSON string to a file within the device's internal storage.
 *
 * It uses Robolectric to provide an Android context, enabling the possibility to simulate file operations
 *
 * The primary focus of these tests is to ensure that the method correctly creates
 * a file in the specified directory with the expected content.
 *
 * The test process involves setting a context in Spellcontroller, so we can call saveJsonToFile to
 * save a predefined JSON string to a file, and then validating that the file creation and content writing
 * are performed correctly.
 *
 * Methods:
 * - {@code setUp()}: Initializes the test environment, setting up the context.
 * - {@code saveJsonToFile_CreatesFileWithCorrectContent()}: Tests the file creation and content writing functionality.
 * - {@code tearDown()}: Cleans up the test environment by deleting created files and directories.
 * @author Kenneth Kaiser
 */
@RunWith(RobolectricTestRunner::class)
class SaveJsonToFileTest {

    private lateinit var context: Context
    private val testDirectoryName = "testDir"
    private val testFileName = "testFile.json"
    private val testJsonString = "{\"key\": \"value\"}"
    private val invalidDirectoryName = "/invalid/testDir" // Invalid path

    @Before
    fun setUp() {
        // Use Robolectric to get a mock Context
        context = ApplicationProvider.getApplicationContext()
        // Set the context in your SpellController or the class where saveJsonToFile is defined
        LocalDataLoader.setContext(context)
    }

    @Test
    fun saveJsonToFile_CreatesFileWithCorrectContent() {

        // Call the method to test
        LocalDataLoader.saveJson(testJsonString, testFileName, LocalDataLoader.DataType.HOMEBREW)

        // Construct the expected file path
        val expectedFile = LocalDataLoader.getJson(testFileName, LocalDataLoader.DataType.HOMEBREW)  //File(context.filesDir, "Homebrews/$testFileName")

        // Assert the file exists
        assertNotNull(expectedFile)

        // Assert the content is as expected
        assertEquals(testJsonString, expectedFile)
    }

    @After
    fun tearDown() {
        // Clean up: Delete the test directory and all of its content
        val testDirectory = File(context.filesDir, testDirectoryName)
        testDirectory.deleteRecursively()
    }
}