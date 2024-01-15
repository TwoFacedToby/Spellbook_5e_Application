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
//@Config(sdk = [Config.OLDEST_SDK], manifest = Config.NONE) // Adjust the SDK version as needed
class TestHomebrewEditing {

    //Borrowed
    private val context: Context = ApplicationProvider.getApplicationContext()

    private val createSpellViewModel = CreateSpellViewModel()

    private val vanillaHomebrew = (Spell.SpellInfo(
        json = "",
        index = "void",
        name = "Void",
        desc = listOf("Make your enemy cease to exist, leaving no trace of body or sould.\nAny attempt at bringing the enemy back will fail unexceptional"),
        atHigherLevel = listOf(""),
        range = "Unlimited",
        components = listOf("V", "S"),
        materials = "",
        ritual = false,
        duration = "Eternal",
        concentration = false,
        casting_time = "Instant",
        level = 9,
        school = Spell.SpellSchool(index = "conjuration", name = "Conjuration", url = "Homebrew"),
        classes = listOf(Spell.SpellClass(index = "wizard", name = "Wizard", url = "Homebrew"),
            Spell.SpellClass(index = "sorcerer", name = "Sorcerer", url = "Homebrew")),
        url = "Homebrew",
        attackType = "Void bound",
        damage = Spell.SpellDamage(Spell.SpellDamageType(name = "Void", index = "void")),
        dc = "1d20",
        homebrew = true
    ))

    private var homebrew: Spell.SpellInfo? = null

    @Before
    fun setUp(){
        homebrew = vanillaHomebrew

        createSpellViewModel.updateEntireSpell(homebrew!!)

        createSpellViewModel.saveSpell()

    }

    /**
     * Tests that different parts of a homebrew can be edited
     *
     * @author Nicklas Christensen
     */
    @Test
    fun testNameChangeAndFileStorage() {

        assertNotNull(LocalDataLoader.getJson("void", LocalDataLoader.DataType.HOMEBREW))

        createSpellViewModel.updateName("Abyss")

        createSpellViewModel.replaceSpell("void")

        assertNotNull(LocalDataLoader.getJson("abyss", LocalDataLoader.DataType.HOMEBREW))

    }

    @Test
    fun testLevelChangeAndFileNameIsSame() {

        assertEquals(9, createSpellViewModel.spell.level)

        createSpellViewModel.updateLevel(3)

        createSpellViewModel.replaceSpell("void")

        assertEquals(3, createSpellViewModel.spell.level)

        assertEquals("void", createSpellViewModel.spell.index)

    }

    @Test
    fun testDescriptionChange() {

        assertEquals(listOf("Make your enemy cease to exist, leaving no trace of body or sould.\nAny attempt at bringing the enemy back will fail unexceptional"),
            createSpellViewModel.spell.desc)

        createSpellViewModel.updateDescription(listOf("Banish the enemy to a plain outside of the physical universe"))

        createSpellViewModel.replaceSpell("void")

        assertEquals(listOf("Banish the enemy to a plain outside of the physical universe"),
            createSpellViewModel.spell.desc)

    }

    @Test
    fun testHigherLevelChange() {

        assertEquals(listOf(""),
            createSpellViewModel.spell.atHigherLevel)

        createSpellViewModel.updateHigherLevel(listOf("At level 7 the spell instead makes the enemy cease to exist"))

        createSpellViewModel.replaceSpell("void")

        assertEquals(listOf("At level 7 the spell instead makes the enemy cease to exist"),
            createSpellViewModel.spell.atHigherLevel)

    }

    @Test
    fun testComponentsChange() {

        assertEquals(listOf("V", "S"),
            createSpellViewModel.spell.components)

        createSpellViewModel.updateComponents(listOf("M"))

        createSpellViewModel.replaceSpell("void")

        assertEquals(listOf("M"),
            createSpellViewModel.spell.components)

    }

    @Test
    fun testRangeChange() {

        assertEquals("Unlimited",
            createSpellViewModel.spell.range)

        createSpellViewModel.updateRange("Sight")

        createSpellViewModel.replaceSpell("void")

        assertEquals("Sight",
            createSpellViewModel.spell.range)

    }

    @Test
    fun testMaterialChange() {

        assertEquals("",
            createSpellViewModel.spell.materials)

        createSpellViewModel.updateMaterial("Void shard")

        createSpellViewModel.replaceSpell("void")

        assertEquals("Void shard",
            createSpellViewModel.spell.materials)

    }

    @Test
    fun testRitualChange() {

        assertEquals(false,
            createSpellViewModel.spell.ritual)

        createSpellViewModel.updateRitual(true)

        createSpellViewModel.replaceSpell("void")

        assertTrue(createSpellViewModel.spell.ritual!!)

    }

    @Test
    fun testConcentrationChange() {

        assertEquals(false,
            createSpellViewModel.spell.concentration)

        createSpellViewModel.updateConcentration(true)

        createSpellViewModel.replaceSpell("void")

        assertTrue(createSpellViewModel.spell.concentration!!)

    }

    @Test
    fun testDurationChange() {

        assertEquals("Eternal",
            createSpellViewModel.spell.duration)

        createSpellViewModel.updateDuration("Everlasting")

        createSpellViewModel.replaceSpell("void")

        assertEquals("Everlasting",
            createSpellViewModel.spell.duration)

    }

    @Test
    fun testCastingTimeChange() {

        assertEquals("Instant",
            createSpellViewModel.spell.casting_time)

        createSpellViewModel.updateCastTime("0.1 nano second")

        createSpellViewModel.replaceSpell("void")

        assertEquals("0.1 nano second",
            createSpellViewModel.spell.casting_time)

    }

    @Test
    fun testSchoolChange() {

        assertEquals("conjuration",
            createSpellViewModel.spell.school!!.index)

        createSpellViewModel.updateSchool("Necromancy")

        createSpellViewModel.replaceSpell("void")

        assertEquals("necromancy",
            createSpellViewModel.spell.school!!.index)

    }

    @Test
    fun testClassesChange() {

        assertEquals(listOf(Spell.SpellClass(index = "wizard", name = "Wizard", url = "Homebrew"),
            Spell.SpellClass(index = "sorcerer", name = "Sorcerer", url = "Homebrew")),
            createSpellViewModel.spell.classes)

        createSpellViewModel.updateClass(listOf("Wizard", "Warlock"))

        createSpellViewModel.replaceSpell("void")

        assertEquals("warlock",
            createSpellViewModel.spell.classes!!.get(1).index)

    }

    @Test
    fun testAttackTypeChange() {

        assertEquals("Void bound", createSpellViewModel.spell.attackType)

        createSpellViewModel.updateAttackType("Void lash")

        createSpellViewModel.replaceSpell("void")

        assertEquals("Void lash", createSpellViewModel.spell.attackType)

    }

    @Test
    fun testDCChange() {

        assertEquals("1d20", createSpellViewModel.spell.dc)

        createSpellViewModel.updateDC("20d20")

        createSpellViewModel.replaceSpell("void")

        assertEquals("20d20", createSpellViewModel.spell.dc)

    }

    @Test
    fun testDamageChange() {

        assertEquals(Spell.SpellDamage(Spell.SpellDamageType(name = "Void", index = "void")),
            createSpellViewModel.spell.damage)

        createSpellViewModel.updateDamage("Abyssal")

        createSpellViewModel.replaceSpell("void")

        assertEquals("abyssal", createSpellViewModel.spell.damage!!.damageType!!.index)

    }

}