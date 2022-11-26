package com.example.oggettoonboarding.auth.fragments.edit_profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oggettoonboarding.fragments.models.Hobby
import com.example.oggettoonboarding.fragments.models.Project
import com.example.oggettoonboarding.fragments.models.TechStack
import com.example.oggettoonboarding.repositories.UserRepository
import java.util.*

class EditProfileViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _hobbyList = MutableLiveData<List<Hobby>>()
    var hobbyList: LiveData<List<Hobby>> = _hobbyList

    private val _imageUri = MutableLiveData<Uri>()
    var imageUri = _imageUri

    private val _projectList = MutableLiveData<List<Project>>()
    var projectList: LiveData<List<Project>> = _projectList

    private val _techStack = MutableLiveData<List<TechStack>>()
    var techStack: LiveData<List<TechStack>> = _techStack

    fun updateHobbyList(hobbyList: List<Hobby>) {
        _hobbyList.postValue(hobbyList)
    }

    fun updateTechStack(techStackList: List<TechStack>) {
        _techStack.postValue(techStackList)
    }

    fun updateProjectList(projects: List<Project>){
        _projectList.postValue(projects)
    }

    fun uploadImage(fileUri: Uri) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        userRepository.getInstance().child(fileName).putFile(fileUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    Log.d("TAG", "uri $it")
                    _imageUri.value = it
                }
            }.addOnFailureListener { e ->
                print(e.message)
            }
    }
}