package com.overstock.inventorymanagement.legacy;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.pojomatic.Pojomatic;
import org.pojomatic.diff.Differences;

import com.google.common.collect.ImmutableList;
import com.overstock.datatypes.Money;
import com.overstock.inventorymanagement.api.dto.InventoryAddress;
import com.overstock.inventorymanagement.api.dto.InvoiceItemQtyLocation;
import com.overstock.inventorymanagement.api.dto.LocationInventoriesByInvoiceItem;
import com.overstock.inventorymanagement.api.dto.LocationInventoriesByInvoiceItems;
import com.overstock.inventorymanagement.api.dto.LocationInventoriesByProductOption;
import com.overstock.inventorymanagement.api.dto.LocationInventory;
import com.overstock.inventorymanagement.api.dto.ProductCountryQtyResponses;
import com.overstock.inventorymanagement.api.dto.ProductOptionCountryQtyResponses;
import com.overstock.inventorymanagement.api.dto.ProductOptionSourceCountry;
import com.overstock.inventorymanagement.api.dto.ProductWithOptionsQtyResponses;
import com.overstock.inventorymanagement.api.dto.ResCommitWithInvoiceItemIdsResponse;
import com.overstock.inventorymanagement.api.dto.SellableInventory;
import com.overstock.inventorymanagement.api.dto.SellableInventoryGraphs;
import com.overstock.inventorymanagement.api.exceptions.BatchInventoryManagementException;
import com.overstock.inventorymanagement.api.exceptions.CannotRetrieveInvSources;
import com.overstock.inventorymanagement.api.exceptions.CommittingToDifferentCountryException;
import com.overstock.inventorymanagement.api.exceptions.DuplicateProductOptionRequestException;
import com.overstock.inventorymanagement.api.exceptions.DuplicateTransactionException;
import com.overstock.inventorymanagement.api.exceptions.InvalidReservationQuantityException;
import com.overstock.inventorymanagement.api.exceptions.InventoryManagementException;
import com.overstock.inventorymanagement.api.exceptions.InvoiceItemNotExistException;
import com.overstock.inventorymanagement.api.exceptions.LegacyQuantityException;
import com.overstock.inventorymanagement.api.exceptions.OutOfInventoryException;
import com.overstock.inventorymanagement.api.exceptions.ProductDoesNotExistException;
import com.overstock.inventorymanagement.api.exceptions.ReservationCanceledException;
import com.overstock.inventorymanagement.api.exceptions.ReservationCommittedException;
import com.overstock.inventorymanagement.api.exceptions.ReservationIdInvalidException;
import com.overstock.inventorymanagement.api.exceptions.VendorDoesntExistException;
import com.overstock.inventorymanagement.api.exceptions.WarehouseCountryCodeChangeException;
import com.overstock.inventorymanagement.api.exceptions.WarehouseDoesntExistException;
import com.overstock.inventorymanagement.api.exceptions.WarehouseLocationAlreadyExistsException;
import com.overstock.inventorymanagement.api.exceptions.WarehouseLocationDoesntExistException;
import com.overstock.inventorymanagement.api.exceptions.WarehouseNameAlreadyExistsException;
import com.overstock.inventorymanagement.id.CountryCode2Char;
import com.overstock.inventorymanagement.id.LocationId;
import com.overstock.inventorymanagement.id.OperatorType;
import com.overstock.inventorymanagement.id.WarehouseCode;
import com.overstock.model.Code;
import com.overstock.model.Id;
import com.overstock.model.codes.CountryCode;
import com.overstock.model.codes.FullSku;
import com.overstock.model.ids.ProductOptionId;
import com.overstock.model.ids.VendorId;

import junit.framework.TestCase;

// import com.overstock.inventorymanagement.model.IniResResponse;
// import com.overstock.inventorymanagement.model.InventoryProduct;
// import com.overstock.inventorymanagement.model.InvoiceItemQtyLocation;
// import com.overstock.inventorymanagement.model.ProductCountryQtyResponse;
// import com.overstock.inventorymanagement.model.ProductWithOptionsQtyResponse;
// import com.overstock.inventorymanagement.model.VendorType;
// import com.overstock.inventorymanagement.model.graph.SellableInventoryGraph;

public class LegacyTranslatorTest extends TestCase {
  protected final Logger logger = Logger.getLogger(LegacyTranslatorTest.class);

  private final LegacyTranslator legacyTranslator = new LegacyTranslator();
  LegacyTranslator translator = new LegacyTranslator();

  public void testSafeToString() throws Exception {
    assertTrue(null == translator.safeToString(null));
    assertEquals("123", translator.safeToString(123));
  }

  protected String safeToString(final Object o) {
    return legacyTranslator.safeToString(o);
  }

  protected Long translateIdToLong(final Id<?> o) {
    return legacyTranslator.translateIdToLong(o);
  }

  protected <T> Id<T> translateLongToId(final Long id) {
    return legacyTranslator.translateLongToId(id);
  }

  protected <T> Code<T> translateStringToCode(final String code) {
    return legacyTranslator.translateStringToCode(code);
  }

  protected String translateCodeToString(Code<?> code) {
    return legacyTranslator.translateCodeToString(code);
  }

