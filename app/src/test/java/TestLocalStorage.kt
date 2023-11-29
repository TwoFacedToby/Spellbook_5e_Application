import android.content.Context
import com.example.spellbook5eapplication.app.Utility.SpellController
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
//@Config(sdk = [Config.OLDEST_SDK], manifest = Config.NONE) // Adjust the SDK version as needed
class TestLocalStorage {

    private val context: Context = ApplicationProvider.getApplicationContext()


    /**
     * Tests the deletion of a file from the device's storage.
     *
     * This test function performs a series of operations to validate the `deleteFileFromDirectory` method in the `SpellController` class.
     * The process involves setting a context in the `SpellController`, saving a JSON file to a specified directory on the device,
     * and then attempting to delete this file. The test asserts whether the file deletion was successful.
     *
     * The function includes several println statements for debugging purposes,
     * which indicate the progress through the different stages of the test.
     *
     * @author Kenneth Kaiser
     */
    @Test
    fun testDeleteFileFromDirectory() {
        // Assuming you have a setup in SpellController to use a passed context
        println("1")
        //Sets the context in spellcontroller
        SpellController.setContext(context)
        println("2")
        //Saves the json file to the device
        SpellController.saveJsonToFile("{}", "testDir", "testFile.json")
        println("3")
        //deleteFileFromDirectory returns true if the file was deleted and false if nothing was deleted
        val isDeleted = SpellController.deleteFileFromDirectory("testDir", "testFile.json")

        //Some prints for debugging
        println("4")
        println(isDeleted)

        //Tests if the file was deleted
        assertTrue(isDeleted)
    }

    /**
     * Tests the behavior of the deleteFileFromDirectory method when attempting to delete a non-existent file.
     *
     * The method attempts to delete a file that does not exist in the directory and checks that the method returns false, indicating
     * no file was deleted. It is expected that the deleteFileFromDirectory method should handle this by not
     * throwing an exception and instead return a false value.
     *
     * Prints to the console are included for debugging purposes to track the flow of the test execution and its outcome.
     *
     * @author Kenneth Kaiser
     */
    @Test
    fun deleteFileFromDirectory_FileDoesNotExist_ReturnsFalse() {
        // Assuming the context has already been set in the setUp method
        // Attempt to delete a file that does not exist
        val doesNotExistFileName = "nonExistentFile.json"
        val isDeleted = SpellController.deleteFileFromDirectory("testDir", doesNotExistFileName)

        // Some prints for debugging
        println("Attempting to delete a non-existent file.")
        println(isDeleted)

        // Test should pass if isDeleted is false, indicating no file was deleted because it didn't exist
        assertFalse(isDeleted)
    }

}