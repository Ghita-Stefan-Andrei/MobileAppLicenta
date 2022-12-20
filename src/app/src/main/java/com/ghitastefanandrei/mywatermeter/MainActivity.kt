package com.ghitastefanandrei.mywatermeter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ghitastefanandrei.mywatermeter.databinding.ActivityMainBinding
import com.sun.mail.imap.IMAPMessage
import java.util.*
import javax.mail.Folder
import javax.mail.Session

class MainActivity : AppCompatActivity()
{

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val esp32camSubject:String = "ESP32-CAM Photo Captured"

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
        //lateinit var messages?//:Array<IMAPMessage>

        val thread = Thread {
            try {
                store.connect(email, password)
            } catch (e: Exception) {
                System.err.println(e)
            }
            val inbox = store.getFolder("INBOX")
            inbox.open(Folder.READ_ONLY)

            val messages = inbox.messages
            binding.mSubject.text = messages[3].subject
            /*for (message in messages) {
                binding.mSubject.text = message.subject
                //Thread.sleep(2000)
            }*/

            inbox.close(false)
            store.close()
        }

        thread.start()
        thread.join()

        /*inbox.open(Folder.READ_ONLY)

        val messages = inbox.messages

        for (message in messages) {
            binding.mSubject.text = message.subject
            Thread.sleep(2000)
        }
*/
    }
}