package io.github.t45k.coroutine_file_io

import kotlin.io.path.Path
import kotlin.io.path.createTempFile
import kotlin.io.path.deleteIfExists
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.file.NoSuchFileException

class PathKtTest {

    @Test
    fun `readTextAsync reads text file when correct file is given`() = runTest {
        // given
        val path = Path("src/test/resources/sample.txt")

        // when
        val text = path.readTextAsync()

        // then
        assertEquals(
            """
                foo
                bar
                baz
                
            """.trimIndent(),
            text,
        )
    }

    @Test
    fun `readTextAsync throws NoSuchFileException when correct file is not found`() = runTest {
        // given
        val path = Path("src/test/resources/not_found.txt")

        // when and then
        assertThrows<NoSuchFileException> {
            path.readTextAsync()
        }
    }

    @Test
    fun `writeTextAsync writes text to given file when it is correct`() = runTest {
        // given
        val path = createTempFile()

        try {
            // when
            path.writeTextAsync(
                """
                    hoge
                    fuga
                    piyo
                    
                """.trimIndent()
            )

            // then
            assertEquals(
                """
                    hoge
                    fuga
                    piyo
                    
                """.trimIndent(),
                path.readTextAsync(),
            )
        } finally {
            path.deleteIfExists()
        }
    }
}