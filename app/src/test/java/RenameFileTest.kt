import android.content.Context
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider
import com.example.spellbook5eapplication.app.Repository.SpellController
import java.io.File



@RunWith(RobolectricTestRunner::class)
class RenameFileTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        // Perform set up if needed before each test
        SpellController.setContext(context)
    }



    /**
     * Tests the renameFileInDirectory method for its ability to successfully rename an existing file within the application's file directory.
     * This test will create a file, attempt to rename it, and then verify the outcome by checking the existence of the files before and after the operation.
     * It asserts that the file with the original name no longer exists and that a new file with the new name does exist after the operation.
     * Upon completion, the test cleans up by deleting the newly named file and its directory.
     *
     * @author Kenneth Kaiser
     */
    @Test
    fun testRenameFileInDirectory_Success() {
        val directoryName = "testDir"
        val currentFileName = "currentTestFile.txt"
        val newFileName = "newTestFile.txt"

        // Create a directory and file for testing
        val testDirectory = File(context.filesDir, directoryName).also { it.mkdirs() }
        val currentFile = File(testDirectory, currentFileName).also { it.createNewFile() }

        assertTrue("File should exist before renaming", currentFile.exists())

        // Attempt to rename the file
        val isRenamed = SpellController.renameFileInDirectory(directoryName, currentFileName, newFileName)

        assertTrue("File should be renamed successfully", isRenamed)
        assertFalse("Old file name should not exist after renaming", currentFile.exists())
        assertTrue("New file name should exist after renaming", File(testDirectory, newFileName).exists())

        // Clean up
        File(testDirectory, newFileName).delete()
        testDirectory.delete()
    }

    /**
     * Tests the renameFileInDirectory method to ensure it correctly handles a scenario where the file to be renamed does not exist.
     * The method should return false to indicate that no file was renamed. This test does not require file creation as it specifically
     * checks the method's behavior with nonexistent files.
     *
     * @author Kenneth Kaiser
     */
    @Test
    fun testRenameFileInDirectory_FileDoesNotExist() {
        val directoryName = "testDir"
        val currentFileName = "nonExistentFile.txt"
        val newFileName = "newTestFile.txt"

        // Attempt to rename a non-existent file
        val isRenamed = SpellController.renameFileInDirectory(directoryName, currentFileName, newFileName)

        assertFalse("File should not be renamed if it does not exist", isRenamed)
    }

    @After
    fun tearDown() {
    }

}
