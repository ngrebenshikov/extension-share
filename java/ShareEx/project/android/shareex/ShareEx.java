package shareex;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import org.haxe.extension.Extension;

public class ShareEx {

	public static void share(String text, String subject, String html, String email, String image, String file, String fileType) {
		Log.d("mulinella-share", text);
		Log.d("mulinella-share", subject);
		Log.d("mulinella-share", html);
		Log.d("mulinella-share", email);
		Log.d("mulinella-share", image);
		Log.d("mulinella-share", file);
		Log.d("mulinella-share", fileType);
		Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
		sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if ( image.equals("") && file.equals("")) {
		    sendIntent.setType("text/plain");
        } else if (!image.equals("")){
            sendIntent.setType("image/*");
            sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + image));
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
			sendIntent.setType(fileType);
			sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
			sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		}

		if(subject!=null && subject!="") sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		if(text!=null && text!="") sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		if(html!=null && html!="") sendIntent.putExtra(android.content.Intent.EXTRA_HTML_TEXT, html);
		if(email!=null && email!="") sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, email);

		Intent chooserIntent = Intent.createChooser(sendIntent, null);
		chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Extension.mainActivity.getApplicationContext().startActivity(chooserIntent);
	}

}
