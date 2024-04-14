# PiskhanSDK 
This SDK provides developers with a solution for integrating and interacting with various service APIs and showing the result 

## Implementation 
### Gradle

> [!Important]
> Add the Jitpack repository to your project's settings.gradle file:

```sh
repositories {
    maven { url 'https://jitpack.io' }
}
```
> [!Important]
>Add the dependency to your app's build.gradle file:

```sh
dependencies {
    implementation 'com.github.AyanTech:pishkhansdk:latest-version'
}
```
## Initialization:
Create an Instance of coreApi and serviceApi in your Application class :

```sh

val gson = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create()

 servicesAyanApi = AyanApi(
            context = this,
            getUserToken = { PishkhanSDK.getPishkhanToken() },
            defaultBaseUrl = "https://services.pishkhan24.ayantech.ir/webservices/services.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )


        coreAyanApi = AyanApi(
            context = this,
            getUserToken = { PishkhanSDK.getPishkhanToken() },
            defaultBaseUrl = "https://core.pishkhan24.ayantech.ir/webservices/core.svc/",
            timeout = 120,
            logLevel = if (BuildConfig.BUILD_TYPE == "debug") LogLevel.LOG_ALL else LogLevel.DO_NOT_LOG,
            gson = gson
        )
```
Initialize pishkhanSDK in Application class:

```sh
        PishkhanSDK.initialize(
            context = this,
            schema = "your_schema",
            host = "your_host",
            corePishkhan24Api = coreAyanApi!!,
            servicesPishkhan24Api = servicesAyanApi!!,
        )
```
Note: By defining "your_schema" and "your_host" accurately in the manifest, you ensure that callbacks are directed to the correct endpoint of your application.
 
## Usage:
Add this method in onCreate of MainActivity : 

```sh
        PishkhanSDK.handleUserSession(
            application = "application_name", origin = "store",
            platform = "android",
            version = "your_version",
            activity = this,
            successCallback = {
                //User session is updated 
            }
        )

```

After clicking the inquiry button, execute the following method to start SDK's function:

```sh
            PishkhanSDK.onInquiryButtonClicked(
                inputModel = your_input,
                product = your_product,
                failureCallBack = {
                   
                },
                handleResultCallback = {
                   // Cast the result here 
                }
            )
```
For services with payment, when the payment process is successful and you are getting back to the app, call this method in MainActivity:

```sh
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent != null)
            handleIntent()
    }

    private fun handleIntent() {
        PishkhanSDK.userPaymentIsSuccessful(
            intent = intent,
        ) { output, serviceName ->
            // Cast the result here 
        }
    }
```
To get the inquiry history list add the following method :

```sh

        PishkhanSDK.getInquiryHistory(
            context = this,
            product = your_product ,
            inquiryHistoryRv = your_recycler_view,
            hasInquiryHistory = {

            },
            handleInquiryHistoryClick = {
                //User has clicked on the item list
            },
            failureCallBack = {
                //Custom failure
            },
            changeStatusCallback = {
                //Custom change status
            }
        )

```

To get the transaction history list add the following method :

```sh
        PishkhanSDK.getUserTransactionHistory(
            userTransactionHistoryRv = ,
            hasTransactionHistory = {
               
            }
        ) { output, serviceName ->
            
        }


```

## Custom Color:

```sh

    <color name="pishkhansdk_button_text_color">#111E24</color>
    <color name="pishkhansdk_inquiry_button_color">#2ECB6D</color>
    <color name="pishkhansdk_cursor_color_text_input_edit_text">#00BAF2</color>
    <color name="pishkhansdk_hint_text_color">#00BAF2</color>
    <color name="pishkhansdk_box_stroke_error_color">#EC1B0F</color>
    <color name="pishkhansdk_helper_text_color">#111E24</color>
    <color name="pishkhansdk_text_color_hint">#BDBDBD</color>
    <color name="pishkhansdk_text_color_inquiry_history">#424242</color>
    <color name="pishkhansdk_is_favorite_icon_color">@color/color_primary</color>
    <color name="pishkhansdk_is_not_favorite_icon_color">#BDBDBD</color>
    <color name="pishkhansdk_bottom_sheet_title_color">#000000</color>

```
