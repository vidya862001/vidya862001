package com.overstock.partnergateway.webservice.integration.base;

import com.overstock.datatypes.Money;
import com.overstock.model.Code;
import com.overstock.model.Id;
import com.overstock.partnergateway.api.SalesOrderLineType;
import com.overstock.partnergateway.core.common.codes.Barcode;
import com.overstock.partnergateway.core.common.codes.CarrierTypeCode;
import com.overstock.partnergateway.core.common.codes.SalesChannelOrderNumber;
import com.overstock.partnergateway.core.common.codes.SalesOrderShipMethodCode;
import com.overstock.partnergateway.core.common.codes.SupplierName;
import com.overstock.partnergateway.core.common.codes.SupplierSku;
import com.overstock.partnergateway.core.common.codes.WarehouseName;
import com.overstock.partnergateway.core.common.codes.WarehouseSku;
import com.overstock.partnergateway.core.common.ids.RetailChannelId;
import com.overstock.partnergateway.core.common.ids.RetailerId;
import com.overstock.partnergateway.core.common.ids.SalesChannelId;
import com.overstock.partnergateway.core.common.ids.SalesOrderId;
import com.overstock.partnergateway.core.common.ids.SupplierId;
import com.overstock.partnergateway.core.common.ids.WarehouseId;
import com.overstock.partnergateway.core.common.ids.WarehouseItemId;
import com.overstock.partnergateway.core.item.api.Item;
import com.overstock.partnergateway.core.item.api.Item.Status;
import com.overstock.partnergateway.core.partner.api.Address;
import com.overstock.partnergateway.core.salesorder.api.SalesChannel;
import com.overstock.partnergateway.core.salesorder.api.SalesOrder;
import com.overstock.partnergateway.core.salesorder.api.SalesOrderLine;
import com.overstock.partnergateway.core.supplier.api.Supplier;
import com.overstock.partnergateway.core.warehouse.api.SupplierWarehouse;
import com.overstock.partnergateway.core.warehouse.api.Warehouse;
import com.overstock.partnergateway.core.warehouse.api.WarehouseItem;
import com.overstock.partnergateway.inventory.coord.jdbc.model.Inventory;
import com.overstock.partnergateway.webservice.integration.data.model.RetailChannel;
import com.overstock.partnergateway.webservice.integration.data.service.AddressDataService;
import com.overstock.partnergateway.webservice.integration.data.service.InventoryDataService;
import com.overstock.partnergateway.webservice.integration.data.service.ItemDataService;
import com.overstock.partnergateway.webservice.integration.data.service.RetailChannelDataService;
import com.overstock.partnergateway.webservice.integration.data.service.RetailerDataService;
import com.overstock.partnergateway.webservice.integration.data.service.SalesOrderDataService;
import com.overstock.partnergateway.webservice.integration.data.service.SalesOrderLineDataService;
import com.overstock.partnergateway.webservice.integration.data.service.SupplierDataService;
import com.overstock.partnergateway.webservice.integration.data.service.SupplierWarehouseDataService;
import com.overstock.partnergateway.webservice.integration.data.service.WarehouseDataService;
import com.overstock.partnergateway.webservice.integration.data.service.WarehouseItemDataService;
import com.overstock.partnergateway.webservice.integration.setup.AddressBuilder;

public abstract class BaseTestDataSetup extends BaseTestConfigSetup {

  protected static SupplierDataService supplierDataService = new SupplierDataService();
  protected static RetailerDataService retailerDataService = new RetailerDataService();
  protected static RetailChannelDataService retailChannelDataService = new RetailChannelDataService();
  protected static AddressDataService addressDataService = new AddressDataService();
  protected static WarehouseDataService warehouseDataService = new WarehouseDataService();
  protected static SupplierWarehouseDataService supplierWarehouseDataService = new SupplierWarehouseDataService();
  protected static ItemDataService itemDataService = new ItemDataService();
  protected static WarehouseItemDataService warehouseItemDataService = new WarehouseItemDataService();
  protected static InventoryDataService inventoryDataService = new InventoryDataService();
  protected static SalesOrderDataService salesOrderDataService = new SalesOrderDataService();
  protected static SalesOrderLineDataService salesOrderLineDataService = new SalesOrderLineDataService();
  
  
  public Supplier createSupplier() {
    Supplier s = new Supplier();
    s.setId(new Id<SupplierId>(10000001));
    s.setSupplierName(new Code<SupplierName>("Supplier Test Name"));
    s.setDisplayName("Supplier Test Display Name");
    s.setStatus(Supplier.Status.ACTIVE);
    s = supplierDataService.create(s);
    return s;
  }
  
  public Id<RetailerId> getRetailerId(String retailerName){
    return retailerDataService.getRetailerIdbyName(retailerName);
  }
  
