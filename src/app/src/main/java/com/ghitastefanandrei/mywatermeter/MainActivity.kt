package com.ghitastefanandrei.mywatermeter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ghitastefanandrei.mywatermeter.databinding.ActivityMainBinding
import java.util.*
import javax.mail.*
//import java.net.Authenticator

class MainActivity : AppCompatActivity()
{

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email:String = "esp32camLicenta@gmail.com"
        val password:String = "mcgd paai veol lssr"

        val properties = Properties().apply {
            put("mail.store.protocol", "imap")
            put("mail.imap.host", "imap.gmail.com")
            put("mail.imap.port", "993")
            put("mail.imap.ssl.enable", "true")
        }

        val session = Session.getInstance(properties)
        val store = session.getStore("imap")

        store.connect(email, password)
/*
        val inbox = store.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)

        val messages = inbox.messages

        for (message in messages) {
            binding.mSubject.text = message.subject
            Thread.sleep(2000)
        }

        inbox.close(false)
        store.close()*/
    }


}