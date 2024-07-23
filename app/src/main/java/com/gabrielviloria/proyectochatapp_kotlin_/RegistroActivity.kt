package com.gabrielviloria.proyectochatapp_kotlin_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity() {

    private lateinit var registrarEditarTextoNombreUsuario : EditText
    private lateinit var registrarEditarTextoEmail : EditText
    private lateinit var registrarEditarTextoPassword : EditText
    private lateinit var registrarEditarTextoRepetirPassword : EditText
    private lateinit var btnRegistrar : Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registro)
        supportActionBar?.title = "registro"
        inicializarVariables()

        btnRegistrar.setOnClickListener{
            ValidarDatos()
        }
    }
    private fun inicializarVariables (){
        registrarEditarTextoNombreUsuario = findViewById(R.id.R_Et_nombre_usuario)
        registrarEditarTextoEmail = findViewById(R.id.R_Et_email)
        registrarEditarTextoPassword = findViewById(R.id.R_Et_password)
        registrarEditarTextoRepetirPassword = findViewById(R.id.R_Et_r_password)
        btnRegistrar = findViewById(R.id.Btn_registrar)
        firebaseAuth = FirebaseAuth.getInstance()

    }
    private fun ValidarDatos() {
        val nombreUsuario : String = registrarEditarTextoNombreUsuario.text.toString()
        val email : String = registrarEditarTextoEmail.text.toString()
        val password : String = registrarEditarTextoPassword.text.toString()
        val repetirpassword : String = registrarEditarTextoRepetirPassword.text.toString()

        if (nombreUsuario.isEmpty()){
            Toast.makeText(applicationContext,"Ingrese nombre de usuario",Toast.LENGTH_SHORT).show()
        }
        else if(email.isEmpty()){
            Toast.makeText(applicationContext,"Ingrese su correo",Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(applicationContext,"Ingrese su contraseña",Toast.LENGTH_SHORT).show()
        }
        else if(repetirpassword.isEmpty()){
            Toast.makeText(applicationContext,"Ingrese repetir contraseña",Toast.LENGTH_SHORT).show()
        }
        else if(!password.equals(repetirpassword)){
            Toast.makeText(applicationContext,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show()
        }else{
            RegistrarUsuario(email,password)
        }
    }

    private fun RegistrarUsuario(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    var uid : String = ""
                    uid = firebaseAuth.currentUser!!.uid
                    reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)

                    val hashMap = HashMap<String,Any>()
                    val hNombreUsuario : String = registrarEditarTextoNombreUsuario.text.toString()
                    val hEmail : String = registrarEditarTextoEmail.text.toString()

                    hashMap["uid"] = uid
                    hashMap["n_usuario"] = hNombreUsuario
                    hashMap["email"] = hEmail
                    hashMap["imagen"] = ""
                    hashMap["buscar"] = hNombreUsuario.lowercase()

                    reference.updateChildren(hashMap)
                        .addOnCompleteListener{
                        task2->
                        if (task2.isSuccessful){
                            val intent = Intent(this@RegistroActivity,MainActivity::class.java)
                            Toast.makeText(applicationContext,"Se ha registrado con exito",Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                    }
                        .addOnFailureListener{e->
                        Toast.makeText(applicationContext,"{${e.message}}",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(applicationContext,"Ha ocurrido un error",Toast.LENGTH_SHORT).show()
                }

        }.addOnFailureListener{e->
                Toast.makeText(applicationContext,"{${e.message}}",Toast.LENGTH_SHORT).show()
            }

    }
}