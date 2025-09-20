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
                },
                showPaymentChannelsFragment = true, // set true to go to payment channels flow. if not set or false(default), payments are only with OnlinePayment channel
                extraInfoComponentDataModel = PishkhansdkExtraInfoComponentDataModel( // set this parameter with your data to show in CNPGFragment, if set to null no extra info shows in CNPGFragment. If size of list is larger that 2 then, component would be expandable
                      extraInfoItems = listOf(
                          ExtraInfo(Key = "خدمت",Value = "پرداخت قبض خلافی"), 
                          ExtraInfo(Key = "مبلغ", Value = "100,000 ریال")
                      )
                )         
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
            serviceName = your_product,
            userTransactionHistoryRv =  your_recycler_view,
            hasTransactionHistory = {
               
            }
        ) { output, serviceName ->
            
        }

```
Note: ServiceName can be null and if you don't pass service name all items in transaction will be showed in the list otherwise you can see just transactions belongs to the service name .

For bills payment, You can start BillsPaymentChannelsFragment and pass the parameters. This fragment contains payment channels (wallet, CNPG and IPG) for bills payment:

```sh
         start(
             BillsPaymentChannelsFragment(
                 bills = bills, // list of bills for payment
                 service = product, // service name
                 callbackUrl = "", // set this for IPG
                 onBillsPaid = {
                     // handle after bills paid.
                 },
                 extraInfoComponentDataModel = PishkhansdkExtraInfoComponentDataModel( // set this parameter with your data to show in CNPGFragment, if set to null no extra info shows in CNPGFragment. If size of list is larger that 2 then, component would be expandable
                     extraInfoItems = listOf(
                         ExtraInfo(Key = "خدمت",Value = "پرداخت قبض خلافی"),
                         ExtraInfo(Key = "مبلغ", Value = amount.formatAmount()),
                         ExtraInfo(Key = "تعداد قبض", Value = bills.size.toString())
                     )
                 )
             )
         )
```

WalletFragment.kt: This class use for charge the wallet. For direct charge use default constructor WalletFragment(). If you wanted start this fragment for bill payment, use constructor with parameters like below:

```sh
         start(
            WalletFragment(
                source = WalletFragment.Source.Service, // set this to Service if you do not want DirectCharge 
                neededCashForChargeWallet = 100000, // set amount for charge wallet
                callbackDataModel = , // set this for IPG payment
                walletChargeSuccessfulViaCNPG = {
                    // handle after wallet charged via CNPG payment
                }
            )
        ) 
         
```

PaymentChannelsFragment.kt: This class contains payment channels for payments. This is an abstract class with necessary and basic properties for payment channels. (BillsPaymentChannelsFragment.kt and InvoicePaymentChannelsFragment.kt inherit from this class)

```sh
      abstract class PaymentChannelsFragment: AyanFragment<PishkhansdkFragmentPaymentChannelsBinding>() {
        // UI implementation and basic logics implemented in this class
      }
```

BillsPaymentChannelsFragment.kt and BillsPaymentChannelsInterface.kt (used for services payment)

```sh
      open class BillsPaymentChannelsFragment(): PaymentChannelsFragment(), BillsPaymentChannelsInterface {
        // Implement the bills payment logic from BillsPaymentChannelsInterface and use them for connecting with PaymentChannelsFragment super class
      }
```

InvoicePaymentChannelsFragment.kt and InvoicePaymentChannelsInterface.kt (used in services inquiry)

```sh
      open class InvoicePaymentChannelsFragment(): PaymentChannelsFragment(), InvoicePaymentChannelsInterface {
        // Implement the invoice payment logic from InvoicePaymentChannelsInterface and use them for connecting with PaymentChannelsFragment super class
      }
```


## Custom Color:

```sh

     <color name="icon_secondary_color">#16A796</color>
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