  protected BigDecimal translateMoneyToBigDecimal(final Money m) {
    return legacyTranslator.translateMoneyToBigDecimal(m);
  }

  protected LocationInventory translateLegacyLocationInventory(
    final com.overstock.inventorymanagement.model.LocationInventory input) {
    return legacyTranslator.translateLegacyLocationInventory(input);
  }

  protected com.overstock.inventorymanagement.model.LocationInventory translateLocationInventory(
    final LocationInventory input) {
    return legacyTranslator.translateLocationInventory(input);
  }

  protected List<LocationInventory> translateLegacyLocationInventories(
    final List<com.overstock.inventorymanagement.model.LocationInventory> locationInventories) {
    return legacyTranslator.translateLegacyLocationInventories(locationInventories);
  }

  protected com.overstock.arch.model.address.Address translateInventoryAddress(final InventoryAddress
                                                                                 inventoryAddress) {
    return legacyTranslator.translateInventoryAddress(inventoryAddress);
  }

  protected com.overstock.inventorymanagement.model.ProductOptionSourceCountry
  translateProductOptionSourceCountry(
    final ProductOptionSourceCountry input) {
    return legacyTranslator.translateProductOptionSourceCountry(input);
  }

  protected ProductOptionSourceCountry
  translateLegacyProductOptionSourceCountry(
    final com.overstock.inventorymanagement.model.ProductOptionSourceCountry input) {
    return legacyTranslator.translateLegacyProductOptionSourceCountry(input);
  }

  protected List<com.overstock.inventorymanagement.model.ProductOptionSourceCountry> translateProductOptionSourceCountries(
    final List<com.overstock.inventorymanagement.api.dto.ProductOptionSourceCountry> productOptionSourceCountries) {
    return legacyTranslator.translateProductOptionSourceCountries(productOptionSourceCountries);
  }

  protected com.overstock.inventorymanagement.model.InventoryProductOption translateInventoryProductOption(
    final Long productOption) {
    return legacyTranslator.translateInventoryProductOption(productOption);
  }

  protected <T> List<Id<T>> translateIds(Class<T> marker, Collection<Long> invoiceItemIds) {
    return legacyTranslator.translateLongsToIds(marker, invoiceItemIds);
  }

  protected List<Id<ProductOptionId>> translateProductOptionIds(Collection<Long> productOptionIds) {
    return legacyTranslator.translateProductOptionIds(productOptionIds);
  }

  protected LocationInventoriesByProductOption translateLegacyListOfLocationInventoriesByProductOption(
    com.overstock.inventorymanagement.model.LocationInventoriesByProductOption input) {
    return legacyTranslator.translateLegacyListOfLocationInventoriesByProductOption(input);
  }

  protected List<LocationInventoriesByProductOption> translateLegacyListOfLocationInventoriesByProductOption(
    final List<com.overstock.inventorymanagement.model.LocationInventoriesByProductOption> sources) {
    return legacyTranslator.translateLegacyListOfLocationInventoriesByProductOption(sources);
  }

  protected LocationInventoriesByInvoiceItems translateLegacyListOfLocationInventoriesByInvoiceItems(
    List<com.overstock.inventorymanagement.model.LocationInventoriesByInvoiceItem> sourcesByInvoiceItems) {
    return legacyTranslator.translateLegacyListOfLocationInventoriesByInvoiceItems(sourcesByInvoiceItems);
  }

  protected LocationInventoriesByInvoiceItem translateLegacyLocationInventoriesByInvoiceItem(
    com.overstock.inventorymanagement.model.LocationInventoriesByInvoiceItem input) {
    return legacyTranslator.translateLegacyLocationInventoriesByInvoiceItem(input);
  }

  protected ProductOptionCountryQtyResponses translateProductOptionCountryResponses(
    List<com.overstock.inventorymanagement.model.PrimeProductOptionCountryQtyResponse> sellableInventories) {
    return legacyTranslator.translatePrimeProductOptionCountryQtyResponses(sellableInventories);
  }

  protected List<com.overstock.inventorymanagement.model.InventoryProductOption> translateListOfInventoryProductOptions(
    List<Long> inventoryProductOptions) {
    return legacyTranslator.translateListOfInventoryProductOptions(inventoryProductOptions);
  }

  protected List<com.overstock.inventorymanagement.model.InventoryProduct> translateListOfInventoryProduct(
    List<Long> inventoryProducts) {
    return legacyTranslator.translateListOfInventoryProduct(inventoryProducts);
  }

  protected SellableInventoryGraphs translateSellableInventoryGraphs(
    final List<com.overstock.inventorymanagement.model.graph.PrimeSellableInventoryGraph> sellableInventoryGraphs) {
    return legacyTranslator.translateSellableInventoryGraphs(sellableInventoryGraphs);
  }

  public List<SellableInventory> translateLegacyListOfSellableInventories(
    List<com.overstock.inventorymanagement.model.graph.PrimeSellableInventory> sellableInventories) {
    return legacyTranslator.translateLegacyListOfSellableInventories(sellableInventories);
  }

  protected ResCommitWithInvoiceItemIdsResponse translateLegacyListOfIniResResponses(
    final List<com.overstock.inventorymanagement.model.IniResResponse> iniResResponses) {
    return legacyTranslator.translateLegacyListOfIniResResponses(iniResResponses);
  }

