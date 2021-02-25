package curtin.edu.au.weeklyspecials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import curtin.edu.au.weeklyspecials.Data.ColesListSingleton;
import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Data.WooliesListSingleton;

public class SMSListActivity extends AppCompatActivity
{
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Button sendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list);

        final EditText phoneNumber = (EditText)findViewById(R.id.editTSMSRecipient);
        final EditText message = (EditText)findViewById(R.id.editTSMSMessage);
        sendSMS = (Button)findViewById(R.id.btnSendSMS);

        if(Build.VERSION.SDK_INT >= 23)
        {
            if(checkPermission())
            {
                Log.e("Permission", "Already granted.");
            }
            else
            {
                requestPermission();
            }
        }

        sendSMS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String phone = phoneNumber.getText().toString();

                StringBuilder sms = new StringBuilder("WEEKLY SPECIALS: \n\n" +
                        message.getText().toString() + "\n\n");
                sms.append(getWooliesList());
                sms.append(getColesList());

                if(!TextUtils.isEmpty(phone))
                {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null,
                            sms.toString(),
                            null,
                            null);

                    Toast.makeText(SMSListActivity.this,
                            "SUCCESS: Shopping list has been sent",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SMSListActivity.this,
                            "ERROR: Enter a phone number",
                             Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* Permission checks ******************************************************/
    private boolean checkPermission()
    {
        int result = ContextCompat.checkSelfPermission(
                SMSListActivity.this,
                Manifest.permission.SEND_SMS);
        boolean isGranted;

        if (result == PackageManager.PERMISSION_GRANTED)
        {
            isGranted = true;
        }
        else
        {
            isGranted = false;
        }

        return isGranted;
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

                Toast.makeText(SMSListActivity.this,
                        "Permission accepted", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(SMSListActivity.this,
                        "Permission denied", Toast.LENGTH_LONG).show();
                sendSMS.setEnabled(false);
            }
        }
    }

    /* Retrieving shopping lists **********************************************/
    private String getWooliesList()
    {
        WooliesListSingleton data = WooliesListSingleton.getInstance();
        List<ItemData> list = data.getShoppingList();
        StringBuilder fullList;

        if(!list.isEmpty())
        {
            fullList = new StringBuilder("Woolies List:\n");

            for(int i = 0; i < list.size(); i++)
            {
                ItemData item = list.get(i);
                fullList.append(item.getDesc() + "\n");
                String cost = String.format("%.2f", item.getCost());
                fullList.append("Cost: $" + cost + "\n");
                fullList.append("Qty: " + item.getQty() + "\n\n");
            }

            String totalCost = String.format("%.2f", data.getTotalCost());
            fullList.append("Woolies Total Cost: $" + totalCost + "\n\n\n");
        }
        else
        {
            fullList = new StringBuilder("");
        }

        return fullList.toString();
    }

    private String getColesList()
    {
        ColesListSingleton data = ColesListSingleton.getInstance();
        List<ItemData> list = data.getShoppingList();
        StringBuilder fullList;

        if(!list.isEmpty())
        {
            fullList = new StringBuilder("Coles List:\n");

            for(int i = 0; i < list.size(); i++)
            {
                ItemData item = list.get(i);
                fullList.append(item.getDesc() + "\n");
                String cost = String.format("%.2f", item.getCost());
                fullList.append("Cost: $" + cost + "\n");
                fullList.append("Qty: " + item.getQty() + "\n\n");
            }

            String totalCost = String.format("%.2f", data.getTotalCost());
            fullList.append("Coles Total Cost: $" + totalCost + "\n\n\n");
        }
        else
        {
            fullList = new StringBuilder("");
        }

        return fullList.toString();
    }
}