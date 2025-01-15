package io.github.t45k.coroutine_file_io

import kotlin.io.path.fileSize
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.bytestring.getByteString
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.StandardOpenOption

suspend fun Path.readTextAsync(charset: Charset = Charsets.UTF_8): String {
    val byteBuffer = ByteBuffer.allocate(fileSize().toInt())
    AsynchronousFileChannel.open(this, StandardOpenOption.READ).use { it.aRead(byteBuffer, 0) }
    return byteBuffer.flip().getByteString().decodeToString(charset)
}

suspend fun Path.writeTextAsync(text: String, charset: Charset = Charsets.UTF_8) {
    val byteBuffer = ByteBuffer.wrap(text.toByteArray(charset))
    AsynchronousFileChannel.open(this, StandardOpenOption.WRITE).use { it.aWrite(byteBuffer, 0) }
}
