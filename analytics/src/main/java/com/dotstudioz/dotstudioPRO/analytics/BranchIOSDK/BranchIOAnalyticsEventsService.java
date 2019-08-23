package com.dotstudioz.dotstudioPRO.analytics.BranchIOSDK;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.BRANCH_STANDARD_EVENT;
import io.branch.referral.util.BranchContentSchema;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.CurrencyType;
import io.branch.referral.util.ProductCategory;

public class BranchIOAnalyticsEventsService {
    private static final BranchIOAnalyticsEventsService ourInstance = new BranchIOAnalyticsEventsService();

    public static BranchIOAnalyticsEventsService getInstance() {
        return ourInstance;
    }

    private BranchIOAnalyticsEventsService() {
    }

    public void initialize(Activity ctx) {
        try {
            // Branch init
            Branch.getInstance().initSession(new Branch.BranchReferralInitListener() {
                @Override
                public void onInitFinished(JSONObject referringParams, BranchError error) {
                    if (error == null) {
                        Log.i("BRANCH SDK", referringParams.toString());
                        // Retrieve deeplink keys from 'referringParams' and evaluate the values to determine where to route the user
                        // Check '+clicked_branch_link' before deciding whether to use your Branch routing logic
                    } else {
                        Log.i("BRANCH SDK", error.getMessage());
                    }
                }
            }, ctx.getIntent().getData(), ctx);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getAutoInstance(boolean enabledDebug, Activity activity) {
        try {
            if(enabledDebug) {
                // Branch logging for debugging
                Branch.enableDebugMode();
            }
            // Branch object initialization
            Branch.getAutoInstance(activity);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to save all the custom events, defined by the app itself
     * @param activity
     * @param eventName
     * @param description
     * @param keyValDTOArrayList
     */
    public void saveCustomEvent(Activity activity, String eventName, String description, ArrayList<BranchIOKeyValDTO> keyValDTOArrayList) {
        try {
            BranchEvent be = new BranchEvent(eventName);
            if(description != null && description.length() > 0) {
                be.setDescription(description);
            } else {
                be.setDescription("");
            }
            if(keyValDTOArrayList != null && keyValDTOArrayList.size() > 0) {
                for (int i = 0; i < keyValDTOArrayList.size(); i++) {
                    if(keyValDTOArrayList.get(i).key != null &&
                            keyValDTOArrayList.get(i).key.length() > 0 &&
                            keyValDTOArrayList.get(i).val != null &&
                            keyValDTOArrayList.get(i).val.length() > 0) {
                        be.addCustomDataProperty(keyValDTOArrayList.get(i).key, keyValDTOArrayList.get(i).val);
                    }
                }
            }
            be.logEvent(activity);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to save all the events related to Content
     *     SEARCH("SEARCH"),
     *     VIEW_ITEM("VIEW_ITEM"),
     *     VIEW_ITEMS("VIEW_ITEMS"),
     *     RATE("RATE"),
     *     SHARE("SHARE"),
     * @param context
     * @param branchStandardEvent
     * @param productName
     * @param description
     * @param keyValDTOArrayList
     */
    public void saveContentEvent(Activity context,
                                   BRANCH_STANDARD_EVENT branchStandardEvent,
                                   String productName,
                                   String description,
                                   ArrayList<BranchIOKeyValDTO> keyValDTOArrayList) {
        try {
            BranchEvent be = new BranchEvent(branchStandardEvent);

            if (productName != null && productName.length() > 0)
                be.setSearchQuery(productName);
            else
                be.setSearchQuery("");

            if (description != null && description.length() > 0)
                be.setDescription(description);
            else
                be.setDescription("");


            if (keyValDTOArrayList != null && keyValDTOArrayList.size() > 0) {
                for (int i = 0; i < keyValDTOArrayList.size(); i++) {
                    if (keyValDTOArrayList.get(i).key != null &&
                            keyValDTOArrayList.get(i).key.length() > 0 &&
                            keyValDTOArrayList.get(i).val != null &&
                            keyValDTOArrayList.get(i).val.length() > 0) {
                        be.addCustomDataProperty(keyValDTOArrayList.get(i).key, keyValDTOArrayList.get(i).val);
                    }
                }
            }

            be.logEvent(context);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to save all the events related to LifeCycle
     *     COMPLETE_REGISTRATION("COMPLETE_REGISTRATION"),
     *     COMPLETE_TUTORIAL("COMPLETE_TUTORIAL"),
     *     ACHIEVE_LEVEL("ACHIEVE_LEVEL"),
     *     UNLOCK_ACHIEVEMENT("UNLOCK_ACHIEVEMENT"),
     * @param context
     * @param branchStandardEvent
     * @param txnId
     * @param description
     * @param keyValDTOArrayList
     */
    public void saveLifeCycleEvent(Activity context,
                                 BRANCH_STANDARD_EVENT branchStandardEvent,
                                 String txnId,
                                 String description,
                                 ArrayList<BranchIOKeyValDTO> keyValDTOArrayList) {
        try {
            BranchEvent be = new BranchEvent(branchStandardEvent);

            if (txnId != null && txnId.length() > 0)
                be.setTransactionID(txnId);
            else
                be.setTransactionID("");

            if (description != null && description.length() > 0)
                be.setDescription(description);
            else
                be.setDescription("");


            if (keyValDTOArrayList != null && keyValDTOArrayList.size() > 0) {
                for (int i = 0; i < keyValDTOArrayList.size(); i++) {
                    if (keyValDTOArrayList.get(i).key != null &&
                            keyValDTOArrayList.get(i).key.length() > 0 &&
                            keyValDTOArrayList.get(i).val != null &&
                            keyValDTOArrayList.get(i).val.length() > 0) {
                        be.addCustomDataProperty(keyValDTOArrayList.get(i).key, keyValDTOArrayList.get(i).val);
                    }
                }
            }

            be.logEvent(context);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to save all the events related to Commerce
     *     ADD_TO_CART("ADD_TO_CART"),
     *     ADD_TO_WISHLIST("ADD_TO_WISHLIST"),
     *     VIEW_CART("VIEW_CART"),
     *     INITIATE_PURCHASE("INITIATE_PURCHASE"),
     *     ADD_PAYMENT_INFO("ADD_PAYMENT_INFO"),
     *     PURCHASE("PURCHASE"),
     *     SPEND_CREDITS("SPEND_CREDITS"),
     * @param context
     * @param branchStandardEvent
     * @param productId
     * @param productTitle
     * @param productURL
     * @param customKeyValDTOArrayList
     * @param streetName
     * @param city
     * @param state
     * @param country
     * @param postalCode
     * @param lat
     * @param lon
     * @param price
     * @param currencyType
     * @param productBrand
     * @param productCategory
     * @param productName
     * @param productCondition
     * @param productVariant
     * @param productQuantity
     * @param sku
     * @param branchContentSchema
     * @param keywordArrayList
     * @param affiliation
     * @param couponCode
     * @param description
     * @param shipping
     * @param tax
     * @param revenue
     * @param searchQuery
     * @param keyValDTOArrayList
     */
    public void saveCommerceEvent(Activity context,
                                  BRANCH_STANDARD_EVENT branchStandardEvent,
                                  String productId,
                                  String productTitle,
                                  String productURL,
                                  ArrayList<BranchIOKeyValDTO> customKeyValDTOArrayList,
                                  String streetName, String city, String state, String country, String postalCode,
                                  double lat, double lon,
                                  double price, CurrencyType currencyType,
                                  String productBrand, ProductCategory productCategory, String productName, ContentMetadata.CONDITION productCondition,
                                  String productVariant, double productQuantity, String sku, BranchContentSchema branchContentSchema,
                                  ArrayList<String> keywordArrayList,
                                  String affiliation, String couponCode, String description, double shipping, double tax, double revenue, String searchQuery,
                                  ArrayList<BranchIOKeyValDTO> keyValDTOArrayList) {
        try {
            BranchUniversalObject buo = new BranchUniversalObject();
            buo.setCanonicalIdentifier(productId);
            buo.setCanonicalUrl(productURL);
            buo.setTitle(productTitle);

            ContentMetadata contentMetadata = new ContentMetadata();
            for (int i = 0; i < customKeyValDTOArrayList.size(); i++) {
                if(customKeyValDTOArrayList.get(i).key != null &&
                        customKeyValDTOArrayList.get(i).key.length() > 0 &&
                        customKeyValDTOArrayList.get(i).val != null &&
                        customKeyValDTOArrayList.get(i).val.length() > 0) {
                    contentMetadata.addCustomMetadata(customKeyValDTOArrayList.get(i).key, customKeyValDTOArrayList.get(i).val);
                }
            }
            contentMetadata.setAddress(streetName, city, state, country, postalCode);
            contentMetadata.setLocation(lat, lon);
            contentMetadata.setPrice(price, currencyType);
            contentMetadata.setProductBrand(productBrand);
            contentMetadata.setProductCategory(productCategory);
            contentMetadata.setProductName(productName);
            contentMetadata.setProductCondition(productCondition);
            contentMetadata.setProductVariant(productVariant);
            contentMetadata.setQuantity(productQuantity);
            contentMetadata.setSku(sku);
            contentMetadata.setContentSchema(branchContentSchema);
            for (int i = 0; i < keywordArrayList.size(); i++) {
                if(keywordArrayList.get(i) != null && keywordArrayList.get(i).length() > 0) {
                    buo.addKeyWord(keywordArrayList.get(i));
                }
            }
            buo.setContentMetadata(contentMetadata);


            BranchEvent be = new BranchEvent(branchStandardEvent);
            be.setAffiliation(affiliation);
            be.setCoupon(couponCode);
            be.setCurrency(currencyType);
            be.setDescription(description);
            be.setShipping(shipping);
            be.setTax(tax);
            be.setRevenue(revenue);
            be.setSearchQuery(searchQuery);
            for (int i = 0; i < keyValDTOArrayList.size(); i++) {
                if(keyValDTOArrayList.get(i).key != null &&
                        keyValDTOArrayList.get(i).key.length() > 0 &&
                        keyValDTOArrayList.get(i).val != null &&
                        keyValDTOArrayList.get(i).val.length() > 0) {
                    be.addCustomDataProperty(keyValDTOArrayList.get(i).key, keyValDTOArrayList.get(i).val);
                }
            }
            be.addContentItems(buo);
            be.logEvent(context);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
