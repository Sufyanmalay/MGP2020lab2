package com.SIDM.MGP2020lab2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class EndConfirmDialogFragment extends DialogFragment
{
    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        IsShown = true;
        GameSystem.Instance.SetIsPaused(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("End Screen")
        .setMessage("You Died! Your Final Score is : " + MainGameSceneState.playerScore)
        .setPositiveButton("Exit Game", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        StateManager.Instance.ChangeState("Mainmenu");
                        GameSystem.Instance.SetIsPaused( false );
                        IsShown = false;
                    }
                }
        );

        //GameSystem.Instance.SetIsEnd(!GameSystem.Instance.GetIsEnd());
        //Create = make the pop up alert dialog based on what you configure
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
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
