package desla.aos.eating.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CameraUtil  {

    companion object {
        private lateinit var context: Context
        private var instance : CameraUtil? = null

        fun  getInstance(context: Context): CameraUtil {
            if (instance == null){
                // NOT thread safe!
                instance = CameraUtil()
                this.context = context
            }


            return instance!!
        }
    }

    lateinit var currentPhotoPath: String
    lateinit var currentFile: File

    //이미지 파일 생성
    private fun createImageFile(): File {
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HH_mm_ss").format(Date())
        val storageDir : File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_",".jpeg",storageDir).apply {
            currentPhotoPath = absolutePath
            currentFile = this
        }
    }

    // 실제 경로 찾기
    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.getActivity()!!.managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    fun makeBitmap(btnAddPhoto: ImageView): Uri {
        val bitmap : Bitmap
        val file = currentFile
        bitmap = if(Build.VERSION.SDK_INT < 28){//안드로이드 9.0 보다 버전이 낮을 경우
            MediaStore.Images.Media.getBitmap(context.contentResolver,Uri.fromFile(file))
            //  img_photo.setImageBitmap(bitmap)
        }else{//안드로이드 9.0 보다 버전이 높을 경우
            val decode = ImageDecoder.createSource(
                    context.contentResolver,
                    Uri.fromFile(file)
            )
            ImageDecoder.decodeBitmap(decode)
            //  img_photo.setImageBitmap(bitmap)
        }
        savePhoto(bitmap)

        return Uri.fromFile(file)
    }

    fun setImageView(btnAddPhoto: ImageView, uri: Uri){

        val getBitmap = decodeSampledBitmapFromResource(uri, 110, 110)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            btnAddPhoto.setImageBitmap(imgRotate(uri, getBitmap))
        }else{
            btnAddPhoto.setImageBitmap(getBitmap)
        }
    }


    //갤러리에 저장
    private fun savePhoto(bitmap: Bitmap) {

        //사진 폴더에 저장하기 위한 경로 선언
        val folderPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/Eating/"
        val timestamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if(!folder.isDirectory){//해당 경로에 폴더가 존재하지
            folder.mkdir() // make directory의 줄임말로 해당경로에 폴더 자동으로
        }
        //실제적인 저장 처리
        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        galleryAddPic(folderPath + fileName)

    }

    val REQUEST_TAKE_PHOTO = 10


    fun dispatchTakePictureIntent() {
        val activity = context.getActivity()!!

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                val photoFile : File? = try{
                    createImageFile()
                }catch (e:Exception){

                    null
                }
                photoFile?.also {
                    val photoURI : Uri = FileProvider.getUriForFile(
                            context,
                            "desla.aos.eating.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }

    }

    private fun galleryAddPic(s: String) {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(s)
            mediaScanIntent.data = Uri.fromFile(f)
            context.sendBroadcast(mediaScanIntent)
        }
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    fun decodeSampledBitmapFromResource(
            uri: Uri,
            reqWidth: Int,
            reqHeight: Int
    ): Bitmap {

        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run {


            inJustDecodeBounds = true
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri),null, this)

            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri),null, this)!!

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun imgRotate(uri: Uri, bitmap: Bitmap) : Bitmap{
        val ins = context.contentResolver.openInputStream(uri)
        val exif = ins?.let { ExifInterface(it) }
        ins?.close()

        val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        val matrix = Matrix()
        when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


}