  public RetailChannel createRetailChannel(Id<SupplierId> sid, Id<RetailerId> rid){
    RetailChannel rc = new RetailChannel();
    rc.setSupplierId(sid);
    rc.setRetailerId(rid);
    rc.setRetailChannelCode("TestCode");
    rc = retailChannelDataService.create(rc);
    return rc;
  }
  
  public Address createAddress(){
    Address a = new Address()
    .withAddressee("Test Address Name")
    .withLineOne("Test Address Line One")
    .withCity("Test Address City")
    .withState("UT")
    .withCountryCode("US")
    .withPostalCode("84101")
    .withPhoneNumber("1111111111");
    a = addressDataService.create(a);
    return a;
  }
  
  public Warehouse createWarehouse(Address a, Warehouse.WarehouseType type){
    Warehouse w =  new Warehouse()
    .withStatus(Warehouse.Status.ACTIVE)
    .withContactAddress(new AddressBuilder().build())
    .withDisplayName("Test Warehouse Display Name")
    .withName(new Code<WarehouseName>("Test Warehouse Name"))
    .withType(type);
    w.setContactAddress(a);
    w = warehouseDataService.create(w);
    return w;
  }
  
  public Warehouse createWarehouse(Address a, Warehouse.WarehouseType type, String displayName, String name){
    Warehouse w =  new Warehouse()
    .withStatus(Warehouse.Status.ACTIVE)
    .withContactAddress(new AddressBuilder().build())
    .withDisplayName(displayName)
    .withName(new Code<WarehouseName>(name))
    .withType(type);
    w.setContactAddress(a);
    w = warehouseDataService.create(w);
    return w;
  }

  public SupplierWarehouse createSupplierWarehouse(Id<SupplierId> sid, Id<WarehouseId> wid){
    SupplierWarehouse sw = new SupplierWarehouse();
    sw.setSupplierId(sid);
    sw.setWarehouseId(wid);
    sw = supplierWarehouseDataService.create(sw);
    return sw;
  }
  
  public Item createItem(Id<SupplierId> sid) {
    
    Item item = new Item();
    item.setName("Test Item Name");
    item.setSupplierId(sid);
    item.setSupplierSku(new Code<SupplierSku>("TestSupplierSku"));
    item.setDescription("Test Item Description");
    item.setReceivingThreshold(1);
    item.setLtl(false);
    item.setMfgPartNumber("TestMfgPartNumber");
    item.setQtyOfSellableUnit(1);
    item.setReplacementCost(new Money(1));
    item.setUpc("11223344");
    item.setStatus(Status.ACTIVE);
    
    item = itemDataService.create(item);
    return item;
  }
  
  public WarehouseItem createWarehouseItem(Warehouse w, SupplierWarehouse sw, Item i){
    
    WarehouseItem wi = new WarehouseItem();
    wi.setWarehouse(w);
    wi.setItem(i);
    wi.setWarehouseSku(new Code<WarehouseSku>("TestWarehouseItemSku"));
    wi.setStatus(WarehouseItem.Status.ACCEPTED);
    wi.setBarcode(new Code<Barcode>("TestBarcode"));
    wi = warehouseItemDataService.create(wi, sw);
    return wi;
  }
  
  public Inventory createInventory(Id<WarehouseItemId> wid){
    Inventory inv = new Inventory();
    inv.setWarehouseItemId(wid);
    inv.setQuantityOnHand(10);
    inv.setQuantityOnHold(5);
    inv.setQuantityReserved(2);
    inv = inventoryDataService.create(inv);
    return inv;
  }
  
  public SalesOrder createSalesOrder(Address a, Id<SupplierId> sid, Id<RetailChannelId> rcid, Id<RetailerId> rid) {
    SalesOrder so = new SalesOrder();
    so.setSalesChannelOrderNumber(new Code<SalesChannelOrderNumber>("TestSalesChannelOrderNumber"));
    so.setShipToAddress(a);
    so.setShipMethod(new Code<SalesOrderShipMethodCode>("GROUND"));
    so.setStatus(SalesOrder.Status.PENDING);
    //so.setTransactionDate(transactionDate);//sysdate
    so.setSupplierId(sid);
    SalesChannel sc = new SalesChannel();
    sc.setId(new Id<SalesChannelId>(rid.longValue()));
    so.setSalesChannel(sc);
    so.setCarrierTypeCode(CarrierTypeCode.LTL);
    so.setRetailOrderNumber("TestRetailOrderNumber");
    so.setRetailChannelId(rcid);
    so =salesOrderDataService.create(so);
    return so;
  }
  
  public SalesOrderLine createSalesOrderLine(Id<SalesOrderId> soid) {
    
    SalesOrderLine sol = new SalesOrderLine();
    sol.setQuantity(10);
    sol.setStatus(SalesOrderLine.Status.ENTERED);
    sol = salesOrderLineDataService.create(sol, soid);
    return sol;
  }

}
