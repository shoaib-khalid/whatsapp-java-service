##################################################
##################################################
# Version 1.3.0| 27-Feb-2023
##################################################
New webhook url for FamilyPlan Prepaid & Postpaid


##Config changes:
route.incoming.familyplan.prepaid.url=http://209.58.160.107:9566/message/prepaid/webhook
route.incoming.familyplan.postpaid.url=http://209.58.160.107:9566/message/postpaid/webhook


##################################################
##################################################
# Version 1.2.2| 14-Nov-2022
##################################################
Different webhook url for dinein & easydukan


##################################################
##################################################
# Version 1.2.1| 2-Nov-2022
##################################################
New msisdn for EasyDukan

##Config changes:
whatsapp.push.url.92516120000=https://graph.facebook.com/v15.0/108910671852368/messages
whatsapp.push.token.92516120000=Bearer EAAG9yFXAfwABACu6stKw1YOpqMA1KJ8lVnLLl0SCdFI8vf4SxVa39x18tjIrxG5rrBgDaSjjI2bLZAW4mlv1XlOKk9Eakc2pGDZBi8YK00qZBCkUiSM8hdwiPsm54TW0C5nTZBZCxKgI2gtG2CSdMdDoxpCKFoN3gWHHvq65mcZAHSt4S7z2tt19w1fsX5AZAXFnSaBju0PZAAZDZD

##################################################
# Version 1.0.1| 29-Sept-2022
##################################################
New url for survey-whatsapp webhook to receive msg from whatsapp

##################################################
##################################################
# Version 1.2.0| 19-Oct-2022
##################################################
New msisdn for Dine-In


##################################################
# Version 1.1.0| 29-Sept-2022
##################################################
New url for survey-whatsapp webhook to receive msg from whatsapp


##################################################
# Version 1.0.0| 16-Aug-2022
##################################################
New url for whatsapp webhook to receive msg from whatsapp

##New config:
route.incoming.default=https://www.kalsym.com/whatsapp/webhook.php
route.incoming.staging.url=https://api.symplified.it/order-service/v1/whatsapp/receive
route.incoming.production.url=https://api.symplified.biz/order-service/v1/whatsapp/receive


##################################################
# Version 0.0.4| 15-Aug-2022
##################################################
New request paramter in Template : ButtonParameter[]
this is duplicate of old parameter parametersButton, but to maintain backward compatibility, old parameter cannot be removed
This field shall be used for new integration. old parameter String[] parametersButton will be removed


##################################################
# Version 0.0.3| 8-Aug-2022
##################################################
1. Normalize number : 
	if start with 01xxx, append 601xxx
	if start with 0xxx, convert to 92xxx
2. Forward http status from Faceboook api to caller
	
	
##################################################
# Version 0.0.2| 5-July-2022
##################################################
Bug fix for set button text


##################################################
# Version 0.0.1| 28-June-2022
##################################################
Initial version
