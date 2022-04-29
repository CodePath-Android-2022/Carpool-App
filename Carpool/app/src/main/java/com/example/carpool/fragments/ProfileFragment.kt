package com.example.carpool.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.carpool.R
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class ProfileFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 71
    var photoFile: ParseFile? = null
    private var filePath: Uri? = null
    lateinit var ivUserImgPreview:ImageView
    lateinit var saveBtn: Button
    lateinit var selectImg: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivUserImgPreview = view.findViewById<ImageView>(R.id.ivUserImgPreview)
        populateProfile(view,ivUserImgPreview)
        saveBtn = view.findViewById<Button>(R.id.btnProfileSave)
        selectImg = view.findViewById<ImageButton>(R.id.ib_selectImg)

        saveBtn.setOnClickListener{
            val firstName = view.findViewById<EditText>(R.id.et_profile_firstN_content).text.toString()
            val lastName = view.findViewById<EditText>(R.id.et_profile_lastN_content).text.toString()
            val email = view.findViewById<EditText>(R.id.et_profileEmail).text.toString()



            //if filePath is null then no photoFile
            if (filePath != null) {

//                photoFile = File (filePath!!.getPath())
            }

            val user = ParseUser.getCurrentUser()
            //val userParseImage = user?.getParseFile("profileImg")?.url

            submitPost(user, firstName, lastName, email, photoFile)
        }

        selectImg.setOnClickListener {
            launchGallery()
        }


    }

    private fun submitPost(user: ParseUser?, firstName: String, lastName: String, email: String, photoFile: ParseFile?) {
        Log.i(TAG,"Clicked Save")
        // Set fields for the user to be created
        if (user != null) {
            user.setEmail(email)
            user.put("firstName", firstName)
            user.put("lastName", lastName)
            //if photo wasnt updated then no need to save it.
            if (photoFile != null) {
                user.put("profileImg", photoFile)
            }

            user.saveInBackground{ e ->
                if (e == null) {
                    Log.i(TAG,"Profile updated")
                    Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()
                } else {
                    e.printStackTrace()
                    Log.i(TAG,"Profile Failed to update")
                    Toast.makeText(requireContext(), "Failed to Update", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if(data == null || data.data == null){

                return
            }

            filePath = data.data
            //val imageFilePath = Uri.parse()
            try {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, filePath!!)
                Log.i(TAG, source.toString())
                val takenImage = ImageDecoder.decodeBitmap(source)
                //val takenImage = MediaStore.Images.Media.getBitmap(getActivity()?.getContentResolver(), filePath)
                val bos = ByteArrayOutputStream()
                takenImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                val scaledData = bos.toByteArray()
                photoFile = ParseFile(scaledData)
                ivUserImgPreview.setImageBitmap(takenImage)

            }

            catch (e: IOException) {

                e.printStackTrace()
            }
        }
    }

    //IMPROVEMENT: NEED TO TRY AND RETRIEVE THE DATA FROM LOCAL STORAGE,
    fun populateProfile(view: View, ivUserImgPreview:ImageView) {
        val user = ParseUser.getCurrentUser()
        val firstName = view.findViewById<EditText>(R.id.et_profile_firstN_content)
        val lastName = view.findViewById<EditText>(R.id.et_profile_lastN_content)
        val email = view.findViewById<EditText>(R.id.et_profileEmail)
        val fullname = view.findViewById<TextView>(R.id.tvProfileFullName)

        val userParseImage = user.getParseFile("profileImg")

        val userFirstname = user.get("firstName").toString()
        val userLastname = user.get("lastName").toString()
        firstName.setText(userFirstname)
        lastName.setText(userLastname)
        val fullTEXT = userFirstname + " " + userLastname
        fullname.text = fullTEXT
        email.setText(user.get("email").toString())
        Glide.with(this).load(userParseImage?.url).into(ivUserImgPreview);
    }

//    // Returns the File for a photo stored on disk given the fileName
//    fun getPhotoFileUri(fileName: String): File {
//        // Get safe storage directory for photos
//        // Use `getExternalFilesDir` on Context to access package-specific directories.
//        // This way, we don't need to request external read/write runtime permissions.
//        val mediaStorageDir =
//            File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
//            Log.d(TAG, "failed to create directory")
//        }
//
//        // Return the file target for the photo based on filename
//        return File(mediaStorageDir.path + File.separator + fileName)
//    }



    companion object{
        val TAG = "ProfileFragment"
    }
}