  protected List<com.overstock.inventorymanagement.model.InvoiceItemQtyLocation> translateListOfInvoiceItemQtyLocations(
    List<InvoiceItemQtyLocation> invoiceItemQtyLocations) {
    return legacyTranslator.translateListOfInvoiceItemQtyLocations(invoiceItemQtyLocations);
  }

  protected ProductCountryQtyResponses translateLegacyListOfProductCountryQtyResponses(
    List<com.overstock.inventorymanagement.prime.service.sellable.ProductCountryQtyResponse> sellableProductInventories) {
    return legacyTranslator.translateLegacyListOfProductCountryQtyResponses(sellableProductInventories);
  }

  protected ProductWithOptionsQtyResponses translateLegacyProductWithOptionsQtyResponses(
    List<com.overstock.inventorymanagement.model.PrimeProductWithOptionsQtyResponse> sellableOptionInventoriesForProducts) {
    return legacyTranslator.translateLegacyProductWithOptionsQtyResponses(sellableOptionInventoriesForProducts);
  }
//  protected Long translateLongToId(final Id<?> o) {
//    if (o != null) {
//      return o.longValue();
//    }
//    else {
//      return null;
//    }
//  }

  interface TestId {
  }

  public void testTranslateIdToLong() throws Exception {
    final long LID = 12345678L;
    final Id<TestId> id = Id.<TestId>factory().create(LID);

    assertTrue(LID == translator.translateIdToLong(id));
  }

//  protected <T> Id<T> translateLongToId(final Long id) {
//    return Id.<T>factory().makeFromLong(id);
//  }

  public void testTranslateLongToId() throws Exception {
    final long LID = 12345678L;
    final Id<TestId> id = Id.<TestId>factory().create(LID);

    assertTrue(LID == id.longValue());
  }

//
//  protected <T> Code<T> translateStringToCode(final String code) {
//    return Code.<T>factory().create(code);
//  }
//

  interface TestCode {
  }

  public void testTranslateCode() throws Exception {
    final String SCODE = "thisisacode";

    final Code<TestCode> code = translator.translateStringToCode(SCODE);

    assertEquals(SCODE, code.toString());
  }

  public void testTranslateMoneyToBigDecimal() throws Exception {
    final Money MONEY = new Money(1234567L);
    final BigDecimal expected = new BigDecimal("12345.67");
    final BigDecimal actual = translator.translateMoneyToBigDecimal(MONEY);

    assertEquals(expected, actual);

    assertTrue(null == translator.translateMoneyToBigDecimal(null));
  }

  static final Date LAST_VENDOR_UPDATE_DATE = new Date();
  static final Long LOCATION_ID = 11111L;
  static final Long VENDOR_ID = 22222L;
  static final Long PRODUCT_OPTION_ID = 33333L;
  static final String FULL_SKU = "123456-00-00";
  static final String COUNTRY_CODE = "US";
  static final String POSTAL_CODE = "84092";
  static final String VENDOR_TYPE = com.overstock.inventorymanagement.model.VendorType.CORE_CASTLE.name();
  //static final Money COST = new Money(123456L);
  static final BigDecimal COST = new BigDecimal("123456.56");
  static final int QUANTITY = 2;
  static final boolean SOURCEABLE = true;
  static final boolean SHIP3PB = true;
  static final String OPERATOR_TYPE = OperatorType.OVERSTOCK.name();
  static final boolean WAREHOUSE_LOCATION = true;
  static final String WAREHOUSE_CODE = "CASTLE";

//  public LocationInventory(
// Id<LocationId> locationId,
//                           Id<VendorId> vendorId,
//                           Id<ProductOptionId> productOptionId,
//                           Code<FullSku> fullSku,
//                           Code<CountryCode2Char> countryCode,
//                           String postalCode,
//                           VendorType vendorType,
//                           Money cost,
//                           int qty,
//                           boolean sourceable,
//                           boolean ship3pb,
//                           Date lastVendorUpdateDate,
//                           OperatorType operatorType,
//                           boolean warehouseLocation,
//                           Code<WarehouseCode> warehouseCode) {
//    this.locationId = locationId;
//    this.vendorId = vendorId;
//    this.productOptionId = productOptionId;
//    this.fullSku = fullSku;
//    this.countryCode = countryCode;
//    this.vendorType = vendorType;
//    this.cost = cost;
//    this.postalCode = postalCode;
//    this.qty = qty;
//    this.sourceable = sourceable;
//    this.ship3pb = ship3pb;
//    this.lastVendorUpdateDate = lastVendorUpdateDate;
//    this.operatorType = operatorType;
//    this.warehouseLocation = warehouseLocation;
//    this.warehouseCode = warehouseCode;
//  }

