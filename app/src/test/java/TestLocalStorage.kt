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
}