package com.example.carpool

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException


class EditProfileActivity : AppCompatActivity(), View.OnClickListener {
    var btnsave: Button? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var textViewemailname: TextView? = null
    private var databaseReference: DatabaseReference? = null
    private var editTextName: EditText? = null
    private var editTextSurname: EditText? = null
    private var editTextPhoneNo: EditText? = null
    private var profileImageView: ImageView? = null
    private var firebaseStorage: FirebaseStorage? = null
    var imagePath: Uri? = null
    private var storageReference: StorageReference? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data!!.data != null) {
            imagePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imagePath)
                profileImageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth!!.currentUser == null) {
            finish()
            startActivity(Intent(applicationContext, SignInActivity::class.java))
        }
        databaseReference = FirebaseDatabase.getInstance().reference
        editTextName = findViewById<View>(R.id.EditTextName) as EditText
        editTextSurname = findViewById<View>(R.id.EditTextSurname) as EditText
        editTextPhoneNo = findViewById<View>(R.id.EditTextPhoneNo) as EditText
        btnsave = findViewById<View>(R.id.btnSaveButton) as Button
        val user = firebaseAuth!!.currentUser
        btnsave!!.setOnClickListener(this)
        textViewemailname = findViewById<View>(R.id.textViewEmailAdress) as TextView
        textViewemailname!!.text = user!!.email
        profileImageView = findViewById(R.id.update_imageView)
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage!!.reference
        profileImageView!!.setOnClickListener(View.OnClickListener {
            val profileIntent = Intent()
            profileIntent.type = "image/*"
            profileIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE)
        })
    }

    private fun userInformation() {
        val name = editTextName?.text.toString().trim { it <= ' ' }
        val surname = editTextSurname?.text.toString().trim { it <= ' ' }
        val phoneno = editTextPhoneNo?.text.toString().trim { it <= ' ' }
        val userinformation = Userinformation(name, surname, phoneno)
        val user = firebaseAuth?.currentUser
        databaseReference?.child(user!!.uid)?.setValue(userinformation)
        Toast.makeText(applicationContext, "User information updated", Toast.LENGTH_LONG).show()
    }

    override fun onClick(view: View) {
        if (view === btnsave) {
            if (imagePath == null) {
                val drawable = this.resources.getDrawable(R.drawable.icon)
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon)
                // openSelectProfilePictureDialog();
                userInformation()
                // sendUserData();
                finish()
                startActivity(Intent(this@EditProfileActivity, HomeActivity::class.java))
            } else {
                userInformation()
                sendUserData()
                finish()
                startActivity(Intent(this@EditProfileActivity, HomeActivity::class.java))
            }
        }
    }

    private fun sendUserData() {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        // Get "User UID" from Firebase > Authentification > Users.
        val databaseReference = firebaseDatabase.getReference(firebaseAuth!!.uid!!)
        val imageReference = storageReference!!.child(firebaseAuth!!.uid!!).child("Images")
            .child("Profile Pic") //User id/Images/Profile Pic.jpg
        val uploadTask = imageReference.putFile(imagePath!!)
        uploadTask.addOnFailureListener {
            Toast.makeText(this@EditProfileActivity,
                "Error: Uploading profile picture",
                Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Toast.makeText(this@EditProfileActivity,
                "Profile picture uploaded",
                Toast.LENGTH_SHORT).show()
        }
    }

    fun openSelectProfilePictureDialog() {
        val alertDialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(this).create()
        val title = TextView(this)
        title.text = "Profile Picture"
        title.setPadding(10, 10, 10, 10) // Set Position
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.BLACK)
        title.textSize = 20f
        alertDialog?.setCustomTitle(title)
        val msg = TextView(this)
        msg.text = "Please select a profile picture \n Tap the sample avatar logo"
        msg.gravity = Gravity.CENTER_HORIZONTAL
        msg.setTextColor(Color.BLACK)
        alertDialog?.setView(msg)
        alertDialog?.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            DialogInterface.OnClickListener { dialog, which ->
                // Perform Action on Button
            })
        Dialog(applicationContext)
        alertDialog?.show()
        val okBT: Button = alertDialog!!.getButton(AlertDialog.BUTTON_NEUTRAL)
        val neutralBtnLP = okBT.layoutParams as LinearLayout.LayoutParams
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL
        okBT.setPadding(50, 10, 10, 10) // Set Position
        okBT.setTextColor(Color.BLUE)
        okBT.layoutParams = neutralBtnLP
    }

    companion object {
        private val TAG = EditProfileActivity::class.java.simpleName
        private const val PICK_IMAGE = 123
    }
}