  public void testTranslateLegacyLocationInventory() throws Exception {
    final Id<LocationId> locationId = translateLongToId(LOCATION_ID);
    final Id<VendorId> vendorId = translateLongToId(VENDOR_ID);
    final Id<ProductOptionId> productOptionId = translateLongToId(PRODUCT_OPTION_ID);
    final Code<FullSku> fullSku = translateStringToCode(FULL_SKU);
    final Code<CountryCode2Char> countryCode = translateStringToCode("US");
    final String postalCode = POSTAL_CODE;
    final com.overstock.inventorymanagement.model.VendorType vendorType = com.overstock.inventorymanagement.model.VendorType.valueOf(VENDOR_TYPE);
    final Money cost = new Money(COST);
    final int qty = QUANTITY;
    boolean sourceable = true;
    boolean ship3pb = true;
    Date lastVendorUpdateDate = LAST_VENDOR_UPDATE_DATE;
    OperatorType operatorType = OperatorType.valueOf(OPERATOR_TYPE);
    boolean warehouseLocation = true;
    Code<WarehouseCode> warehouseCode = translateStringToCode(WAREHOUSE_CODE);
    String locationCountryCode = LOCATION_COUNTRY_CODE;

    final com.overstock.inventorymanagement.model.LocationInventory legacy =

      new com.overstock.inventorymanagement.model.LocationInventory(
        locationId,
        vendorId,
        productOptionId,
        fullSku,
        countryCode,
        postalCode,
        vendorType,
        cost,
        qty,
        sourceable,
        ship3pb,
        lastVendorUpdateDate,
        operatorType,
        warehouseLocation,
        warehouseCode);

    final LocationInventory expected = new LocationInventory(
      LOCATION_ID /* Long locationId */,
      VENDOR_ID /* Long vendorId */,
      PRODUCT_OPTION_ID /* Long productOptionId */,
      FULL_SKU /* String fullSku */,
      COUNTRY_CODE /* String countryCode */,
      POSTAL_CODE /* String postalCode */,
      VENDOR_TYPE /* com.overstock.inventorymanagement.model.VendorType vendorType */,
      COST /* BigDecimal cost */,
      QUANTITY /* int qty */,
      SOURCEABLE /* boolean sourceable */,
      SHIP3PB /* boolean ship3pb */,
      LAST_VENDOR_UPDATE_DATE /* Date lastVendorUpdateDate */,
      OPERATOR_TYPE /* OperatorType operatorType */,
      WAREHOUSE_LOCATION /* boolean warehouseLocation */,
      WAREHOUSE_CODE /* String warehouseCode */,
      null /* String locationCountryCode */);

    final LocationInventory actual = translateLegacyLocationInventory(legacy);
    final Differences diff = Pojomatic.diff(expected, actual);

    logger.info("diff: " + diff);

    assertEquals(expected, actual);
  }

//  protected com.overstock.arch.model.address.Address translateLongToId(final InventoryAddress inventoryAddress) {
//    return new com.overstock.arch.model.address.Address() {
//
//      @Override
//      public String getLineOne() {
//        return inventoryAddress.getLineOne();
//      }
//
//      public String getLineTwo() {
//        return inventoryAddress.getLineTwo();
//      }
//
//      public String getLineThree() {
//        return inventoryAddress.getLineThree();
//      }
//
//      public String getCity() {
//        return inventoryAddress.getCity();
//      }
//
//      public String getState() {
//        return inventoryAddress.getState();
//      }
//
//      public String getCountryCode() {
//        return inventoryAddress.getCountryCode();
//      }
//
//      public String getPostalCode() {
//        return inventoryAddress.getPostalCode();
//      }
//
//      @Override
//      public AddressVerificationStatus getAddressVerificationStatus() {
//        return AddressVerificationStatus.NOT_VERIFIED;
//      }
//    };
//  }
//public InventoryAddress(String lineOne,
//                        String lineTwo,
//                        String lineThree,
//                        String city,
//                        String state,
//                        String countryCode,
//                        String postalCode) {
//
//  Preconditions.checkNotNull(countryCode);
//  this.lineOne = lineOne;
//  this.lineTwo = lineTwo;
//  this.lineThree = lineThree;
//  this.city = city;
//  this.state = state;
//  this.countryCode = countryCode;
//  this.postalCode = postalCode;
//}

  final String ADDRESS_LINE_1 = "Address Line 1";
  final String ADDRESS_LINE_2 = "Address Line 2";
  final String ADDRESS_LINE_3 = "Address Line 3";
  final String ADDRESS_CITY = "Salt Lake City";
  final String ADDRESS_STATE = "UT";
  final String ADDRESS_COUNTRY_CODE = "US";
  final String ADDRESS_POSTAL_CODE = "84092";
  final String LOCATION_COUNTRY_CODE = "CA";

  static <T> boolean safeEquals(T a, T b) {
    return (((a != null) && a.equals(b)) ||
            (a == b));
  }

  static boolean addressEquals(com.overstock.arch.model.address.Address a,
                               com.overstock.arch.model.address.Address b) {

    return (
      safeEquals(a.getLineOne(), b.getLineOne()) &&
      safeEquals(a.getLineTwo(), b.getLineTwo()) &&
      safeEquals(a.getLineThree(), b.getLineThree()) &&
      safeEquals(a.getCity(), b.getCity()) &&
      safeEquals(a.getLineThree(), b.getLineThree()) &&
      safeEquals(a.getCountryCode(), b.getCountryCode()) &&
      safeEquals(a.getPostalCode(), b.getPostalCode()));
  }

