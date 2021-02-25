package curtin.edu.au.weeklyspecials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import curtin.edu.au.weeklyspecials.Data.ColesListSingleton;
import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Data.WooliesListSingleton;

public class EmailListActivity extends AppCompatActivity
{
    private EditText editTRecipient, editTSubject, editTMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);

        editTRecipient = (EditText)findViewById(R.id.editTRecipient);
        editTSubject = (EditText)findViewById(R.id.editTSubject);
        editTMessage = (EditText)findViewById(R.id.editTMessage);

        Button btnSend = (Button)findViewById(R.id.btnSendEmail);
        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendEmail();
            }
        });
    }

    private void sendEmail()
    {
        String recipientList = editTRecipient.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = "WEEKLY SPECIALS: " + editTSubject.getText().toString();
        StringBuilder message = new StringBuilder(editTMessage.getText().toString() + "\n\n");
        message.append(getWooliesList());
        message.append(getColesList());

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message.toString());

        intent.setType("message/rfc822");

        EmailListActivity.this.finish();
        startActivity(Intent.createChooser(intent, "Select an email client:"));
    }

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