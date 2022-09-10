package com.android.mismenu.features.domain.data.repositories

import com.android.mismenu.features.domain.entities.Category
import com.android.mismenu.features.domain.data.datasource.remoteDataSource.FirestoreManagement
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ManageCategoryImpl @Inject constructor(private val fireStore: FirebaseFirestore) :
    FirestoreManagement {

//    override suspend fun fetchCategories(): List<Category> {
//        var list = listOf<Category>()
//        withContext(Dispatchers.IO){
//            val childList = mutableListOf<Category>()
//            fireStore.collection("category").get().addOnSuccessListener {collectionsSnapshot  ->
//                for (item in collectionsSnapshot){
//                    childList.add(item.toObject<Category>())
//                }
//            }
//            Log.d("LIST", "$list")
//        }
//        return list
//    }


    override suspend fun addData(name: String, id: String, image: String) {
        return withContext(Dispatchers.IO){
            val category = Category(id, name, image)
            fireStore.collection("category").add(category)
        }
    }


    override suspend fun updateData(id: String, name: String, image: String) {
        return withContext(Dispatchers.IO){
            fireStore.collection("category").document(id).update(
                mapOf(
                    "name" to name,
                    "image" to image
                )
            )
        }
    }

    override suspend fun removeData(id: String) {
        return withContext(Dispatchers.IO){
            fireStore.collection("category").document(id).delete()
        }
    }
}