#include <Share.h>
#import <UIKit/UIKit.h>

namespace openflShareExtension {
	
	void doShare(const char *text, const char *url, const char *subject, const char *image, const char *file){
    	NSLog(@"doShare");
        UIViewController *root = [[[UIApplication sharedApplication] keyWindow] rootViewController];
        NSString *sText = [[NSString alloc] initWithUTF8String:text];
        NSArray *itemsToShare;
        if(url != nil){
            NSString * urlString = [[NSString alloc] initWithUTF8String:url];
            NSLog(urlString);
	        NSURL *sURL = ([urlString rangeOfString:@"https://"].location == 0)
	            ? [NSURL URLWithString:urlString]
	            : [NSURL fileURLWithPath:urlString isDirectory:NO];
	        itemsToShare = ([urlString rangeOfString:@"https://"].location == 0) ? @[sText,sURL] : @[sURL];
        } else if (file != nil) {
            NSString * fileString = [[NSString alloc] initWithUTF8String:file];
            NSLog(fileString);
	        itemsToShare = @[[NSURL fileURLWithPath:fileString isDirectory:NO]];
        } else {
	        itemsToShare = @[sText];        	
        }

        NSString *sImage = [[NSString alloc] initWithUTF8String:image];
        if(![sImage isEqualToString:@""])
        {
            UIImage* sharedImg=[UIImage imageWithContentsOfFile:sImage];
            NSArray *imageArray = @[sharedImg];
            itemsToShare=[itemsToShare arrayByAddingObjectsFromArray:imageArray];
        }

        UIActivityViewController *activityVC = [[UIActivityViewController alloc] initWithActivityItems:itemsToShare applicationActivities:nil];
        if(subject != nil){
            [activityVC setValue:[[NSString alloc] initWithUTF8String:subject] forKey:@"subject"];
        }

        // Required for iPad on iOS >=8
        if ([activityVC respondsToSelector:@selector(popoverPresentationController)]) {
            if(NULL != [activityVC valueForKey: @"popoverPresentationController"]) {
                [[activityVC valueForKey: @"popoverPresentationController"] setValue:[[UIApplication sharedApplication] keyWindow] forKey:@"sourceView"];
                [[activityVC valueForKey: @"popoverPresentationController"] setPermittedArrowDirections:0]; // Remove arrow from action sheet.
                [[activityVC valueForKey: @"popoverPresentationController"] setValue:[NSValue valueWithCGRect:[[UIApplication sharedApplication] keyWindow].frame] forKey:@"sourceRect"]; // Set action sheet to middle of view.
            }
        }

//        activityVC.excludedActivityTypes = @[UIActivityTypeAddToReadingList,
//                                             UIActivityTypeCopyToPasteboard,
//                                             UIActivityTypePrint,
//                                             UIActivityTypeAssignToContact,
//                                             UIActivityTypeSaveToCameraRoll,
//                                             UIActivityTypeAddToReadingList,
//                                             //UIActivityTypeMail,
//                                             UIActivityTypeAirDrop];
        [root presentViewController:activityVC animated:YES completion:nil];
    }

}