  public void testTranslateInventoryAddress() throws Exception {
    final InventoryAddress address = new InventoryAddress(
      ADDRESS_LINE_1,
      ADDRESS_LINE_2,
      ADDRESS_LINE_3,
      ADDRESS_CITY,
      ADDRESS_STATE,
      ADDRESS_COUNTRY_CODE,
      ADDRESS_POSTAL_CODE);

    final com.overstock.arch.model.address.Address expected =
      new com.overstock.inventorymanagement.model.InventoryAddress(
        ADDRESS_LINE_1,
        ADDRESS_LINE_2,
        ADDRESS_LINE_3,
        ADDRESS_CITY,
        ADDRESS_STATE,
        ADDRESS_COUNTRY_CODE,
        ADDRESS_POSTAL_CODE);

    com.overstock.arch.model.address.Address actual = translateInventoryAddress(address);

    assertTrue(addressEquals(actual, expected));
  }

//  protected List<LocationInventory> translateLegacyLocationInventories(
//    final List<com.overstock.inventorymanagement.model.LocationInventory> locationInventories) {
//    return Lists.newArrayList(
//      Collections2.transform(locationInventories,
//                             new Function<com.overstock.inventorymanagement.model.LocationInventory, LocationInventory>() {
//                               @Override
//                               public LocationInventory apply(
//                                 @Nullable com.overstock.inventorymanagement.model.LocationInventory input) {
//                                 return translateLongToId(input);
//                               }
//                             }));
//  }
//
//public LocationInventory(Id<LocationId> locationId, Id<VendorId> vendorId, Id<ProductOptionId> productOptionId,
//                         Code<FullSku> fullSku, Code<CountryCode2Char> countryCode, String postalCode,
//                         com.overstock.inventorymanagement.model.VendorType vendorType,
//                         Money cost, int qty, boolean sourceable, boolean ship3pb, Date lastVendorUpdateDate,
//                         OperatorType operatorType, boolean warehouseLocation, Code<WarehouseCode> warehouseCode,
//                         String locationCountryCode) {
//
//  this.locationId = locationId;
//  this.vendorId = vendorId;
//  this.productOptionId = productOptionId;
//  this.fullSku = fullSku;
//  this.countryCode = countryCode;
//  this.vendorType = vendorType;
//  this.cost = cost;
//  this.postalCode = postalCode;
//  this.qty = qty;
//  this.sourceable = sourceable;
//  this.ship3pb = ship3pb;
//  this.lastVendorUpdateDate = lastVendorUpdateDate;
//  this.operatorType = operatorType;
//  this.warehouseLocation = warehouseLocation;
//  this.warehouseCode = warehouseCode;
//  //3pb changes
//  this.locationCountryCode = locationCountryCode;
//}
  public void testTranslateLegacyLocationInventories() throws Exception {

    final Id<LocationId> locationId = translateLongToId(LOCATION_ID);
    final Id<VendorId> vendorId = translateLongToId(VENDOR_ID);
    final Id<ProductOptionId> productOptionId = translateLongToId(PRODUCT_OPTION_ID);
    final Code<FullSku> fullSku = translateStringToCode(FULL_SKU);
    final Code<CountryCode2Char> countryCode = translateStringToCode("US");
    final String postalCode = POSTAL_CODE;
    final com.overstock.inventorymanagement.model.VendorType vendorType = com.overstock.inventorymanagement.model.VendorType.valueOf(VENDOR_TYPE);
    final Money cost = new Money(COST);
    final int qty = QUANTITY;
    final boolean sourceable = SOURCEABLE;
    final boolean ship3pb = SHIP3PB;
    final Date lastVendorUpdateDate = LAST_VENDOR_UPDATE_DATE;
    final OperatorType operatorType = OperatorType.valueOf(OPERATOR_TYPE);
    final boolean warehouseLocation = WAREHOUSE_LOCATION;
    final Code<WarehouseCode> warehouseCode = translateStringToCode(WAREHOUSE_CODE);
    final String locationCountryCode = LOCATION_COUNTRY_CODE;

    final com.overstock.inventorymanagement.model.LocationInventory input =
      new com.overstock.inventorymanagement.model.LocationInventory(
        locationId /* Id<LocationId> locationId */,
        vendorId /* Id<VendorId> vendorId */,
        productOptionId /* Id<ProductOptionId> productOptionId*/,
        fullSku /* Code<FullSku> fullSku*/,
        countryCode /* Code<CountryCode2Char> countryCode*/,
        postalCode /* String postalCode*/,
        vendorType/* com.overstock.inventorymanagement.model.VendorType vendorType*/,
        cost/* Money cost*/,
        qty/* int qty*/,
        sourceable/* boolean sourceable*/,
        ship3pb/* boolean ship3pb*/,
        lastVendorUpdateDate/* Date lastVendorUpdateDate*/,
        operatorType/* OperatorType operatorType*/,
        warehouseLocation/* boolean warehouseLocation*/,
        warehouseCode/* Code<WarehouseCode> warehouseCode*/);

    final LocationInventory expected =
      new LocationInventory(
        LOCATION_ID /*Long locationId*/,
        VENDOR_ID /*Long vendorId*/,
        PRODUCT_OPTION_ID /*Long productOptionId*/,
        FULL_SKU /*String fullSku*/,
        COUNTRY_CODE /*String countryCode*/,
        POSTAL_CODE /*String postalCode*/,
        VENDOR_TYPE /*String vendorType*/,
        COST /*BigDecimal cost*/,
        QUANTITY /*int qty*/,
        SOURCEABLE /*boolean sourceable*/,
        SHIP3PB /*boolean ship3pb*/,
        LAST_VENDOR_UPDATE_DATE /*Date lastVendorUpdateDate*/,
        OPERATOR_TYPE /*String operatorType*/,
        WAREHOUSE_LOCATION /*boolean warehouseLocation*/,
        WAREHOUSE_CODE /*String warehouseCode*/,
        null /* String locationCountryCode */);

    final LocationInventory actual = translateLegacyLocationInventories(
      ImmutableList.of(input)).get(0);

    Differences diff = Pojomatic.diff(actual, expected);

    logger.info("diff: " + diff);

    assertEquals(actual, expected);

    final com.overstock.inventorymanagement.model.LocationInventory roundTrip =
      translateLocationInventory(actual);

    diff = Pojomatic.diff(input, roundTrip);

    logger.info("diff: " + diff);

    assertEquals(input, roundTrip);
  }

