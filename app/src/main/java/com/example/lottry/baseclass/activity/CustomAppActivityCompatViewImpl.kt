package  com.softs.meetupfellow.components.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.widget.EditText
import com.google.gson.Gson

import java.lang.reflect.Type
import java.util.*

/**
 * Created by Maheshwar on 21-07-2017.
 */
@Suppress("UNUSED_PARAMETER")
@SuppressLint("Registered")
open class CustomAppActivityCompatViewImpl : CustomPermissionActivity() {



    // Tracks the bound state of the service.
    private var mBoundIAB = false


    override fun onResume() {
        super.onResume()

    }

    internal fun convertResponseToRequest(from: Any, to: Type): Any {
        return Gson().fromJson(Gson().toJson(from), to)
    }


    protected fun getRandomNumber(max: Int): Int {
        return Random().nextInt(max)
    }

    protected fun hideKeyboard(editText: EditText) {
       /* val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)*/
    }

    protected fun showAlert(message: String, title: String) {
       /* val alertDialog = AlertDialog.Builder(
            this@CustomAppActivityCompatViewImpl, R.style.MyDialogTheme2
        )
        // Setting DialogAction Message
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        // On pressing SettingsActivity button
        alertDialog.setPositiveButton(getString(R.string.text_yes)) { dialog, _ ->
            dialog.dismiss()
            System.exit(0)
            finish()
        }
        alertDialog.setNegativeButton(getString(R.string.text_no)) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.setCancelable(false)
        // Showing Alert Message
        alertDialog.show()*/
    }


    protected fun showAlertForStatusRejected(message: String, title: String) {
       /* val alertDialog = AlertDialog.Builder(
            this@CustomAppActivityCompatViewImpl, R.style.MyDialogTheme2
        )
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(getString(R.string.text_okay)) { dialog, _ ->
            dialog.dismiss()
            logout()
        }
        alertDialog.setCancelable(false)

        alertDialog.show()*/
    }


    /*  fun showProgress() {
          getLoader().show()
      }

      fun hideProgress() {
          getLoader().dismiss()
      }
      fun Activity.getLoader(): MainLoader {
          return MainLoader(this@CustomAppActivityCompatViewImpl)
      }

      companion object {
          internal val DEVICE_HEIGHT = ArchitectureApp.instance!!.resources.displayMetrics.heightPixels
      }*/
}