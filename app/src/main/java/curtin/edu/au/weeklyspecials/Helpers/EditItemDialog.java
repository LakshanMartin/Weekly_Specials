package curtin.edu.au.weeklyspecials.Helpers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.text.HtmlCompat;

import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.R;

public class EditItemDialog extends AppCompatDialogFragment
{
    private EditText editDesc;
    private EditText editCost;
    private EditText editQty;
    private ItemData item;
    private ColesDialogBoxListener listener;

    public EditItemDialog(ItemData toEdit)
    {
        item = toEdit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_item_layout, null);

        builder.setView(view)
                .setTitle("Edit Item")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String newDesc = editDesc.getText().toString();
                        String newCost = editCost.getText().toString();
                        String newQty = editQty.getText().toString();

                        listener.applyTexts(newDesc, newCost, newQty);
                    }
                });

        editDesc = view.findViewById(R.id.popupDesc);
        editCost = view.findViewById(R.id.popupCost);
        editQty = view.findViewById(R.id.popupQty);

        //Format Cost and Qty strings
        String strCost = getActivity().getResources().getString(
                R.string.list_cost, item.getCost());
        CharSequence styledCost = HtmlCompat.fromHtml(strCost, HtmlCompat.FROM_HTML_MODE_LEGACY);

        String strQty = getActivity().getResources().getString(
                R.string.list_qty, item.getQty());
        CharSequence styledQty = HtmlCompat.fromHtml(strQty, HtmlCompat.FROM_HTML_MODE_LEGACY);

        //Display edit text hints
        editDesc.setHint(item.getDesc());
        editCost.setHint(styledCost);
        editQty.setHint(styledQty);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

        try
        {
            listener = (ColesDialogBoxListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() +
                    "must implement DialogBoxListener");
        }
    }

    public interface ColesDialogBoxListener
    {
        void applyTexts(String newDesc, String newCost, String newQty);
    }
}
