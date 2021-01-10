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

        Intent intent = new Intent();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("End Screen");
        builder.setMessage("You Died! Your Final Score is : " + MainGameSceneState.playerScore);

        builder.setPositiveButton("Exit Game", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        AudioManager.Instance.StopAudio(R.raw.music); // end music
                        AudioManager.Instance.PlayAudio(R.raw.buttontwo, 1.0f, 1.0f); // button sound
                        StateManager.Instance.ChangeState("Mainmenu");
                    }
                }
        );

        GameSystem.Instance.SetIsEnd(!GameSystem.Instance.GetIsEnd());
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
