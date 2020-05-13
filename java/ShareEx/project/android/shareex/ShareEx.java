package shareex;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import org.haxe.extension.Extension;
import java.io.File;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager;

public class ShareEx {

	public static void share(String text, String subject, String html, String email, String image, String file, String fileType) {
		Log.d("haxe-share", text);
		Log.d("haxe-share", subject);
		Log.d("haxe-share", html);
		Log.d("haxe-share", email);
		Log.d("haxe-share", image);
		Log.d("haxe-share", file);
		Log.d("haxe-share", fileType);
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
			Uri uri = android.support.v4.content.FileProvider.getUriForFile(
					Extension.mainContext,
					Extension.mainContext.getApplicationContext().getPackageName() + ".provider",
					new File(file));
			Extension.mainContext.grantUriPermission(
					Extension.mainContext.getApplicationContext().getPackageName(),
					uri,
					Intent.FLAG_GRANT_READ_URI_PERMISSION);

			java.util.List<ResolveInfo> resInfoList = Extension.mainContext.getPackageManager().queryIntentActivities(sendIntent, PackageManager.MATCH_DEFAULT_ONLY);
			for (ResolveInfo resolveInfo : resInfoList) {
				String packageName = resolveInfo.activityInfo.packageName;
				Log.d("haxe-share", "Granted to " + packageName);
				Extension.mainContext.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
			}

			Log.d("haxe-share", uri.toString());

			sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
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
