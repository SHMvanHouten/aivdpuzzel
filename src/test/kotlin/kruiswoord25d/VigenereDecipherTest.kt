package kruiswoord25d

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Before
import org.junit.Test

class VigenereDecipherTest {

    private lateinit var decipher: VigenereDecipher

    @Before
    fun setUp() {
        decipher = VigenereDecipher()
    }

    @Test
    fun `'LXFOPVEFRNHR' should decode to 'ATTACKATDAWN' with key 'LEMON'`() {
        val encryptedMessage = "LXFOPVEFRNHR".toLowerCase()
        val key = "LEMON".toLowerCase()
        assertThat(decipher.decipher(encryptedMessage, key), equalTo("ATTACKATDAWN".toLowerCase()))
    }

    @Test
    fun `'CSASTPKVSIQUTGQUCSASTPIUAQJB' should decode to 'CRYPTOISSHORTFORCRYPTOGRAPHY' with key 'ABCD'`() {
        val encryptedMessage = "CSASTPKVSIQUTGQUCSASTPIUAQJB".toLowerCase()
        val key = "ABCD".toLowerCase()
        assertThat(decipher.decipher(encryptedMessage, key), equalTo("CRYPTOISSHORTFORCRYPTOGRAPHY".toLowerCase()))
    }


}