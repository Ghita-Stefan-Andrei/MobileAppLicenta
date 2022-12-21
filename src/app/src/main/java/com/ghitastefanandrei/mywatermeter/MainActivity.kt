package com.ghitastefanandrei.mywatermeter

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ghitastefanandrei.mywatermeter.databinding.ActivityMainBinding
import com.sun.mail.imap.IMAPMessage
import java.util.*
import javax.mail.*

class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val esp32camSubject:String = "ESP32-CAM Photo Captured"
        //lateinit var messages:Array<Message>

        val email:String = "esp32camlicenta@gmail.com"
        val password:String = "mcgd paai veol lssr"

        val properties = Properties().apply {
            put("mail.store.protocol", "imap")
            put("mail.imap.host", "imap.gmail.com")
            put("mail.imap.port", "993")
            put("mail.imap.ssl.enable", "true")
            put("mail.imap.auth", "true")
        }

        val session = Session.getInstance(properties)
        val store = session.getStore("imap")

        val thread = Thread {
            try {
                store.connect(email, password)
            } catch (e: Exception) {
                System.err.println(e)
            }

            val inbox = store.getFolder("INBOX")

            inbox.open(Folder.READ_ONLY)
            val messages = inbox.messages
            lateinit var lastEspMessage:Message
            for (message in messages)
            {
                if (message.subject == esp32camSubject)
                {
                    lastEspMessage = message
                }
            }
            binding.mSubject.text = lastEspMessage.subject
            displayImage(getImageAttachment(lastEspMessage), binding.WaterMeterIMG)
            inbox.close(false)
            store.close()
        }

        thread.start()
        thread.join()
    }
    fun getImageAttachment(message: Message): ByteArray? {
        // Check if the message has a Multipart content
        if (message.contentType.startsWith("multipart/")) {
            val iparts = message.content as Multipart
            // Iterate through the parts
            for (i in 0 until iparts.count) {
                val part = iparts.getBodyPart(i)
                // Check if the part is an attachment
                if (Part.ATTACHMENT.equals(part.disposition, ignoreCase = true)) {
                    // Check if the attachment is an image
                    if (part.contentType.startsWith("image/")) {
                        // Retrieve the image data
                        val imageData = part.inputStream.readBytes()
                        return imageData
                    }
                }
            }
        }
        return null
    }

    fun displayImage(imageData: ByteArray?, imageView: ImageView) {
        // Check if the image data is null
        if (imageData == null) {
            return
        }

        // Decode the image data into a Bitmap
        val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        // Set the Bitmap as the ImageView's source
        imageView.setImageBitmap(bitmap)
    }
}