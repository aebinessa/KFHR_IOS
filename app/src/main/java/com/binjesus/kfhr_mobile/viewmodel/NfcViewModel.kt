import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NfcViewModel : ViewModel() {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    fun initializeNfc(adapter: NfcAdapter, context: Context) {
        nfcAdapter = adapter

        val intent = Intent(context, context::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }

        intentFiltersArray = arrayOf(ndef)
        techListsArray = arrayOf(arrayOf(Ndef::class.java.name))
    }

    fun onNewIntent(intent: Intent) {
        viewModelScope.launch {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
                val ndef = Ndef.get(tag)
                ndef?.connect()
                val messages = ndef?.ndefMessage
                val records = messages?.records
                records?.forEach { record ->
                    val payload = record.payload
                    val text = String(payload)
                    Log.d("NFC", "NFC Tag Content: $text")
                }
                ndef?.close()
            }
        }
    }

    fun enableNfcForegroundDispatch(activity: AppCompatActivity) {
        nfcAdapter.enableForegroundDispatch(
            activity, pendingIntent, intentFiltersArray, techListsArray
        )
    }

    fun disableNfcForegroundDispatch(activity: AppCompatActivity) {
        nfcAdapter.disableForegroundDispatch(activity)
    }
}
