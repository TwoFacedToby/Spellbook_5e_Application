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

@RunWith(RobolectricTestRunner::class)
class TestLocalStorage {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun testDeleteFileFromDirectory() {
        // Assuming you have a setup in SpellController to use a passed context
        println("1")
        SpellController.setContext(context)
        println("2")
        SpellController.saveJsonToFile("{}", "testDir", "testFile.json")
        println("3")
        bool deletedOrNot = SpellController.deleteFileFromDirectory("testDir", "testFile.json")
        println("4")

        assertTrue()
    }
}