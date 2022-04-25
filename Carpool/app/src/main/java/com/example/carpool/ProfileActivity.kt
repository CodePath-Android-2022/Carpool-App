package com.example.carpool

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.IOException
import java.util.*


class ProfileActivity : AppCompatActivity() {
    private var databaseReference: DatabaseReference? = null
    private var profileNameTextView: TextView? = null
    private var profileEmailTextView: TextView? = null
    private var profilePhonenoTextView: TextView? = null
    private var profileAddressTextView: TextView? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var profilePicImageView: ImageView? = null
    private var firebaseStorage: FirebaseStorage? = null
    private var textViewemailname: TextView? = null
    private var editTextName: EditText? = null
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    private var btn_choose_image: ImageButton? = null
    private var btn_upload_image: ImageButton? = null
    @SuppressLint("CutPasteId")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        databaseReference = FirebaseDatabase.getInstance().reference
        editTextName = findViewById(R.id.et_username)
        profilePicImageView = findViewById(R.id.profile_pic_imageView)
        profileNameTextView = findViewById(R.id.profile_name_textView)
        profileEmailTextView = findViewById(R.id.profile_email_textView)
        profilePhonenoTextView = findViewById(R.id.profile_phoneno_textView)
        profileAddressTextView = findViewById(R.id.profile_address_textView)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        btn_choose_image = findViewById<View>(R.id.btn_choose_image) as? ImageButton
        imagePreview = findViewById(R.id.profile_pic_imageView)
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        btn_choose_image?.setOnClickListener { launchGallery() }
        btn_upload_image?.setOnClickListener { uploadImage() }
        val databaseReference: DatabaseReference =
            firebaseDatabase!!.getReference(firebaseAuth!!.uid.toString())
        val storageReference: StorageReference = firebaseStorage!!.reference
        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".
        firebaseAuth!!.uid?.let {

            storageReference.child(it).child("Images").child("Profile Pic")
                .downloadUrl.addOnSuccessListener { uri -> // Using "Picasso" (http://square.github.io/picasso/) after adding the dependency in the Gradle.
                    // ".fit().centerInside()" fits the entire image into the specified area.
                    // Finally, add "READ" and "WRITE" external storage permissions in the Manifest.
                    Picasso.get().load(uri).fit().centerInside().into(profilePicImageView)
                }
        }

        if (firebaseAuth!!.currentUser == null) {

            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        val user: FirebaseUser? = firebaseAuth!!.currentUser
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val userProfile: Userinformation? = dataSnapshot.getValue(Userinformation::class.java)
                profileNameTextView?.text = userProfile?.userName
                profileEmailTextView?.text = userProfile?.userEmail
                profilePhonenoTextView?.text = userProfile?.userPhoneno
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Toast.makeText(this@ProfileActivity, databaseError.code, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun buttonClickedEditName(view: View?) {

        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.layout_custom_dialog_edit_name, null)
        val etUsername = alertLayout.findViewById<EditText>(R.id.et_username)
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle("Name Edit")
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout)
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false)
        alert.setNegativeButton("Cancel") { _, _ -> }
        alert.setPositiveButton("OK") { _, _ ->

            val name = etUsername.text.toString()
            val email = profileEmailTextView!!.text.toString()
            val phoneno = profilePhonenoTextView!!.text.toString()
            val address = profileAddressTextView!!.text.toString()
            val userinformation = Userinformation(name, email, phoneno, address)
            val user: FirebaseUser? = firebaseAuth?.currentUser
            databaseReference?.child(user!!.uid)?.setValue(userinformation)
            databaseReference?.child(user!!.uid)?.setValue(userinformation)
            etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }

        val dialog: android.app.AlertDialog? = alert.create()
        dialog?.show()
    }

    fun buttonClickedEditEmail(view: View?) {

        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.layout_custom_dialog_edit_email, null)
        val etUserEmail = alertLayout.findViewById<EditText>(R.id.et_userEmail)
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle("Email edit")
        alert.setView(alertLayout)
        alert.setCancelable(false)
        alert.setNegativeButton("Cancel") { _, _ -> }
        alert.setPositiveButton("OK") { _, _ ->

            val name = profileNameTextView!!.text.toString()
            val email = etUserEmail.text.toString()
            val phoneno = profilePhonenoTextView!!.text.toString()
            val address = profileAddressTextView!!.text.toString()
            val userinformation = Userinformation(name, email, phoneno, address)
            val user: FirebaseUser? = firebaseAuth?.currentUser
            databaseReference?.child(user!!.uid)?.setValue(userinformation)
            databaseReference?.child(user!!.uid)?.setValue(userinformation)
            etUserEmail.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }

        val dialog: android.app.AlertDialog? = alert.create()
        dialog?.show()
    }

    fun buttonClickedEditPhoneNo(view: View?) {

        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.layout_custom_dialog_edit_contactinfo, null)
        val etUserPhoneno = alertLayout.findViewById<EditText>(R.id.et_userContactno)
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle("Contact info Edit")
        alert.setView(alertLayout)
        alert.setCancelable(false)
        alert.setNegativeButton("Cancel") { _, _ -> }
        alert.setPositiveButton("OK") { _, _ ->

            val name = profileNameTextView!!.text.toString()
            val email = profileEmailTextView!!.text.toString()
            val phoneno = etUserPhoneno.text.toString()
            val address = profileAddressTextView!!.text.toString()
            val userinformation = Userinformation(name, email, phoneno, address)
            val user: FirebaseUser? = firebaseAuth?.currentUser
            databaseReference?.child(user!!.uid)?.setValue(userinformation)
            databaseReference?.child(user!!.uid)?.setValue(userinformation)
            etUserPhoneno.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }

        val dialog: android.app.AlertDialog? = alert.create()
        dialog?.show()
    }

    fun buttonClickedEditAddress(view: View?) {

        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.layout_custom_dialog_edit_address, null)
        val etUserEmail = alertLayout.findViewById<EditText>(R.id.et_userAddress)
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle("Address edit")
        alert.setView(alertLayout)
        alert.setCancelable(false)
        alert.setNegativeButton("Cancel") { _, _ -> }
        alert.setPositiveButton("OK") { _, _ ->

            val name = profileNameTextView!!.text.toString()
            val email = etUserEmail.text.toString()
            val phoneno = profilePhonenoTextView!!.text.toString()
            val address = profileAddressTextView!!.text.toString()
            val userinformation = Userinformation(name, email, phoneno, address)
            val user: FirebaseUser? = firebaseAuth?.currentUser
            databaseReference?.child(user!!.uid)?.setValue(userinformation)
            databaseReference?.child(user!!.uid)?.setValue(userinformation)
            etUserEmail.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }

        val dialog: android.app.AlertDialog? = alert.create()
        dialog?.show()
    }

    private fun launchGallery() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if(data == null || data.data == null){

                return
            }

            filePath = data.data
            try {

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imagePreview.setImageBitmap(bitmap)
            }

            catch (e: IOException) {

                e.printStackTrace()
            }
        }
    }

    private fun uploadImage(){

        if(filePath != null){

            val ref = storageReference?.child("myImages/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

        }

        else{

            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    fun navigateLogOut(v: View?) {

        FirebaseAuth.getInstance().signOut()
        val inent = Intent(this, MainActivity::class.java)
        startActivity(inent)
    }
}