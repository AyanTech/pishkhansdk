# PiskhanSDK 
This SDK provides developers with a solution for integrating and interacting with various service APIs and shows the result 

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
    implementation 'com.github.AyanTech:OCR-SDK:latest-version'
}
```
## Initialization:
Create Instance of coreApi and serviceApi in your Application class :

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
If you have payment, when the payment process is successful and you are getting back to the app call this method in MainActivity:

```sh
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent != null)
            handleIntent()
    }

    private fun handleIntent() {
        PishkhanSDK.userPaymentIsSuccessful(
            intent = intent,
        ) {
            // Cast the result here 
        }
    }
```
To get the inquiry history list add the following method :

```sh
        PishkhanSDK.getInquiryHistory(
            context = this,
            product = your_product,
            inquiryHistoryRv = your_recycler_view
        ) {
           
        }

```

To get the transaction history list add the following method :

```sh
        PishkhanSDK.getUserTransactionHistory(
            userTransactionHistoryRv = your_recycler_view,
            hasTransactionHistory = {
               
            }
        ) { output, serviceName ->
            
        }

```
