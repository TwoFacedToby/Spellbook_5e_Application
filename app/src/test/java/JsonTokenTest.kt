import android.content.Context
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Utility.JsonTokenManager
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellViewModel
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before

@RunWith(RobolectricTestRunner::class)
//@Config(sdk=[Config.OLDEST_SDK],manifest=Config.NONE)//AdjusttheSDKversionasneeded
class JsonTokenTest{

//Borrowed
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val createSpellViewModel = CreateSpellViewModel()

    private val vanillaHomebrew =(Spell.SpellInfo(
        json="",
        index="void",
        name="Void",
        desc=listOf("Makeyourenemyceasetoexist,leavingnotraceofbodyorsould.\nAnyattemptatbringingtheenemybackwillfailunexceptional"),
        atHigherLevel=listOf(""),
        range="Unlimited",
        components=listOf("V","S"),
        materials="",
        ritual=false,
        duration="Eternal",
        concentration=false,
        casting_time="Instant",
        level=9,
        school=Spell.SpellSchool(index="conjuration",name="Conjuration",url="Homebrew"),
        classes=listOf(Spell.SpellClass(index="wizard",name="Wizard",url="Homebrew"),
            Spell.SpellClass(index="sorcerer",name="Sorcerer",url="Homebrew")),
        url="Homebrew",
        attackType="Voidbound",
        damage=Spell.SpellDamage(Spell.SpellDamageType(name="Void",index="void")),
        dc="1d20",
        homebrew=true
    ))

    private var homebrew: Spell.SpellInfo? = null

    @Before
    fun setUp(){
        homebrew = vanillaHomebrew

        createSpellViewModel.updateEntireSpell(homebrew!!)

        createSpellViewModel.saveSpell()

    }

    /**
     *Teststhatdifferentpartsofahomebrewcanbeedited
     *
     *@authorNicklasChristensen
     */
    @Test
    fun testToken(){

        val token = JsonTokenManager.tokenMyHomebrew("void")

        assertNotNull("The token is: $token", token)

        LocalDataLoader.deleteFile("void", LocalDataLoader.DataType.HOMEBREW)

        assertNull("Trying to remove before inserting", LocalDataLoader.getJson("void", LocalDataLoader.DataType.HOMEBREW))

         JsonTokenManager.saveTokenAsHomebrew(token) // an issue occurs here

        val json = LocalDataLoader.getJson("void", LocalDataLoader.DataType.HOMEBREW)

        println(json)

        assertNotNull("json is: $json", json)

    }


}