  public void testTranslateProductOptionSourceCountry() throws Exception {

    final ProductOptionSourceCountry input =
      new ProductOptionSourceCountry(
        PRODUCT_OPTION_ID,
        COUNTRY_CODE);

    final com.overstock.inventorymanagement.model.ProductOptionSourceCountry actual =
      translateProductOptionSourceCountry(input);

    Id<ProductOptionId> apiProductOptionId = translateLongToId(PRODUCT_OPTION_ID);
    Code<CountryCode> countryCode=translateStringToCode(COUNTRY_CODE);
    com.overstock.inventorymanagement.model.InventoryProductOption inventoryProductOption =
      new com.overstock.inventorymanagement.model.InventoryProductOption(
        apiProductOptionId);

    final com.overstock.inventorymanagement.model.ProductOptionSourceCountry expected =
      new com.overstock.inventorymanagement.model.ProductOptionSourceCountry(
        inventoryProductOption,
        countryCode);

    assertEquals(expected,actual);

    final ProductOptionSourceCountry roundTrip =
      translateLegacyProductOptionSourceCountry(actual);

    assertEquals(input, roundTrip);

  }

//  protected List<com.overstock.inventorymanagement.model.ProductOptionSourceCountry>
//  translateProductOptionSourceCountries(final List<ProductOptionSourceCountry> productOptionSourceCountries) {
//    return Lists.newArrayList(Collections2.transform(productOptionSourceCountries, new
//      Function<ProductOptionSourceCountry, com.overstock
//        .inventorymanagement.model.ProductOptionSourceCountry>() {
//        @Override
//        public com.overstock.inventorymanagement.model.ProductOptionSourceCountry apply(
//          @Nullable ProductOptionSourceCountry input) {
//          return translateLongToId(input);
//        }
//      }));
//  }

  public void test() throws Exception {

  }

//  protected Code<CountryCode> translateLongToId(final String sourceCountryCode) {
//    return Code.<CountryCode>factory().create(sourceCountryCode);
//  }

  public void testTranslateStringToCodeCountryCode() throws Exception {

  }

//
//
//  protected com.overstock.inventorymanagement.model.InventoryProductOption translateLongToId(
//    final InventoryProductOption productOption) {
//    return new com.overstock.inventorymanagement.model.InventoryProductOption(
//      Id.<ProductOptionId>factory().makeFromLong(productOption.getProductOptionId()));
//  }
//

//  protected <T> List<Id<T>> translateIds(Class<T> marker, Collection<Long> invoiceItemIds) {
//    return Lists.newArrayList(
//      Collections2.transform(
//        invoiceItemIds,
//        new Function<Long, Id<T>>() {
//          @Override
//          public Id<T> apply(@Nullable Long input) {
//            return Id.<T>factory().makeFromLong(input);
//          }
//        }));
//  }

  public void testTranslateIds() throws Exception {

  }

//  protected List<Id<ProductOptionId>> translateProductOptionIds(Collection<Long> productOptionIds) {
//    return translateIds(ProductOptionId.class, productOptionIds);
//  }

  public void testTranslateProductOptionIds() throws Exception {

  }

//  protected LocationInventoriesByProductOption translateLongToId(
//    com.overstock.inventorymanagement.model.LocationInventoriesByProductOption input) {
//    return new LocationInventoriesByProductOption(input.getProductOptionId().longValue(),
//                                                  translateLegacyLocationInventories(input.getLocationInventories()));
//  }

  public void testTranslateLocationInventoriesByProductOption() throws Exception {

  }

//  protected LocationInventoriesByInvoiceItems translateLegacyListOfLocationInventoriesByInvoiceItems(
//    List<com.overstock.inventorymanagement.model.LocationInventoriesByInvoiceItem> sourcesByInvoiceItems) {
//    return new LocationInventoriesByInvoiceItems(
//      Lists.newArrayList(Collections2.transform(
//        sourcesByInvoiceItems,
//        new Function<com.overstock.inventorymanagement.model.LocationInventoriesByInvoiceItem,
//          LocationInventoriesByInvoiceItem>() {
//          @Override
//          public LocationInventoriesByInvoiceItem apply(
//            @Nullable com.overstock.inventorymanagement.model.LocationInventoriesByInvoiceItem input) {
//            return translateLongToId(input);
//          }
//        })));
//  }

