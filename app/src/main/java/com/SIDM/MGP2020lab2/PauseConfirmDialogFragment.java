package com.SIDM.MGP2020lab2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PauseConfirmDialogFragment extends DialogFragment {
    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        IsShown = true;

        //Use the Builder class to create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Paused")
                .setNegativeButton("Resume", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Trigger Resume
                        GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                        //IsShown = false;
                    }
                })
                .setPositiveButton("ExitGame", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //No pause to be triggered
                        StateManager.Instance.ChangeState("Mainmenu");
                        //IsShown = true;
                    }
                });
        GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
        //Create = make the pop up alert dialog based on what you configure
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        return alert;
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        IsShown = false;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        IsShown = false;
    }

}

