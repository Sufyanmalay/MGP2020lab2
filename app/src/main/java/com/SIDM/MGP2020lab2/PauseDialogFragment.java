package com.SIDM.MGP2020lab2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class PauseDialogFragment extends DialogFragment {
    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        IsShown = true;

        GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Resume game or exit to main menu?");
        builder.setNegativeButton("Resume", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // Resume the game
                    GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                    IsShown = false;
                }
            }
        );

        builder.setPositiveButton("Exit Game", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    GameSystem.Instance.SetIsPaused((false));
                    StateManager.Instance.ChangeState("Mainmenu");
                    IsShown = false;
                }
            }
        );

        return builder.create();
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