  public void testTranslateLocationInventoriesByInvoiceItems() throws Exception {

  }

//  protected ProductOptionCountryQtyResponses translateProductOptionCountryResponses(
//    List<com.overstock.inventorymanagement.model.ProductOptionCountryQtyResponse> sellableInventories) {
//
//    return new ProductOptionCountryQtyResponses(translateProductOptionCountryQtyResponse(sellableInventories));
//  }

  public void testTranslateProductOptionCountryResponses() throws Exception {

  }

  //  protected List<com.overstock.inventorymanagement.model.InventoryProductOption> translateListOfInventoryProductOptions(
//    List<InventoryProductOption> inventoryProductOptions) {
//    return Lists.newArrayList(Collections2.transform(inventoryProductOptions,
//                                                     new Function<InventoryProductOption, com.overstock.inventorymanagement.model.InventoryProductOption>() {
//                                                       @Override
//                                                       public com.overstock.inventorymanagement.model.InventoryProductOption apply(
//                                                         @Nullable InventoryProductOption input) {
//                                                         return translateLongToId(input);
//                                                       }
//                                                     }));
//  }
  public void testTranslateInventoryProductOptions() throws Exception {

  }

//  protected List<com.overstock.inventorymanagement.model.InventoryProduct> translateListOfInventoryProduct(
//    List<InventoryProduct> inventoryProducts) {
//    return Lists.newArrayList(Collections2.transform(inventoryProducts,
//                                                     new Function<InventoryProduct, com.overstock.inventorymanagement.model.InventoryProduct>() {
//                                                       @Override
//                                                       public com.overstock.inventorymanagement.model.InventoryProduct apply(
//                                                         @Nullable InventoryProduct input) {
//                                                         return translateLongToId(input);
//                                                       }
//                                                     }));
//  }

  public void testTranslateInventoryProducts() throws Exception {

  }

//  protected SellableInventoryGraphs translateSellableInventoryGraphs(
//    final List<com.overstock.inventorymanagement.model.graph.SellableInventoryGraph> sellableInventoryGraphs) {
//    return new SellableInventoryGraphs(
//      Lists.newArrayList(Collections2.transform(sellableInventoryGraphs,
//                                                new Function<com.overstock.inventorymanagement.model.graph.SellableInventoryGraph, SellableInventoryGraph>() {
//                                                  @Override
//                                                  public SellableInventoryGraph apply(
//                                                    @Nullable
//                                                    com.overstock.inventorymanagement.model.graph.SellableInventoryGraph input) {
//                                                    return translateLongToId(input);
//                                                  }
//                                                })));
//  }

  public void testTranslateSellableInventoryGraphs() throws Exception {

  }

//  public List<SellableInventory> translateLegacyListOfSellableInventories(
//    List<com.overstock.inventorymanagement.model.graph.SellableInventory> sellableInventories) {
//
//    return Lists.newArrayList(
//      Collections2.transform(sellableInventories,
//                             new Function<com.overstock.inventorymanagement.model.graph.SellableInventory, SellableInventory>() {
//                               @Override
//                               public SellableInventory apply(
//                                 @Nullable com.overstock.inventorymanagement.model.graph.SellableInventory input) {
//                                 return translateLongToId(input);
//                               }
//                             }));
//  }

  public void testTranslateSellableInventories() throws Exception {

  }

//  protected ResCommitWithInvoiceItemIdsResponse translateLegacyListOfIniResResponses(
//    final List<com.overstock.inventorymanagement.model.IniResResponse> iniResResponses) {
//
//    return new ResCommitWithInvoiceItemIdsResponse(
//      Lists.newArrayList(
//        Collections2.transform(
//          iniResResponses,
//          new Function<com.overstock.inventorymanagement.model.IniResResponse, IniResResponse>() {
//            @Override
//            public IniResResponse apply(@Nullable com.overstock.inventorymanagement.model.IniResResponse input) {
//              return translateLongToId(input);
//            }
//          })));
//  }

  public void testTranslateIniResResponses() throws Exception {

  }

//  protected List<com.overstock.inventorymanagement.model.InvoiceItemQtyLocation> translateListOfInvoiceItemQtyLocations
//    (List<InvoiceItemQtyLocation>  invoiceItemQtyLocations) {
//
//    return Lists.newArrayList(Collections2.transform(invoiceItemQtyLocations,
//                                                     new Function<InvoiceItemQtyLocation, com.overstock.inventorymanagement.model.InvoiceItemQtyLocation>() {
//                                                       @Override
//                                                       public com.overstock.inventorymanagement.model.InvoiceItemQtyLocation apply(
//                                                         @Nullable InvoiceItemQtyLocation input) {
//                                                         Id<InvoiceItemId> invoiceItemId = translateLongToId(
//                                                           input.getInvoiceItemId());
//                                                         Id<LocationId> locationId = translateLongToId(input.getLocationId());
//
//                                                         return new com.overstock.inventorymanagement.model.InvoiceItemQtyLocation(
//                                                           invoiceItemId,
//                                                           locationId,
//                                                           input.getOnHandQuantityForOptimisticLock());
//                                                       }
//                                                     }));
//  }

