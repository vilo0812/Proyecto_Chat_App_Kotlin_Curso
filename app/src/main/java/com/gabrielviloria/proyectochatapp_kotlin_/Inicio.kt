package com.gabrielviloria.proyectochatapp_kotlin_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Inicio : AppCompatActivity() {
    private lateinit var Btn_ir_registros : Button
    private lateinit var Btn_ir_logeo : Button

    var firebaseUser : FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        Btn_ir_registros = findViewById(R.id.Btn_ir_registro)
        Btn_ir_logeo = findViewById(R.id.Btn_ir_logeo)

        Btn_ir_registros.setOnClickListener{
            val intent = Intent(this@Inicio, RegistroActivity::class.java)
            Toast.makeText(applicationContext, "Registros",Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        Btn_ir_logeo.setOnClickListener{
            val intent = Intent(this@Inicio, RegistroActivity::class.java)
            Toast.makeText(applicationContext,"Login",Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
    private fun ComprobarSesion(){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if(firebaseUser!=null){
            val intent = Intent(this@Inicio,MainActivity::class.java)
            Toast.makeText(applicationContext,"La sesion esta activa",Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        ComprobarSesion()
        super.onStart()
    }
}