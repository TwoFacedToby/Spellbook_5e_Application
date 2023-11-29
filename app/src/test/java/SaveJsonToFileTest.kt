import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.spellbook5eapplication.app.Utility.SpellController
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.io.IOException


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
        SpellController.setContext(context)
    }

    @Test
    fun saveJsonToFile_CreatesFileWithCorrectContent() {
        // Call the method to test
        SpellController.saveJsonToFile(testJsonString, testDirectoryName, testFileName)

        // Construct the expected file path
        val expectedFilePath = File(context.filesDir, "$testDirectoryName/$testFileName")

        // Assert the file exists
        assertTrue(expectedFilePath.exists())

        // Read the content of the file
        val fileContent = expectedFilePath.readText()

        //Prints for debugging
        println(testJsonString)
        println(fileContent)

        // Assert the content is as expected
        assertEquals(testJsonString, fileContent)
    }

    @After
    fun tearDown() {
        // Clean up: Delete the test directory and all of its content
        val testDirectory = File(context.filesDir, testDirectoryName)
        testDirectory.deleteRecursively()
    }
}