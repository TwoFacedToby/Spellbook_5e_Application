import android.content.Context
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellViewModel
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before

@RunWith(RobolectricTestRunner::class)
//@Config(sdk=[Config.OLDEST_SDK],manifest=Config.NONE)//AdjusttheSDKversionasneeded
class EditHomebrewTest{

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
    fun testNameChangeAndFileStorage(){

        assertNotNull(LocalDataLoader.getJson("void",LocalDataLoader.DataType.HOMEBREW))

        createSpellViewModel.updateName("Abyss")

        createSpellViewModel.replaceSpell("void")

        assertNotNull(LocalDataLoader.getJson("abyss",LocalDataLoader.DataType.HOMEBREW))

    }

    @Test
    fun testLevelChangeAndFileNameIsSame(){

        assertEquals(9,createSpellViewModel.spell.level)

        createSpellViewModel.updateLevel(3)

        createSpellViewModel.replaceSpell("void")

        assertEquals(3,createSpellViewModel.spell.level)

        assertEquals("void",createSpellViewModel.spell.index)

    }

    @Test
    fun testDescriptionChange(){

        assertEquals(listOf("Makeyourenemyceasetoexist,leavingnotraceofbodyorsould.\nAnyattemptatbringingtheenemybackwillfailunexceptional"),
            createSpellViewModel.spell.desc)

        createSpellViewModel.updateDescription(listOf("Banishtheenemytoaplainoutsideofthephysicaluniverse"))

        createSpellViewModel.replaceSpell("void")

        assertEquals(listOf("Banishtheenemytoaplainoutsideofthephysicaluniverse"),
            createSpellViewModel.spell.desc)

    }

    @Test
    fun testHigherLevelChange(){

        assertEquals(listOf(""),
            createSpellViewModel.spell.atHigherLevel)

        createSpellViewModel.updateHigherLevel(listOf("Atlevel7thespellinsteadmakestheenemyceasetoexist"))

        createSpellViewModel.replaceSpell("void")

        assertEquals(listOf("Atlevel7thespellinsteadmakestheenemyceasetoexist"),
            createSpellViewModel.spell.atHigherLevel)

    }

    @Test
    fun testComponentsChange(){

        assertEquals(listOf("V","S"),
            createSpellViewModel.spell.components)

        createSpellViewModel.updateComponents(listOf("M"))

        createSpellViewModel.replaceSpell("void")

        assertEquals(listOf("M"),
            createSpellViewModel.spell.components)

    }

    @Test
    fun testRangeChange(){

        assertEquals("Unlimited",
            createSpellViewModel.spell.range)

        createSpellViewModel.updateRange("Sight")

        createSpellViewModel.replaceSpell("void")

        assertEquals("Sight",
            createSpellViewModel.spell.range)

    }

    @Test
    fun testMaterialChange(){

        assertEquals("",
            createSpellViewModel.spell.materials)

        createSpellViewModel.updateMaterial("Voidshard")

        createSpellViewModel.replaceSpell("void")

        assertEquals("Voidshard",
            createSpellViewModel.spell.materials)

    }

    @Test
    fun testRitualChange(){

        assertEquals(false,
            createSpellViewModel.spell.ritual)

        createSpellViewModel.updateRitual(true)

        createSpellViewModel.replaceSpell("void")

        assertTrue(createSpellViewModel.spell.ritual!!)

    }

    @Test
    fun testConcentrationChange(){

        assertEquals(false,
            createSpellViewModel.spell.concentration)

        createSpellViewModel.updateConcentration(true)

        createSpellViewModel.replaceSpell("void")

        assertTrue(createSpellViewModel.spell.concentration!!)

    }

    @Test
    fun testDurationChange(){

        assertEquals("Eternal",
            createSpellViewModel.spell.duration)

        createSpellViewModel.updateDuration("Everlasting")

        createSpellViewModel.replaceSpell("void")

        assertEquals("Everlasting",
            createSpellViewModel.spell.duration)

    }

    @Test
    fun testCastingTimeChange(){

        assertEquals("Instant",
            createSpellViewModel.spell.casting_time)

        createSpellViewModel.updateCastTime("0.1nanosecond")

        createSpellViewModel.replaceSpell("void")

        assertEquals("0.1nanosecond",
            createSpellViewModel.spell.casting_time)

    }

    @Test
    fun testSchoolChange(){

        assertEquals("conjuration",
            createSpellViewModel.spell.school!!.index)

        createSpellViewModel.updateSchool("Necromancy")

        createSpellViewModel.replaceSpell("void")

        assertEquals("necromancy",
            createSpellViewModel.spell.school!!.index)

    }

    @Test
    fun testClassesChange(){

        assertEquals(listOf(Spell.SpellClass(index="wizard",name="Wizard",url="Homebrew"),
            Spell.SpellClass(index="sorcerer",name="Sorcerer",url="Homebrew")),
            createSpellViewModel.spell.classes)

        createSpellViewModel.updateClass(listOf("Wizard","Warlock"))

        createSpellViewModel.replaceSpell("void")

        assertEquals("warlock",
            createSpellViewModel.spell.classes!!.get(1).index)

    }

    @Test
    fun testAttackTypeChange(){

        assertEquals("Voidbound",createSpellViewModel.spell.attackType)

        createSpellViewModel.updateAttackType("Voidlash")

        createSpellViewModel.replaceSpell("void")

        assertEquals("Voidlash",createSpellViewModel.spell.attackType)

    }

    @Test
    fun testDCChange(){

        assertEquals("1d20",createSpellViewModel.spell.dc)

        createSpellViewModel.updateDC("20d20")

        createSpellViewModel.replaceSpell("void")

        assertEquals("20d20",createSpellViewModel.spell.dc)

    }

    @Test
    fun testDamageChange(){

        assertEquals(Spell.SpellDamage(Spell.SpellDamageType(name="Void",index="void")),
            createSpellViewModel.spell.damage)

        createSpellViewModel.updateDamage("Abyssal")

        createSpellViewModel.replaceSpell("void")

        assertEquals("abyssal",createSpellViewModel.spell.damage!!.damageType!!.index)

    }

}