  public void testTranslateInvoiceItemQtyLocations() throws Exception {

  }

//  protected ProductCountryQtyResponses translateLegacyListOfProductCountryQtyResponses(
//    List<com.overstock.inventorymanagement.model.ProductCountryQtyResponse> sellableProductInventories) {
//
//    final List<ProductCountryQtyResponse> responses = Lists.newArrayList(Collections2.transform(
//      sellableProductInventories,
//      new Function<com.overstock.inventorymanagement.model.ProductCountryQtyResponse, ProductCountryQtyResponse>() {
//        @Override
//        public ProductCountryQtyResponse apply(
//          @Nullable com.overstock.inventorymanagement.model.ProductCountryQtyResponse input) {
//          return translateLongToId(input);
//        }
//      }));
//
//    return new ProductCountryQtyResponses(responses);
//  }

  public void testTranslateProductCountryQtyResponses() throws Exception {

  }

//  protected ProductWithOptionsQtyResponses translateLegacyProductWithOptionsQtyResponses(
//    List<com.overstock.inventorymanagement.model.ProductWithOptionsQtyResponse> sellableOptionInventoriesForProducts) {
//
//    return new ProductWithOptionsQtyResponses(Lists.newArrayList(Collections2.transform(
//      sellableOptionInventoriesForProducts,
//      new Function<com.overstock.inventorymanagement.model.ProductWithOptionsQtyResponse, ProductWithOptionsQtyResponse>() {
//        @Override
//        public ProductWithOptionsQtyResponse apply(
//          @Nullable com.overstock.inventorymanagement.model.ProductWithOptionsQtyResponse input) {
//          return translateLongToId(input);
//        }
//      })));
//  }

  public void testTranslateProductWithOptionsQtyResponses() throws Exception {

  }
  
  private static class ExceptionMap {
    com.overstock.inventorymanagement.exceptions.InventoryManagementException legacyException;
    Class<? extends InventoryManagementException> newClass;
    String message;
    
    public ExceptionMap(com.overstock.inventorymanagement.exceptions.InventoryManagementException legacyException, Class<? extends InventoryManagementException> newClass) {
      this(legacyException, newClass, null);
    }
    
    public ExceptionMap(com.overstock.inventorymanagement.exceptions.InventoryManagementException legacyException, Class<? extends InventoryManagementException> newClass, String message) {
      this.legacyException = legacyException;
      this.newClass = newClass;
      this.message = message;
    }
  }
  
  public void testTranslateBatch() throws Exception {
    com.overstock.inventorymanagement.exceptions.BatchInventoryManagementException legacyException = new com.overstock.inventorymanagement.exceptions.BatchInventoryManagementException();
    
    ExceptionMap[] maps = new ExceptionMap[] {
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.CannotRetrieveInvSources("0"), CannotRetrieveInvSources.class, "0"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.CommittingToDifferentCountryException(), CommittingToDifferentCountryException.class),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.DuplicateProductOptionRequestException(), DuplicateProductOptionRequestException.class),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.DuplicateTransactionException("3"), DuplicateTransactionException.class, "3"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.InvalidReservationQuantityException("4"), InvalidReservationQuantityException.class, "4"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.InventoryManagementException("5"), InventoryManagementException.class, "5"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.InvoiceItemNotExistException("6"), InvoiceItemNotExistException.class, "6"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.LegacyQuantityException("7"), LegacyQuantityException.class, "7"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.OutOfInventoryException(), OutOfInventoryException.class),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.ProductDoesNotExistException("9"), ProductDoesNotExistException.class, "9"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.ReservationCanceledException(), ReservationCanceledException.class),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.ReservationCommittedException(), ReservationCommittedException.class),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.ReservationIdInvalidException(), ReservationIdInvalidException.class),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.VendorDoesntExistException("14"), VendorDoesntExistException.class, "14"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.WarehouseCountryCodeChangeException(), WarehouseCountryCodeChangeException.class),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.WarehouseDoesntExistException("16"), WarehouseDoesntExistException.class, "16"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.WarehouseLocationAlreadyExistsException("17"), WarehouseLocationAlreadyExistsException.class, "17"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.WarehouseLocationDoesntExistException("18"), WarehouseLocationDoesntExistException.class, "18"),
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.WarehouseNameAlreadyExistsException("19"), WarehouseNameAlreadyExistsException.class, "19"),
      
      //As of 10/27/2017, this is the only exception in the failSafeTranslate 
      new ExceptionMap(new com.overstock.inventorymanagement.exceptions.QuantityOptimisticLockException("10"), InventoryManagementException.class, "10"),
    };
    int i=0;
    for (ExceptionMap map : maps) {
      legacyException.put(new Id(i++), map.legacyException);
    }
    
    BatchInventoryManagementException exception = legacyTranslator.translateLegacyBatchInventoryManagementException(legacyException);
    final Map<Long, InventoryManagementException> exceptionMap = exception.getExceptionMap();
    for (int j = 0; j < maps.length; j++) {
      final InventoryManagementException inventoryManagementException = exceptionMap.get((long)j);
      final ExceptionMap map = maps[j];
      
      assertEquals(map.newClass, inventoryManagementException.getClass());
      assertEquals(map.message, inventoryManagementException.getMessage());
    }
    
  }
}
