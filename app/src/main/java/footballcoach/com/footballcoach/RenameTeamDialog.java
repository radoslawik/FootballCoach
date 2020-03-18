package footballcoach.com.footballcoach;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class RenameTeamDialog extends AppCompatDialogFragment {
    private TextInputLayout editTeamName;
    private RenameTeamListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.team_rename_dialog_layout, null);
        editTeamName = view.findViewById(R.id.etRenameTeam);
        builder.setView(view)
                .setTitle("Rename Team")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = editTeamName.getEditText().getText().toString();
                        listener.sendText(newName);
                        return;
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (RenameTeamListener)context;
        } catch (Exception e) {
            System.out.println("Could not attach listener");
        }
    }

    public interface RenameTeamListener{
        void sendText(String teamName);
    }
}
