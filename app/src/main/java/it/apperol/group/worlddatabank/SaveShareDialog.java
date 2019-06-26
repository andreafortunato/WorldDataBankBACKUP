package it.apperol.group.worlddatabank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class SaveShareDialog extends DialogFragment implements View.OnClickListener {

    private MaterialButton mbSave, mbShare, mbCancel;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.save_share_layout, null, false);

        builder.setView(view);

        mbSave = view.findViewById(R.id.mbSave);
        mbShare = view.findViewById(R.id.mbShare);
        mbCancel = view.findViewById(R.id.mbCancel);

        // 2. set click listeners on desired views
        mbSave.setOnClickListener(this);
        mbShare.setOnClickListener(this);
        mbCancel.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mbSave:
                dismiss();
                Log.i("[TAG]", "mbSave");
                Toast.makeText(getContext(), "mbSave", Toast.LENGTH_SHORT).show();

                // TODO: SALVARE GRAFICO IN GALLERIA

                Snackbar.make(getActivity().findViewById(android.R.id.content), "Chart saved to gallery", Snackbar.LENGTH_LONG)
                        .setAction("Delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO: ELIMINARE DA GALLERIA IL GRAFICO APPENA SALVATO
                                Toast.makeText(v.getContext(), "Chart deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.mbShare:
                Log.i("[TAG]", "mbShare");
                Toast.makeText(getContext(), "mbShare", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mbCancel:
                dismiss();
                break;
            default:
                break;
        }
    }
}
